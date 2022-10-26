package com.dam.spacereporter.Utils;

import com.dam.spacereporter.Datamodels.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class DBUsers {

    private static DBUsers instance = null;
    private final DBHelper dbHelper;

    private static final String usersDatabase = "users";
    private DatabaseReference usersDB;

    /*
     * CONSTRUCTOR
     */

    private DBUsers() {
        dbHelper = DBHelper.getInstance();
        usersDB = dbHelper.getRef(usersDatabase);
    }
    public static DBUsers getInstance() {
        if (instance == null)
            instance = new DBUsers();
        return instance;
    }

    /*
     * FUNCTIONS
     */

    public Task<Void> storeUser(String fullName, String username, String email) {
        User newUser = new User(fullName, username, email);
        return usersDB.child(username).setValue(newUser);
    }

    public Query isUserInDB(String username) {
        return usersDB.orderByChild("username").equalTo(username);
    }



}
