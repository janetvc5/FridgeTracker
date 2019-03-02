package com.example.fridge_tracker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    Button b1, b2;
    EditText ed1, ed2;
    TextView t1;
    int counter = 3; //will be used to count log-in tries


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed1 = (EditText) findViewById(R.id.editTextUser);
        ed2 = (EditText) findViewById(R.id.editTextPass);
        b1 = (Button) findViewById(R.id.buttonlogin);

        //b2=(Button)findViewById(R.id.button2);
        //t1=(TextView)findViewById(R.id.text)

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed1.getText().toString().equals("admin") &&
                        ed2.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    t1.setVisibility(View.VISIBLE);
                    t1.setBackgroundColor(Color.RED);
                    counter--;
                    t1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }
            }
        });
    }


    EditText username = (EditText) findViewById(R.id.editTextUser);
    EditText password = (EditText) findViewById(R.id.editTextPass);

    public void login(View view) {
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {

            //correcct password
        } else {
            //wrong password
            counter--;
            if (counter == 0) {

            }
        }
    }
}
