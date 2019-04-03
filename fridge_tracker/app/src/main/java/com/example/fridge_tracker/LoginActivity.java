package com.example.fridge_tracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    private void validate(String username, String password){

        if((username.equals("user2")) && (password.equals("pass")))
        {
            Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
            startActivity(intent);
        }else {
            counter--;

            attempts.setText("Login attempts remaining: "+String.valueOf(counter));

            if (counter == 0) {
                login.setEnabled(false);
            }
        }
    }
}

//test below!!!!
    private String[] getUserByProperty(String uValue){
//function getUserByProperty(key, value, strict, multiple, case_insensitive) {
    // prepare a result array
        String[] result;
    //var result = [];

    // loop through all of our users
    for (var index in users) {
        // get the user we are iterating through now
        var user = users[index];

        // check if the user has the specified property
        if (user[propertyName] != "undefined") {
            // get the property value
            String compare = user[propertyValue];

            // if specified value is not defined, or values match
            if (typeof value == 'undefined' || ((strict && compare === value) || (!strict && compare == value))) {
                // if we want multiple results
                if (multiple) {
                    // the result will be appended to the result array
                    result.push(user);
                } else {
                    // otherwise we just return it
                    return user;
                }
            }
        }
    }

    // return the results or null, if nothing was found (for single match search)
    return multiple ? result : null;
}



    /**
     * boolean|Object login ( string username, string password )
     *
     * Provides the functionality to be able to log in on a user.
     *
     * @param string username Username of the user to log in on.
     * @param string password Password of the user to log in on.
     *
     * @return boolean|Object Returns the user object, or false, if login was not successful.
     */
    function login(String username, String password) {
        // checks whether username and password have been filled in
        if (username.length() > 0 && password.length() > 0) {
            // prepare a variable to store the user object, if any is received
            var loggeduser;

            // server should handle everything below...
            // iterate through all users in the 'users' array (or database table perhaps, on server-side)
            for (var index in users) {
                // grab the property value with the property
                var user = users[index];

                // check if username and password match
                if (username === user.username && password === user.password)
                    // set value of 'loggeduser' to the property value (user)
                    loggeduser = user;
            }
            // ... up to this point, and the user returned from the server should be set in to 'loggeduser'
            // make sure highly sensitive information is not returned, such as hash, salt or anything

            // check whether the user is set
            if (typeof loggeduser != 'undefined') {
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
     *
     * Provides the functionality to be able to log out from a user.
     *
     * @param number userid ID of the user to log out of.
     *
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

    /**
     * boolean updatelist ( void )
     *
     * Provides the functionality to update the #logged-in-list element
     * with the logged in users names and logout links.
     *
     * @return boolean Returns a boolean representing whether the update was successful or not.
     */
    function updatelist() {
        // get the #logged-in-list element
        var list_element = document.getElementById('logged-in-list');

        // check the element exists
        if (list_element) {
            // get the #logged-in element
            var list_container_element = document.getElementById('logged-in');

            // check the element exists and that we should be changing the styles
            if (list_container_element)
                // if there are no logged in users, "hide" the element, otherwise "show" it
                list_container_element.style.visibility = loggedusers.length === 0 ? 'hidden' : 'visible';

            // we take the first child with a while loop
            while (list_element.firstChild)
                // remove the child, and it will repeat doing so until there is no firstChild left for the list_element
                list_element.removeChild(list_element.firstChild);

            // we loop through every logged in user
            for (var id in loggedusers) {
                // get the user by ID
                var user = getUserById(id);

                // check if that user is a user
                if (user) {
                    // we create necessary elements to cover our logout functionality
                    var p = document.createElement('P');
                    p.innerText = user.username;
                    var a = document.createElement('A');
                    a.userid = id;
                    a.href = '#';
                    a.innerHTML = '(logout)';

                    // we bind an onclick event listener to the link
                    a.addEventListener('click', function(e) {
                        e.preventDefault();

                        // we will now execute the logout function for this user ID
                        logout(e.srcElement.userid);
                    });

                    // we append the link to the paragraph element
                    p.appendChild(a);

                    // we append the paragraph to the list element
                    list_element.appendChild(p);
                }
            }

            return true;
        }

        return false;
    }