package fr.ensim.superprojetavion.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
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

public class CodeActivity extends AppCompatActivity {

    private CodeInfo snowtam ;
    private AirportInfo airport;
    private ArrayList<AirportInfo> favorisList;

    private ArrayList<AirportInfo> allAirportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        super.setTitle(getString(R.string.codeName));


        importFavorisList();
        allAirportList = favorisList;

        Intent i = getIntent();

        snowtam = i.getParcelableExtra("snowtam");
        airport = i.getParcelableExtra("airport");

        boolean inList = false;
        for(AirportInfo airportInfo : favorisList){
            if(airportInfo.getOaciCode().equals(airport.getOaciCode())) inList = true;
        }
        if (!inList) allAirportList.add(airport);

        if(i.getParcelableArrayListExtra("allAirportList")!=null) allAirportList = i.getParcelableArrayListExtra("allAirportList");

        ImageView flag = findViewById(R.id.flag);
        new DownloadImageTask(flag).execute(airport.getFlag());

        TextView oaci = findViewById(R.id.oaci);
        oaci.setText(airport.getOaciCode());

        final Switch switchCode = findViewById(R.id.switchCode);

        final TextView snowtamText = findViewById(R.id.snowtam);
        snowtamText.setText(snowtam.toString(switchCode.isChecked()));

        switchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snowtamText.setText(snowtam.toString(switchCode.isChecked()));
            }
        });

        ImageButton map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (CodeActivity.this, MapsActivity.class);
                i.putExtra("airport",(Parcelable)airport);
                startActivity(i);
                finish();
            }
        });

        Button left = findViewById(R.id.left);
        Button righ = findViewById(R.id.right);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentIndex = 0;
                for(AirportInfo test : allAirportList){
                    if(test.getOaciCode().equals(airport.getOaciCode())) currentIndex = allAirportList.indexOf(test);
                }

                final AirportInfo previous = allAirportList.get((currentIndex-1)%allAirportList.size());

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
                            Intent intent = new Intent(CodeActivity.this, CodeActivity.class);
                            intent.putExtra("snowtam",snowtam);
                            intent.putExtra("airport",(Parcelable)previous);
                            intent.putExtra("allAirportList", allAirportList);
                            startActivity(intent);
                            finish();
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

                SnowtamService.searchSnowtam(previous.getOaciCode(),responseListener,errorListener,CodeActivity.this);
            }
        });

        righ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentIndex = 0;
                for(AirportInfo test : allAirportList){
                    if(test.getOaciCode().equals(airport.getOaciCode())) currentIndex = allAirportList.indexOf(test);
                }

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
                            Intent intent = new Intent(CodeActivity.this, CodeActivity.class);
                            intent.putExtra("snowtam",snowtam);
                            intent.putExtra("airport",(Parcelable)next);
                            intent.putExtra("allAirportList", allAirportList);
                            startActivity(intent);
                            finish();
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

                SnowtamService.searchSnowtam(next.getOaciCode(),responseListener,errorListener,CodeActivity.this);
            }
        });
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
