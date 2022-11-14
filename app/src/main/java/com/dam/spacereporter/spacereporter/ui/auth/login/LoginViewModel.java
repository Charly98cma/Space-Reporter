package com.dam.spacereporter.spacereporter.ui.auth.login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.ui.auth.DataValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginViewModel extends ViewModel {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("users");
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    LoginViewModel() {
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(
                email, password
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()) {
                    loginResult.setValue(new LoginResult(true));
                } else {
                    loginResult.setValue(new LoginResult(R.string.login_error_unverifiedEmail));
                    firebaseAuth.signOut();
                }
            } else {
                loginResult.setValue(new LoginResult(R.string.login_error_fail));
            }
        });
    }

    public void loginDataChanged(String email, String password) {
        if (!DataValidator.isEmailValid(email))
            loginFormState.setValue(new LoginFormState(R.string.login_error_email, null));
        else if (!DataValidator.isPasswordValid(password))
            loginFormState.setValue(new LoginFormState(null, R.string.login_error_password));
        else
            loginFormState.setValue(new LoginFormState(true));
    }

    public void cacheUserInfo(Context context) {
        usersDatabase.child(
                Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()
        ).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPreferences.Editor editor = context.getSharedPreferences(
                        context.getString(R.string.pref_name), MODE_PRIVATE
                ).edit();
                editor.putString(context.getString(R.string.pref_fullName), (String) snapshot.child("fullName").getValue());
                editor.putString(context.getString(R.string.pref_username), (String) snapshot.child("username").getValue());
                editor.putString(context.getString(R.string.pref_email), (String) snapshot.child("email").getValue());
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
