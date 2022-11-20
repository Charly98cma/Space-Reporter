package com.dam.spacereporter.spacereporter.ui.auth.signup;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dam.spacereporter.R;
import com.dam.spacereporter.spacereporter.data.models.User;
import com.dam.spacereporter.spacereporter.ui.auth.DataValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SignUpViewModel extends ViewModel {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference("users");
    private final MutableLiveData<SignUpFormState> signUpFormState = new MutableLiveData<>();
    private final MutableLiveData<SignUpResult> signUpResult = new MutableLiveData<>();

    SignUpViewModel() {
    }

    LiveData<SignUpFormState> getSignUpFormState() {
        return signUpFormState;
    }

    LiveData<SignUpResult> getSignUpResult() {
        return signUpResult;
    }

    public void signup(String fullName, String username, String email, String password) {
        // First, check if username already in use
        usersDatabase.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    signUpResult.setValue(new SignUpResult(R.string.signUp_error_usernameUsed));
                else
                    signUpProcess(fullName, username, email, password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void signupDataChanged(String fullName,
                                  String username,
                                  String email,
                                  String password,
                                  String confirmPassword) {
        if (!DataValidator.isFullNameValid(fullName))
            signUpFormState.setValue(new SignUpFormState(R.string.signUp_error_fullname, null, null, null, null));
        else if (!DataValidator.isUsernameValid(username))
            signUpFormState.setValue(new SignUpFormState(null, R.string.signUp_error_username, null, null, null));
        else if (!DataValidator.isEmailValid(email))
            signUpFormState.setValue(new SignUpFormState(null, null, R.string.signUp_error_email, null, null));
        else if (!DataValidator.isPasswordValid(password))
            signUpFormState.setValue(new SignUpFormState(null, null, null, R.string.signUp_error_password, null));
        else if (!DataValidator.doPasswordsMatch(password, confirmPassword))
            signUpFormState.setValue(new SignUpFormState(null, null, null, null, R.string.signUp_error_matchPassword));
        else
            signUpFormState.setValue(new SignUpFormState(true));
    }

    private void signUpProcess(String fullName, String username, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(
                email, password
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                signUpFirebaseDB(fullName, username, email);
            else
                signUpResult.setValue(new SignUpResult(R.string.signUp_error_firebaseAuth));
        });
    }

    private void signUpFirebaseDB(String fullName, String username, String email) {
        usersDatabase.child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).setValue(
                new User(fullName, username, email)
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                sendVerificationEmail(username);
            else
                undoSignUp(R.string.signUp_error_firebaseDatabase);
        });
    }

    private void sendVerificationEmail(String username) {
        Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                signUpResult.setValue(new SignUpResult(true));
            } else {
                usersDatabase.child(username).removeValue();
                undoSignUp(R.string.signUp_error_verificationEmail);
            }
        });
    }

    private void undoSignUp(@StringRes Integer errorString) {
        Objects.requireNonNull(firebaseAuth.getCurrentUser()).delete();
        signUpResult.setValue(new SignUpResult(errorString));
    }
}
