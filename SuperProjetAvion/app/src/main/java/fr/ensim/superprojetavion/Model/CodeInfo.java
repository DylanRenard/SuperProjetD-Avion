package fr.ensim.superprojetavion.Model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.sql.Types.NULL;

/**
 * Created by Dana on 15/11/2018.
 */

public class CodeInfo {

    public CodeInfo(String info, AirportInfo airportInfo){
        this.airport = airportInfo;

        this.code_location = airportInfo.getOaciCode();
        if(info.contains("B) ")) this.code_date = info.substring(info.indexOf("B) ")+3,info.indexOf(')',info.indexOf("B) ")+2)-1);
        else this.code_date = null;
        if(info.contains("C) ")) this.code_idRunway = info.substring(info.indexOf("C) ")+3,info.indexOf(')',info.indexOf("C) ")+2)-1);
        else this.code_idRunway = null;
        if(info.contains("D) ")) this.code_clearedRunwayLength = info.substring(info.indexOf("D) ")+3,info.indexOf(')',info.indexOf("D) ")+2)-1);
        else this.code_clearedRunwayLength = null;
        if(info.contains("E) ")) this.code_clearedRunwayWidth = info.substring(info.indexOf("E) ")+3,info.indexOf(')',info.indexOf("E) ")+2)-1);
        else this.code_clearedRunwayWidth = null;
        if(info.contains("F) ")) this.code_conditionsRunway = info.substring(info.indexOf("F) ")+3,info.indexOf(')',info.indexOf("F) ")+2)-1);
        else this.code_conditionsRunway = null;
        if(info.contains("G) ")) this.code_thickness = info.substring(info.indexOf("G) ")+3,info.indexOf(')',info.indexOf("G) ")+2)-1);
        else this.code_thickness = null;
        if(info.contains("H) ")) this.code_frictionCoef = info.substring(info.indexOf("H) ")+3,info.indexOf(')',info.indexOf("H) ")+2)-1);
        else this.code_frictionCoef = null;
        if(info.contains("J) ")) this.code_criticalSnowbanks = info.substring(info.indexOf("J) ")+3,info.indexOf(')',info.indexOf("J) ")+2)-1);
        else this.code_criticalSnowbanks = null;
        if(info.contains("K) ")) this.code_lightsObscured = info.substring(info.indexOf("K) ")+3,info.indexOf(')',info.indexOf("K) ")+2)-1);
        else this.code_lightsObscured = null;
        if(info.contains("L) ")) this.code_furtherClearance = info.substring(info.indexOf("L) ")+3,info.indexOf(')',info.indexOf("L) ")+2)-1);
        else this.code_furtherClearance = null;
        if(info.contains("M) ")) this.code_anticipatedTimecompletion = info.substring(info.indexOf("M) ")+3,info.indexOf(')',info.indexOf("M) ")+2)-1);
        else this.code_anticipatedTimecompletion = null;
        if(info.contains("N) ")) this.code_taxiwaysState = info.substring(info.indexOf("N) ")+3,info.indexOf(')',info.indexOf("N) ")+2)-1);
        else this.code_taxiwaysState = null;
        if(info.contains("P) ")) this.code_snowBanks = info.substring(info.indexOf("P) ")+3,info.indexOf(')',info.indexOf("P) ")+2)-1);
        else this.code_snowBanks = null;
        if(info.contains("R) ")) this.code_parking = info.substring(info.indexOf("R) ")+3,info.indexOf(')',info.indexOf("R) ")+2)-1);
        else this.code_parking = null;
        if(info.contains("S) ")) this.code_nextObservation = info.substring(info.indexOf("S) ")+3,info.indexOf(')',info.indexOf("S) ")+2)-1);
        else this.code_nextObservation = null;
        if(info.contains("T) ")) this.code_comment = info.substring(info.indexOf("T) ")+3,info.indexOf(')',info.indexOf("T) ")+2)-1);
        else this.code_comment = null;

        Log.d("TEST SNOWTAM : ", this.code_location+"\n"+this.code_date+"\n"+this.code_idRunway+this.code_clearedRunwayLength);
        //this.onDecode();
    }

    private AirportInfo airport;

