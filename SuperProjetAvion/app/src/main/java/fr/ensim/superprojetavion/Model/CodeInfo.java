package fr.ensim.superprojetavion.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dana on 15/11/2018.
 */

public class CodeInfo implements Parcelable{

    public CodeInfo(String info, AirportInfo airportInfo){
        int i=0;

        //A
        this.airport = airportInfo;
        //B
        this.code_location = airportInfo.getOaciCode();
        if(info.contains("B) ")) {
            this.code_date = info.substring(info.indexOf("B) ")+3,info.indexOf(')',info.indexOf("B) ")+2)-1);
            info = info.substring(info.indexOf(')',info.indexOf("B) ")+2)-1);
        }
        else this.code_date = null;

        while(info.contains("C) ")){
            if(info.contains("C) ")) this.code_idRunway.add(i, info.substring(info.indexOf("C) ") + 3, info.indexOf(')', info.indexOf("C) ") + 2) - 1));
            if(info.contains("D) ")) this.code_clearedRunwayLength.add(i, info.substring(info.indexOf("D) ") + 3, info.indexOf(')', info.indexOf("D) ") + 2) - 1));
            if(info.contains("E) ")) this.code_clearedRunwayWidth.add(i, info.substring(info.indexOf("E) ") + 3, info.indexOf(')', info.indexOf("E) ") + 2) - 1));
            if(info.contains("F) ")) this.code_conditionsRunway.add(i, info.substring(info.indexOf("F) ") + 3, info.indexOf(')', info.indexOf("F) ") + 2) - 1));
            if(info.contains("G) ")) this.code_thickness.add(i, info.substring(info.indexOf("G) ") + 3, info.indexOf(')', info.indexOf("G) ") + 2) - 1));
            if(info.contains("H) ")) this.code_frictionCoef.add(i, info.substring(info.indexOf("H) ") + 3, info.indexOf(')', info.indexOf("H) ") + 2) - 1));
            if(info.contains("J) ")) this.code_criticalSnowbanks.add(i, info.substring(info.indexOf("J) ") + 3, info.indexOf(')', info.indexOf("J) ") + 2) - 1));
            if(info.contains("K) ")) this.code_lightsObscured.add(i, info.substring(info.indexOf("K) ") + 3, info.indexOf(')', info.indexOf("K) ") + 2) - 1));
            if(info.contains("L) ")) this.code_furtherClearance.add(i, info.substring(info.indexOf("L) ") + 3, info.indexOf(')', info.indexOf("L) ") + 2) - 1));
            if(info.contains("M) ")) this.code_anticipatedTimecompletion.add(i, info.substring(info.indexOf("M) ") + 3, info.indexOf(')', info.indexOf("M) ") + 2) - 1));
            if(info.contains("N) ")) this.code_taxiwaysState.add(i, info.substring(info.indexOf("N) ") + 3, info.indexOf(')', info.indexOf("N) ") + 2) - 1));
            if(info.contains("P) ")) this.code_snowBanks.add(i, info.substring(info.indexOf("P) ") + 3, info.indexOf(')', info.indexOf("P) ") + 2) - 1));

            if(info.indexOf("C)",info.indexOf("C)")+1)!=-1) info = info.substring(info.indexOf("C)",info.indexOf("C)")+1));
            else break;
            i++;
        }

        if(info.contains("R) ")) this.code_parking = info.substring(info.indexOf("R) ")+3,info.indexOf(')',info.indexOf("R) ")+2)-1);
        else this.code_parking = null;
        if(info.contains("S) ")) this.code_nextObservation = info.substring(info.indexOf("S) ")+3,info.indexOf(')',info.indexOf("S) ")+2)-1);
        else this.code_nextObservation = null;
        if(info.contains("T) ")) this.code_comment = info.substring(info.indexOf("T) ")+3,info.indexOf(')',info.indexOf("T) ")+2)-1);
        else this.code_comment = null;

        this.onDecode();
    }

    private AirportInfo airport;

