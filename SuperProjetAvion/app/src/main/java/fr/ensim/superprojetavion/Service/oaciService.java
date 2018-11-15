package fr.ensim.superprojetavion.Service;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import fr.ensim.superprojetavion.Model.AirportInfo;

public class oaciService {
    public AirportInfo getAirportInfo(String oaci){

        try {
            Document result = Jsoup.connect("https://www.world-airport-codes.com/search/?s="+oaci).get();

            Elements e = result.getElementsByClass("airport-info-table");

            Log.d("Test recup info :", e.toString() );


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
