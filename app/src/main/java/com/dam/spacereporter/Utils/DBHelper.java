package com.dam.spacereporter.Utils;

import com.dam.spacereporter.Datamodels.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBHelper {

    private static DBHelper instance = null;

    private final FirebaseDatabase rootNode;

    /*
     * CONSTRUCTOR
     */

    private DBHelper() {
        rootNode = FirebaseDatabase.getInstance();
    }
    public static DBHelper getInstance() {
        if (instance == null)
            instance = new DBHelper();
        return instance;
    }

    /*
     * FUNCTIONS
     */

    public DatabaseReference getRef(String dbName) {
        return rootNode.getReference(dbName);
    }

}
