package com.example.fridge_tracker;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Switch;

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

/**
 * A page to add a food item to the user's fridge
 */
public class AddScreen extends AppCompatActivity {

    TextView tvName, tvQuantity, tvExpiration, response;
    EditText etName, etQuantity, etExpiration;
    Button buttonAdd;
    Switch location;
    Snackbar popUp;
//    Snackbar popUp2, popUp3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_screen);

        tvName = (TextView) findViewById(R.id.tvName);
        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        tvExpiration = (TextView) findViewById(R.id.tvExpiration);

        etName = (EditText) findViewById(R.id.etName);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etExpiration = (EditText) findViewById(R.id.etExpiration);

        buttonAdd = (Button) findViewById(R.id.button2);

        location = (Switch) findViewById(R.id.switch1);
        response = (TextView) findViewById(R.id.response);

        popUp = (Snackbar) Snackbar.make(findViewById(R.id.Coordinator), "You must be a Grocery Shopper or Admin to add items to the grocery list.", Snackbar.LENGTH_SHORT);
//        popUp2 = (Snackbar) Snackbar.make(findViewById(R.id.Coordinator), ((GlobalVariables) getApplication()).getSelectedSearchItem() + " added to Fridge List", Snackbar.LENGTH_LONG);
//       popUp3 = (Snackbar) Snackbar.make(findViewById(R.id.Coordinator), ((GlobalVariables) getApplication()).getSelectedSearchItem() + " added to Grocery List", Snackbar.LENGTH_LONG);



        buttonAdd.setOnClickListener(new View.OnClickListener() {

            /**
             * Sends a post json request
             *
             * @param v
             */
            @Override
            public void onClick(View v) {

                if(location.isChecked())
                {
                    try{
                        postJsonToFridge();
                        Intent intentF = new Intent(AddScreen.this, MainActivity.class);
                        //based on item add info to intent
                        startActivity(intentF);

                    } catch (JSONException e){

                    }


                }
                else
                {
                    try{
                        if (((GlobalVariables) getApplication()).getRole() != "2" ){
                            postJsonToGrocery();
                            Intent intentG = new Intent(AddScreen.this, GroceryListActivity.class);
                            //based on item add info to intent
                            startActivity(intentG);
                        }
                        else{
                            popUp.show();
                        }

                    } catch (JSONException e){

                    }

                }
            }
        });

        etName.setText(((GlobalVariables) getApplication()).getSelectedSearchItem());

    }

    private void postJsonToFridge() throws JSONException{
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/fridgecontents/new";
        JSONObject jsob = getJsFridge( etName.getText().toString(), etQuantity.getText().toString(), etExpiration.getText().toString(), ((GlobalVariables) getApplication()).getFridgeID());
        Log.d("myObject", "this is the string value: " + jsob.toString());


        JsonObjectRequest jsonPostReq = new JsonObjectRequest(Request.Method.POST,
                url, jsob,
                new Response.Listener<JSONObject>() {

                    /**
                     * Shows the response from the server on the screen
                     *
                     * @param jsonresponse
                     */
                    @Override
                    public void onResponse(JSONObject jsonresponse) {

                        //popUp2.show();
                        response.setText(jsonresponse.toString());


                    }
                },new Response.ErrorListener() {
            /**
             * Error report
             *
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })

        {
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
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("foodname",etName.getText().toString());
                params.put("quantity", etQuantity.getText().toString());
                params.put("expirationdate", etExpiration.getText().toString());
                params.put("fridge", "{id:" + ((GlobalVariables) getApplication()).getFridgeID() + "}");
                return params;
            }
        };


        mQueue.add(jsonPostReq);
    }


    private void postJsonToGrocery () throws JSONException {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/grocerylist/new";
        JSONObject jsob = getJsGrocery( etName.getText().toString(), etQuantity.getText().toString(), ((GlobalVariables) getApplication()).getFridgeID());
        Log.d("myObject", "this is the string value: " + jsob.toString());

        JsonObjectRequest jsonPostReq = new JsonObjectRequest(Request.Method.POST,
                url, jsob,
                new Response.Listener<JSONObject>() {

                    /**
                     * Shows the response from the server on the screen
                     *
                     * @param jsonresponse
                     */
                    @Override
                    public void onResponse(JSONObject jsonresponse) {

                        //popUp3.show();
                        response.setText(jsonresponse.toString());

                    }
                },new Response.ErrorListener() {
            /**
             * Error report
             *
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })

        {
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
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("foodname", etName.getText().toString());
                params.put("quantity", etQuantity.getText().toString());
                params.put("fridge", "{id:" + ((GlobalVariables) getApplication()).getFridgeID() + "}");

                return params;
            }
        };


        mQueue.add(jsonPostReq);
    }

    protected JSONObject getJsFridge(String food, String quantity, String expiration, String fridgeID) throws JSONException {
        JSONObject params = new JSONObject();
            params.put("foodname", food);
            params.put("quantity", quantity);
            params.put("expirationdate", expiration);
            JSONObject fridge = new JSONObject();
            fridge.put("id", fridgeID);

            params.putOpt("fridge", fridge);

            return params;

    }

    protected JSONObject getJsGrocery(String food, String quantity, String fridgeID) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("foodname", food);
        params.put("quantity", quantity);

        JSONObject fridge = new JSONObject();
        fridge.put("id", fridgeID);

        params.putOpt("fridge", fridge);

        return params;

    }
}



