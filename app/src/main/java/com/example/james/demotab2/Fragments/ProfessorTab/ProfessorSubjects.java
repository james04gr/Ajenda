package com.example.james.demotab2.Fragments.ProfessorTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.james.demotab2.Fragments.SubjectTab.SubjectInfo;
import com.example.james.demotab2.MainActivity;
import com.example.james.demotab2.Model.CoursesDto;
import com.example.james.demotab2.R;
import com.example.james.demotab2.Utilities.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfessorSubjects extends AppCompatActivity {

    ListView subjects_listView;
    List<CoursesDto> coursesDtoList;
    List<String> courseList;
    MainActivity activity;
    private CoursesDto myCourseDto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_subjects);
        activity = new MainActivity();

        subjects_listView = (ListView) findViewById(R.id.subjects_listView);

        //----------------Get Data from Intent--------------//
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        coursesDtoList = new ArrayList<>();
        coursesDtoList = (List<CoursesDto>) bundle.getSerializable("CoursesDtoList");
        courseList = new ArrayList<>();
        for (int i = 0; i < coursesDtoList.size(); i++)
            courseList.add(coursesDtoList.get(i).getCourse_name());

        setUpAdapter(courseList,coursesDtoList);

    }

    private void setUpAdapter(List<String> list,List<CoursesDto> coursesDtoList) {
        ListViewAdapter listViewAdapter = new ListViewAdapter(this, R.layout.course_item, list, 1);
        subjects_listView.setAdapter(listViewAdapter);
        final List<CoursesDto> myDto = coursesDtoList;

        subjects_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                for (int i=0; i< myDto.size(); i++){
                    if (myDto.get(i).getCourse_name().equals(selection)) {
                        myCourseDto = myDto.get(i);
                        break;
                    }
                }

                Intent intent = new Intent(ProfessorSubjects.this, SubjectInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("myCourseDto", myCourseDto);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
