package com.dam.spacereporter.spacereporter.ui.auth.signup;

import androidx.annotation.Nullable;

/**
 * Data validation state of the signup form.
 */
@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
public class SignUpFormState {

    @Nullable
    private Integer fullNameError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirmPasswordError;

    private boolean isDataValid;

    SignUpFormState(@Nullable Integer fullNameError,
                    @Nullable Integer usernameError,
                    @Nullable Integer emailError,
                    @Nullable Integer passwordError,
                    @Nullable Integer confirmPasswordError) {
        this.fullNameError = fullNameError;
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.isDataValid = false;
    }

    @SuppressWarnings("SameParameterValue")
    SignUpFormState(boolean isDataValid) {
        this.fullNameError = null;
        this.usernameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.confirmPasswordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getFullNameError() {
        return fullNameError;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
