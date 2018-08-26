package com.example.james.demotab2.Fragments.SectionTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.james.demotab2.Fragments.ProfessorTab.ProfessorInfo;
import com.example.james.demotab2.Fragments.SubjectTab.SubjectInfo;
import com.example.james.demotab2.Model.CoursesDto;
import com.example.james.demotab2.Model.ProfessorDto;
import com.example.james.demotab2.Model.SectionDto;
import com.example.james.demotab2.R;
import com.example.james.demotab2.Utilities.ListViewAdapter;
import com.example.james.demotab2.Utilities.Requests;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SectionInfo extends AppCompatActivity {

    private TextView sectionName;
    private RadioGroup radioGroup;
    private RadioButton professorsRadioBtn, coursesRadioBtn;
    private ListView listView;
    private SectionDto mySectionDto;
    private Requests request = new Requests();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.section_info);

        sectionName = (TextView) findViewById(R.id.fullname_sec_textView);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        professorsRadioBtn = (RadioButton) findViewById(R.id.professorsRadioBtn);
        coursesRadioBtn = (RadioButton) findViewById(R.id.coursesRadioBtn);
        listView = (ListView) findViewById(R.id.listview);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mySectionDto = (SectionDto) bundle.getSerializable("mySectionDto");
        sectionName.setText(mySectionDto.getSection_name());

        try {
            downloadInfos(mySectionDto.getSectionID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void downloadInfos(int sectionID) throws JSONException {
        request.getSectionInformation(sectionID, new Requests.VolleyCallbackSectionInfos() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {

                    JSONArray professorsJsonArray = jsonArray.getJSONArray(0);
                    JSONArray coursesJsonArray = jsonArray.getJSONArray(1);

                    List<ProfessorDto> professorDtoList = new ArrayList<>();
                    List<CoursesDto> courseDtoList = new ArrayList<>();

                    ArrayList<String> profNameList = new ArrayList<>();
                    ArrayList<String> subNameList = new ArrayList<>();

                    for (int i=0; i<professorsJsonArray.length(); i++){
                        ProfessorDto professorDto = new ProfessorDto(professorsJsonArray.getJSONObject(i));
                        professorDtoList.add(professorDto);
                        profNameList.add(professorDto.getFull_name());
                    }

                    for (int i=0; i<coursesJsonArray.length(); i++){
                        CoursesDto coursesDto = new CoursesDto(coursesJsonArray.getJSONObject(i));
                        courseDtoList.add(coursesDto);
                        subNameList.add(coursesDto.getCourse_name());
                    }

                    setUpAdapter(profNameList,subNameList,professorDtoList,courseDtoList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setUpAdapter(final List<String> profNames, final List<String> subNames, final List<ProfessorDto> professorDtoList, final List<CoursesDto> coursesDtoList){
        final ListViewAdapter listViewAdapterProf = new ListViewAdapter(this, R.layout.professor_item, profNames, 0);
        final ListViewAdapter listViewAdapterSub = new ListViewAdapter(this, R.layout.course_item, subNames, 1);
        listView.setAdapter(listViewAdapterProf);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.coursesRadioBtn){
                    listView.setAdapter(listViewAdapterSub);
                } else if (checkedId == R.id.professorsRadioBtn){
                    listView.setAdapter(listViewAdapterProf);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String selection = (String) parent.getItemAtPosition(position);
                if (selectedId == professorsRadioBtn.getId()){
                    ProfessorDto myProfessorDto = null;
                    for (int i=0; i<professorDtoList.size(); i++){
                        if (professorDtoList.get(i).getFull_name().equals(selection)){
                            myProfessorDto = professorDtoList.get(i);
                            break;
                        }
                    }
                    Intent intent = new Intent(SectionInfo.this, ProfessorInfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("myProfessorDto", myProfessorDto);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else if (selectedId == coursesRadioBtn.getId()){
                    CoursesDto myCourseDto = null;
                    for (int i=0; i<coursesDtoList.size(); i++){
                        if (coursesDtoList.get(i).getCourse_name().equals(selection)){
                            myCourseDto = coursesDtoList.get(i);
                            break;
                        }
                    }
                    Intent intent = new Intent(SectionInfo.this, SubjectInfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("myCourseDto", myCourseDto);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
