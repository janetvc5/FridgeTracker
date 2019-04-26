package com.example.fridge_tracker;

import android.app.Activity;
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

    Button login;
    EditText user;
    EditText pass;
    TextView title;
    TextView attempts;
    boolean valid;
    int counter = 5;

    String loggeduser;
    ArrayList<String> loggedInUsers;
    //String[] loggedInUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.buttonLogin);
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
                validate(user.getText().toString(), pass.getText().toString());
            }
        });

    }

    private void validate(String username, String password) {
        try{
            if ( sendJsonLogin(username,password) ) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                counter--;

                attempts.setText("Login attempts remaining: " + String.valueOf(counter));

                if (counter == 0) {
                    login.setEnabled(false);
                }
            }
        } catch (JSONException e){

        }

    }

    private boolean sendJsonLogin(final String username, final String password) throws JSONException {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/login";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, getJs(username, password),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                             boolean valid = response.getBoolean("login success");

                             if (valid){
                                 //then set the global variables like userid, fridgeid, role, etc
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
        return valid; //UNCOMMENT ME
        //return true; //CHANGE ME TO THE CORRECT VALUE or delete me
    }


    protected JSONObject getJs(String username, String password) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("username", username);
        params.put("password", password);

        return params;
    }

    //test below!!!!
//    private void getUser(String uValue, String pValue) {
//        RequestQueue mQueue = Volley.newRequestQueue(this);
//        // prepare a result array
//
//        String url="http://cs309-af-1.misc.iastate.edu:8080/user";
//        JsonArrayRequest userArrayReq=new JsonObjectRequest(Request.Method.GET,
//                url, new JSONObject(),
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        userResult=response.getJSONArray("username");
//
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//
//        int i=0;
//
//        // loop through all of our users
//        while(userResult[i]!=null){
//            // get the user we are iterating through now
//            String user = userResult[i];
//
//            // check if the user has the specified property
//            if (user == uValue) {
//                JsonArrayRequest passArrayReq=new JsonObjectRequest(Request.Method.GET,
//                        url, new JSONObject(),
//                        new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                                passResult=response.getJSONArray("password");
//
//                            }
//                        }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                });
//
//                int j=0;
//                while(j<passResult.length)
//                {
//                    String pass=passResult[j];
//                    if (pass==pValue);
//                }
//                mQueue.add(passArrayReq);
//            }
//        }
//
//        // return the results or null, if nothing was found (for single match search)
//
//        return ;
//
//        mQueue.add(userArrayReq);
//
//    }
//
//
//    /**
//     * boolean|Object login ( string username, string password )
//     * <p>
//     * Provides the functionality to be able to log in on a user.
//     *
//     * @param string username Username of the user to log in on.
//     * @param string password Password of the user to log in on.
//     * @return boolean|Object Returns the user object, or false, if login was not successful.
//     */
//    private void login(String username, String password) {
//        // checks whether username and password have been filled in
//        if (username.length() > 0 && password.length() > 0) {
//            // prepare a variable to store the user object, if any is received
//            JSONObject loggeduser;
//
//            // server should handle everything below...
//            // iterate through all users in the 'users' array (or database table perhaps, on server-side)
//            for (int i=0; i<userResult.length; i++){
//                // grab the property value with the property
//                String user = userResult[i];
//
//                // check if username and password match
//                if (username == user && password == = user.password)
//                    // set value of 'loggeduser' to the property value (user)
//                    loggeduser = user;
//            }
//            // ... up to this point, and the user returned from the server should be set in to 'loggeduser'
//            // make sure highly sensitive information is not returned, such as hash, salt or anything
//
//            // check whether the user is set
//            if (typeof loggeduser != 'undefined'){
//                // save the ID of the user to the 'loggedusers' array
//                loggedusers[loggeduser.id] = true;
//
//                // return the received user object
//                return loggeduser;
//            }
//        }
//
//        return false;
//    }

    /**
     * boolean logout ( number userid )
     * <p>
     * Provides the functionality to be able to log out from a user.
     *
     * @param number userid ID of the user to log out of.
     * @return boolean Returns a boolean representing whether the log out was successful or not.
     */

    /*
    function logout(userid) {
        // check whether the ID is actually logged in
        if (loggedusers[userid]) {
            // temporary array, which we will be filling
            var temporary = [];

            // let's loop through logged users
            for (var id in loggedusers)
            // ignore our user
            if (id != userid)
                // let's put this user to the array
                temporary[id] = true;

            // we replace the 'loggedusers' array with our new array
            loggedusers = temporary;

            // update the logged in list
            updatelist();

            // we have successfully logged out
            return true;
        }

        // we have not successfully logged out
        return false;
    }
    */

}
