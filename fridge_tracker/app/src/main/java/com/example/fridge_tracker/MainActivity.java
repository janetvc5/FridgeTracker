package com.example.fridge_tracker;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.support.v7.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the user's list of items in their fridge.
 */
public class MainActivity extends AppCompatActivity {
    Button add, ref;
    FloatingActionButton floatingActionButton;
    EditText getUserInfo, sendID, sendRole;
    ListView list;
    ArrayList<String> items = new ArrayList<String>();
    Boolean loaded = false;
    JSONArray fridgeArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // sendButton = (Button) findViewById(R.id.sendbutton);
       // getButton = (Button) findViewById(R.id.addbutton);
        //getUserInfo = (EditText) findViewById(R.id.sendtext);
       // sendRole = (EditText) findViewById(R.id.sendrole);
        //sendID = (EditText) findViewById(R.id.sendfridgeid);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        list = (ListView) findViewById(R.id.list);
        add = (Button) findViewById(R.id.addbutton);
        ref = (Button) findViewById(R.id.buttonRef2);
        floatingActionButton.setImageResource(R.drawable.listicon);

        getFridge();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loaded == false){
                    getFridge();
                }

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
                        } else if (item.getTitle().equals("Chat")) {
                            Intent intent3 = new Intent(MainActivity.this, ChatActivity.class);
                            startActivity(intent3);
                        } else if (item.getTitle().equals("My Fridge")) {
                            Intent intent4 = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent4);
                        }

                        return true;

                    }
                });

                popup.show(); //showing popup menu

            }
        });
    }

    private void getFridge()
    {
        String fridgeID = ((GlobalVariables) getApplication()).getFridgeID();
            if (fridgeID == null){
                items.add("Please refresh while contents are loading...");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_single_choice, items);
                list.setAdapter(adapter);
            }
            else {
                loaded = true;
                items.clear();
                RequestQueue mQueue = Volley.newRequestQueue(this);
                String url = "http://cs309-af-1.misc.iastate.edu:8080/fridge/" + fridgeID + "/contents";
                JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONArray>() {

                            /**
                             * api returns the list
                             *
                             * @param response
                             */
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    fridgeArr = response;

                                    if (response.length() != 0) {
                                        for (int i = 0; (i < response.length()); i++) {
                                            JSONObject fridgeItem = response.getJSONObject(i);
                                            String fridge = fridgeItem.getString("foodname");
                                            Log.d("hints", "hints response " + fridge);

                                            items.add(fridge);
                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_single_choice, items);
                                            list.setAdapter(adapter);

                                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                boolean somethingChecked = false;

                                                public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                                                    if (somethingChecked) {
                                                        CheckedTextView cv = (CheckedTextView) arg1;
                                                        cv.setChecked(false);
                                                    }
                                                    CheckedTextView cv = (CheckedTextView) arg1;
                                                    if (!cv.isChecked())
                                                        cv.setChecked(true);
                                                    somethingChecked = true;

                                                    try{
                                                        JSONObject item = fridgeArr.getJSONObject(arg2);
                                                        String itemID = item.getString("id");
                                                        String quantity = item.getString("quantity");
                                                        String expires = item.getString("expirationdate");
                                                        ((GlobalVariables) getApplication()).setItemID(itemID);
                                                        ((GlobalVariables) getApplication()).setQuantity(quantity);
                                                        ((GlobalVariables) getApplication()).setExpiration(expires);
                                                    } catch (JSONException e){

                                                    }

                                                    ((GlobalVariables) getApplication()).setSelectedSearchItem(items.get(arg2));

                                                    Intent intent = new Intent(MainActivity.this, ItemInfo.class);
                                                    startActivity(intent);
                                                }
                                            });

                                        }
                                    } else {
                                        items.add("There are no items in your fridge");
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_single_choice, items);
                                        list.setAdapter(adapter);
                                    }

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

                mQueue.add(jsonArrReq);
            }
    }



/**
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
                     *
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

            /**
             * Error catcher
             *
             * @param error
             *
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            /**
             * Passing some request headers
             **
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
                     * prints on log for testing
                     *
                     * @param response
                     *
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
             * Error catcher
             *
             * @param error
             *
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }) {

            /**
             * Passing some request headers
             * *
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
             *
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
    } **/
}