    private String code_location;                       // A) aerodrome location indicator
    private String code_date;                           // B) observation date & time
    private String code_idRunway;                       // C) runway designator
    private String code_clearedRunwayLength;            // D) cleared runway length
    private String code_clearedRunwayWidth;             // E) cleared runway width
    private String code_conditionsRunway;               // F) deposits over total runway length
    private String code_thickness;                      // G) mean depth deposit for each third of total runway length
    private String code_frictionCoef;                   // H) friction measurements and friction measurement device
    private String code_criticalSnowbanks;              // J) critical snowbanks
    private String code_lightsObscured;                 // K) runway lights
    private String code_furtherClearance;               // L) further clearance
    private String code_anticipatedTimecompletion;      // M) further clearance expected to be completed
    private String code_taxiwaysState;                  // N) taxiway
    private String code_snowBanks;                      // P) taxiway snowbanks
    private String code_parking;                        // R) apron
    private String code_nextObservation;                // S) next planned observation/measurement
    private String code_comment;                        // T) plain-language remarks

    private String decode_location;
    private String decode_date;
    private String decode_idRunway;
    private String decode_clearedRunwayLength;
    private String decode_clearedRunwayWidth;
    private String decode_conditionsRunway;
    private String decode_thickness;
    private String decode_frictionCoef;
    private String decode_criticalSnowbanks;
    private String decode_lightsObscured;
    private String decode_furtherClearance;
    private String decode_anticipatedTimecompletion;
    private String decode_taxiwaysState;
    private String decode_snowBanks;
    private String decode_parking;
    private String decode_nextObservation;
    private String decode_comment;


