package fr.ensim.superprojetavion.Activity;

import android.annotation.SuppressLint;
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

    AirportInfo result;
    CodeInfo snowtam;

    ArrayList<AirportInfo> favorisList;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        super.setTitle(getString(R.string.searchName));

        importFavorisList();

        Intent i = getIntent();
        result = i.getParcelableExtra("result");
        result.setfavoris(false);

        ImageView flag = findViewById(R.id.flag);
        new DownloadImageTask(flag).execute(result.getFlag());

        TextView oaciCode = findViewById(R.id.codeAirport);
        oaciCode.setText(result.getOaciCode());

        TextView airportName = findViewById(R.id.nameAirport);
        airportName.setText(result.getAirportName());

        TextView description = findViewById(R.id.description);
        description.setText("Latitude : " + result.getLatitude() + "\nLongitude : " + result.getLongitude());

        final ImageButton favIcon = findViewById(R.id.favorisIcon);
        favIcon.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if(result.isfavoris()){
                    result.setfavoris(false);
                    favIcon.setImageResource(0x0108000b);
                }
                else {
                    result.setfavoris(true);
                    favIcon.setImageResource(0x0108000c);
                }
            }
        });

        for (AirportInfo fav : favorisList){
            if(fav.getOaciCode().equals(result.getOaciCode())) {
                result.setfavoris(true);
                favIcon.setImageResource(0x0108000c);
            }
        }

        TextView phoneNumber = findViewById(R.id.phoneNumber);
        phoneNumber.setText(result.getPhoneNumber());

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
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(snowtamInfo!=null) {
                            snowtam = new CodeInfo(snowtamInfo,result);
                            Intent intent = new Intent(SearchActivity.this, CodeActivity.class);
                            intent.putExtra("snowtam",snowtam);
                            intent.putExtra("airport",(Parcelable)result);
                            startActivity(intent);
                        }
                        else {
                            //Pas de snowtam
                            //Afficher un truc?
                        }

                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                };

                SnowtamService.searchSnowtam(result.getOaciCode(),responseListener,errorListener,SearchActivity.this);
            }
        });
    }

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

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        finish();
    }

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