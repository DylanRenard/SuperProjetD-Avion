package fr.ensim.superprojetavion.Service;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class SnowtamService {

    public static void searchSnowtam(String icao,
                              Response.Listener responseListener,
                              Response.ErrorListener errorListener,
                              Context context){
        final String url = "https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/states/notams/notams-list?api_key=fc2b1670-ec93-11e8-acf9-1d6bfa3c323d&format=json&type=&Qcode=&locations="+icao+"&qstring=&states=&ICAOonly=";

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(url, responseListener, errorListener);

        queue.add(request);
    }
}
