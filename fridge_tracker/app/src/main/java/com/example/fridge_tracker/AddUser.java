package com.example.fridge_tracker;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

public class AddUser extends AppCompatActivity {

    Button sendButton, getButton;
    FloatingActionButton floatingActionButton;
    EditText getUserInfo, sendID, sendRole;
    TextView confirmAdd, viewFridge;
    Spinner spinner;
    int userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        sendButton = (Button) findViewById(R.id.sendbutton);
        getButton = (Button) findViewById(R.id.addbutton);
        getUserInfo = (EditText) findViewById(R.id.sendtext);
        //sendRole = (EditText) findViewById(R.id.sendRole);
        sendID = (EditText) findViewById(R.id.sendfridgeid);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        confirmAdd = (TextView) findViewById(R.id.confirmAdd);
        viewFridge = (TextView) findViewById(R.id.viewFridge);


        spinner = (Spinner) findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userRole=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sendButton.setEnabled(false);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {

            /**
             * sends the back end the new users ID and Role
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                try {
                    sendJson(String.valueOf(sendID.getText()), userRole);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {

            /**
             * requests the user's information
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                getJson(String.valueOf(getUserInfo.getText()));
            }
        });

    }
    private void getJson(String userID)
    {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/" + userID;

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
                            String fName = response.getString("role");
                            String lName = String.valueOf(response.getInt("fridgeid"));
                            confirmAdd.setText("Added! Log in now to use MyFridgeTracker!");

                            Log.d("Role: ",fName + "  Fridge for user: " + lName);
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

    private void sendJson(final String userID, final int userRole) throws JSONException {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/new";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, getJs(userRole, userID),
                new Response.Listener<JSONObject>() {

                    /**
                     * prints on log for testing
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            Log.d("Response from server: ", response.getString("fridgeid"));
                            viewFridge.setText("Fridge ID: " + response.getString("fridgeid"));
                        }
                        catch (JSONException e)
                        {

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
                *@return
                */
                @Override
                protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fridgeid", userID);
                params.put("role", getString(userRole));

                return params;
                }
                };

        mQueue.add(jsonObjReq);

    }


    protected JSONObject getJs(int role, String id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("fridgeid", id);
        params.put("role", role);

        return params;
    }

}
