package com.silk.smartdoc.View;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Controller.SmartDocAccountManager;
import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Statement;
import com.silk.smartdoc.R;
import com.silk.smartdoc.View.MedicineSearch;
import com.silk.smartdoc.View.TestSearchActivity;

import java.util.ArrayList;
import java.util.Date;

public class HealthForum extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Person person;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_forum);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setOverflowIcon(getDrawable(R.drawable.ic_action_more_vert));
        toolbar.setNavigationIcon(R.drawable.ic_action_dehaze);
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        // getting Person who logged in
        Intent loginIntent = getIntent();
        person = loginIntent.getParcelableExtra("Person");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView nameTV = (TextView)header.findViewById(R.id.nameTV);
        TextView emailTV = (TextView)header.findViewById(R.id.emailTV);
        listView = (ListView) findViewById(R.id.faqListView);

        nameTV.setText(person.getName());
        emailTV.setText(person.getEmail());
        drawer.setBackgroundColor(getResources().getColor(R.color.accentcolor));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Query");



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Query> queries = new ArrayList<Query>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                for (DataSnapshot c: children)
                {
                    queries.add(c.getValue(Query.class));

                }
                PostQueryAdapter postQueryAdapter = new PostQueryAdapter(queries,HealthForum.this,person);
                listView.setAdapter(postQueryAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        navigationView.setNavigationItemSelectedListener(this);
    }

    public void openPostQueryActivity(View view){
        //Toast.makeText(HealthForum.this, "This functionality is coming soon",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,PostQueryExperience.class);
        intent.putExtra("Person",person);
        startActivity(intent);
    }

    public void openAnswerQueryActivity(View view){
        //Toast.makeText(HealthForum.this, "This functionality is coming soon",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,AnswerQuery.class);
        intent.putExtra("Person",person);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.health_forum, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        SmartDocManager sdm = (SmartDocManager)getApplicationContext();
        if (id == R.id.health_forum) {
            Toast.makeText(HealthForum.this, "This funcionality is coming soon",Toast.LENGTH_LONG).show();
        } else if (id == R.id.medical_tests) {

            sdm.displayMgr.displayTestSearchPage(this);

        } else if (id == R.id.medicines) {

            sdm.displayMgr.displayMedicineSerachPage(this);

        } else if (id == R.id.my_questions) {
            Intent intent = new Intent(this,MyQuestions.class);
            intent.putExtra("Person",person);
            startActivity(intent);
        } else if (id == R.id.my_experiences) {
            Intent intent = new Intent(this,MyAnswers.class);
            intent.putExtra("Person",person);
            startActivity(intent);
        }
        else if(id == R.id.logout){
            AccountManager accountManager = AccountManager.get(HealthForum.this);
            Account[] accounts = accountManager.getAccountsByType(LoginActivity.AUTH_TOKEN_TYPE);
            for(Account account : accounts) {
                accountManager.removeAccount(account, new AccountManagerCallback<Boolean>() {
                    @Override
                    public void run(AccountManagerFuture<Boolean> future) {
                        try {
                            if (future.getResult()) {
                                // do something
                                Intent i = new Intent(HealthForum.this, LoginActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, null);
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
