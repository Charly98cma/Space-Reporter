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

    @SerializedName("fullName")
    public String getFullName() {
        return fullName;
    }

    @SerializedName("username")
    public String getUsername() {
        return username;
    }

    @SerializedName("email")
    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
