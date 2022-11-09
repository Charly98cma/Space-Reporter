package com.dam.spacereporter.spacereporter.ui.auth.forgotPwd;

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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPwdActivity extends AppCompatActivity {

    private ForgotPwdViewModel forgotPwdViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        forgotPwdViewModel = new ViewModelProvider(this, new ForgotPwdViewModelFactory())
                .get(ForgotPwdViewModel.class);

        /*---------- UI ELEMENTS ----------*/

        final EditText forgotPwd_et_email =
                ((TextInputLayout) findViewById(R.id.forgotPwd_et_email)).getEditText();
        final MaterialButton forgotPwd_btn_send = findViewById(R.id.forgotPwd_btn_send);
        final ProgressBar forgotPwd_bar_loading = findViewById(R.id.forgotPwd_bar_loading);

        /*---------- VIEW MODEL LISTENERS ----------*/

        forgotPwdViewModel.getForgotPwdFormState().observe(this, forgotPwdFormState -> {
            if (forgotPwdFormState == null) return;

            forgotPwd_btn_send.setEnabled(forgotPwdFormState.isDataValid());
            if (forgotPwdFormState.getEmailError() != null)
                forgotPwd_et_email.setError(getString(forgotPwdFormState.getEmailError()));
        });

        forgotPwdViewModel.getForgotPwdResult().observe(this, forgotPwdResult -> {
            if (forgotPwdResult == null) return;

            forgotPwd_bar_loading.setVisibility(View.GONE);
            if (forgotPwdResult.getError() != null)
                showForgotPwdError(forgotPwdResult.getError());
            if (forgotPwdResult.getSuccess()) {
                setResult(RESULT_OK);
                Toast.makeText(this, R.string.forgotPwd_toast_sent, Toast.LENGTH_SHORT).show();
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
                forgotPwdViewModel.forgotPwdDataChanged(
                        forgotPwd_et_email.getText().toString().trim()
                );
            }
        };

        forgotPwd_et_email.addTextChangedListener(afterTextChangedListener);

        forgotPwd_btn_send.setOnClickListener(view -> {
            forgotPwd_bar_loading.setVisibility(View.VISIBLE);
            forgotPwdViewModel.sendPasswordResetEmail(
                    forgotPwd_et_email.getText().toString().trim()
            );
        });
    }

    private void showForgotPwdError(@StringRes Integer errorString) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
    }
}