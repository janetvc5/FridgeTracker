package com.example.fridge_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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

public class ItemInfo extends AppCompatActivity {

        TextView tvName, tvQuantity, tvExpiration;
        Button buttonDelete;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_item_info);

            tvName = (TextView) findViewById(R.id.tvName);
            tvQuantity = (TextView) findViewById(R.id.tvQuantity);
            tvExpiration = (TextView) findViewById(R.id.tvExpiration);

            //buttonDelete = (Button) findViewById(R.id.button2);

            tvName.append(((GlobalVariables) getApplication()).getSelectedSearchItem());
            tvQuantity.append(((GlobalVariables) getApplication()).getQuantity());
            tvExpiration.append(((GlobalVariables) getApplication()).getExpiration());

            //getItemInfo();



        }

        private void getItemInfo()
        {
            RequestQueue mQueue = Volley.newRequestQueue(this);
            String url = "http://cs309-af-1.misc.iastate.edu:8080/fridge/" + ((GlobalVariables) getApplication()).getFridgeID() + "/contents";

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
//                         try {
                             Log.d("contents", "list" + response.toString());

                         //       tvName.setText(((GlobalVariables) getApplication()).getSelectedSearchItem());
//            tvQuantity.setText(((GlobalVariables) getApplication()).getSelectedSearchItem());
//            tvExpiration.setText(((GlobalVariables) getApplication()).getSelectedSearchItem());
//                             String fName = response.getString("role");
//                             String lName = String.valueOf(response.getInt("fridgeid"));
//
//
//                             Log.d("Role: ",fName + "  Fridge for user: " + lName);
//                         } catch (JSONException e) {
//                            e.printStackTrace();
//                         }


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

//                         /**
//                          * Passing some request headers
//                         **/
//                         @Override
//                         public Map<String, String> getHeaders() throws AuthFailureError {
    //                         HashMap<String, String> headers = new HashMap<String, String>();
    //                         headers.put("Content-Type", "application/json");
    //                         //headers.put("fridgeid", "role");
    //                         return headers;
//                         }
//
//                         @Override
//                         protected Map<String, String> getParams() {
    //                         Map<String, String> params = new HashMap<String, String>();
    //                         params.put("fridgeid", "36");
    //                         params.put("role", "abc@androidhive.info");
    //
    //
    //                         return params;
//                         }
            };

            mQueue.add(jsonObjReq);
        }
    }

