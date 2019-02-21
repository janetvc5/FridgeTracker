package com.example.fridgetracker;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_myfridge:
                    mTextMessage.setText("My Fridge");
                    return true;
                case R.id.navigation_grocerylist:
                    mTextMessage.setText("Grocery List");
                    return true;
                case R.id.navigation_recipes:
                    mTextMessage.setText("Recipes");
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText("Settings");
                    return true;

            }
            return false;
        }
    };


    private Button btnJson, btnString, btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnString = findViewById(R.id.btnStringRequest);
        btnJson = findViewById(R.id.btnJsonRequest);
        btnImage = findViewById(R.id.btnImageRequest);

        // button click listeners
        btnString.setOnClickListener(this);
        btnJson.setOnClickListener(this);
        btnImage.setOnClickListener(this);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStringRequest:
                startActivity(new Intent(MainActivity.this,
                        StringRequestActivity.class));
                break;
            case R.id.btnJsonRequest:
                startActivity(new Intent(MainActivity.this,
                        JsonRequestActivity.class));
                break;
            case R.id.btnImageRequest:
                startActivity(new Intent(MainActivity.this,
                        ImageRequestActivity.class));
                break;
            default:
                break;
        }
    }


}
