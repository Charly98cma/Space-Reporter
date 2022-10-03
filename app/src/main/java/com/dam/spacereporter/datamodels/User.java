package com.dam.spacereporter.datamodels;

public class User {

    private String name;
    private String username;
    private String email;
    private String password_hash;

    public User(String name, String username, String email, String password_hash) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
}
