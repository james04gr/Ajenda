package com.example.james.demotab2.Utilities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.james.demotab2.Fragments.ProfessorTab.ProfessorInfo;
import com.example.james.demotab2.Fragments.SubjectTab.SubjectInfo;
import com.example.james.demotab2.MainActivity;
import com.example.james.demotab2.Model.CoursesDto;
import com.example.james.demotab2.Model.HotspotDto;
import com.example.james.demotab2.Model.ProfessorDto;
import com.example.james.demotab2.R;

import org.json.JSONArray;
import org.json.JSONException;


public class VisitNotify {

    private Requests requests = new Requests();
    private HotspotDto hotspotDto;
    private Context context;

    public VisitNotify(HotspotDto hotspotDto) {
        this.hotspotDto = hotspotDto;
        context = AjendaContext.getContext();
    }

    public void OfficeType() {
        try {
            requests.getVisitProfessor(1, new Requests.VolleyCallbackVisitProfessor() {
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    try {
                        System.out.println("Results :: "+jsonArray.toString());
                        ProfessorDto professorDto = new ProfessorDto(jsonArray.getJSONObject(0));
                        String upDate = "Δείτε πληροφορίες για τον Καθηγητή!";
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                        mBuilder.setSmallIcon(R.drawable.notify_img);
                        mBuilder.setContentTitle("Γραφείο " + professorDto.getFull_name());
                        mBuilder.setContentText(upDate);
                        mBuilder.setAutoCancel(true);

                        Intent resultIntent = new Intent(context, ProfessorInfo.class);
                        resultIntent.putExtra("myProfessorDto", professorDto);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(AjendaContext.getContext());

                        stackBuilder.addParentStack(MainActivity.class);

                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(
                                        0,
                                        PendingIntent.FLAG_CANCEL_CURRENT
                                );
                        mBuilder.setContentIntent(resultPendingIntent);

                        NotificationManager mNotificationManager =
                                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                        mNotificationManager.notify(1, mBuilder.build());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ClassType() {
        try {
            requests.getCurrentSubject(getClassID(hotspotDto.getSpotname()), new Requests.VolleyCallbackCurrentSubject() {
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    try {

                        if (jsonArray.length() == 0) {
                            String upDate = "Δεν γίνεται Μάθημα αυτή τη στιγμή";
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                            mBuilder.setSmallIcon(R.drawable.notify_img);
                            mBuilder.setContentTitle(hotspotDto.getSpotname());
                            mBuilder.setContentText(upDate);
                            mBuilder.setAutoCancel(true);

                            NotificationManager mNotificationManager =
                                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(2, mBuilder.build());
                        } else {
                            CoursesDto coursesDto = new CoursesDto(jsonArray.getJSONObject(0));
                            String upDate = "Δείτε ποιο Μάθημα γίνετε αυτή τη στιγμή!";
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                            mBuilder.setSmallIcon(R.drawable.notify_img);
                            mBuilder.setContentTitle(hotspotDto.getSpotname());
                            mBuilder.setContentText(upDate);
                            mBuilder.setAutoCancel(true);

                            Intent resultIntent = new Intent(context, SubjectInfo.class);
                            resultIntent.putExtra("myCourseDto", coursesDto);
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(AjendaContext.getContext());

                            stackBuilder.addParentStack(MainActivity.class);

                            stackBuilder.addNextIntent(resultIntent);
                            PendingIntent resultPendingIntent =
                                    stackBuilder.getPendingIntent(
                                            0,
                                            PendingIntent.FLAG_CANCEL_CURRENT
                                    );
                            mBuilder.setContentIntent(resultPendingIntent);

                            NotificationManager mNotificationManager =
                                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                            mNotificationManager.notify(1, mBuilder.build());
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

    private int getClassID(String string) {
        switch (string) {
            case "Αμφ. 1":
                return 1;
            case "Αμφ. 2":
                return 2;
            case "Αμφ. 3":
                return 3;
            case "Αμφ. 4":
                return 4;
            case "Αμφ. 5":
                return 5;
        }
        return 0;
    }
}
