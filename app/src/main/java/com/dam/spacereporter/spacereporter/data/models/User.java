package com.dam.spacereporter.spacereporter.data.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class User {

    private String fullName;
    private String username;
    private String email;

    public User() {
    }

    public User(String fullName, String username, String email) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
    }

    @SerializedName(value = "fullName")
    public final String getFullName() {
        return this.fullName;
    }

    @SerializedName(value = "username")
    public final String getUsername() {
        return this.username;
    }

    @SerializedName(value = "email")
    public final String getEmail() {
        return this.email;
    }

    @NonNull
    @Override
    public final String toString() {
        return String.format(
                "User{fullName='%s', username='%s', email='%s'}",
                this.fullName, this.username, this.email
        );
    }
}
