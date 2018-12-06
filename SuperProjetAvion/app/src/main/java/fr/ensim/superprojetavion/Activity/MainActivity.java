package fr.ensim.superprojetavion.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

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
import fr.ensim.superprojetavion.Service.oaciService;

public class MainActivity extends AppCompatActivity {

    //List of favorite airports
    ArrayList<AirportInfo> favorisList;
    //Airport info from search
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

        //Search bar
        final SearchView search = findViewById(R.id.searchView);
        search.setIconified(false);
        search.setQueryHint(getString(R.string.oaci));
        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });

        //listener to submit the search request
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                result = null;
                search(s.toUpperCase());

                //wait while no result yet
                while(result==null) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String toastText;
                Toast toast;

                switch(result.getAirportName()){
                    //Toast to inform user if there is something wrong
                    case "noConnection" :
                        toastText = getString(R.string.noConnectionToast);
                        toast = Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    case "noResult" :
                        toastText = getString(R.string.searchToast);
                        toast = Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    case "ioEx" :
                        toastText = getString(R.string.searchToast);
                        toast = Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    //ready to go to searchActivity
                    default:
                        Intent i = new Intent(MainActivity.this, SearchActivity.class);
                        i.putExtra("result", (Parcelable)result);
                        startActivity(i);
                }
                return true;
            }

            //only return true to prevent default action to execute
            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        //button to go to settingsActivity
        ImageButton settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });
    }

    //retreive favorite list when returning to mainActivity
    @Override
    protected void onResume(){
        super.onResume();
        importFavorisList();
        if(favorisList!=null){
            final RecyclerView list =(RecyclerView) findViewById(R.id.list);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(new MyAdapter(favorisList, MainActivity.this));
        }

        //display logo of the app if favorite list is empty
        ImageView logo = findViewById(R.id.logoAccueil);
        if(favorisList!=null && favorisList.size()>0) logo.setVisibility(View.GONE);
        else logo.setVisibility(View.VISIBLE);
    }

    //save favorite list when app exiting
    @Override
    protected void onDestroy(){
        super.onDestroy();
        saveFavorisList();
    }

    //asynchronous function to get airport infos
    private void search(final String oaci){

        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                result = oaciService.getAirportInfo(oaci);
            }
        });

        t.start();
    }

    //function to save favorite list in file
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

    //function to retreive favorite list from file
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
