package com.example.james.demotab2.Model;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ProfessorDto implements Serializable {

    private int professorID;
    private String full_name;
    private String email;
    private int sectionID;
    private int officeID;
    private int announce;
    private String announce_text;

    public String getAnnounce_text() {
        return announce_text;
    }

    public void setAnnounce_text(String announce_text) {
        this.announce_text = announce_text;
    }

    public int getAnnounce() {
        return announce;
    }

    public void setAnnounce(int announce) {
        this.announce = announce;
    }

    public ProfessorDto(JSONObject jsonObject) throws JSONException{
        professorID = jsonObject.getInt("professorID");
        full_name = jsonObject.getString("full_name");
        email = jsonObject.getString("email");
        sectionID = jsonObject.getInt("sectionID");
        officeID = jsonObject.getInt("officeID");
        announce = jsonObject.getInt("announce");
        announce_text = jsonObject.getString("announce_text");


    }


    public int getProfessorID() {
        return professorID;
    }

    public void setProfessorID(int professorID) {
        this.professorID = professorID;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public int getOfficeID() {
        return officeID;
    }

    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }


}
