package com.example.james.demotab2.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by James on 11/25/2015.
 */
public class SectionDto implements Serializable {

    private int sectionID;
    private String section_name;

    public SectionDto(JSONObject jsonObject) throws JSONException {
        sectionID = jsonObject.getInt("sectionID");
        section_name = jsonObject.getString("section_name");
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }
}
