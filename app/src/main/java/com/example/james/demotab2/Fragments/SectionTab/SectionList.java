package com.example.james.demotab2.Fragments.SectionTab;

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
import com.example.james.demotab2.Model.SectionDto;
import com.example.james.demotab2.R;
import com.example.james.demotab2.Utilities.ListViewAdapter;
import com.example.james.demotab2.Utilities.Requests;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class SectionList extends Fragment {

    static final int PICK_VIEW_REQUEST = 0;  // The request code
    private final int CURRENT_TAB = 2;
    ListView section_listView;
    Requests requests = new Requests();
    List<SectionDto> sectionDtoList;
    ArrayList<String> sectionList;
    MainActivity activity;
    private SectionDto mySectionDto;

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
            downloadSections(getString(R.string.Section));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.sections_list, container, false);
        section_listView = (ListView) view.findViewById(R.id.section_list);
        activity = (MainActivity) getActivity();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {

            ArrayList<String> list = new ArrayList<>();
            list.addAll(sectionList);
            setUpAdapter(list, sectionDtoList);
        }

    }

    private void downloadSections(String data) throws JSONException {

        sectionList = new ArrayList<>();
        sectionDtoList = new ArrayList<>();

        requests.getSearchSecList(data, new Requests.VolleyCallbackSearchSecList() {
            @Override
            public void onSuccess(JSONArray jsonArray) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        SectionDto sectionDto = new SectionDto(jsonArray.getJSONObject(i));
                        sectionDtoList.add(sectionDto);
                        sectionList.add(sectionDto.getSection_name());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (activity.getCurrentTab() == 2) {
                    ArrayList<String> list = new ArrayList<>();
                    list.addAll(sectionList);
                    setUpAdapter(list, sectionDtoList);
                }
            }
        });
    }

    private void setUpAdapter(List<String> list, List<SectionDto> sectionDtoList) {
        activity.setAdapter(new ListViewAdapter(activity, R.layout.section_item, list, CURRENT_TAB));
        section_listView.setAdapter(activity.getAdapter());

        final List<SectionDto> myDto = sectionDtoList;

        section_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                for (int i = 0; i < myDto.size(); i++) {
                    if (myDto.get(i).getSection_name().equals(selection)) {
                        mySectionDto = myDto.get(i);
                        break;
                    }
                }
                Intent intent = new Intent(activity, SectionInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mySectionDto", mySectionDto);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, PICK_VIEW_REQUEST);
            }
        });
    }
}