package fr.ensim.superprojetavion.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.R;
import fr.ensim.superprojetavion.Service.oaciService;

public class MainActivity extends AppCompatActivity {

    AirportInfo[] favorisList;
    AirportInfo result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.search("lfpg");
//
//        while(result==null);
//        Log.d("result : ", result.toString());

    }

    private void search(final String oaci){

        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                result = oaciService.getAirportInfo(oaci);
            }
        });

        t.start();
    }
}
