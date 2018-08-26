package com.example.james.demotab2.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class HotspotDto implements Serializable {

    private int hotspotID;
    private double latitude;
    private double longitude;
    private String spotname;
    private String gimbalID;
    private String type;



    public HotspotDto(JSONObject jsonObject) throws JSONException {
        hotspotID = jsonObject.getInt("hotspotID");
        latitude = jsonObject.getDouble("latitude");
        longitude = jsonObject.getDouble("longitude");
        spotname = jsonObject.getString("spotname");
        gimbalID = jsonObject.getString("gimbalID");
        type = jsonObject.getString("type");
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getHotspotID() {
        return hotspotID;
    }

    public void setHotspotID(int hotspotID) {
        this.hotspotID = hotspotID;
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

    public String getSpotname() {
        return spotname;
    }

    public void setSpotname(String spotname) {
        this.spotname = spotname;
    }


    public String getGimbalID() {
        return gimbalID;
    }

    public void setGimbalID(String gimbalID) {
        this.gimbalID = gimbalID;
    }
}
