package com.example.james.demotab2.Fragments.ProfessorTab;

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
import com.example.james.demotab2.Model.ProfessorDto;
import com.example.james.demotab2.R;
import com.example.james.demotab2.Utilities.ListViewAdapter;
import com.example.james.demotab2.Utilities.Requests;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ProfessorsList extends Fragment {

    static final int PICK_VIEW_REQUEST = 0;  // The request code
    private final int CURRENT_TAB = 0;
    List<ProfessorDto> professorDtoList;
    ArrayList<String> full_nameList;
    private ListView professor_listView;
    private Requests requests = new Requests();
    private MainActivity activity;
    private ProfessorDto myProfessorDto;

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
            downloadProfessors(getString(R.string.FullName));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.professors_list, container, false);
        professor_listView = (ListView) view.findViewById(R.id.professor_list);


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        System.out.println("onUserVisibleHint ProfList");

        if (isVisibleToUser && isResumed()) {
            ArrayList<String> list = new ArrayList<>();
            list.addAll(full_nameList);
            setUpAdapter(list, professorDtoList);
        }
    }

    private void downloadProfessors(String data) throws JSONException {

        professorDtoList = new ArrayList<>();
        full_nameList = new ArrayList<>();

        System.out.println("Download Professors.....");

        requests.getSearchProfList(data, new Requests.VolleyCallbackSearchProfList() {
                    @Override
                    public void onSuccess(JSONArray jsonArray) {

                        System.out.println("Response Called.....");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                ProfessorDto professorDto = new ProfessorDto(jsonArray.getJSONObject(i));
                                professorDtoList.add(professorDto);
                                full_nameList.add(professorDto.getFull_name());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (activity.getCurrentTab() == 0) {
                            ArrayList<String> list = new ArrayList<>();
                            list.addAll(full_nameList);
                            setUpAdapter(list, professorDtoList);
                        }
                    }
                }
        );
    }

    private void setUpAdapter(List<String> list, List<ProfessorDto> professorDtoList) {

        activity.setAdapter(new ListViewAdapter(activity, R.layout.professor_item, list, CURRENT_TAB));
        professor_listView.setAdapter(activity.getAdapter());

        final List<ProfessorDto> myDto = professorDtoList;

        professor_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selection = (String) parent.getItemAtPosition(position);
                for (int i = 0; i < myDto.size(); i++) {
                    if (myDto.get(i).getFull_name().equals(selection)) {
                        myProfessorDto = myDto.get(i);
                        break;
                    }
                }
                Intent intent = new Intent(activity, ProfessorInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("myProfessorDto", myProfessorDto);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, PICK_VIEW_REQUEST);
            }
        });
    }


}
