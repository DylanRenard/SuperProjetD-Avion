package fr.ensim.superprojetavion.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import fr.ensim.superprojetavion.Model.AirportInfo;

public class oaciService {
    public static AirportInfo getAirportInfo(String oaci){

        try {
            AirportInfo result = new AirportInfo();

            Document responce = Jsoup.connect("https://www.world-airport-codes.com/search/?s="+oaci).get();

            Element flag = responce.getElementsByClass("small-1 columns flag").first();
            Element location = responce.getElementsByClass("subheader").first();
            Element name = responce.getElementsByClass("airport-title").first();
            Element latitude = responce.getElementById("latitude");
            Element longitude = responce.getElementById("longitude");
            Element phone = responce.getElementById("phone");
            Element timezone = responce.getElementById("timezone");

            result.setOaciCode(oaci);

            result.setFlag(flag.children().first().attributes().asList().get(0).getValue());
            result.setLocation(location.text());
            result.setAirportName(name.text());
            result.setLatitude(Double.parseDouble(latitude.val()));
            result.setLongitude(Double.parseDouble(longitude.val()));
            result.setPhoneNumber(phone.val());
            result.setTimeZone(timezone.val());

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}