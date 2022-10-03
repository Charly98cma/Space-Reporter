package com.dam.spacereporter.utils;

import android.content.Context;
import android.widget.Toast;

import com.dam.spacereporter.R;

public class Utils {

    public static void toastException(Context context) {
        Toast.makeText(context, context.getText(R.string.global_unexpected_exception), Toast.LENGTH_SHORT).show();
    }

}
