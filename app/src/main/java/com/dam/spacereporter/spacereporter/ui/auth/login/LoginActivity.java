package com.dam.spacereporter.spacereporter.ui.auth.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.ui.MainActivity;
import com.dam.spacereporter.spacereporter.ui.auth.forgotPwd.ForgotPwdActivity;
import com.dam.spacereporter.spacereporter.ui.auth.signup.SignUpActivity;
import com.dam.spacereporter.spacereporter.utils.Constants;
import com.dam.spacereporter.spacereporter.utils.NetworkConnection;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        /*---------- UI ELEMENTS ----------*/

        final EditText login_et_email = ((TextInputLayout) findViewById(R.id.login_et_email)).getEditText();
        final EditText login_et_password = ((TextInputLayout) findViewById(R.id.login_et_password)).getEditText();
        final MaterialButton login_btn_login = findViewById(R.id.login_btn_login);
        final MaterialButton login_btn_forgotPwd = findViewById(R.id.login_btn_forgotPwd);
        final MaterialButton login_btn_signUp = findViewById(R.id.login_btn_signUp);
        final ProgressBar login_bar_loading = findViewById(R.id.login_bar_loading);
        final SwitchCompat login_sw_stayLogged = findViewById(R.id.login_sw_stayLogged);

        /*---------- VIEW MODEL LISTENERS ----------*/

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) return;

            login_btn_login.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getEmailError() != null)
                Objects.requireNonNull(login_et_email).setError(getString(loginFormState.getEmailError()));
            if (loginFormState.getPasswordError() != null)
                Objects.requireNonNull(login_et_password).setError(getString(loginFormState.getPasswordError()));
        });
        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) return;

            login_bar_loading.setVisibility(View.GONE);
            if (loginResult.getError() != null)
                showLoginFailed(loginResult.getError());
            if (loginResult.getSuccess()) {
                setResult(RESULT_OK);
                loginViewModel.cacheUserInfo(this);
                readRememberMeSwitch(login_sw_stayLogged);
                // Wait to have user information on SharedPreferences
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, R.string.login_toast_success, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
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
                loginViewModel.loginDataChanged(
                        Objects.requireNonNull(login_et_email).getText().toString().trim(),
                        Objects.requireNonNull(login_et_password).getText().toString().trim()
                );
            }
        };
        Objects.requireNonNull(login_et_email).addTextChangedListener(afterTextChangedListener);
        Objects.requireNonNull(login_et_password).addTextChangedListener(afterTextChangedListener);

        login_btn_login.setOnClickListener(view -> {
            if (NetworkConnection.isNetworkConnected(this)) {
                login_bar_loading.setVisibility(View.VISIBLE);
                loginViewModel.login(
                        login_et_email.getText().toString().trim(),
                        login_et_password.getText().toString().trim()
                );
            } else {
                Toast.makeText(this, R.string.global_noConn, Toast.LENGTH_SHORT).show();
            }
        });
        login_btn_forgotPwd.setOnClickListener(view -> startActivity(new Intent(this, ForgotPwdActivity.class)));
        login_btn_signUp.setOnClickListener(view -> startActivity(new Intent(this, SignUpActivity.class)));
    }

    private void readRememberMeSwitch(Checkable login_sw_stayLogged) {
        SharedPreferences.Editor editor =
                getSharedPreferences(Constants.PREF_KEY, MODE_PRIVATE).edit();
        editor.putBoolean(Constants.PREF_SAVE_LOGIN, login_sw_stayLogged.isChecked());
        editor.apply();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
    }
}