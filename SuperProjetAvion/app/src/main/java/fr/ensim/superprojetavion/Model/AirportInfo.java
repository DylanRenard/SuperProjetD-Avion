package fr.ensim.superprojetavion.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class AirportInfo implements Serializable,Parcelable{

    //Declaration
    private String oaciCode;
    private String airportName;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String timeZone;
    private String flag;
    private String location;

    private boolean favoris;

    //builder
    public AirportInfo(){}

    //Parcelable stuff
    protected AirportInfo(Parcel in) {
        oaciCode = in.readString();
        airportName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        phoneNumber = in.readString();
        timeZone = in.readString();
        flag = in.readString();
        location = in.readString();
        favoris = in.readByte() != 0;
    }

    public static final Creator<AirportInfo> CREATOR = new Creator<AirportInfo>() {
        @Override
        public AirportInfo createFromParcel(Parcel in) {
            return new AirportInfo(in);
        }

        @Override
        public AirportInfo[] newArray(int size) {
            return new AirportInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(oaciCode);
        parcel.writeString(airportName);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(phoneNumber);
        parcel.writeString(timeZone);
        parcel.writeString(flag);
        parcel.writeString(location);
        parcel.writeByte((byte) (favoris ? 1 : 0));
    }

    //function to display info from airport
    @Override
    public String toString(){
        return "name : "+this.airportName+"\noaci : "+this.oaciCode+"\nCoords : "+this.latitude+"/"+this.longitude;
    }

    //getters and setters
    public boolean isfavoris() {
        return favoris;
    }

    public void setfavoris(boolean favoris) {
        this.favoris = favoris;
    }

    public String getOaciCode() {
        return oaciCode;
    }

    public void setOaciCode(String oaciCode) {
        this.oaciCode = oaciCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
