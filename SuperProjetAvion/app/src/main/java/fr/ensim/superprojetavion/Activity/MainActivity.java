package fr.ensim.superprojetavion.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.ensim.superprojetavion.Model.AirportInfo;
import fr.ensim.superprojetavion.R;
import fr.ensim.superprojetavion.Service.OaciService;

public class MainActivity extends AppCompatActivity {

    ArrayList<AirportInfo> favorisList;
    AirportInfo result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t = findViewById(R.id.test);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search("enbr".toUpperCase());
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        importFavorisList();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        saveFavorisList();
    }

    private void search(final String oaci){

        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                result = OaciService.getAirportInfo(oaci);

                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                i.putExtra("result", result);
                startActivity(i);
            }
        });

        t.start();
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
