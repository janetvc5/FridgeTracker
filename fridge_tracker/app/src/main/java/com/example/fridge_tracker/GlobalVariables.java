package com.example.fridge_tracker;

import android.app.Application;

public class GlobalVariables extends Application {

    private String userID;
    private String fridgeID;
    private String role;

    // TO USE

//    ((GlobalVariables) getApplication()).setSomeVariable("foo");
//    or
//    String s = ((GlobalVariables) getApplication()).getSomeVariable();

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFridgeID() {
        return fridgeID;
    }

    public void setFridgeID(String fridgeID) {
        this.fridgeID = fridgeID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