    //WARNING ! C à P CAN BE MULTIPLES
    private String code_location;                       // A) aerodrome location indicator
    private String code_date;                           // B) observation date & time
    private ArrayList<String> code_idRunway = new ArrayList<String>();                       // C) runway designator
    private ArrayList<String> code_clearedRunwayLength = new ArrayList<String>();            // D) cleared runway length
    private ArrayList<String> code_clearedRunwayWidth = new ArrayList<String>();             // E) cleared runway width
    private ArrayList<String> code_conditionsRunway = new ArrayList<String>();               // F) deposits over total runway length
    private ArrayList<String> code_thickness = new ArrayList<String>();                      // G) mean depth deposit for each third of total runway length
    private ArrayList<String> code_frictionCoef = new ArrayList<String>();                   // H) friction measurements and friction measurement device
    private ArrayList<String> code_criticalSnowbanks = new ArrayList<String>();              // J) critical snowbanks
    private ArrayList<String> code_lightsObscured = new ArrayList<String>();                 // K) runway lights
    private ArrayList<String> code_furtherClearance = new ArrayList<String>();               // L) further clearance
    private ArrayList<String> code_anticipatedTimecompletion = new ArrayList<String>();      // M) further clearance expected to be completed
    private ArrayList<String> code_taxiwaysState = new ArrayList<String>();                  // N) taxiway
    private ArrayList<String> code_snowBanks = new ArrayList<String>();                      // P) taxiway snowbanks
    private String code_parking;                        // R) apron
    private String code_nextObservation;                // S) next planned observation/measurement
    private String code_comment;                        // T) plain-language remarks

    private String decode_location;
    private String decode_date;
    private ArrayList<String> decode_idRunway = new ArrayList<String>();
    private ArrayList<String> decode_clearedRunwayLength = new ArrayList<String>();
    private ArrayList<String> decode_clearedRunwayWidth = new ArrayList<String>();
    private ArrayList<String> decode_conditionsRunway = new ArrayList<String>();
    private ArrayList<String> decode_thickness = new ArrayList<String>();
    private ArrayList<String> decode_frictionCoef = new ArrayList<String>();
    private ArrayList<String> decode_criticalSnowbanks = new ArrayList<String>();
    private ArrayList<String> decode_lightsObscured = new ArrayList<String>();
    private ArrayList<String> decode_furtherClearance = new ArrayList<String>();
    private ArrayList<String> decode_anticipatedTimecompletion = new ArrayList<String>();
    private ArrayList<String> decode_taxiwaysState = new ArrayList<String>();
    private ArrayList<String> decode_snowBanks = new ArrayList<String>();
    private String decode_parking;
    private String decode_nextObservation;
    private String decode_comment;


    protected CodeInfo(Parcel in) {
        airport = in.readParcelable(AirportInfo.class.getClassLoader());
        code_location = in.readString();
        code_date = in.readString();
        code_idRunway = in.createStringArrayList();
        code_clearedRunwayLength = in.createStringArrayList();
        code_clearedRunwayWidth = in.createStringArrayList();
        code_conditionsRunway = in.createStringArrayList();
        code_thickness = in.createStringArrayList();
        code_frictionCoef = in.createStringArrayList();
        code_criticalSnowbanks = in.createStringArrayList();
        code_lightsObscured = in.createStringArrayList();
        code_furtherClearance = in.createStringArrayList();
        code_anticipatedTimecompletion = in.createStringArrayList();
        code_taxiwaysState = in.createStringArrayList();
        code_snowBanks = in.createStringArrayList();
        code_parking = in.readString();
        code_nextObservation = in.readString();
        code_comment = in.readString();
        decode_location = in.readString();
        decode_date = in.readString();
        decode_idRunway = in.createStringArrayList();
        decode_clearedRunwayLength = in.createStringArrayList();
        decode_clearedRunwayWidth = in.createStringArrayList();
        decode_conditionsRunway = in.createStringArrayList();
        decode_thickness = in.createStringArrayList();
        decode_frictionCoef = in.createStringArrayList();
        decode_criticalSnowbanks = in.createStringArrayList();
        decode_lightsObscured = in.createStringArrayList();
        decode_furtherClearance = in.createStringArrayList();
        decode_anticipatedTimecompletion = in.createStringArrayList();
        decode_taxiwaysState = in.createStringArrayList();
        decode_snowBanks = in.createStringArrayList();
        decode_parking = in.readString();
        decode_nextObservation = in.readString();
        decode_comment = in.readString();
    }

