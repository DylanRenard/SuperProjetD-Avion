package fr.ensim.superprojetavion.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.Model.CodeInfo;
import fr.ensim.superprojetavion.R;
import fr.ensim.superprojetavion.Service.DownloadImageTask;
import fr.ensim.superprojetavion.Service.SnowtamService;

public class SearchActivity extends AppCompatActivity {

    //airport info from result
    AirportInfo result;
    //snowtam to go to codeActivity
    CodeInfo snowtam;
    //list of favorite airports
    ArrayList<AirportInfo> favorisList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        super.setTitle(getString(R.string.searchName));

        //retreive favorite list from file
        importFavorisList();

        //retreive airport info from intent
        Intent i = getIntent();
        result = i.getParcelableExtra("result");
        result.setfavoris(false);

        //set flag picture
        ImageView flag = findViewById(R.id.flag);
        new DownloadImageTask(flag).execute(result.getFlag());

        //set icao text
        TextView oaciCode = findViewById(R.id.codeAirport);
        oaciCode.setText(result.getOaciCode());

        //set airport name text
        TextView airportName = findViewById(R.id.nameAirport);
        airportName.setText(result.getAirportName());

        //set airport description text
        TextView description = findViewById(R.id.description);
        description.setText("Latitude : " + result.getLatitude() + "\nLongitude : " + result.getLongitude());

        //button to add airport to or remove airport from favorite list
        final ImageButton favIcon = findViewById(R.id.favorisIcon);
        favIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(result.isfavoris()){
                    result.setfavoris(false);
                    favIcon.setImageResource(R.drawable.snow1);
                    String toastText = getString(R.string.removeFavToast);
                    Toast toast = Toast.makeText(SearchActivity.this, toastText, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    result.setfavoris(true);
                    favIcon.setImageResource(R.drawable.snow2);
                    String toastfav = getString(R.string.addFavoriteToast);
                    Toast toast = Toast.makeText(SearchActivity.this, toastfav, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //set favorite picture depending of airport already present in favorite list
        for (AirportInfo fav : favorisList){
            if(fav.getOaciCode().equals(result.getOaciCode())) {
                result.setfavoris(true);
                favIcon.setImageResource(R.drawable.snow2);
            }
        }

        ImageView phone = findViewById(R.id.phone);
        if(result.getPhoneNumber().length()<3) phone.setVisibility(View.GONE);
        //set phone number text
        TextView phoneNumber = findViewById(R.id.phoneNumber);
        phoneNumber.setText(result.getPhoneNumber());

        //button to go to codeActivity with the corresponding snowtam
        Button toSnowtam = findViewById(R.id.toSnowtam);
        toSnowtam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String snowtamInfo = null;
                        int i;

                        try{
                            for (i=0 ; i<response.length() ; i++) {
                                JSONObject info = response.getJSONObject(i);

                                if(info.getString("id").contains("SW")){
                                    snowtamInfo = info.getString("all");

                                    Log.w ("snowtam : ",snowtamInfo);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(snowtamInfo!=null) {
                            snowtam = new CodeInfo(snowtamInfo,result);
                            if(snowtam.getCode_date()!=null){
                                Intent intent = new Intent(SearchActivity.this, CodeActivity.class);
                                intent.putExtra("snowtam",snowtam);
                                intent.putExtra("airport",(Parcelable)result);
                                startActivity(intent);
                                onStop();
                                finish();
                            }
                            else{
                                String toastText = getString(R.string.invalidSnowtam);
                                Toast toast = Toast.makeText(SearchActivity.this, toastText, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                        else {
                            String toastText = getString(R.string.noSnowtamToast);
                            Toast toast = Toast.makeText(SearchActivity.this, toastText, Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String toastText = getString(R.string.noConnectionToast);
                        Toast toast = Toast.makeText(SearchActivity.this, toastText, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                };

                SnowtamService.searchSnowtam(result.getOaciCode(),responseListener,errorListener,SearchActivity.this);
            }
        });
    }

    //update favorite list when exiting the activity
    @Override
    protected void onStop(){
        super.onStop();

        boolean inList = false;

        for (AirportInfo fav : favorisList){
            if(fav.getOaciCode().equals(result.getOaciCode())) {
                inList = true;
                if(!result.isfavoris()) favorisList.remove(fav);
                break;
            }
        }
        if(result.isfavoris() && !inList) favorisList.add(result);

        saveFavorisList();
    }

    //make sure that favorite list is updated then finish the activity
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        onStop();
        finish();
    }

    //function to save favorite in file
    private void saveFavorisList() {
        FileOutputStream outputStream;
        ObjectOutputStream oos;
        try {
            outputStream = openFileOutput("favoris.txt", Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(favorisList);

            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //function to retreive favorite list from file
    private void importFavorisList() {
        try {
            FileInputStream fis = openFileInput("favoris.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            favorisList = (ArrayList<AirportInfo>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            favorisList = new ArrayList<AirportInfo>();
        } catch (IOException e) {
            e.printStackTrace();
            favorisList = new ArrayList<AirportInfo>();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            favorisList = new ArrayList<AirportInfo>();
        }
    }
}