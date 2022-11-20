package com.dam.spacereporter.spacereporter.ui.auth;

import androidx.annotation.NonNull;
import androidx.core.util.PatternsCompat;

import java.util.regex.Pattern;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class DataValidator {

    private static final Integer FULLNAME_MAX_LEN = 30;
    private static final Integer USERNAME_MAX_LEN = 20;
    private static final Integer PWD_MIN_LEN = 8;

    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("[0-9]");

    public static boolean isFullNameValid(String fullName) {
        return fullName != null && !fullName.isEmpty() && fullName.length() <= FULLNAME_MAX_LEN;
    }

    public static boolean isUsernameValid(String username) {
        return username != null && !username.isEmpty() && username.length() <= USERNAME_MAX_LEN;
    }

    public static boolean isEmailValid(CharSequence email) {
        return email != null && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(CharSequence password) {
        return password != null &&
                password.length() >= PWD_MIN_LEN &&
                UPPERCASE.matcher(password).find() &&
                LOWERCASE.matcher(password).find() &&
                DIGIT.matcher(password).find();
    }

    public static boolean doPasswordsMatch(@NonNull String password, @NonNull String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
