package fr.ensim.superprojetavion.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.Model.CodeInfo;
import fr.ensim.superprojetavion.R;
import fr.ensim.superprojetavion.Service.DownloadImageTask;

public class CodeActivity extends AppCompatActivity {

    private CodeInfo snowtam ;
    private AirportInfo airport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        Intent i = getIntent();

        snowtam = i.getParcelableExtra("snowtam");
        airport = i.getParcelableExtra("airport");

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
    }
}
