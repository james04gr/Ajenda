package com.example.james.demotab2.Fragments.SubjectTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.james.demotab2.MainActivity;
import com.example.james.demotab2.Model.CoursesDto;
import com.example.james.demotab2.R;
import com.example.james.demotab2.Utilities.ListViewAdapter;
import com.example.james.demotab2.Utilities.Requests;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class SubjectList extends Fragment {

    static final int PICK_VIEW_REQUEST = 0;  // The request code
    private final int CURRENT_TAB = 1;
    ArrayList<String> coursesList = new ArrayList<>();
    private ListView subject_listView;
    private Requests requests = new Requests();
    private List<CoursesDto> coursesDtoList;
    private MainActivity activity = (MainActivity) getActivity();
    private CoursesDto myCourseDto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();

    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            activity = (MainActivity) getActivity();
            downloadSubjects(getString(R.string.Subjects));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.subjects_list, container, false);
        subject_listView = (ListView) view.findViewById(R.id.subject_list);
        activity = (MainActivity) getActivity();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {

            ArrayList<String> list = new ArrayList<>();
            list.addAll(coursesList);
            setUpAdapter(list, coursesDtoList);
        }
    }

    private void downloadSubjects(String data) throws JSONException {

        coursesList = new ArrayList<>();
        coursesDtoList = new ArrayList<>();

        requests.getSearchSubjList(data, new Requests.VolleyCallbackSearchSubjList() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        CoursesDto coursesDto = new CoursesDto(jsonArray.getJSONObject(i));
                        coursesDtoList.add(coursesDto);
                        coursesList.add(coursesDto.getCourse_name());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (activity.getCurrentTab() == 1) {
                    ArrayList<String> list = new ArrayList<>();
                    list.addAll(coursesList);
                    setUpAdapter(list, coursesDtoList);
                }
            }
        });
    }

    private void setUpAdapter(List<String> list, List<CoursesDto> coursesDtoList) {
        activity.setAdapter(new ListViewAdapter(activity, R.layout.professor_item, list, CURRENT_TAB));
        subject_listView.setAdapter(activity.getAdapter());

        final List<CoursesDto> myDto = coursesDtoList;

        subject_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                for (int i = 0; i < myDto.size(); i++) {
                    if (myDto.get(i).getCourse_name().equals(selection)) {
                        myCourseDto = myDto.get(i);
                        break;
                    }
                }
                Intent intent = new Intent(activity, SubjectInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("myCourseDto", myCourseDto);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, PICK_VIEW_REQUEST);
            }
        });
    }


}