package com.dam.spacereporter.spacereporter.ui.auth.forgotPwd;

import androidx.annotation.Nullable;

@SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
public class ForgotPwdFormState {

    @Nullable
    private Integer emailError;

    private boolean isDataValid;

    ForgotPwdFormState(@Nullable Integer emailError) {
        this.emailError = emailError;
        this.isDataValid = false;
    }

    @SuppressWarnings("SameParameterValue")
    ForgotPwdFormState(boolean isDataValid) {
        this.emailError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
