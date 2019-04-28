package com.example.fridge_tracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.PopupMenu;
import android.widget.TextView;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;

import android.view.View;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Grocery list displays the user's list of needed groceries
 */
public class GroceryListActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView title;
    Button addButton;
    RecyclerView list;
    ArrayList<String> myDataset;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        addButton = (Button) findViewById(R.id.buttonAdd);
        title = (TextView) findViewById(R.id.title);
        list = (RecyclerView) findViewById(R.id.groceryList);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        myDataset = new ArrayList<String>();

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        list.setAdapter(mAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroceryListActivity.this, SearchActivity.class);
                startActivity(intent);
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
                        } else if (item.getTitle().equals("Fridge View")) {
                            Intent intent2 = new Intent(GroceryListActivity.this, SearchActivity.class);
                            startActivity(intent2);
                        } else if (item.getTitle().equals("Chat")) {
                            Intent intent3 = new Intent(GroceryListActivity.this, ChatActivity.class);
                            startActivity(intent3);
                        }

                        return true;

                    }
                });

                popup.show(); //showing popup menu
            }
        });

    }
}
