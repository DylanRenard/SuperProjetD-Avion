package fr.ensim.superprojetavion.Activity;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.R;
import fr.ensim.superprojetavion.Service.DownloadImageTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AirportInfo airportInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        super.setTitle(getString(R.string.mapName));

        Intent i = getIntent();
        airportInfo = i.getParcelableExtra("airport");

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
}
