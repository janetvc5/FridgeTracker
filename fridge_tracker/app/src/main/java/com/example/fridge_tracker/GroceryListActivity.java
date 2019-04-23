package com.example.fridge_tracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;


public class GroceryListActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView title;
    Button addButton;
    RecyclerView list;
    ArrayList<String> myDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        addButton = (Button) findViewById(R.id.buttonAdd);
        title = (TextView) findViewById(R.id.title);
        list = (RecyclerView) findViewById(R.id.groceryList);
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
                Intent intent = new Intent(GroceryListActivity.this, AddScreen.class);
                startActivity(intent);
            }
        });




        }
    }
