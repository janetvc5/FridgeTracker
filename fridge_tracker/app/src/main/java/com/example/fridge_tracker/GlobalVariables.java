package com.example.fridge_tracker;

import android.app.Application;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public void getAndSetGlobalVariables(String userid){
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/" + userid;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    /**
                     * Back end returns different values for role and fridge id per user
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("user", "user info: " + response.toString());

                            setUserID(userID);
                            setUsername(username);

                            String role = response.getString("role");
                            setRole(role);

                            JSONObject fridge = response.getJSONObject("fridge");
                            String fridgeID = fridge.getString("id");
                            setFridgeID(fridgeID);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            /**
             * Error catcher
             *
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            /**
             * Passing some request headers
             **/
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        mQueue.add(jsonObjReq);
    }

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
