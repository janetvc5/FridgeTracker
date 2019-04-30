package com.example.fridge_tracker;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Log in page, the first page seen by a user and a correct username and password must be entered.
 */
public class LoginActivity extends AppCompatActivity {

    Button login, newUser;
    EditText user;
    EditText pass;
    TextView title;
    TextView attempts;
    int counter = 5;
    String fridgeNum;

    String loggeduser;
    ArrayList<String> loggedInUsers;
    //String[] loggedInUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.buttonLogin);
        newUser = (Button) findViewById(R.id.buttonNewUser);
        user = (EditText) findViewById(R.id.etUsername);
        pass = (EditText) findViewById(R.id.etPassword);
        title = (TextView) findViewById(R.id.titleLogin);
        attempts = (TextView) findViewById(R.id.tvAttempts);

        attempts.setText("Login attempts remaining: 5");

        login.setOnClickListener(new View.OnClickListener() {

            /**
             * Verifies that the username and password are valid and correct.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                try{
                    sendJsonLogin(user.getText().toString(), pass.getText().toString());
                } catch (JSONException e){

                }

            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {

            /**
             * Verifies that the username and password are valid and correct.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AddUser.class);
                startActivity(intent);

            }
        });

    }

    private void sendJsonLogin(final String username, final String password) throws JSONException {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/login";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, getJs(username, password),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean valid = response.getBoolean("login success");
                            Log.d("login", "valid value: " + valid);


                            if ( valid ) {
                                getAndSetGlobalVariables();
                                String userID = response.getString("id");
                                ((GlobalVariables) getApplication()).setUserID(userID);
                                ((GlobalVariables) getApplication()).setUsername(username);

                                //user userID to get other values


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                counter--;
                                String test = "Login attempts remaining: " + String.valueOf(counter);
                                attempts.setText(test);

                                if (counter == 0) {
                                    login.setEnabled(false);
                                }
                            }


                        } catch (JSONException e) {

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
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            /**
             * passing some params
             *
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }



        };

        mQueue.add(jsonObjReq);
    }

    protected JSONObject getJs(String username, String password) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("username", username);
        params.put("password", password);

        return params;
    }

    private void getAndSetGlobalVariables(){
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/" + ((GlobalVariables) getApplication()).getUserID();

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


                            String role = response.getString("role");
                            ((GlobalVariables) getApplication()).setRole(role);

                            JSONObject fridge = response.getJSONObject("fridge");
                            String fridgeID = fridge.getString("id");
                            ((GlobalVariables) getApplication()).setFridgeID(fridgeID);




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

//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("fridgeid", "36");
//                params.put("role", "abc@androidhive.info");
//
//
//                return params;
//            }

        };

        mQueue.add(jsonObjReq);
    }



    private void getJson(String userID)
    {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/" + userID;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    /**
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String fName = response.getString("role");
                            //String lName = String.valueOf(response.getInt("fridge"));
                            //fridgeNum=lName;


                            //Log.d("Role: ",fName + "  Fridge for user: " + lName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            /**
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
                //headers.put("fridgeid", "role");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fridgeid", "36");
                params.put("role", "abc@androidhive.info");


                return params;
            }

        };

        mQueue.add(jsonObjReq);

    }

    private void sendJson(final String userID, final String userRole) throws JSONException {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/new";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, getJs(userRole, userID),
                new Response.Listener<JSONObject>() {

                    /**
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            Log.d("Response from server: ", response.getString("fridgeid"));
                        }
                        catch (JSONException e)
                        {

                        }

                    }

                }, new Response.ErrorListener() {

            /**
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
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            /**
             *
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fridgeid", userID);
                params.put("role", userRole);

                return params;
            }
        };

        mQueue.add(jsonObjReq);
    }


}
