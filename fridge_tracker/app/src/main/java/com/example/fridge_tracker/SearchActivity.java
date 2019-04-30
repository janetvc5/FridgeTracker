package com.example.fridge_tracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The user searches for a specific food item, the back end returns a json array, front end displays it as a list for the user to select
 */
public class SearchActivity extends AppCompatActivity {

    EditText search;
    Button searchButton, buttonAdd;
    ListView list;
    ArrayList<String> items = new ArrayList<String>();
    //String[] items = new String[20];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = (EditText) findViewById(R.id.etSearch);
        searchButton = (Button) findViewById(R.id.searchButton);
        list = (ListView) findViewById(R.id.list);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        searchButton.setOnClickListener(new View.OnClickListener() {

            /**
             * takes in the user's input and requests a json array from the back end.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {

                getJson(search.getText().toString());

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {

            /**
             * Opens a new page for the user to enter more information about the searched for item
             *
             * @param v
             */
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchActivity.this, AddScreen.class);
                startActivity(intent);

            }
        });

    }

    private void getJson(String item)
    {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/search?ingr=" + item;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    /**
                     * api returns the values for each food item
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hints = response.getJSONArray("hints");
                            String stuff = hints.getString(1);
                            Log.d("hints", "hints response " + stuff);


                            for (int i = 0; (i < hints.length() && i < 20); i++){
                                JSONObject hintItem = hints.getJSONObject(i).getJSONObject("food");
                                Log.d("hintitem", "hint index: " + hintItem);

                                String foodInIndex = hintItem.getString("label");
                                Log.d("fooditem", "food label" + foodInIndex);

                                items.add(foodInIndex);
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchActivity.this,android.R.layout.simple_list_item_single_choice, items);
                                list.setAdapter(adapter);

                                list.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                {
                                    boolean somethingChecked = false;
                                    int lastChecked;
                                    public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                                            long arg3) {
                                        if(somethingChecked){
//                                            ListView lv = (ListView) arg0;
//                                            TextView tv = (TextView) lv.getChildAt(lastChecked);
                                            CheckedTextView cv = (CheckedTextView) arg1;
                                            cv.setChecked(false);
                                        }
//                                        ListView lv = (ListView) arg0;
//                                        TextView tv = (TextView) lv.getChildAt(arg2);
                                        CheckedTextView cv = (CheckedTextView) arg1;
                                        if(!cv.isChecked())
                                            cv.setChecked(true);
                                        lastChecked = arg2;
                                        somethingChecked = true;

                                        ((GlobalVariables) getApplication()).setSelectedSearchItem(items.get(arg2));
                                    }
                                });

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

}
