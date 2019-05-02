package com.example.fridge_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class GrocItemInfo extends AppCompatActivity {

        TextView tvName, tvQuantity;
        Button buttonDelete;
        Snackbar popUp, popUp2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_groc_item_info);

            tvName = (TextView) findViewById(R.id.tvName);
            tvQuantity = (TextView) findViewById(R.id.tvQuantity);

            buttonDelete = (Button) findViewById(R.id.button2);
            popUp = (Snackbar) Snackbar.make(findViewById(R.id.Coordinator2), "You must be a Grocery Shopper to delete items from the grocery list.", Snackbar.LENGTH_SHORT);
            popUp2 = (Snackbar) Snackbar.make(findViewById(R.id.Coordinator2), "Item Deleted.", Snackbar.LENGTH_SHORT);


            buttonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (((GlobalVariables) getApplication()).getRole() == "0"){
                        deleteItem();

                        Intent intent = new Intent(GrocItemInfo.this, GroceryListActivity.class);
                        startActivity(intent);
                    }
                    else{
                        popUp.show();
                    }

                }

            });

            tvName.append(((GlobalVariables) getApplication()).getSelectedSearchItem());
            tvQuantity.append(((GlobalVariables) getApplication()).getQuantity());


        }

        private void deleteItem() {
            String item = ((GlobalVariables) getApplication()).getItemID();
            if (item == null) {
                deleteItem();
            } else {
                RequestQueue mQueue = Volley.newRequestQueue(this);
                String url = "http://cs309-af-1.misc.iastate.edu:8080/grocerylist/delete/" + item;

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            /**
                             * Shows the response from the server on the screen
                             *
                             * @param jsonresponse
                             */
                            @Override
                            public void onResponse(JSONObject jsonresponse) {

                                popUp2.show();

                            }
                        }, new Response.ErrorListener() {

                    /**
                     * @param error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }

                }) {
                };

                mQueue.add(jsonObjReq);
            }
        }

}
