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

        buttonDelete = (Button) findViewById(R.id.button2);

        buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteItem();

                Intent intent = new Intent(ItemInfo.this, MainActivity.class);
                startActivity(intent);
            }

        });


        tvName.append(((GlobalVariables) getApplication()).getSelectedSearchItem());
        tvQuantity.append(((GlobalVariables) getApplication()).getQuantity());
        tvExpiration.append(((GlobalVariables) getApplication()).getExpiration());

        //getItemInfo();


    }

    private void deleteItem() {
        String item = ((GlobalVariables) getApplication()).getItemID();
        if (item == null) {
            deleteItem();
        } else {
                RequestQueue mQueue = Volley.newRequestQueue(this);
                String url = "http://cs309-af-1.misc.iastate.edu:8080/fridgecontents/delete/" + item;

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                        url, null,
                        null, new Response.ErrorListener() {

                    /**
                     *
                     * @param error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }

                }) {};

                mQueue.add(jsonObjReq);
            }
        }
    }


