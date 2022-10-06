package com.dam.spacereporter.utils;

import androidx.core.util.PatternsCompat;

public class EmailValidator {

    public static boolean isValidEmail(String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }
}
