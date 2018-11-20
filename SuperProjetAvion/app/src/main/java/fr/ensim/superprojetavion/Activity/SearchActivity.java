package fr.ensim.superprojetavion.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.Model.CodeInfo;
import fr.ensim.superprojetavion.Model.DataSearchSnowtam;
import fr.ensim.superprojetavion.Model.SnowtamInfo;
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

        Response.Listener<DataSearchSnowtam> responseListener = new Response.Listener<DataSearchSnowtam>() {
            @Override
            public void onResponse(DataSearchSnowtam response) {
                List<SnowtamInfo> notamList;
                SnowtamInfo snowtamInfo = null;

                notamList = response.getData();

                for (SnowtamInfo n : notamList) {
                    if(n.getId().contains("SW")){
                        snowtamInfo = n;
                        break;
                    }
                }

                snowtam = new CodeInfo(snowtamInfo);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        SnowtamService.searchSnowtam(result.getOaciCode(),responseListener,errorListener,SearchActivity.this);
    }
}