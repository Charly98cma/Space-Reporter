package com.dam.spacereporter.spacereporter.ui.auth.signup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.utils.NetworkConnection;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpViewModel = new ViewModelProvider(this, new SignUpViewModelFactory())
                .get(SignUpViewModel.class);

        /*---------- UI ELEMENTS ----------*/

        final EditText signup_et_fullName =
                ((TextInputLayout) findViewById(R.id.signUp_et_fullName)).getEditText();
        final EditText signup_et_username =
                ((TextInputLayout) findViewById(R.id.signUp_et_username)).getEditText();
        final EditText signup_et_email =
                ((TextInputLayout) findViewById(R.id.signUp_et_email)).getEditText();
        final EditText signup_et_password =
                ((TextInputLayout) findViewById(R.id.signUp_et_password)).getEditText();
        final EditText signup_et_confirmPassword =
                ((TextInputLayout) findViewById(R.id.signUp_et_confirmPassword)).getEditText();
        final MaterialButton signup_btn_signup =
                findViewById(R.id.signUp_btn_signup);
        final ProgressBar signup_bar_loading =
                findViewById(R.id.signUp_bar_loading);

        /*---------- VIEW MODEL LISTENERS ----------*/

        signUpViewModel.getSignUpFormState().observe(this, signUpFormState -> {
            if (signUpFormState == null) return;

            signup_btn_signup.setEnabled(signUpFormState.isDataValid());
            if (signUpFormState.getFullNameError() != null)
                Objects.requireNonNull(signup_et_fullName).setError(getString(signUpFormState.getFullNameError()));
            if (signUpFormState.getUsernameError() != null)
                Objects.requireNonNull(signup_et_username).setError(getString(signUpFormState.getUsernameError()));
            if (signUpFormState.getEmailError() != null)
                Objects.requireNonNull(signup_et_email).setError(getString(signUpFormState.getEmailError()));
            if (signUpFormState.getPasswordError() != null)
                Objects.requireNonNull(signup_et_password).setError(getString(signUpFormState.getPasswordError()));
            if (signUpFormState.getConfirmPasswordError() != null)
                Objects.requireNonNull(signup_et_confirmPassword).setError(getString(signUpFormState.getConfirmPasswordError()));
        });

        signUpViewModel.getSignUpResult().observe(this, signUpResult -> {
            if (signUpResult == null) return;

            signup_bar_loading.setVisibility(View.GONE);
            if (signUpResult.getError() != null)
                showSignUpFailed(signUpResult.getError());
            if (signUpResult.getSuccess()) {
                setResult(RESULT_OK);
                Toast.makeText(this, R.string.signUp_toast_success, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        /*---------- UI ELEMENTS LISTENERS ----------*/

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                signUpViewModel.signupDataChanged(
                        Objects.requireNonNull(signup_et_fullName).getText().toString().trim(),
                        Objects.requireNonNull(signup_et_username).getText().toString().trim(),
                        Objects.requireNonNull(signup_et_email).getText().toString().trim(),
                        Objects.requireNonNull(signup_et_password).getText().toString().trim(),
                        Objects.requireNonNull(signup_et_confirmPassword).getText().toString().trim()
                );
            }
        };

        Objects.requireNonNull(signup_et_fullName).addTextChangedListener(afterTextChangedListener);
        Objects.requireNonNull(signup_et_username).addTextChangedListener(afterTextChangedListener);
        Objects.requireNonNull(signup_et_email).addTextChangedListener(afterTextChangedListener);
        Objects.requireNonNull(signup_et_password).addTextChangedListener(afterTextChangedListener);
        Objects.requireNonNull(signup_et_confirmPassword).addTextChangedListener(afterTextChangedListener);

        signup_btn_signup.setOnClickListener(view -> {
            if (NetworkConnection.isNetworkConnected(this)) {
                signup_bar_loading.setVisibility(View.VISIBLE);
                signUpViewModel.signup(
                        signup_et_fullName.getText().toString().trim(),
                        signup_et_username.getText().toString().trim(),
                        signup_et_email.getText().toString().trim(),
                        signup_et_password.getText().toString().trim()
                );
            }else {
                Toast.makeText(this, R.string.global_noConn, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSignUpFailed(@StringRes Integer errorString) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
    }
}