package com.example.james.demotab2.Model;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CoursesDto implements Serializable {

    private int courseID;
    private String course_name;
    private String roi;
    private int sectionID;
    private int classRoomID;
    private int valid;
    private int grades;

    public CoursesDto(JSONObject jsonObject) throws JSONException{
        courseID = jsonObject.getInt("courseID");
        course_name = jsonObject.getString("course_name");
        roi = jsonObject.getString("roi");
        sectionID = jsonObject.getInt("sectionID");
        classRoomID = jsonObject.getInt("classRoomID");
        valid = jsonObject.getInt("valid");
        grades = jsonObject.getInt("grades");
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public int getClassRoomID() {
        return classRoomID;
    }

    public void setClassRoomID(int classRoomID) {
        this.classRoomID = classRoomID;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getGrades() {
        return grades;
    }

    public void setGrades(int grades) {
        this.grades = grades;
    }
}

