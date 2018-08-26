package com.example.james.demotab2.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by James on 11/25/2015.
 */
public class OfficeDto implements Serializable {

    private int officeID;
    private double longitude;
    private double latitude;
    private String phone;
    private String number;
    private String floor;
    private String building;

    public OfficeDto(JSONObject jsonObject) throws JSONException {
        officeID = jsonObject.getInt("officeID");
        phone = jsonObject.getString("phone");
        number = jsonObject.getString("number");
        floor = jsonObject.getString("floor");
        building = jsonObject.getString("building");
        longitude = jsonObject.getDouble("longitude");
        latitude = jsonObject.getDouble("latitude");
    }


    public int getOfficeID() {
        return officeID;
    }

    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longtitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

}
