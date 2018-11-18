package fr.ensim.superprojetavion.Model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Dana on 15/11/2018.
 */

public class CodeInfo {
    AirportInfo airport = new AirportInfo();

    String code_location;                       // A) aerodrome location indicator
    String code_date;                           // B) observation date & time
    String code_idRunway;                       // C) runway designator
    String code_clearedRunwayLength;            // D) cleared runway length
    String code_clearedRunwayWidth;             // E) cleared runway width
    String code_conditionsRunway;               // F) deposits over total runway length
    String code_thickness;                      // G) mean depth deposit for each third of total runway length
    String code_frictionCoef;                   // H) friction measurements and friction measurement device
    String code_criticalSnowbanks;              // J) critical snowbanks
    String code_lightsObscured;                 // K) runway lights
    String code_furtherClearance;               // L) further clearance
    String code_anticipatedTimecompletion;      // M) further clearance expected to be completed
    String code_taxiwaysState;                  // N) taxiway
    String code_snowBanks;                      // P) taxiway snowbanks
    String code_parking;                        // R) apron
    String code_nextObservation;                // S) next planned observation/measurement
    String code_comment;                        // T) plain-language remarks

    String decode_location;
    String decode_date;
    String decode_idRunway;
    String decode_clearedRunwayLength;
    String decode_clearedRunwayWidth;
    String decode_conditionsRunway;
    String decode_thickness;
    String decode_frictionCoef;
    String decode_criticalSnowbanks;
    String decode_lightsObscured;
    String decode_furtherClearance;
    String decode_anticipatedTimecompletion;
    String decode_taxiwaysState;
    String decode_snowBanks;
    String decode_parking;
    String decode_nextObservation;
    String decode_comment;


    void onDecode() {
        // A)
        decode_location = airport.getAirportName();

        // B)
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmm", Locale.getDefault());
        try {
            Date date = dateFormat.parse(code_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        decode_date = code_date.toString();

        // C)
        // TO DO

        // D)
        decode_clearedRunwayLength = " CLEARED RUNWAY LENGTH " + code_clearedRunwayLength + "M";

        // E)
        String axis="";
        String [] clearedRunwayWidth = code_clearedRunwayWidth.split("");

        if (clearedRunwayWidth[clearedRunwayWidth.length -1].equals("L")){
            axis = "LEFT";
        }
        else {
            axis = "RIGHT";
        }

        if (clearedRunwayWidth.length == 2) {
            decode_clearedRunwayWidth = "CLEARED RUNWAY WIDTH " + clearedRunwayWidth[0] + "M " + axis;
        }
        else {
            decode_clearedRunwayWidth = "CLEARED RUNWAY WIDTH " + clearedRunwayWidth[0] + clearedRunwayWidth[1] + "M " + axis;
        }

        // F)
        String [] conditionsRunway = code_conditionsRunway.split("/");
        decode_conditionsRunway = "Threshold: " + switchConditions(conditionsRunway[0]) + " / "
                                 + "Mid runway: " + switchConditions(conditionsRunway[1]) + " / "
                                 + "Roll out: " + switchConditions(conditionsRunway[2]) ;

        // G)
        String[] thickness = code_thickness.split("/");
        decode_thickness = "MEAN DEPTH Threshold: " + thickness[0] + "mm / "
                            + "Mid runway: " + thickness[1] + "mm / "
                            + "Roll out: " + thickness[2] + "mm";

        // H)
        
        // J)

        // K)

        // L)

        // M)

        // N)

        // P)


        // R)

        // S)


        // T)


    }

    String switchConditions(String number){
        String value="";
        switch (number.toLowerCase()){
            case "0":
                value = "CLEAR AND DRY";
                break;
            case "1":
                value = "DAMP";
                break;
            case "2":
                value = "WET or WATER PATCHES";
                break;
            case "3":
                value = "RIME OR FROST COVERED";
                break;
            case "4":
                value = "DRY SNOW";
                break;
            case "5":
                value = "WET SNOW";
                break;
            case "6":
                value = "SLUSH";
                break;
            case "7":
                value = "ICE";
                break;
            case "8":
                value = "COMPACTED OR ROLLED SNOW";
                break;
            case "9":
                value = "FROZEN RUTS OR RIDGES";
                break;
        }
        return value;
    }


}