    void onDecode() {
        // A)
        decode_location = airport.getAirportName();


        // B)
        if (!code_date.equals(NULL)){
            decode_date = getDate(code_date).toString();
        }


        // C)
        if(!code_idRunway.equals(NULL)) {
            if (code_idRunway.equals("88")) {
                decode_idRunway = "ALL RUNWAYS";
            } else {
                decode_idRunway = "RUNWAY " + code_idRunway;
            }
        }

        // D)
        if (!code_clearedRunwayLength.equals(NULL)) {
            decode_clearedRunwayLength = " CLEARED RUNWAY LENGTH " + code_clearedRunwayLength + "M";
        }


        // E)
        if(!code_clearedRunwayWidth.equals(NULL)) {
            String axis = "";
            String[] clearedRunwayWidth = code_clearedRunwayWidth.split("");
            if (clearedRunwayWidth[clearedRunwayWidth.length - 1].equals("L")) {
                axis = "LEFT";
            } else {
                axis = "RIGHT";
            }

            if (clearedRunwayWidth.length == 2) {
                decode_clearedRunwayWidth = "CLEARED RUNWAY WIDTH " + clearedRunwayWidth[0] + "M " + axis;
            } else {
                decode_clearedRunwayWidth = "CLEARED RUNWAY WIDTH " + clearedRunwayWidth[0] + clearedRunwayWidth[1] + "M " + axis;
            }
        }


        // F)
        if (!code_conditionsRunway.equals(NULL)) {
            String[] conditionsRunway = code_conditionsRunway.split("/");
            decode_conditionsRunway = "Threshold: " + switchConditions(conditionsRunway[0]) + " / "
                    + "Mid runway: " + switchConditions(conditionsRunway[1]) + " / "
                    + "Roll out: " + switchConditions(conditionsRunway[2]);
        }


        // G)
        if (!code_thickness.equals(NULL)) {
            String[] thickness = code_thickness.split("/");
            decode_thickness = "MEAN DEPTH Threshold: " + thickness[0] + "mm / "
                    + "Mid runway: " + thickness[1] + "mm / "
                    + "Roll out: " + thickness[2] + "mm";
        }


        // H)
        if(!code_frictionCoef.equals(NULL)) {
            String[] frictionCoefInstru = code_frictionCoef.split(" ");  // Separates coef and instrument
            String[] frictionCoef = frictionCoefInstru[0].split("/");    // Separates all coef
            String[] frictionCoefValue = {"", "", ""};                            // Contains values of coef
            if (frictionCoef[0].length() == 1) {
                frictionCoefValue = switchEstimatedCoef(frictionCoef);
            } else {
                frictionCoefValue = switchCalculatedCoef(frictionCoef);
            }
            decode_frictionCoef = "BRAKING ACTION Threshold: " + frictionCoefValue[0] + " / " +
                    "Mid runway: " + frictionCoefValue[1] + " / " +
                    "Roll out: " + frictionCoefValue[2] + " " +
                    "Instrument: " + switchInstrument(frictionCoefInstru[1]);
        }


        // J)
        if(!code_criticalSnowbanks.equals(NULL)) {
            String[] criticalSnowbanks = code_criticalSnowbanks.split("/");
            String directionDistance = criticalSnowbanks[1];
            String[] directionAndDistance = {"", ""};
            directionAndDistance = directionDistanceCriticalSnowbanks(directionDistance);

            decode_criticalSnowbanks = "CRITICAL SNOW BANK " + criticalSnowbanks[0] + "cm / " +
                    directionAndDistance[0] + "m " + getDirection(directionAndDistance[1]) + " of Runway";
        }


        // K)
        if(!code_lightsObscured.equals(NULL)) {
            String[] lightsObscured = code_lightsObscured.split(" ");
            decode_lightsObscured = "Lights obscured: " + lightsObscured[0] + " " +
                    getDirection(lightsObscured[1]) + " of RUNWAY";
        }


        // L)
        if(!code_furtherClearance.equals(NULL)) {
            String[] furtherClearance = code_furtherClearance.split("/");
            decode_furtherClearance = "FURTHER CLEARANCE " + furtherClearance[0] + "m / " +
                    furtherClearance[1] + "m";
        }


        // M)
        if(!code_anticipatedTimecompletion.equals(NULL)) {
            decode_anticipatedTimecompletion = "Anticipated time of completion " + code_anticipatedTimecompletion + " UTC";
        }


        // N)
        if(!code_taxiwaysState.equals(NULL)) {
            decode_taxiwaysState = "Taxiway " + code_taxiwaysState.substring(0, 1) + " : " +
                    switchConditions(code_taxiwaysState.substring(1));
        }


        // P)
        if(!code_snowBanks.equals(NULL)) {
            decode_snowBanks = "SNOW BANKS : YES SPACE" + code_snowBanks.substring(3) + "m";
        }


        // R)
        if(!code_parking.equals(NULL)) {
            String[] usabilityParking = decode_parking.split(" ");
            String usability = "";
            if (usabilityParking[1].equals("NO")) {
                usability = "UNUSABLE";
            }
            decode_parking = "Parking " + usabilityParking[0] + " " + usability;
        }


        // S)
        if(!code_nextObservation.equals(NULL)) {
            decode_nextObservation = "NEXT OBSERVATION : " + getDate(code_nextObservation).toString() + "UTC";
        }
        // T)
        if (!code_comment.equals(NULL)) {
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

    public String getCode_idRunway() {
        return code_idRunway;
    }

    public void setCode_idRunway(String code_idRunway) {
        this.code_idRunway = code_idRunway;
    }

    public String getCode_clearedRunwayLength() {
        return code_clearedRunwayLength;
    }

    public void setCode_clearedRunwayLength(String code_clearedRunwayLength) {
        this.code_clearedRunwayLength = code_clearedRunwayLength;
    }

    public String getCode_clearedRunwayWidth() {
        return code_clearedRunwayWidth;
    }

    public void setCode_clearedRunwayWidth(String code_clearedRunwayWidth) {
        this.code_clearedRunwayWidth = code_clearedRunwayWidth;
    }

    public String getCode_conditionsRunway() {
        return code_conditionsRunway;
    }

    public void setCode_conditionsRunway(String code_conditionsRunway) {
        this.code_conditionsRunway = code_conditionsRunway;
    }

    public String getCode_thickness() {
        return code_thickness;
    }

    public void setCode_thickness(String code_thickness) {
        this.code_thickness = code_thickness;
    }

    public String getCode_frictionCoef() {
        return code_frictionCoef;
    }

    public void setCode_frictionCoef(String code_frictionCoef) {
        this.code_frictionCoef = code_frictionCoef;
    }

    public String getCode_criticalSnowbanks() {
        return code_criticalSnowbanks;
    }

    public void setCode_criticalSnowbanks(String code_criticalSnowbanks) {
        this.code_criticalSnowbanks = code_criticalSnowbanks;
    }

    public String getCode_lightsObscured() {
        return code_lightsObscured;
    }

    public void setCode_lightsObscured(String code_lightsObscured) {
        this.code_lightsObscured = code_lightsObscured;
    }

    public String getCode_furtherClearance() {
        return code_furtherClearance;
    }

    public void setCode_furtherClearance(String code_furtherClearance) {
        this.code_furtherClearance = code_furtherClearance;
    }

    public String getCode_anticipatedTimecompletion() {
        return code_anticipatedTimecompletion;
    }

    public void setCode_anticipatedTimecompletion(String code_anticipatedTimecompletion) {
        this.code_anticipatedTimecompletion = code_anticipatedTimecompletion;
    }

    public String getCode_taxiwaysState() {
        return code_taxiwaysState;
    }

    public void setCode_taxiwaysState(String code_taxiwaysState) {
        this.code_taxiwaysState = code_taxiwaysState;
    }

    public String getCode_snowBanks() {
        return code_snowBanks;
    }

    public void setCode_snowBanks(String code_snowBanks) {
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

    public String getDecode_idRunway() {
        return decode_idRunway;
    }

    public void setDecode_idRunway(String decode_idRunway) {
        this.decode_idRunway = decode_idRunway;
    }

    public String getDecode_clearedRunwayLength() {
        return decode_clearedRunwayLength;
    }

    public void setDecode_clearedRunwayLength(String decode_clearedRunwayLength) {
        this.decode_clearedRunwayLength = decode_clearedRunwayLength;
    }

    public String getDecode_clearedRunwayWidth() {
        return decode_clearedRunwayWidth;
    }

    public void setDecode_clearedRunwayWidth(String decode_clearedRunwayWidth) {
        this.decode_clearedRunwayWidth = decode_clearedRunwayWidth;
    }

    public String getDecode_conditionsRunway() {
        return decode_conditionsRunway;
    }

    public void setDecode_conditionsRunway(String decode_conditionsRunway) {
        this.decode_conditionsRunway = decode_conditionsRunway;
    }

    public String getDecode_thickness() {
        return decode_thickness;
    }

    public void setDecode_thickness(String decode_thickness) {
        this.decode_thickness = decode_thickness;
    }

    public String getDecode_frictionCoef() {
        return decode_frictionCoef;
    }

    public void setDecode_frictionCoef(String decode_frictionCoef) {
        this.decode_frictionCoef = decode_frictionCoef;
    }

    public String getDecode_criticalSnowbanks() {
        return decode_criticalSnowbanks;
    }

    public void setDecode_criticalSnowbanks(String decode_criticalSnowbanks) {
        this.decode_criticalSnowbanks = decode_criticalSnowbanks;
    }

    public String getDecode_lightsObscured() {
        return decode_lightsObscured;
    }

    public void setDecode_lightsObscured(String decode_lightsObscured) {
        this.decode_lightsObscured = decode_lightsObscured;
    }

    public String getDecode_furtherClearance() {
        return decode_furtherClearance;
    }

    public void setDecode_furtherClearance(String decode_furtherClearance) {
        this.decode_furtherClearance = decode_furtherClearance;
    }

    public String getDecode_anticipatedTimecompletion() {
        return decode_anticipatedTimecompletion;
    }

    public void setDecode_anticipatedTimecompletion(String decode_anticipatedTimecompletion) {
        this.decode_anticipatedTimecompletion = decode_anticipatedTimecompletion;
    }

    public String getDecode_taxiwaysState() {
        return decode_taxiwaysState;
    }

    public void setDecode_taxiwaysState(String decode_taxiwaysState) {
        this.decode_taxiwaysState = decode_taxiwaysState;
    }

    public String getDecode_snowBanks() {
        return decode_snowBanks;
    }

    public void setDecode_snowBanks(String decode_snowBanks) {
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
}
