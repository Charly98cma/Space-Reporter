package com.dam.spacereporter.spacereporter.ui.auth.signup;

import androidx.annotation.Nullable;

public class SignUpResult {

    private boolean success;
    @Nullable
    private Integer error;

    SignUpResult(@Nullable Integer error) {
        this.error = error;
    }

    @SuppressWarnings("SameParameterValue")
    SignUpResult(boolean success) {
        this.success = success;
    }

    boolean getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
