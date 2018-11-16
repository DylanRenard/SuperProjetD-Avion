package fr.ensim.superprojetavion.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.R;

public class SearchActivity extends AppCompatActivity {

    AirportInfo result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent i = getIntent();
        result = i.getParcelableExtra("result");

        Log.d("test parcelable : ", result.toString() );
    }
}