    public String toString(boolean code){
        String s;
        if(code) {
            s = "A) " + code_location + "\nB) " + code_date + "\n" ;
            for (int i=0 ; i<code_idRunway.size() ; i++){
                s = s.concat("C) " + code_idRunway.get(i) + "\n");
                if(code_clearedRunwayLength.size()!=0) s = s.concat("D) " + code_clearedRunwayLength.get(i) + "\n");
                if(code_clearedRunwayWidth.size()!=0) s = s.concat("E) " + code_clearedRunwayWidth.get(i) + "\n");
                if(code_conditionsRunway.size()!=0) s = s.concat("F) " + code_conditionsRunway.get(i) + "\n");
                if(code_thickness.size()!=0) s = s.concat("G) " + code_thickness.get(i) + "\n");
                if(code_frictionCoef.size()!=0) s = s.concat("H) " + code_frictionCoef.get(i) + "\n");
                if(code_criticalSnowbanks.size()!=0) s = s.concat("J) " + code_criticalSnowbanks.get(i) + "\n");
                if(code_lightsObscured.size()!=0) s = s.concat("K) " + code_lightsObscured.get(i) + "\n");
                if(code_furtherClearance.size()!=0) s = s.concat("L) " + code_furtherClearance.get(i) + "\n");
                if(code_anticipatedTimecompletion.size()!=0) s = s.concat("M) " + code_anticipatedTimecompletion.get(i) + "\n");
                if(code_taxiwaysState.size()!=0) s = s.concat("N) " + code_taxiwaysState.get(i) + "\n");
                if(code_snowBanks.size()!=0) s = s.concat("P) " + code_snowBanks.get(i) + "\n");
            }
            if(code_parking!=null) s = s.concat("R) " + code_parking + "\n");
            if(code_nextObservation!=null) s = s.concat("S) " + code_nextObservation + "\n");
            if(code_comment!=null) s = s.concat("T) " + code_comment + "\n");
        }
        else {
            s = "A) " + decode_location + "\nB) " + decode_date + "\n" ;
            for (int i=0 ; i<decode_idRunway.size() ; i++){
                s = s.concat("C) " + decode_idRunway.get(i) + "\n");
                if(decode_clearedRunwayLength.size()!=0) s = s.concat("D) " + decode_clearedRunwayLength.get(i) + "\n");
                if(decode_clearedRunwayWidth.size()!=0) s = s.concat("E) " + decode_clearedRunwayWidth.get(i) + "\n");
                if(decode_conditionsRunway.size()!=0) s = s.concat("F) " + decode_conditionsRunway.get(i) + "\n");
                if(decode_thickness.size()!=0) s = s.concat("G) " + decode_thickness.get(i) + "\n");
                if(decode_frictionCoef.size()!=0) s = s.concat("H) " + decode_frictionCoef.get(i) + "\n");
                if(decode_criticalSnowbanks.size()!=0) s = s.concat("J) " + decode_criticalSnowbanks.get(i) + "\n");
                if(decode_lightsObscured.size()!=0) s = s.concat("K) " + decode_lightsObscured.get(i) + "\n");
                if(decode_furtherClearance.size()!=0) s = s.concat("L) " + decode_furtherClearance.get(i) + "\n");
                if(decode_anticipatedTimecompletion.size()!=0) s = s.concat("M) " + decode_anticipatedTimecompletion.get(i) + "\n");
                if(decode_taxiwaysState.size()!=0) s = s.concat("N) " + decode_taxiwaysState.get(i) + "\n");
                if(decode_snowBanks.size()!=0) s = s.concat("P) " + decode_snowBanks.get(i) + "\n");
            }
            if(decode_parking!=null) s = s.concat("R) " + decode_parking + "\n");
            if(decode_nextObservation!=null) s = s.concat("S) " + decode_nextObservation + "\n");
            if(decode_comment!=null) s = s.concat("T) " + decode_comment + "\n");
        }

        return s;
    }

