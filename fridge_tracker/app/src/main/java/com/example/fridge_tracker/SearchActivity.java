package com.example.fridge_tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The user searches for a specific food item, the back end returns a json array, front end displays it as a list for the user to select
 */
public class SearchActivity extends AppCompatActivity {

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

        search = (EditText) findViewById(R.id.etSearch);
        searchButton = (Button) findViewById(R.id.searchButton);
        dropdown = (Spinner) findViewById(R.id.dropdown);
        msgResponse = (TextView) findViewById(R.id.msgResponse);
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

        dropdown.setPrompt("Results:");

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


                            for (int i = 0; i < hints.length(); i++){
                                JSONObject hintItem = hints.getJSONObject(i);
                                JSONObject foodInIndex = hintItem.getJSONObject("food");


                                String foodname = foodInIndex.getString("label");

                                msgResponse.setText("\n" + foodname);
                                Log.d("tag","response from api:" + foodname);
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

    private void getJson1(String itemID)
    {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "https://api.edamam.com/api/food-database/parser?ingr=" + itemID + "&app_id=cabafde8&app_key=302c40ba00505410d9b0e8e9bf7ca8e2";
        //String url= "http://cs309-af-1.misc.iastate.edu:8080/item";


        JsonObjectRequest jsonArReq = new JsonObjectRequest(Request.Method.GET,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    /**
                     * Takes the response from back end and prints it onto the screen.
                     *
                     * @param response
                     */
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
                //headers.put("fridgeid", "role");
                return headers;
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


}
