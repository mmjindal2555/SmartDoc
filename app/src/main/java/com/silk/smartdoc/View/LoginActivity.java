package com.silk.smartdoc.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silk.smartdoc.Controller.LoggingController;
import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.R;

public class LoginActivity extends AppCompatActivity {

    TextView sdLogo;
    EditText usernameET;
    EditText passwordET;
    Button loginButton;
    Button signUpButton;

    LoggingController loggingController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sdLogo = (TextView)findViewById(R.id.sdLogo);
        usernameET = (EditText)findViewById(R.id.usernameEditText);
        passwordET = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/Satisfy-Regular.ttf");
        sdLogo.setTypeface(myCustomFont);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");
        //myRef.setValue("Hello, World!");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                if (username.equals("akash") && password.equals("agarwal")) {
                    Intent intent = new Intent(LoginActivity.this, HealthForum.class);
                    startActivity(intent);
                }
                else if(username.equals("admin") && password.equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, AdminControl.class);
                    startActivity(intent);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);
            }
        });


    }
}
