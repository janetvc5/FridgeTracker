package com.example.fridge_tracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

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
    ArrayList<FoodItem> groceries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        addButton = (Button) findViewById(R.id.buttonAdd);
        title = (TextView) findViewById(R.id.title);
        list = (RecyclerView) findViewById(R.id.groceryList);
        groceries = new ArrayList<FoodItem>();



        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new MyAdapter(groceries);
        list.setAdapter(mAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroceryListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        }
    }
