package com.example.fridge_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A page to add a food item to the user's fridge
 */
public class AddScreen extends AppCompatActivity {

    TextView tvName, tvQuantity, tvExpiration, tvFridge, response;
    EditText etName, etQuantity, etExpiration, etFridge;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_screen);

        tvName = (TextView) findViewById(R.id.tvName);
        tvQuantity = (TextView) findViewById(R.id.tvQuantity);
        tvExpiration = (TextView) findViewById(R.id.tvQuantity);
        tvFridge = (TextView) findViewById(R.id.tvFridge);

        etName = (EditText) findViewById(R.id.etName);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        etExpiration = (EditText) findViewById(R.id.etExpiration);
        etFridge = (EditText) findViewById(R.id.etFridge);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        response = (TextView) findViewById(R.id.response);

        buttonAdd.setOnClickListener(new View.OnClickListener() {

            /**
             * Sends a post json request
             *
             * @param v
             */
            @Override
            public void onClick(View v) {

                postJson();

            }
        });

    }

    private void postJson() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "http://cs309-af-1.misc.iastate.edu:8080/fridgecontents/new";

        JsonObjectRequest jsonPostReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    /**
                     * Shows the response from the server on the screen
                     *
                     * @param jsonresponse
                     */
                    @Override
                    public void onResponse(JSONObject jsonresponse) {

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
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("foodname","apple");
                params.put("quantity", etQuantity.getText().toString());
                params.put("expiration", etExpiration.getText().toString());
                params.put("fridge", "id:1");
                return params;
            }
        };


        Volley.newRequestQueue(this).add(jsonPostReq);
    }
}

