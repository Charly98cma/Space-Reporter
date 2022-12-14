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
import com.dam.spacereporter.spacereporter.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginViewModel extends ViewModel {

    // Initialization of the connections and data storage
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("users");
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    // Mandatory empty constructor
    LoginViewModel() {
    }

    /**
     * Returns the current LoginFormState, which states if the form is correct or wrong
     *
     * @return the current mutable LoginFormState
     */
    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    /**
     * Returns the LoginResult, which indicates whether the login operation has been successful
     *
     * @return the current mutable loginResult
     */
    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    /**
     * Async login operation through FirebaseAuth, which checks if the credentials are valid and
     * if the user has verified the email/account.
     *
     * @param email String with the user email address
     * @param password String with the user password
     */
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

    /**
     * Check the data introduced by the user (email and password) are valid and follow the
     * imposed restrictions on the DataValidator.
     *
     * @param email String with the user email address
     * @param password String with the user password
     */
    public void loginDataChanged(String email, String password) {
        if (!DataValidator.isEmailValid(email))
            loginFormState.setValue(new LoginFormState(R.string.login_error_email, null));
        else if (!DataValidator.isPasswordValid(password))
            loginFormState.setValue(new LoginFormState(null, R.string.login_error_password));
        else
            loginFormState.setValue(new LoginFormState(true));
    }

    /**
     * Save the basic user information (fullName, username and email) in the SharedPreferences
     * for later use.
     *
     * @param context COntext object of the activity
     */
    public void cacheUserInfo(Context context) {
        usersDatabase.child(
                Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()
        ).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPreferences.Editor editor = context.getSharedPreferences(
                        Constants.PREF_KEY, MODE_PRIVATE
                ).edit();
                editor.putString(Constants.PREF_USER_FULLNAME, (String) snapshot.child("fullName").getValue());
                editor.putString(Constants.PREF_USER_USERNAME, (String) snapshot.child("username").getValue());
                editor.putString(Constants.PREF_USER_EMAIL, (String) snapshot.child("email").getValue());
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
