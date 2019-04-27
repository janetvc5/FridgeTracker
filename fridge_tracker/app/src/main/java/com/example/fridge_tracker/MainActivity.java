package com.example.fridge_tracker;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the user's list of items in their fridge.
 */
public class MainActivity extends AppCompatActivity {
    TextView pageTitle;
    //Button sendButton, getButton;
    FloatingActionButton floatingActionButton;
    //EditText getUserInfo, sendID, sendRole;
    RecyclerView fridgeList;
    //ArrayList<String> myDataset;
    String food;
    String quan;
    String exp;
    ArrayList<FoodItem> fridgeContents=new ArrayList<FoodItem>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String URL_DATA="http://cs309-af-1.misc.iastate.edu:8080/fridge/1/contents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pageTitle = (TextView) findViewById(R.id.pageTitle);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fridgeList = (RecyclerView) findViewById(R.id.fridgeList);

        loadFoodData();

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        fridgeList.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new MyAdapter(fridgeContents);
        fridgeList.setAdapter(mAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, floatingActionButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Grocery List")) {
                             Intent intent1 = new Intent(MainActivity.this, GroceryListActivity.class);
                             startActivity(intent1);
                        } else if (item.getTitle().equals("Fridge View")) {
                            Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent2);
                        } else if (item.getTitle().equals("Chat")) {
                            Intent intent3 = new Intent(MainActivity.this, ChatActivity.class);
                            startActivity(intent3);
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
    }

    private void loadFoodData(){
        //StringRequest stringRequest = new StringRequest(URL_DATA, Response.Listener<String>(), Response.ErrorListener<>()){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL_DATA, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           // JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = response.getJSONArray("items");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jo = array.getJSONObject(i);
                                //ArrayList<String> food=new ArrayList<String>();
                                food=(jo.getString("foodname"));
                                quan=(jo.getString("quantity"));
                                exp=(jo.getString("expirationdate"));
                                fridgeContents.add(new FoodItem(food, quan, exp));
                            }
                        } catch(Exception e){

                        }
                    }
        }, new Response.ErrorListener() {
            /**
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    protected JSONObject getJs(String role, String id) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("fridgeid", id);
        params.put("role", role);
        return params;
    }
}
