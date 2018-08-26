package com.example.james.demotab2.Fragments.Gimbal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.james.demotab2.Fragments.ProfessorTab.ProfessorInfo;
import com.example.james.demotab2.MainActivity;
import com.example.james.demotab2.Model.CoursesDto;
import com.example.james.demotab2.Model.HotspotDto;
import com.example.james.demotab2.Model.ProfessorDto;
import com.example.james.demotab2.R;
import com.example.james.demotab2.Utilities.AjendaContext;
import com.example.james.demotab2.Utilities.Requests;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class GimbalFragment extends Fragment implements OnMapReadyCallback {

    static final int PICK_VIEW_REQUEST = 0;  // The request code
    private final LatLng center = new LatLng(37.977689, 23.783292);
    public MenuItem searchItem;
    TextView resultMarker;
    View mView;
    Requests requests = new Requests();
    List<HotspotDto> hotspotDtoList = new ArrayList<>();
    List<String> markers = new ArrayList<>();
    String upDate = "";
    private GoogleMap mGoogleMap;
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.gimbal_fragment, container, false);
        resultMarker = (TextView) mView.findViewById(R.id.resultMarker);
        resultMarker.setText("Επιλέξτε σημείο στο χάρτη για Πληροφορίες");
        activity = (MainActivity) getActivity();

        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        downloadInfos();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        searchItem = menu.findItem(R.id.menu_search);
        searchItem.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void downloadInfos() {
        try {
            requests.getAllHotspots(new Requests.VolleyCallbackHotspots() {
                @Override
                public void onSuccess(JSONArray jsonArray) {

                    markers.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            HotspotDto hotspotDto = new HotspotDto(jsonArray.getJSONObject(i));
                            hotspotDtoList.add(hotspotDto);
                            markers.add(hotspotDto.getSpotname());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapView mMapView = (MapView) mView.findViewById(R.id.myMap);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(AjendaContext.getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap = googleMap;
        for (int i = 0; i < hotspotDtoList.size(); i++) {
            HotspotDto hotspotDto = hotspotDtoList.get(i);
            LatLng latLng = new LatLng(hotspotDto.getLatitude(), hotspotDto.getLongitude());
            final MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(hotspotDto.getSpotname());
            googleMap.addMarker(markerOptions);
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    selectedInfoWindow(marker.getTitle());
                }
            });
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 16.0f));
    }


    private HotspotDto mapNameToHotSpotDto(String spotName){
        for (int i = 0; i < hotspotDtoList.size(); i++)
            if(hotspotDtoList.get(i).getSpotname().equals(spotName))
                return hotspotDtoList.get(i);
        return null;
    }

    private void selectedInfoWindow(String spotName) {
        HotspotDto hotspotDto = mapNameToHotSpotDto(spotName);
        if(hotspotDto != null) {
            try {
                if (hotspotDto.getType().equals("class")) {
                    if (hotspotDto.getHotspotID() == 1) {
                        requests.getCurrentSubject(1, new Requests.VolleyCallbackCurrentSubject() {
                            @Override
                            public void onSuccess(JSONArray jsonArray) {

                                if (jsonArray.length() == 0) {
                                    upDate = "Δεν γίνεται Μάθημα αυτή τη στιγμή";
                                } else {
                                    try {
                                        CoursesDto coursesDto = new CoursesDto(jsonArray.getJSONObject(0));
                                        upDate = "Τρέχων Μάθημα :: " + coursesDto.getCourse_name();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                resultMarker.setText(upDate);
                            }
                        });

                    } else {
                        resultMarker.setText("Δεν γίνεται Μάθημα αυτή τη στιγμή");
                    }

                } else if (hotspotDto.getType().equals("office")) {
                    requests.getVisitProfessor(1, new Requests.VolleyCallbackVisitProfessor() {
                        @Override
                        public void onSuccess(JSONArray jsonArray) {
                            try {
                                ProfessorDto professorDto = new ProfessorDto(jsonArray.getJSONObject(0));
                                Intent intent = new Intent(activity, ProfessorInfo.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("myProfessorDto", professorDto);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
