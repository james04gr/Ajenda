package com.example.james.demotab2.Model;

/**
 * Created by James on 12/12/2015.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class TimeDto implements Serializable {

    private int timeID;
    private String day;
    private String start_hour;
    private String end_hour;

    public TimeDto(JSONObject jsonObject) throws JSONException{
        timeID = jsonObject.getInt("timeID");
        day = jsonObject.getString("day");
        start_hour = jsonObject.getString("start_hour");
        end_hour = jsonObject.getString("end_hour");
    }

    public int getTimeID() {
        return timeID;
    }

    public void setTimeID(int timeID) {
        this.timeID = timeID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(String end_hour) {
        this.end_hour = end_hour;
    }

}

