package com.dam.spacereporter.Utils;

import androidx.core.util.PatternsCompat;

import java.util.regex.Pattern;

public class DataValidator {

    // Password requirements
    private static final Pattern uppercase = Pattern.compile("[A-Z]");
    private static final Pattern lowercase = Pattern.compile("[a-z]");
    private static final Pattern digit = Pattern.compile("[0-9]");

    public static boolean validatePassword(String pwd, String rpwd) {
        return isValidPassword(pwd) && isValidPassword(rpwd);
    }

    private static boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                uppercase.matcher(password).find() &&
                lowercase.matcher(password).find() &&
                digit.matcher(password).find();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidEmail(String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }
}
