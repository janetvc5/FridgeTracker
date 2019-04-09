package com.example.fridge_tracker;

import android.app.Activity;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

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

    EditText search;
    Button searchButton, buttonAdd;
    Spinner dropdown;
    private TextView msgResponse;
    private String foodData;
    String[] items;


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

        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //dropdown.setAdapter(dataAdapter);

        dropdown = (Spinner) findViewById(R.id.dropdown);
        msgResponse = (TextView) findViewById(R.id.msgResponse);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // uncomment me ???
                //getJsonArr(search.getText().toString());



            }
        });


        dropdown.setPrompt("Results:");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchActivity.this, AddScreen.class);
                startActivity(intent);

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

    }


    private void getJson(String itemID)
    {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "https://api.edamam.com/api/food-database/parser?ingr=" + itemID + "&app_id=cabafde8&app_key=302c40ba00505410d9b0e8e9bf7ca8e2\n";
        //String url= "http://cs309-af-1.misc.iastate.edu:8080/item";
        JsonObjectRequest jsonArReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        foodData=response.toString();

                        msgResponse.setText(foodData);



                        // JSONObject json = readJsonFromUrl(url);
                        // System.out.println(json.toString());
                        // System.out.println(json.get("label"));

                        //Log.d("Response", foodData);
                        //msgResponse.setText(foodData);

                        // Scanner scan=new Scanner(response);

                        //scan.next("foodId");
                        // msgResponse.setText(food);

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
                String foodName = "";
                params.put("foodname", foodName);
                params.put("id", "10");
                params.put("quantity", "3");
                params.put("expirationdate", "2019-03-29");
                params.put("fridge", "(\"id\":\"1\")");

                return params;
            }



        };

        mQueue.add(jsonArReq);

    }

    private static JSONArray intoArray(String strJSON) throws JSONException{
        JSONArray jsonarray = new JSONArray(strJSON);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            String foodname = jsonobject.getString("food.label");
            //String url = jsonobject.getString("url");


            //items = new String[]{foodname};

        }

        return jsonarray;

    }

    protected JSONObject getJs(String foodName) throws JSONException {
        JSONObject params = new JSONObject();
        //add in additional needed params???
        params.put("foodname", foodName);
        params.put("id", "10");
        params.put("quantity", "3");
        params.put("expirationdate", "2019-03-29");
        params.put("fridge", "{\"id\":\"53\"}");

        return params;
    }

}