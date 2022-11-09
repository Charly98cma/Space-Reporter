package com.dam.spacereporter.spacereporter.ui.auth.login;

import androidx.annotation.Nullable;

class LoginResult {

    @Nullable
    private Integer error;

    private boolean success;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    @SuppressWarnings("SameParameterValue")
    LoginResult(boolean success) {
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