    public static final Creator<CodeInfo> CREATOR = new Creator<CodeInfo>() {
        @Override
        public CodeInfo createFromParcel(Parcel in) {
            return new CodeInfo(in);
        }

        @Override
        public CodeInfo[] newArray(int size) {
            return new CodeInfo[size];
        }
    };

    void onDecode() {
        //itérateur
        int i;

        // A)
        decode_location = airport.getAirportName();


        // B)
        if (code_date!=null){
            decode_date = getDate(code_date).toString();
        }


        // C)
        if(code_idRunway!=null) {
            if (code_idRunway.get(0).equals("88")) {
                decode_idRunway.set(0, "ALL RUNWAYS");
            } else {
                for(i=0 ; i< code_idRunway.size(); i++){
                    decode_idRunway.add(i, "RUNWAY " + code_idRunway.get(i));
                }
            }
        }

        // D)
        if (code_clearedRunwayLength!=null) {
            for(i=0 ; i< code_clearedRunwayLength.size(); i++){
                decode_clearedRunwayLength.add(i, " CLEARED RUNWAY LENGTH " + code_clearedRunwayLength.get(i) + "M");
            }
        }

        // E)
        if(code_clearedRunwayWidth!=null) {
            for(i=0 ; i< code_clearedRunwayWidth.size(); i++){
                decode_clearedRunwayWidth.add(i, "CLEARED RUNWAY WIDTH ");

                char[] clearedRunwayWidth = code_clearedRunwayWidth.get(i).toCharArray();
                for (int j=0 ; j<clearedRunwayWidth.length-1 ; j++){
                    decode_clearedRunwayWidth.add(i, decode_clearedRunwayWidth.get(i) + clearedRunwayWidth[j]);
                }
                char last = clearedRunwayWidth[clearedRunwayWidth.length-1];
                switch(last){
                    case 'L':
                        decode_clearedRunwayWidth.add(i, decode_clearedRunwayWidth.get(i) + "M LEFT");
                        break;
                    case 'R':
                        decode_clearedRunwayWidth.add(i, decode_clearedRunwayWidth.get(i) + "M RIGHT");
                        break;
                    default:
                        decode_clearedRunwayWidth.add(i, decode_clearedRunwayWidth.get(i) + last + "M");
                        break;
                }
            }
        }

        // F)
        if (code_conditionsRunway!=null) {
            for(i=0 ; i< code_conditionsRunway.size(); i++){
                String[] conditionsRunway = code_conditionsRunway.get(i).split("/");
                decode_conditionsRunway.add(i, "Threshold: " + switchConditions(conditionsRunway[0]) + " / "
                        + "Mid runway: " + switchConditions(conditionsRunway[1]) + " / "
                        + "Roll out: " + switchConditions(conditionsRunway[2]));
            }
        }

        // G)
        if (code_thickness!=null) {
            for(i=0 ; i< code_thickness.size(); i++){
                String[] thickness = code_thickness.get(i).split("/");
                decode_thickness.add(i, "MEAN DEPTH Threshold: " + thickness[0] + "mm / "
                        + "Mid runway: " + thickness[1] + "mm / "
                        + "Roll out: " + thickness[2] + "mm");
            }
        }

        // H)
        if(code_frictionCoef!=null) {
            for(i=0 ; i< code_frictionCoef.size(); i++){
                String[] frictionCoefInstru = code_frictionCoef.get(i).split(" ");          // Separates coef and instrument
                String[] frictionCoef = frictionCoefInstru[0].trim().split("/");        // Separates all coef
                String[] frictionCoefValue = {"", "", ""};                                      // Contains values of coef
                String instru="";

                if (frictionCoef[0].length() == 1) {
                    frictionCoefValue = switchEstimatedCoef(frictionCoef);
                } else {
                    frictionCoefValue = switchCalculatedCoef(frictionCoef);
                }
                if(frictionCoefInstru.length==2){
                    instru = switchInstrument(frictionCoefInstru[1]);
                }

                decode_frictionCoef.add(i, "BRAKING ACTION Threshold: " + frictionCoefValue[0] + " / " +
                        "Mid runway: " + frictionCoefValue[1] + " / " +
                        "Roll out: " + frictionCoefValue[2] + " " +
                        "Instrument: " + instru);
            }
        }

        // J)
        if(code_criticalSnowbanks!=null) {
            for(i=0 ; i< code_criticalSnowbanks.size(); i++){
                String[] criticalSnowbanks = code_criticalSnowbanks.get(i).trim().split("/");
                String directionDistance = criticalSnowbanks[1];
                String[] directionAndDistance = {"", ""};
                directionAndDistance = directionDistanceCriticalSnowbanks(directionDistance);

                decode_criticalSnowbanks.add(i, "CRITICAL SNOW BANK " + criticalSnowbanks[0] + "cm / " +
                        directionAndDistance[0] + "m " + getDirection(directionAndDistance[1]) + " of Runway");
            }
        }

        // K)
        if(code_lightsObscured!=null) {
            for(i=0 ; i< code_lightsObscured.size(); i++){
                String[] lightsObscured = code_lightsObscured.get(i).trim().split(" ");
                decode_lightsObscured.add(i, "Lights obscured: " + lightsObscured[0] + " " +
                        getDirection(lightsObscured[1]) + " of RUNWAY");
            }
        }

        // L)
        if(code_furtherClearance!=null) {
            for(i=0 ; i< code_furtherClearance.size(); i++){
                String[] furtherClearance = code_furtherClearance.get(i).trim().split("/");
                decode_furtherClearance.add(i, "FURTHER CLEARANCE " + furtherClearance[0] + "m / " +
                        furtherClearance[1] + "m");
            }
        }

        // M)
        if(code_anticipatedTimecompletion!=null) {
            for(i=0 ; i< code_anticipatedTimecompletion.size(); i++){
                decode_anticipatedTimecompletion.add(i, "Anticipated time of completion " + code_anticipatedTimecompletion.get(i) + " UTC");
            }
        }

        // N)
        if(code_taxiwaysState!=null) {
            for(i=0 ; i< code_taxiwaysState.size(); i++){
                decode_taxiwaysState.add(i, "Taxiway " + code_taxiwaysState.get(i).substring(0, 1) + " : " +
                        switchConditions(code_taxiwaysState.get(i).substring(1)));
            }
        }

        // P)
        if(code_snowBanks!=null) {
            for(i=0 ; i< code_snowBanks.size(); i++){
                decode_snowBanks.add(i, "SNOW BANKS : YES SPACE" + code_snowBanks.get(i).substring(3) + "m");
            }
        }

        // R)
        Log.d("R) apron",""+code_parking);
        // A REVOIR
        if(code_parking!=null) {
            if (code_parking.contains("NO")){
                decode_parking ="UNUSABLE";
            }
            else {
                decode_parking = code_parking;
            }
        }


        // S)
        if(code_nextObservation!=null) {
            decode_nextObservation = "NEXT OBSERVATION : " + getDate(code_nextObservation).toString() + "UTC";
        }
        // T)
        if (code_comment!=null) {
            decode_comment = code_comment;
        }

    }

