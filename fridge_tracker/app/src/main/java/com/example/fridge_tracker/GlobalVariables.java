package com.example.fridge_tracker;

import android.app.Application;
import android.util.Log;

public class GlobalVariables extends Application {

    private String userID;
    private String fridgeID;
    private String role;
    private String username;
    private String selectedSearchItem;

    // TO USE

//    ((GlobalVariables) getApplication()).setSomeVariable("foo");
//    or
//    String s = ((GlobalVariables) getApplication()).getSomeVariable();

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
        Log.d("variable1", "Global Variable UserID set to " + userID);
    }

    public String getFridgeID() {
        return fridgeID;
    }

    public void setFridgeID(String fridgeID) {
        this.fridgeID = fridgeID;
        Log.d("variable2", "Global Variable FridgeID set to " + fridgeID);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
        Log.d("variable3", "Global Variable Role set to " + role);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        Log.d("variable4", "Global Variable Username set to " + username);
    }

    public String getSelectedSearchItem() {
        return selectedSearchItem;
    }

    public void setSelectedSearchItem(String selectedSearchItem) {
        this.selectedSearchItem = selectedSearchItem;
        Log.d("variable5", "Global Variable SelectedSearchItem set to " + selectedSearchItem);
    }

}
