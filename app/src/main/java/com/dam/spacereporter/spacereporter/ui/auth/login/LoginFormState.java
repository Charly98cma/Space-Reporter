package com.dam.spacereporter.spacereporter.ui.auth.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
public class LoginFormState {

    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;

    private boolean isDataValid;

    LoginFormState(@Nullable Integer emailError, @Nullable Integer passwordError) {
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    @SuppressWarnings("SameParameterValue")
    LoginFormState(boolean isDataValid) {
        this.emailError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}