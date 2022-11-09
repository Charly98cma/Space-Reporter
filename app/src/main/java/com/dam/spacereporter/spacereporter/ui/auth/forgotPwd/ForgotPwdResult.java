package com.dam.spacereporter.spacereporter.ui.auth.forgotPwd;

import androidx.annotation.Nullable;

/**
 * Data validation state of the forgotPwd form.
 */
@SuppressWarnings({"CanBeFinal"})
public class ForgotPwdResult {

    @Nullable
    private Integer error;

    private boolean success;

    ForgotPwdResult(@Nullable Integer error) {
        this.error = error;
    }

    @SuppressWarnings("SameParameterValue")
    ForgotPwdResult(boolean success) {
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
