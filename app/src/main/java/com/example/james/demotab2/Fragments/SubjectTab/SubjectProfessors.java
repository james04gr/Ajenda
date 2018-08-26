package com.example.james.demotab2.Fragments.SubjectTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.james.demotab2.Fragments.ProfessorTab.ProfessorInfo;
import com.example.james.demotab2.MainActivity;
import com.example.james.demotab2.Model.ProfessorDto;
import com.example.james.demotab2.R;
import com.example.james.demotab2.Utilities.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SubjectProfessors extends AppCompatActivity {

    ListView professors_listView;
    List<ProfessorDto> professorDtoList;
    List<String> professorList;
    MainActivity activity;
    private ProfessorDto myProfessorDto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_professors);

        professors_listView = (ListView) findViewById(R.id.professors_listView);
        professorDtoList = new ArrayList<>();
        activity = new MainActivity();

        //----------------Get Data from Intent--------------//
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        professorDtoList = new ArrayList<>();
        professorDtoList = (List<ProfessorDto>) bundle.getSerializable("ProfessorsDtoList");
        professorList = new ArrayList<>();
        for (int i=0; i<professorDtoList.size(); i++)
            professorList.add(professorDtoList.get(i).getFull_name());

        setUpAdapter(professorList,professorDtoList);
    }


    private void setUpAdapter(List<String> list, final List<ProfessorDto> professorDtoList) {
        ListViewAdapter listViewAdapter = new ListViewAdapter(this, R.layout.professor_item, list, 0);
        professors_listView.setAdapter(listViewAdapter);
        final List<ProfessorDto> myDto = professorDtoList;

        professors_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                for (int i=0; i<professorDtoList.size(); i++){
                    if (myDto.get(i).getFull_name().equals(selection)) {
                        myProfessorDto = myDto.get(i);
                        break;
                    }
                }

                Intent intent = new Intent(SubjectProfessors.this, ProfessorInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("myProfessorDto", myProfessorDto);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
