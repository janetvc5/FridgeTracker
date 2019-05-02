package com.example.fridge_tracker;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;

import android.view.View;

import android.support.v7.app.AppCompatActivity;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Grocery list displays the user's list of needed groceries
 */
public class GroceryListActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView title;
    Button addButton, refButton;
    //RecyclerView list;
    ListView list;
    ArrayList<String> myDataset;
    FloatingActionButton floatingActionButton;
    ArrayList<String> items = new ArrayList<String>();
    Boolean loaded = false;
    JSONArray grocArr;
    Snackbar popUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        addButton = (Button) findViewById(R.id.buttonAdd);
        refButton = (Button) findViewById(R.id.buttonRef);
        title = (TextView) findViewById(R.id.title);
        list = (ListView) findViewById(R.id.list);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        popUp = (Snackbar) Snackbar.make(findViewById(R.id.Coordinator1), "You must be a Grocery Shopper or Admin to add items to the grocery list.", Snackbar.LENGTH_SHORT);


        getGroceries();

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((GlobalVariables) getApplication()).getRole() != "2" ){
                    Intent intent = new Intent(GroceryListActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
                else{
                    popUp.show();
                }

            }
        });

        refButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loaded == false){
                    getGroceries();
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(GroceryListActivity.this, floatingActionButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Grocery List")) {
                            Intent intent1 = new Intent(GroceryListActivity.this, GroceryListActivity.class);
                            startActivity(intent1);
                        } else if (item.getTitle().equals("Chat")) {
                            Intent intent3 = new Intent(GroceryListActivity.this, ChatActivity.class);
                            startActivity(intent3);
                        } else if (item.getTitle().equals("My Fridge")) {
                            Intent intent4 = new Intent(GroceryListActivity.this, MainActivity.class);
                            startActivity(intent4);
                        }
                        return true;
                    }

                });

                popup.show(); //showing popup menu
            }
        });

    }


    private void getGroceries() {
        String fridgeID = ((GlobalVariables) getApplication()).getFridgeID();
        if (fridgeID == null){
            items.add("Please refresh while contents are loading...");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroceryListActivity.this, android.R.layout.simple_list_item_single_choice, items);
            list.setAdapter(adapter);
        }
        else {
            loaded = true;
            items.clear();
            RequestQueue mQueue = Volley.newRequestQueue(this);
            String url = "http://cs309-af-1.misc.iastate.edu:8080/fridge/" + fridgeID + "/list";
            JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONArray>() {
                        /**
                         * api returns the list
                         *
                         * @param response
                         */
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                Log.d("groclist", "response: " + response.toString());

                                grocArr = response;

                                if (response.length() != 0) {
                                    for (int i = 0; (i < response.length() && i < 20); i++) {
                                        JSONObject groceryItem = response.getJSONObject(i);
                                        String grocery = groceryItem.get("foodname").toString();

                                        items.add(grocery);
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroceryListActivity.this, android.R.layout.simple_list_item_single_choice, items);
                                        list.setAdapter(adapter);

                                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            boolean somethingChecked = false;
                                            int lastChecked;

                                            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                                                    long arg3) {
                                                if (somethingChecked) {
                                                    //                                            ListView lv = (ListView) arg0;
                                                    //                                            TextView tv = (TextView) lv.getChildAt(lastChecked);
                                                    CheckedTextView cv = (CheckedTextView) arg1;
                                                    cv.setChecked(false);
                                                }
                                                //                                        ListView lv = (ListView) arg0;
                                                //                                        TextView tv = (TextView) lv.getChildAt(arg2);
                                                CheckedTextView cv = (CheckedTextView) arg1;
                                                if (!cv.isChecked())
                                                    cv.setChecked(true);
                                                lastChecked = arg2;
                                                somethingChecked = true;

                                                try{
                                                    JSONObject item = grocArr.getJSONObject(arg2);
                                                    String itemID = item.getString("id");
                                                    String quantity = item.getString("quantity");

                                                    ((GlobalVariables) getApplication()).setItemID(itemID);
                                                    ((GlobalVariables) getApplication()).setQuantity(quantity);

                                                } catch (JSONException e){

                                                }
                                                ((GlobalVariables) getApplication()).setSelectedSearchItem(items.get(arg2));

                                                Intent intent = new Intent(GroceryListActivity.this, GrocItemInfo.class);
                                                startActivity(intent);

                                            }
                                        });

                                    }
                                } else {
                                    items.add("There are no items in your grocery list");
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroceryListActivity.this, android.R.layout.simple_list_item_single_choice, items);
                                    list.setAdapter(adapter);
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

            };

            mQueue.add(jsonArrReq);

        }
    }

}