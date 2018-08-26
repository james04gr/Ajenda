package com.example.james.demotab2.Utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by James on 10/7/2016.
 */

public class ErrorToast {

    public void ShowToastMsg(String data) {
        Context context = AjendaContext.getContext();
        CharSequence text = "Error onResponse fetching data from " + data;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}