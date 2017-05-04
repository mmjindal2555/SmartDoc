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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Controller.LoggingController;
import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.Model.Person;
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
                final String username = usernameET.getText().toString();
                final String password = passwordET.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference = reference.child("Users");
                final LoggingController loggingController = new LoggingController();
                if(username.equals("admin") && password.equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, AdminControl.class);
                    startActivity(intent);
                }
                else {
                    reference.addValueEventListener(new ValueEventListener() {
                          @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Person person = loggingController.isValid(dataSnapshot, username, password);
                            if(person!=null) {
                                Intent intent = new Intent(LoginActivity.this, HealthForum.class);
                                intent.putExtra("Name",person.getName());
                                intent.putExtra("Username",person.getEmail());
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Incorrect Username and/or Password"
                                        , Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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
