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



    TextView results;
    //String url = "";
    String data = "yup";
    EditText search;
    Button searchButton;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = (EditText) findViewById(R.id.etSearch);
        searchButton = (Button) findViewById(R.id.searchButton);
        dropdown = (Spinner) findViewById(R.id.spinner);


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

                //getJsonArr(search.getText().toString());



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
                            JSONObject foodObj = response.getJSONObject(0);

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

                                data += "Result #" + (i+1) + " Name: " + label + "     ";
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
}