    // DATE FONCTION
    Date getDate(String chain){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmm", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(chain);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // F) FONCTIONS
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


    // H) FONCTIONS
    String[] switchEstimatedCoef(String[] chaine){
        String [] value ={"","",""};
        for (int i=0 ; i< chaine.length ; i++){
            String number = chaine[i];
            switch (number.toLowerCase()){
                case "5":
                    value[i] ="GOOD";
                    break;
                case "4":
                    value[i]="MEDIUM TO GOOD";
                    break;
                case "3":
                    value[i]="MEDIUM";
                    break;
                case "2":
                    value[i]="MEDIUM TO POOR";
                    break;
                case "1":
                    value[i]="POOR";
                    break;
            }
        }

        return value;
    }

    String [] switchCalculatedCoef (String[] chaine){
        String [] value ={"","",""};
        for (int i=0 ; i< chaine.length ; i++){
            String number = chaine[i];
            if (Integer.parseInt(number) >= 40){
                value[i] = "GOOD";
            }
            else if(Integer.parseInt(number) <40 && Integer.parseInt(number) >= 36 ) {
                value[i] = "MEDIUM TO GOOD";
            }
            else if(Integer.parseInt(number) <36 && Integer.parseInt(number) >= 30 ) {
                value[i] = "MEDIUM";
            }
            else if(Integer.parseInt(number) <30 && Integer.parseInt(number) >= 26 ) {
                value[i] = "MEDIUM TO POOR";
            }
            else if(Integer.parseInt(number) <26) {
                value[i] = "POOR";
            }
        }
        return value;
    }

    String switchInstrument(String letters){
        String instrument ="";
        switch (letters){
            case "BRD":
                instrument="Brakemeter-Dynometer";
                break;
            case "GRT":
                instrument="Grip tester";
                break;
            case "MUM":
                instrument="Mu-meter";
                break;
            case "RFT":
                instrument ="Runway friction tester";
                break;
            case "SFH":
                instrument="Surface friction tester (high-pressure tire)";
                break;
            case "SFL":
                instrument="Surface friction tester (low-pressure tire)";
                break;
            case "SKH":
                instrument="Skiddometer (high-pressure tire)";
                break;
            case "SKL":
                instrument="Skiddometer (low-pressure tire)";
                break;
            case "TAP":
                instrument="Tapley meter";
                break;
            case "":
                instrument="";
                break;
        }
        return  instrument;
    }

    // J) FONCTIONS
    String[] directionDistanceCriticalSnowbanks (String chain){
        String distance = "";
        String direction ="";
        String[] distanceAndDirection= {"",""};
        switch (chain.length()){
            case 2:
                distance = chain.substring(0,1);
                direction =chain.substring(1);
                break;
            case 3:
                if (chain.substring(1,2).equals("L")){          // EXAMPLE : 3LR
                    distance = chain.substring(0,1);
                    direction = chain.substring(1);
                }
                else{                                           // EXAMPLE : 15L
                    distance = chain.substring(0,2);
                    direction = chain.substring(2);
                }
                break;
            case 4:
                if (chain.substring(2,3).equals("L")){          // EXAMPLE : 15LR
                    distance = chain.substring(0,2);
                    direction = chain.substring(2);
                }
                else {                                          // EXAMPLE : 110R
                    distance = chain.substring(0,3);
                    direction = chain.substring(3);
                }
                break;
            case 5:
                if (chain.substring(3,4).equals("L")){          // EXAMPLE : 110LR
                    distance = chain.substring(0,3);
                    direction = chain.substring(3);
                }
                else {                                          // EXAMPLE : 1124L
                    distance = chain.substring(0,4);
                    direction = chain.substring(4);
                }
        }

        distanceAndDirection[0] = distance;
        distanceAndDirection[1] = direction;
        return  distanceAndDirection;
    }

    String getDirection(String chain){
        String value="";
        switch (chain){
            case "L":
                value = "LEFT";
                break;
            case "R":
                value= "RIGHT";
                break;
            case "LR":
                value = "LEFT AND RIGHT";
                break;
        }
        return value;
    }

    // GETTERS SETTERS

    public AirportInfo getAirport() {
        return airport;
    }

    public void setAirport(AirportInfo airport) {
        this.airport = airport;
    }

    public String getCode_location() {
        return code_location;
    }

    public void setCode_location(String code_location) {
        this.code_location = code_location;
    }

    public String getCode_date() {
        return code_date;
    }

    public void setCode_date(String code_date) {
        this.code_date = code_date;
    }

    public ArrayList<String> getCode_idRunway() {
        return code_idRunway;
    }

    public void setCode_idRunway(ArrayList<String> code_idRunway) {
        this.code_idRunway = code_idRunway;
    }

    public ArrayList<String> getCode_clearedRunwayLength() {
        return code_clearedRunwayLength;
    }

    public void setCode_clearedRunwayLength(ArrayList<String> code_clearedRunwayLength) {
        this.code_clearedRunwayLength = code_clearedRunwayLength;
    }

    public ArrayList<String> getCode_clearedRunwayWidth() {
        return code_clearedRunwayWidth;
    }

    public void setCode_clearedRunwayWidth(ArrayList<String> code_clearedRunwayWidth) {
        this.code_clearedRunwayWidth = code_clearedRunwayWidth;
    }

    public ArrayList<String> getCode_conditionsRunway() {
        return code_conditionsRunway;
    }

    public void setCode_conditionsRunway(ArrayList<String> code_conditionsRunway) {
        this.code_conditionsRunway = code_conditionsRunway;
    }

    public ArrayList<String> getCode_thickness() {
        return code_thickness;
    }

    public void setCode_thickness(ArrayList<String> code_thickness) {
        this.code_thickness = code_thickness;
    }

    public ArrayList<String> getCode_frictionCoef() {
        return code_frictionCoef;
    }

    public void setCode_frictionCoef(ArrayList<String> code_frictionCoef) {
        this.code_frictionCoef = code_frictionCoef;
    }

    public ArrayList<String> getCode_criticalSnowbanks() {
        return code_criticalSnowbanks;
    }

    public void setCode_criticalSnowbanks(ArrayList<String> code_criticalSnowbanks) {
        this.code_criticalSnowbanks = code_criticalSnowbanks;
    }

    public ArrayList<String> getCode_lightsObscured() {
        return code_lightsObscured;
    }

    public void setCode_lightsObscured(ArrayList<String> code_lightsObscured) {
        this.code_lightsObscured = code_lightsObscured;
    }

    public ArrayList<String> getCode_furtherClearance() {
        return code_furtherClearance;
    }

    public void setCode_furtherClearance(ArrayList<String> code_furtherClearance) {
        this.code_furtherClearance = code_furtherClearance;
    }

    public ArrayList<String> getCode_anticipatedTimecompletion() {
        return code_anticipatedTimecompletion;
    }

    public void setCode_anticipatedTimecompletion(ArrayList<String> code_anticipatedTimecompletion) {
        this.code_anticipatedTimecompletion = code_anticipatedTimecompletion;
    }

    public ArrayList<String> getCode_taxiwaysState() {
        return code_taxiwaysState;
    }

    public void setCode_taxiwaysState(ArrayList<String> code_taxiwaysState) {
        this.code_taxiwaysState = code_taxiwaysState;
    }

    public ArrayList<String> getCode_snowBanks() {
        return code_snowBanks;
    }

    public void setCode_snowBanks(ArrayList<String> code_snowBanks) {
        this.code_snowBanks = code_snowBanks;
    }

    public String getCode_parking() {
        return code_parking;
    }

    public void setCode_parking(String code_parking) {
        this.code_parking = code_parking;
    }

    public String getCode_nextObservation() {
        return code_nextObservation;
    }

    public void setCode_nextObservation(String code_nextObservation) {
        this.code_nextObservation = code_nextObservation;
    }

    public String getCode_comment() {
        return code_comment;
    }

    public void setCode_comment(String code_comment) {
        this.code_comment = code_comment;
    }

    public String getDecode_location() {
        return decode_location;
    }

    public void setDecode_location(String decode_location) {
        this.decode_location = decode_location;
    }

    public String getDecode_date() {
        return decode_date;
    }

    public void setDecode_date(String decode_date) {
        this.decode_date = decode_date;
    }

    public ArrayList<String> getDecode_idRunway() {
        return decode_idRunway;
    }

    public void setDecode_idRunway(ArrayList<String> decode_idRunway) {
        this.decode_idRunway = decode_idRunway;
    }

    public ArrayList<String> getDecode_clearedRunwayLength() {
        return decode_clearedRunwayLength;
    }

    public void setDecode_clearedRunwayLength(ArrayList<String> decode_clearedRunwayLength) {
        this.decode_clearedRunwayLength = decode_clearedRunwayLength;
    }

    public ArrayList<String> getDecode_clearedRunwayWidth() {
        return decode_clearedRunwayWidth;
    }

    public void setDecode_clearedRunwayWidth(ArrayList<String> decode_clearedRunwayWidth) {
        this.decode_clearedRunwayWidth = decode_clearedRunwayWidth;
    }

    public ArrayList<String> getDecode_conditionsRunway() {
        return decode_conditionsRunway;
    }

    public void setDecode_conditionsRunway(ArrayList<String> decode_conditionsRunway) {
        this.decode_conditionsRunway = decode_conditionsRunway;
    }

    public ArrayList<String> getDecode_thickness() {
        return decode_thickness;
    }

    public void setDecode_thickness(ArrayList<String> decode_thickness) {
        this.decode_thickness = decode_thickness;
    }

    public ArrayList<String> getDecode_frictionCoef() {
        return decode_frictionCoef;
    }

    public void setDecode_frictionCoef(ArrayList<String> decode_frictionCoef) {
        this.decode_frictionCoef = decode_frictionCoef;
    }

    public ArrayList<String> getDecode_criticalSnowbanks() {
        return decode_criticalSnowbanks;
    }

    public void setDecode_criticalSnowbanks(ArrayList<String> decode_criticalSnowbanks) {
        this.decode_criticalSnowbanks = decode_criticalSnowbanks;
    }

    public ArrayList<String> getDecode_lightsObscured() {
        return decode_lightsObscured;
    }

    public void setDecode_lightsObscured(ArrayList<String> decode_lightsObscured) {
        this.decode_lightsObscured = decode_lightsObscured;
    }

    public ArrayList<String> getDecode_furtherClearance() {
        return decode_furtherClearance;
    }

    public void setDecode_furtherClearance(ArrayList<String> decode_furtherClearance) {
        this.decode_furtherClearance = decode_furtherClearance;
    }

    public ArrayList<String> getDecode_anticipatedTimecompletion() {
        return decode_anticipatedTimecompletion;
    }

    public void setDecode_anticipatedTimecompletion(ArrayList<String> decode_anticipatedTimecompletion) {
        this.decode_anticipatedTimecompletion = decode_anticipatedTimecompletion;
    }

    public ArrayList<String> getDecode_taxiwaysState() {
        return decode_taxiwaysState;
    }

    public void setDecode_taxiwaysState(ArrayList<String> decode_taxiwaysState) {
        this.decode_taxiwaysState = decode_taxiwaysState;
    }

    public ArrayList<String> getDecode_snowBanks() {
        return decode_snowBanks;
    }

    public void setDecode_snowBanks(ArrayList<String> decode_snowBanks) {
        this.decode_snowBanks = decode_snowBanks;
    }

    public String getDecode_parking() {
        return decode_parking;
    }

    public void setDecode_parking(String decode_parking) {
        this.decode_parking = decode_parking;
    }

    public String getDecode_nextObservation() {
        return decode_nextObservation;
    }

    public void setDecode_nextObservation(String decode_nextObservation) {
        this.decode_nextObservation = decode_nextObservation;
    }

    public String getDecode_comment() {
        return decode_comment;
    }

    public void setDecode_comment(String decode_comment) {
        this.decode_comment = decode_comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(airport, i);
        parcel.writeString(code_location);
        parcel.writeString(code_date);
        parcel.writeStringList(code_idRunway);
        parcel.writeStringList(code_clearedRunwayLength);
        parcel.writeStringList(code_clearedRunwayWidth);
        parcel.writeStringList(code_conditionsRunway);
        parcel.writeStringList(code_thickness);
        parcel.writeStringList(code_frictionCoef);
        parcel.writeStringList(code_criticalSnowbanks);
        parcel.writeStringList(code_lightsObscured);
        parcel.writeStringList(code_furtherClearance);
        parcel.writeStringList(code_anticipatedTimecompletion);
        parcel.writeStringList(code_taxiwaysState);
        parcel.writeStringList(code_snowBanks);
        parcel.writeString(code_parking);
        parcel.writeString(code_nextObservation);
        parcel.writeString(code_comment);
        parcel.writeString(decode_location);
        parcel.writeString(decode_date);
        parcel.writeStringList(decode_idRunway);
        parcel.writeStringList(decode_clearedRunwayLength);
        parcel.writeStringList(decode_clearedRunwayWidth);
        parcel.writeStringList(decode_conditionsRunway);
        parcel.writeStringList(decode_thickness);
        parcel.writeStringList(decode_frictionCoef);
        parcel.writeStringList(decode_criticalSnowbanks);
        parcel.writeStringList(decode_lightsObscured);
        parcel.writeStringList(decode_furtherClearance);
        parcel.writeStringList(decode_anticipatedTimecompletion);
        parcel.writeStringList(decode_taxiwaysState);
        parcel.writeStringList(decode_snowBanks);
        parcel.writeString(decode_parking);
        parcel.writeString(decode_nextObservation);
        parcel.writeString(decode_comment);
    }
}
