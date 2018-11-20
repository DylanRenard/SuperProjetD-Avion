package fr.ensim.superprojetavion.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.Model.CodeInfo;
import fr.ensim.superprojetavion.R;
import fr.ensim.superprojetavion.Service.SnowtamService;

public class SearchActivity extends AppCompatActivity {

    AirportInfo result;
    CodeInfo snowtam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent i = getIntent();
        result = i.getParcelableExtra("result");

        Log.d("test parcelable : ", result.toString() );

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

                if(snowtamInfo!=null) snowtam = new CodeInfo(snowtamInfo,result);
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
}