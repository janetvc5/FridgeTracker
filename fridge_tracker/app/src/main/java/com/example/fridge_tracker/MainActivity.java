package com.example.fridge_tracker;


import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button sendButton, getButton;
    FloatingActionButton floatingActionButton;
    EditText getUserInfo, sendID, sendRole;

//    /* Bottom Navigation */
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_myfridge:
//                    return true;
//                case R.id.navigation_grocerylist:
//                    return true;
//                case R.id.navigation_recipes:
//                    return true;
//                case R.id.navigation_settings:
//                    return true;
//                case R.id.navigation_chat:
//            }
//            return false;
//        }
//
//    };
//    /*End Bottom Navigation*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sendButton = (Button) findViewById(R.id.sendbutton);
        getButton = (Button) findViewById(R.id.getbutton);
        getUserInfo = (EditText) findViewById(R.id.sendtext);
        sendRole = (EditText) findViewById(R.id.sendrole);
        sendID = (EditText) findViewById(R.id.sendfridgeid);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendJson(String.valueOf(sendID.getText()), String.valueOf(sendRole.getText()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJson(String.valueOf(getUserInfo.getText()));
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, floatingActionButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Grocery List")) {
                             Intent intent1 = new Intent(MainActivity.this, GroceryListActivity.class);
                             startActivity(intent1);
                        } else if (item.getTitle().equals("Fridge View")) {
                            Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
                            startActivity(intent2);
                        } else if (item.getTitle().equals("Chat")) {
                            Intent intent3 = new Intent(MainActivity.this, ChatActivity.class);
                            startActivity(intent3);
                        }

                        return true;

                    }
                });

                popup.show(); //showing popup menu

            }
        });

        // bottom nav
        BottomNavigationView navigation = findViewById(R.id.navigationView);
            //navigation.setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener);
        // end bottom nav

    }




    void getJson(String userID)
    {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/" + userID;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String fName = response.getString("role");
                            String lName = String.valueOf(response.getInt("fridgeid"));


                            Log.d("Role: ",fName + "  Fridge for user: " + lName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

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

    void sendJson(final String userID, final String userRole) throws JSONException {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/new";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, getJs(userRole, userID),
                new Response.Listener<JSONObject>() {

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


    protected JSONObject getJs(String role, String id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("fridgeid", id);
        params.put("role", role);

        return params;
    }
}
