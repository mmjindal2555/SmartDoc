package com.silk.smartdoc.View;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Controller.LoggingController;
import com.silk.smartdoc.Controller.SmartDocAccountManager;
import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.R;

public class LoginActivity extends AppCompatActivity {


    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String AUTH_TOKEN_TYPE = "silk.smartdoc.com";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";


    TextView sdLogo;
    EditText usernameET;
    EditText passwordET;
    Button loginButton;
    Button signUpButton;

    LoggingController loggingController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccountManager am = AccountManager.get(this); // "this" references the current Context
        Account[] accounts = am.getAccountsByType(AUTH_TOKEN_TYPE);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        loggingController = new LoggingController();
        if(accounts.length > 0){
            final String user = accounts[0].name;
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref = ref.child("Users");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Person person = loggingController.getAlreadyPerson(dataSnapshot,user);
                    startHealthForumActivity(person);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        setContentView(R.layout.activity_login);
        sdLogo = (TextView)findViewById(R.id.sdLogo);
        usernameET = (EditText)findViewById(R.id.usernameEditText);
        passwordET = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        signUpButton = (Button)findViewById(R.id.signUpButton);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/Satisfy-Regular.ttf");
        sdLogo.setTypeface(myCustomFont);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameET.getText().toString();
                final String password = passwordET.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                reference = reference.child("Users");
                if(username.equals("admin") && password.equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, AdminControl.class);
                    startActivity(intent);
                }
                else {
                    final DatabaseReference finalReference = reference;
                    if(!(username.equals("")||password.equals(""))){
                        mAuth.signInWithEmailAndPassword(username, password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            finalReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Person person = loggingController.isValid(dataSnapshot,
                                                            username, password);
                                                    finishLogin(person);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        } else {
                                            Toast.makeText(LoginActivity.this,
                                                    "Incorrect Username and/or Password", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"Enter the credentials",Toast.LENGTH_LONG).show();
                    }

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
    private void finishLogin(Person person) {

        AccountManager mAccountManager = AccountManager.get(LoginActivity.this);
        String accountName = person.getEmail();
        String accountPassword = person.getPassword();
        final Account account = new Account(accountName, AUTH_TOKEN_TYPE);

        // Creating the account on the device and setting the auth token we got
        // (Not setting the auth token will cause another call to the server to authenticate the user)
        String authtokenType = "Bearer";
        mAccountManager.addAccountExplicitly(account, accountPassword, null);
        mAccountManager.setAuthToken(account, authtokenType, accountName);
        startHealthForumActivity(person);
    }
    public void startHealthForumActivity(Person person){
        Intent i = new Intent(LoginActivity.this, HealthForum.class);
        i.putExtra("Person",person);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}
