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
import android.widget.Switch;
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

public class CodeActivity extends AppCompatActivity {

    //Snowtam corresponding to the current airport
    private CodeInfo snowtam ;
    //Current airport
    private AirportInfo airport;
    //List of favorite airports
    private ArrayList<AirportInfo> favorisList;

    //List of favorite airports plus searched airport if coming from SearchActivity
    private ArrayList<AirportInfo> allAirportList = new ArrayList<AirportInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        super.setTitle(getString(R.string.codeName));

        //retreive list of favorite from file
        importFavorisList();

        //retreive infos from intent
        Intent i = getIntent();
        snowtam = i.getParcelableExtra("snowtam");
        airport = i.getParcelableExtra("airport");
        if(i.getParcelableArrayListExtra("allAirportList")!=null) allAirportList = i.getParcelableArrayListExtra("allAirportList");
        else{
            allAirportList.addAll(favorisList);

            //add current airport to the list if is not favorite
            boolean inList = false;
            for(AirportInfo airportInfo : favorisList){
                if(airportInfo.getOaciCode().equals(airport.getOaciCode())) inList = true;
            }
            if (!inList) allAirportList.add(airport);
        }

        //set flag picture
        ImageView flag = findViewById(R.id.flag);
        new DownloadImageTask(flag).execute(airport.getFlag());

        //set icao text
        TextView oaci = findViewById(R.id.oaci);
        oaci.setText(airport.getOaciCode());

        //set switch mode depending of settings
        final Switch switchCode = findViewById(R.id.switchCode);
        try {
            FileInputStream fis = openFileInput("settings.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            switchCode.setChecked((boolean) ois.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            switchCode.setChecked(false);
        } catch (IOException e) {
            e.printStackTrace();
            switchCode.setChecked(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            switchCode.setChecked(false);
        }

        //set snowtam text depending of view mode
        final TextView snowtamText = findViewById(R.id.snowtam);
        snowtamText.setText(snowtam.toString(!switchCode.isChecked()));

        //listener to change view mode
        switchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snowtamText.setText(snowtam.toString(!switchCode.isChecked()));
            }
        });

        //button to go to MapsActivity
        ImageButton map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (CodeActivity.this, MapsActivity.class);
                i.putExtra("airport",(Parcelable)airport);
                i.putExtra("allAirportList", allAirportList);
                startActivity(i);
                finish();
            }
        });

        //buttons to browse the airport list
        ImageButton left = findViewById(R.id.left);
        ImageButton righ = findViewById(R.id.right);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the index of the current airport
                int currentIndex = 0;
                for(AirportInfo test : allAirportList){
                    if(test.getOaciCode().equals(airport.getOaciCode())) currentIndex = allAirportList.indexOf(test);
                }

                //get the previous airport on the list
                final AirportInfo previous = allAirportList.get((currentIndex-1+allAirportList.size())%allAirportList.size());

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
                            snowtam = new CodeInfo(snowtamInfo,previous);
                            if(snowtam.getCode_date()!=null){
                                Intent intent = new Intent(CodeActivity.this, CodeActivity.class);
                                intent.putExtra("snowtam",snowtam);
                                intent.putExtra("airport",(Parcelable)previous);
                                intent.putExtra("allAirportList", allAirportList);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                String toastText = getString(R.string.invalidSnowtam);
                                Toast toast = Toast.makeText(CodeActivity.this, toastText, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                        else {
                            String toastText = getString(R.string.noSnowtamToast);
                            Toast toast = Toast.makeText(CodeActivity.this, toastText, Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String toastText = getString(R.string.noConnectionToast);
                        Toast toast = Toast.makeText(CodeActivity.this, toastText, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                };

                //get the snowtam of the previous airport
                SnowtamService.searchSnowtam(previous.getOaciCode(),responseListener,errorListener,CodeActivity.this);
            }
        });

        righ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the index of the current airport
                int currentIndex = 0;
                for(AirportInfo test : allAirportList){
                    if(test.getOaciCode().equals(airport.getOaciCode())) currentIndex = allAirportList.indexOf(test);
                }

                //get the next airport on the list
                final AirportInfo next = allAirportList.get((currentIndex+1)%allAirportList.size());

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
                            snowtam = new CodeInfo(snowtamInfo,next);
                            if(snowtam.getCode_date()!=null){
                                Intent intent = new Intent(CodeActivity.this, CodeActivity.class);
                                intent.putExtra("snowtam",snowtam);
                                intent.putExtra("airport",(Parcelable)next);
                                intent.putExtra("allAirportList", allAirportList);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                String toastText = getString(R.string.invalidSnowtam);
                                Toast toast = Toast.makeText(CodeActivity.this, toastText, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                        else {
                            String toastText = getString(R.string.noSnowtamToast);
                            Toast toast = Toast.makeText(CodeActivity.this, toastText, Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String toastText = getString(R.string.noConnectionToast);
                        Toast toast = Toast.makeText(CodeActivity.this, toastText, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                };

                //get the snowtam of the next airport
                SnowtamService.searchSnowtam(next.getOaciCode(),responseListener,errorListener,CodeActivity.this);
            }
        });

        //favorite button
        final ImageButton favIcon = findViewById(R.id.favorisIcon);
        favIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(airport.isfavoris()){
                    airport.setfavoris(false);
                    favIcon.setImageResource(R.drawable.snow1);
                    String toastText = getString(R.string.removeFavToast);
                    Toast toast = Toast.makeText(CodeActivity.this, toastText, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    airport.setfavoris(true);
                    favIcon.setImageResource(R.drawable.snow2);
                    String toastfav = getString(R.string.addFavoriteToast);
                    Toast toast = Toast.makeText(CodeActivity.this, toastfav, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //initialize favorite button depending if airport is on favorite list
        for (AirportInfo fav : favorisList){
            Log.w("testFavoris",""+fav.getOaciCode().equals(airport.getOaciCode()));
            if(fav.getOaciCode().equals(airport.getOaciCode())) {
                airport.setfavoris(true);
                favIcon.setImageResource(R.drawable.snow2);
            }
        }
    }

    //save favorite list before exiting the activity
    @Override
    protected void onStop(){
        super.onStop();

        boolean inList = false;

        for (AirportInfo fav : favorisList){
            if(fav.getOaciCode().equals(airport.getOaciCode())) {
                inList = true;
                if(!airport.isfavoris()) favorisList.remove(fav);
                break;
            }
        }
        if(airport.isfavoris() && !inList) favorisList.add(airport);

        saveFavorisList();
    }

    //make sure that favorite list is saved then finish the activity
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        onStop();
        finish();
    }

    //function to save favorite list in file
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
