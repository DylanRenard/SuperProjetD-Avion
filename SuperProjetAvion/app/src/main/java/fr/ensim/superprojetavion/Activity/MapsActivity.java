package fr.ensim.superprojetavion.Activity;

import android.content.Intent;
import android.media.Image;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.Model.CodeInfo;
import fr.ensim.superprojetavion.R;
import fr.ensim.superprojetavion.Service.DownloadImageTask;
import fr.ensim.superprojetavion.Service.SnowtamService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AirportInfo airportInfo;
    private ArrayList<AirportInfo> allAirportList;
    private CodeInfo snowtam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        super.setTitle(getString(R.string.mapName));

        Intent i = getIntent();
        airportInfo = i.getParcelableExtra("airport");
        allAirportList = i.getParcelableArrayListExtra("allAirportList");

        TextView oaci = findViewById(R.id.oaci);
        oaci.setText(airportInfo.getOaciCode());

        TextView name = findViewById(R.id.name);
        name.setText(airportInfo.getAirportName());

        ImageView flag = findViewById(R.id.flag);
        new DownloadImageTask(flag).execute(airportInfo.getFlag());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button left = findViewById(R.id.left);
        Button righ = findViewById(R.id.right);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentIndex = 0;
                for(AirportInfo test : allAirportList){
                    if(test.getOaciCode().equals(airportInfo.getOaciCode())) currentIndex = allAirportList.indexOf(test);
                }

                AirportInfo previous = allAirportList.get((currentIndex-1)%allAirportList.size());

                Intent i = new Intent(MapsActivity.this, MapsActivity.class);
                i.putExtra("airport", (Parcelable)previous);
                i.putExtra("allAirportList", allAirportList);
                startActivity(i);
                finish();
            }
        });

        righ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentIndex = 0;
                for(AirportInfo test : allAirportList){
                    if(test.getOaciCode().equals(airportInfo.getOaciCode())) currentIndex = allAirportList.indexOf(test);
                }

                AirportInfo next = allAirportList.get((currentIndex+1)%allAirportList.size());

                Intent i = new Intent(MapsActivity.this, MapsActivity.class);
                i.putExtra("airport", (Parcelable)next);
                i.putExtra("allAirportList", allAirportList);
                startActivity(i);
                finish();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double lat = airportInfo.getLatitude();
        double longitude =  airportInfo.getLongitude();
        // Add a marker in Sydney and move the camera
        LatLng airport = new LatLng( lat , longitude);
        float zoomLevel = (float) 14.0;
        mMap.addMarker(new MarkerOptions().position(airport).title(airportInfo.getAirportName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(airport, zoomLevel));
    }

    @Override
    public void onBackPressed(){

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
                    snowtam = new CodeInfo(snowtamInfo,airportInfo);
                    Intent intent = new Intent(MapsActivity.this, CodeActivity.class);
                    intent.putExtra("snowtam",snowtam);
                    intent.putExtra("airport",(Parcelable)airportInfo);
                    intent.putExtra("allAirportList", allAirportList);
                    startActivity(intent);
                    finish();
                }
                else {
                    String toastText = getString(R.string.noSnowtamToast);
                    Toast toast = Toast.makeText(MapsActivity.this, toastText, Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };

        SnowtamService.searchSnowtam(airportInfo.getOaciCode(),responseListener,errorListener,MapsActivity.this);

    }
}
