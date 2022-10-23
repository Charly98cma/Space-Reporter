package com.dam.spacereporter.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.dam.spacereporter.R;

public class Utils {

    public static void toastException(Context context) {
        if (context == null) return;
        Toast.makeText(context, context.getText(R.string.global_unexpected_exception), Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetwork() != null;
    }
}
