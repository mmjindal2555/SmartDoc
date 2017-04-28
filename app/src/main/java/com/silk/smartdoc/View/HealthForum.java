package com.silk.smartdoc.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.R;
import com.silk.smartdoc.View.MedicineSearch;
import com.silk.smartdoc.View.TestSearchActivity;

public class HealthForum extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_forum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setBackgroundColor(getResources().getColor(R.color.accentcolor));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void openPostQueryActivity(View view){
        Intent intent = new Intent(this,PostQueryExperience.class);
        startActivity(intent);
    }

    public void openAnswerQueryActivity(View view){
        Intent intent = new Intent(this,AnswerQuery.class);
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
            Intent intents = new Intent(this,HealthForum.class);
            startActivity(intents);
        } else if (id == R.id.medical_tests) {
            sdm.displayMgr.displayTestSearchPage(this);
            //Intent intents = new Intent(this,TestSearchActivity.class);
            //startActivity(intents);
        } else if (id == R.id.medicines) {
            sdm.displayMgr.displayMedicineSerachPage(this);
        } else if (id == R.id.my_questions) {
            Intent intents = new Intent(this,HealthForum.class);
            startActivity(intents);
        } else if (id == R.id.my_experiences) {
            Intent intents = new Intent(this,HealthForum.class);
            startActivity(intents);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
