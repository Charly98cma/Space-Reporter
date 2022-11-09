package com.dam.spacereporter.spacereporter.ui.auth.forgotPwd;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.ui.auth.DataValidator;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPwdViewModel extends ViewModel {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final MutableLiveData<ForgotPwdFormState> forgotPwdFormState = new MutableLiveData<>();
    private final MutableLiveData<ForgotPwdResult> forgotPwdResult = new MutableLiveData<>();

    ForgotPwdViewModel() {
    }

    LiveData<ForgotPwdFormState> getForgotPwdFormState() {
        return forgotPwdFormState;
    }

    LiveData<ForgotPwdResult> getForgotPwdResult() {
        return forgotPwdResult;
    }

    public void sendPasswordResetEmail(String email) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                forgotPwdResult.setValue(new ForgotPwdResult(true));
            else
                forgotPwdResult.setValue(new ForgotPwdResult(R.string.forgotPwd_error_emailUnregistered));
        });
    }

    public void forgotPwdDataChanged(String email) {
        if (!DataValidator.isEmailValid(email))
            forgotPwdFormState.setValue(new ForgotPwdFormState(R.string.forgotPwd_error_invalidEmail));
        else
            forgotPwdFormState.setValue(new ForgotPwdFormState(true));
    }
}
