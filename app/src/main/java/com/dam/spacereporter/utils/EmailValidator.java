package com.dam.spacereporter.utils;

import android.util.Patterns;

public class EmailValidator {

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
