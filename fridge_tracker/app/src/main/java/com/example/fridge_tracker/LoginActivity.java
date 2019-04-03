package com.example.fridge_tracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText user;
    EditText pass;
    TextView title;
    TextView attempts;
    int counter = 5;

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
            @Override
            public void onClick(View v) {
                validate(user.getText().toString(), pass.getText().toString());
            }
        });

    }

    private void validate(String username, String password) {

        if ((username.equals("user2")) && (password.equals("pass"))) {
            Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
            startActivity(intent);
        } else {
            counter--;

            attempts.setText("Login attempts remaining: " + String.valueOf(counter));

            if (counter == 0) {
                login.setEnabled(false);
            }
        }
    }


    //test below!!!!
    private void getUser(String uValue) {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        // prepare a result array
        final String[] result;
        String url="http://cs309-af-1.misc.iastate.edu:8080/user";
        JsonArrayRequest userArrayReq=new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        result=response.getJSONArray("username");

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        int i=0;

        // loop through all of our users
        while(result[i]!=null){
            // get the user we are iterating through now
            String user = result[i];

            // check if the user has the specified property
            if (user != "undefined") {
                // get the property value
                //String compare = user[propertyValue];

                // if specified value is not defined, or values match
                if (user == uValue)
                {

                }
            }
        }

        // return the results or null, if nothing was found (for single match search)
        return ;

        mQueue.add(jsonArReq);
    }


    /**
     * boolean|Object login ( string username, string password )
     * <p>
     * Provides the functionality to be able to log in on a user.
     *
     * @param string username Username of the user to log in on.
     * @param string password Password of the user to log in on.
     * @return boolean|Object Returns the user object, or false, if login was not successful.
     */
    private void login(String username, String password) {
        // checks whether username and password have been filled in
        if (username.length() > 0 && password.length() > 0) {
            // prepare a variable to store the user object, if any is received
            JSONObject loggeduser;

            // server should handle everything below...
            // iterate through all users in the 'users' array (or database table perhaps, on server-side)
            for (var index in users){
                // grab the property value with the property
                var user = users[index];

                // check if username and password match
                if (username == = user.username && password == = user.password)
                    // set value of 'loggeduser' to the property value (user)
                    loggeduser = user;
            }
            // ... up to this point, and the user returned from the server should be set in to 'loggeduser'
            // make sure highly sensitive information is not returned, such as hash, salt or anything

            // check whether the user is set
            if (typeof loggeduser != 'undefined'){
                // save the ID of the user to the 'loggedusers' array
                loggedusers[loggeduser.id] = true;

                // update the logged in list
                updatelist();

                // return the received user object
                return loggeduser;
            }
        }

        return false;
    }

    /**
     * boolean logout ( number userid )
     * <p>
     * Provides the functionality to be able to log out from a user.
     *
     * @param number userid ID of the user to log out of.
     * @return boolean Returns a boolean representing whether the log out was successful or not.
     */
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


}