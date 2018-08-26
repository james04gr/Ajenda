package com.example.james.demotab2.Fragments.SubjectTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.james.demotab2.Model.ClassRoomDto;
import com.example.james.demotab2.Model.CoursesDto;
import com.example.james.demotab2.Model.ProfessorDto;
import com.example.james.demotab2.Model.SectionDto;
import com.example.james.demotab2.Model.TimeDto;
import com.example.james.demotab2.R;
import com.example.james.demotab2.Utilities.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubjectInfo extends AppCompatActivity {

    CoursesDto myCourseDto;
    private TextView subjectName, resultView;
    private RadioGroup radioGroup;
    private Button professorsBtn, grade, valid;
    private Requests requests = new Requests();
    private JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_info);

        subjectName = (TextView) findViewById(R.id.fullname_sub_textView);
        resultView = (TextView) findViewById(R.id.result_sub_textView);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupSub);
        professorsBtn = (Button) findViewById(R.id.professors_sub_Btn);
        grade = (Button) findViewById(R.id.sub_grade);
        valid = (Button) findViewById(R.id.sub_valid);
        RadioButton sectionBtn = (RadioButton) findViewById(R.id.section_sub_Btn);
        RadioButton classBtn = (RadioButton) findViewById(R.id.class_sub_Btn);
        RadioButton timeBtn = (RadioButton) findViewById(R.id.time_sub_Btn);

        //----------------Get Data from Intent--------------//
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        myCourseDto = (CoursesDto) bundle.getSerializable("myCourseDto");
        subjectName.setText(myCourseDto.getCourse_name());

        //----------Download Subject Infos from Server--------------//
        if (savedInstanceState == null) {
            try {
                downloadInfos(myCourseDto.getCourseID(), myCourseDto.getSectionID(), myCourseDto.getClassRoomID());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            setUpTabs(jsonObject);
        }

        professorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    requests.getSubjectProfessorList(myCourseDto.getCourseID(), new Requests.VolleyCallbackSubjectProfessorList() {
                        @Override
                        public void onSuccess(JSONArray jsonArray) {
                            List<ProfessorDto> professorDtoList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jo = jsonArray.getJSONObject(i);
                                    ProfessorDto professorDto = new ProfessorDto(jo);
                                    professorDtoList.add(professorDto);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Intent intent = new Intent(SubjectInfo.this, SubjectProfessors.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("ProfessorsDtoList", (Serializable) professorDtoList);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downloadInfos(final int courseID, int sectionID, int classRoomID) throws JSONException {
        requests.getSubjectInformation(courseID, sectionID, classRoomID, new Requests.VolleyCallbackSubjectInfos() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    jsonObject = jsonArray.getJSONObject(0);
                    setUpTabs(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void setUpTabs(final JSONObject jsonObject) {

        subjectName.setText(myCourseDto.getCourse_name());

        try {
            JSONObject jo = jsonObject.getJSONObject("section");
            SectionDto sectionDto = new SectionDto(jo);
            String string = "Τομέας:  " + sectionDto.getSection_name();
            resultView.setText(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.section_sub_Btn) {
                    try {
                        JSONObject jo = jsonObject.getJSONObject("section");
                        SectionDto sectionDto = new SectionDto(jo);
                        String string = "Τομέας:  " + sectionDto.getSection_name();
                        resultView.setText(string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (checkedId == R.id.class_sub_Btn) {
                    try {
                        JSONObject jo = jsonObject.getJSONObject("classRoom");
                        ClassRoomDto classRoomDto = new ClassRoomDto(jo);
                        String string = "Αίθουσα:  " + classRoomDto.getNumber();
                        resultView.setText(string);
                        resultView.append("\nΚτήριο:  " + classRoomDto.getBuilding());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (checkedId == R.id.time_sub_Btn) {
                    try {
                        resultView.setText("");
                        JSONArray jo = jsonObject.getJSONArray("times");
                        for (int i = 0; i < jo.length(); i++) {
                            TimeDto timeDto = new TimeDto(jo.getJSONObject(i));
                            String string = "Ημέρα:  " + timeDto.getDay();
                            resultView.setText(string);
                            resultView.append("\nΈναρξη:  " + timeDto.getStart_hour());
                            resultView.append("\nΛήξη:  " + timeDto.getEnd_hour() + "\n");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string;
                if (myCourseDto.getGrades() == 1) {
                    string = "Έχουν ανακοινωθεί βαθμολογίες γι' αυτό το μάθημα";
                } else {
                    string = "Δεν έχουν βγει βαθμολογίες γι' αυτό το μάθημα!";
                }

                new AlertDialog.Builder(SubjectInfo.this)
                        .setTitle("Βαθμολογία!")
                        .setMessage(string)
                        .setCancelable(true)
                        .show();
            }
        });

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string;
                if (myCourseDto.getValid() == 1) {
                    string = "Το μάθημα θα πραγματοποιηθεί κανονικά!";
                } else {
                    string = "Το επόμενο μάθημα έχει ακυρωθεί!";
                }
                new AlertDialog.Builder(SubjectInfo.this)
                        .setTitle("Ακύρωση!")
                        .setMessage(string)
                        .setCancelable(true)
                        .show();
            }
        });
    }
}
