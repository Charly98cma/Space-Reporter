package com.dam.spacereporter.Tasks;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginManager {

    private static LoginManager instance = null;
    private final FirebaseAuth firebaseAuth;

    private LoginManager() { firebaseAuth = FirebaseAuth.getInstance(); }

    public static LoginManager getInstance() {
        if (instance == null)
            instance = new LoginManager();
        return instance;
    }

    public Task<AuthResult> createUser(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> loginUser(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public void deleteUser() {
        Objects.requireNonNull(firebaseAuth.getCurrentUser()).delete();
    }

    public void signOut() {
        firebaseAuth.signOut();
    }
}
