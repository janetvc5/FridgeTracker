package com.example.fridge_tracker;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    /* Bottom Navigation */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_myfridge:
                    return true;
                case R.id.navigation_grocerylist:
                    return true;
                case R.id.navigation_recipes:
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }

    };
    /*End Bottom Navigation*/





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final TextView results;
        //String url = "";
        final EditText search;
        final Button searchButton, addButton;
        final Spinner dropdown;

        search = (EditText) findViewById(R.id.etSearch);
        searchButton = (Button) findViewById(R.id.searchButton);
        dropdown = (Spinner) findViewById(R.id.spinner);
        addButton = (Button) findViewById(R.id.addButton);


        //fake item list, search button will do nothing
        List<String> list = new ArrayList<String>();
        list.add("full apple");
        list.add("apple pie");
        list.add("baked apple");
        list.add("freeze dried apple");
        list.add("apple crisp");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);



        //end

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uncomment me ???
                //getJsonArr(search.getText().toString());



            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // What is needed for new food item input???
                    sendJson(String.valueOf(dropdown.getSelectedItem()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void getJsonArr(String item)
    {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "https://api.edamam.com/api/food-database/parser?ingr=" + item + "&app_id=cabafde8&app_key=302c40ba00505410d9b0e8e9bf7ca8e2";
        //String url = "http://cs309-af-1.misc.iastate.edu:8080/user";


        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                url, new JSONArray(),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("Found: ", response.toString()); //prints to logcat

                            // first json object in outer array
                            JSONObject foodObj = response.getJSONObject(2); // it might be 0???

                            // json array in object
                            JSONArray foodArr = foodObj.getJSONArray("hints");


                            //iterate through
                            for (int i = 0; i < foodArr.length(); i++){
                                //get json object from within the array
                                JSONObject jsonObject = foodArr.getJSONObject(i);

                                String label = jsonObject.getString("label");
                                //String category = String.valueOf(response.getInt("category"));

                                // putting into a string list
                                //List<String> list = new ArrayList<String>();
                                //list.add(label);

                                // ??? option = document.createElement('option');
                                // option.text = label;
                                // dropdown.add(option);


                                //Log.d("Found: ", label + " in category " + category); //prints to logcat
                            }


                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) ;
        //remove above semicolon if we want to pass the headers in!!
//        {
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };

        mQueue.add(jsonArrReq);

    }

    void sendJson(final String foodName) throws JSONException {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        // update the url for adding a food item ???
        String url = "http://cs309-af-1.misc.iastate.edu:8080/user/new";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, getJs(foodName), // update this if function is updated???
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            Log.d("Response from server: ", response.getString("label"));
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
                //add in additional needed params???
                params.put("label", foodName);

                return params;
            }



        };

        mQueue.add(jsonObjReq);

    }

    protected JSONObject getJs(String foodName) throws JSONException {
        JSONObject params = new JSONObject();
        //add in additional needed params???
        params.put("label", foodName);

        return params;
    }

}
