package com.example.james.demotab2.Fragments.Gimbal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.james.demotab2.Model.HotspotDto;
import com.example.james.demotab2.Utilities.AjendaContext;
import com.example.james.demotab2.Utilities.Requests;
import com.example.james.demotab2.Utilities.VisitNotify;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Visit;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class GimbalServiceFinder extends Service {

    private static final String Gimbal_API_KEY = "f9a75eff-d6cc-42c3-ad7b-34c3bd61a540";
    PlaceEventListener placeEventListener;
    PlaceManager placeManager;
    Requests requests = new Requests();
    List<String> beaconList = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }


    @Override
    public void onCreate() {

        Gimbal.setApiKey(this.getApplication(), Gimbal_API_KEY);
        Gimbal.start();

        if (!Gimbal.isStarted()) {
            Toast toast = Toast.makeText(AjendaContext.getContext(), "Gimbal failed to start", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(AjendaContext.getContext(), "Gimbal started", Toast.LENGTH_SHORT);
            toast.show();

            //Listening for Places//
            placeEventListener = new PlaceEventListener() {
                @Override
                public void onBeaconSighting(BeaconSighting beaconSighting, List<Visit> list) {
                    if (!foundBefore(beaconSighting.getBeacon().getIdentifier())) {
                        System.out.println("---------------BeaconFound :: " + beaconSighting.getBeacon().getIdentifier());
                        try {
                            requests.getHotspotID(beaconSighting.getBeacon().getIdentifier(), new Requests.VolleyCallbackHotspotID() {
                                @Override
                                public void onSuccess(JSONArray jsonArray) {
                                    try {
                                        System.out.println("Hotspot :: " + jsonArray.toString());
                                        HotspotDto hotspotDto = new HotspotDto(jsonArray.getJSONObject(0));
                                        VisitNotify visitNotify = new VisitNotify(hotspotDto);
                                        if (hotspotDto.getType().equals("office")) {
                                            visitNotify.OfficeType();
                                        } else if (hotspotDto.getType().equals("class")) {
                                            visitNotify.ClassType();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


                @Override
                public void onVisitStart(Visit visit) {
                    super.onVisitStart(visit);
                }
            };

            placeManager = PlaceManager.getInstance();
            placeManager.addListener(placeEventListener);
            placeManager.startMonitoring();
        }
    }

    private boolean foundBefore(String beaconIdentifier){
        System.out.println(beaconList.toString());
        if (beaconList.contains(beaconIdentifier)) {
            return true;
        } else {
            beaconList.add(beaconIdentifier);
            return false;
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
        super.onTaskRemoved(rootIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}