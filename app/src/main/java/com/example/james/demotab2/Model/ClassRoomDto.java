package com.example.james.demotab2.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by James on 12/9/2015.
 */
public class ClassRoomDto implements Serializable {


    private int classroomID;
    private double longitude;
    private double latitude;
    private String building;
    private String number;

    public ClassRoomDto (JSONObject jsonObject) throws JSONException{
        classroomID = jsonObject.getInt("classRoomID");
        building = jsonObject.getString("building");
        number = jsonObject.getString("number");
        longitude = jsonObject.getDouble("longitude");
        latitude = jsonObject.getDouble("latitude");
    }



    public int getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(int classroomID) {
        this.classroomID = classroomID;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
