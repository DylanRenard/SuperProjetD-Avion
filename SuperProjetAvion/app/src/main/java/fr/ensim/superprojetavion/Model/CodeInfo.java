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

    public CodeInfo(SnowtamInfo snowtamInfo){
        
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
        String[] frictionCoefInstru = code_frictionCoef.split(" ");  // Separates coef and instrument
        String[] frictionCoef = frictionCoefInstru[0].split("/");    // Separates all coef
        String[] frictionCoefValue={"","",""};                            // Contains values of coef
        if(frictionCoef[0].length() == 1){
            frictionCoefValue = switchEstimatedCoef(frictionCoef);
        }
        else{
            frictionCoefValue = switchCalculatedCoef(frictionCoef);
        }
        decode_frictionCoef = "BRAKING ACTION Threshold: " + frictionCoefValue[0] +  " / " +
                                "Mid runway: " + frictionCoefValue[1] + " / " +
                                "Roll out: " + frictionCoefValue[2] + " " +
                                "Instrument: " + switchInstrument(frictionCoefInstru[1]);


        // J)
        String[] criticalSnowbanks = code_criticalSnowbanks.split("/");
        String directionDistance = criticalSnowbanks[1];
        String distance = "";
        String direction ="";
        if(directionDistance.length() == 2 ){
            distance = directionDistance.substring(0,1);
            direction =directionDistance.substring(2);
        }
        if (directionDistance.length() == 3){
            if (directionDistance.substring(2,3).equals("L")){
                
            }
        }

        decode_criticalSnowbanks = "CRITICAL SNOW BANK " + criticalSnowbanks[0] + "cm / " +
                                    distance + "m" + direction + " of Runway";


        // K)

        // L)

        // M)

        // N)

        // P)


        // R)

        // S)


        // T)


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
        switch (letters.toLowerCase()){
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
