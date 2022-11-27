package com.dam.spacereporter.spacereporter.ui.auth.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Factory to create the LoginViewModel and pass arguments (if necessary) to its constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class))
            return (T) new LoginViewModel();
        else
            throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
