package com.silk.smartdoc.View;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silk.smartdoc.Controller.LoggingController;
import com.silk.smartdoc.R;

public class LoginActivity extends Activity {

    TextView sdlogo;
    EditText usernameET;
    EditText passwordET;
    Button loginButton;
    Button signupButton;

    LoggingController loggingController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sdlogo = (TextView)findViewById(R.id.sdLogo);
        usernameET = (EditText)findViewById(R.id.usernameEditText);
        passwordET = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        signupButton = (Button)findViewById(R.id.signUpButton);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/Satisfy-Regular.ttf");
        sdlogo.setTypeface(myCustomFont);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");



    }
}
