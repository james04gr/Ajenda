package com.example.james.demotab2.Fragments.ProfessorTab;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.james.demotab2.Model.CoursesDto;
import com.example.james.demotab2.Model.OfficeDto;
import com.example.james.demotab2.Model.ProfessorDto;
import com.example.james.demotab2.Model.SectionDto;
import com.example.james.demotab2.R;
import com.example.james.demotab2.Utilities.AjendaContext;
import com.example.james.demotab2.Utilities.Requests;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.location.LocationServices.API;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class ProfessorInfo extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    final static int REQUEST_LOCATION = 100;
    ProfessorDto myProfessorDto;
    private Requests requests = new Requests();
    private RadioGroup radioGroup;
    private Button subjectsBtn, professor_map, gradesBtn, validBtn, announceBtn;
    private TextView result_textView;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_info);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(AjendaContext.getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(API)
                    .build();
        }

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupProf);
        subjectsBtn = (Button) findViewById(R.id.subjects_prof_Btn);
        professor_map = (Button) findViewById(R.id.professor_map);
        gradesBtn = (Button) findViewById(R.id.grades);
        validBtn = (Button) findViewById(R.id.valid);
        announceBtn = (Button) findViewById(R.id.announce);
        TextView fullName_textView = (TextView) findViewById(R.id.fullname_prof_textView);
        result_textView = (TextView) findViewById(R.id.result_prof_textView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        myProfessorDto = (ProfessorDto) bundle.getSerializable("myProfessorDto");
        fullName_textView.setText(myProfessorDto.getFull_name());

        try {
            downloadInfos(myProfessorDto.getProfessorID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void downloadInfos(int professorID) throws JSONException {

        requests.getProfessorSectionOffice(professorID, new Requests.VolleyCallbackSectionOffice() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    final JSONObject officeJson = jsonArray.getJSONObject(0);
                    final JSONObject sectionJson = jsonArray.getJSONObject(1);
                    final List<CoursesDto> coursesDtoList = new ArrayList<>();
                    requests.getProfessorSubjectList(myProfessorDto.getProfessorID(), new Requests.VolleyCallbackProfessorSubjectList() {
                        @Override
                        public void onSuccess(JSONArray jsonArray) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jo = jsonArray.getJSONObject(i);
                                    CoursesDto coursesDto = new CoursesDto(jo);
                                    coursesDtoList.add(coursesDto);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            setUpTabs(officeJson, sectionJson, coursesDtoList);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setUpTabs(final JSONObject officeJson, final JSONObject sectionJson, final List<CoursesDto> coursesDtoList) {
        String string = "Email:  " + myProfessorDto.getEmail();
        result_textView.setText(string);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.email_prof_Btn) {
                    String string = "Email:  " + myProfessorDto.getEmail();
                    result_textView.setText(string);

                } else if (checkedId == R.id.office_prof_Btn) {
                    try {
                        OfficeDto officeDto = new OfficeDto(officeJson);
                        String string = "Τηλέφωνο:  " + officeDto.getPhone();
                        result_textView.setText(string);
                        result_textView.append("\nΓραφείο:   " + officeDto.getNumber());
                        result_textView.append("\nΚτήριο:    " + officeDto.getBuilding());
                        result_textView.append("\nΌροφος:    " + officeDto.getFloor());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (checkedId == R.id.section_prof_Btn) {
                    try {
                        SectionDto sectionDto = new SectionDto(sectionJson);
                        String string = "Τομέας:  " + sectionDto.getSection_name();
                        result_textView.setText(string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        subjectsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfessorInfo.this, ProfessorSubjects.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CoursesDtoList", (Serializable) coursesDtoList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        gradesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                String string = "";
                for (int i = 0; i < coursesDtoList.size(); i++) {
                    CoursesDto coursesDto = coursesDtoList.get(i);
                    if (coursesDto.getGrades() == 1) {
                        count++;
                        string = string + coursesDto.getCourse_name() + "\n";
                    }
                }
                if (count == 0)
                    string = "Δεν έχουν βγει βαθμολογίες για κάποιο μάθημα του καθηγητή!";
                new AlertDialog.Builder(ProfessorInfo.this)
                        .setTitle("Βαθμολογιες!")
                        .setMessage(string)
                        .setCancelable(true)
                        .show();
            }
        });

        validBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                String string = "";
                for (int i = 0; i < coursesDtoList.size(); i++) {
                    CoursesDto coursesDto = coursesDtoList.get(i);
                    if (coursesDto.getValid() == 0) {
                        count++;
                        string = string + coursesDto.getCourse_name() + "\n";
                    }
                }
                if (count == 0)
                    string = "Όλα τα Μαθήματα γίνονται κανονικά!";
                new AlertDialog.Builder(ProfessorInfo.this)
                        .setTitle("Άκυρα Μαθήματα!")
                        .setMessage(string)
                        .setCancelable(true)
                        .show();
            }
        });

        announceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myProfessorDto.getAnnounce() == 0) {
                    new AlertDialog.Builder(ProfessorInfo.this)
                            .setTitle("Ανακοίνωση")
                            .setMessage("Δεν υπάρχει κάποια διαθέσιμη ανακοίνωση")
                            .setCancelable(true)
                            .show();
                } else if (myProfessorDto.getAnnounce() == 1) {
                    new AlertDialog.Builder(ProfessorInfo.this)
                            .setTitle("Ανακοίνωση")
                            .setMessage(myProfessorDto.getAnnounce_text())
                            .setCancelable(true)
                            .show();
                }
            }
        });

        professor_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(30 * 1000);
                locationRequest.setFastestInterval(5 * 1000);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest);

                builder.setAlwaysShow(true);

                PendingResult<LocationSettingsResult> result =
                        LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        final LocationSettingsStates state = result.getLocationSettingsStates();
                        switch (status.getStatusCode()) {

                            case LocationSettingsStatusCodes.SUCCESS:
                                try {
                                    mLastLocation = getmLastLocation();

                                    if (mLastLocation == null)
                                        Toast.makeText(ProfessorInfo.this, "Αναμονή τοποθεσίας..Δοκιμάστε ξανά", Toast.LENGTH_SHORT).show();
                                    else {
                                        OfficeDto officeDto = new OfficeDto(officeJson);
                                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", mLastLocation.getLatitude(), mLastLocation.getLongitude(), "Είστε εδώ!", officeDto.getLatitude(), officeDto.getLongitude(), "Γραφείο Καθηγητή ");
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;

                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                try {
                                    status.startResolutionForResult(
                                            ProfessorInfo.this, REQUEST_LOCATION);
                                } catch (IntentSender.SendIntentException e) {
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Toast.makeText(ProfessorInfo.this, "Unavailable Location!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLastLocation = getmLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case RESULT_OK: {
                        mLastLocation = getmLastLocation();
                        break;
                    }
                    case RESULT_CANCELED: {
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
        }

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private Location getmLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            System.out.println("Permision not Granted..!!!!!!!!!!!!!!!!!!!");
            return null;
        } else {
            return mLastLocation = FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }
}
