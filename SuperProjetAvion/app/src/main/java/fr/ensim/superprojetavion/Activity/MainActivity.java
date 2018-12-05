package fr.ensim.superprojetavion.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import fr.ensim.superprojetavion.Adapter.MyAdapter;
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
        super.setTitle(getString(R.string.homeName));

       ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.justplane);


        final SearchView search = findViewById(R.id.searchView);
        search.setIconified(false);
        search.setQueryHint(getString(R.string.oaci));
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s.toUpperCase());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        importFavorisList();
        if(favorisList!=null){
            final RecyclerView list =(RecyclerView) findViewById(R.id.list);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(new MyAdapter(favorisList, MainActivity.this));
        }
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
                i.putExtra("result", (Parcelable)result);
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
