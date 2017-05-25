package com.silk.smartdoc.View;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Controller.SmartDocAccountManager;
import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.R;
import com.silk.smartdoc.View.MedicineSearch;
import com.silk.smartdoc.View.TestSearchActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.tkeunebr.gravatar.Gravatar;

public class HealthForum extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Person person;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    ListView listView;
    TextView emptyTV;
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

        listView = (ListView)findViewById(R.id.faqListView);
        emptyTV = (TextView)findViewById(R.id.emptyTVHF);
        listView.setEmptyView(emptyTV);
        // getting Person who logged in
        Intent loginIntent = getIntent();
        person = loginIntent.getParcelableExtra("Person");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View header = navigationView.getHeaderView(0);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Menu navMenu = navigationView.getMenu();
        try {
            mUser.reload();
            if (mUser.isEmailVerified()) {
                navMenu.getItem(4).getSubMenu().getItem(0).setVisible(false);
                String gravatarUrl = Gravatar.init().with(mUser.getEmail()).force404().build();
                reference.child("Users").child(person.getId())
                        .child("gravatarUrl").setValue(gravatarUrl);
            }
        }
        catch (Exception e){}
        TextView nameTV = (TextView)header.findViewById(R.id.nameTV);
        TextView emailTV = (TextView)header.findViewById(R.id.emailTV);

        nameTV.setText(person.getName());
        emailTV.setText(person.getEmail());
        reference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String picUrl = dataSnapshot.child(person.getId()).child("gravatarUrl").getValue(String.class);
                if(picUrl!=null) {
                    Picasso.with(HealthForum.this)
                            .load(picUrl)
                            .into((ImageView)header.findViewById(R.id.picTV));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        drawer.setBackgroundColor(getResources().getColor(R.color.accentcolor));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        reference.child("Query").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<Query> queries = new ArrayList<Query>();
                if(dataSnapshot!=null){
                    for(DataSnapshot q:dataSnapshot.getChildren()){
                        queries.add(q.getValue(Query.class));
                    }
                    listView.setAdapter(new PostQueryAdapter(queries,HealthForum.this,person));
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Query query = queries.get(position);
                        Intent i = new Intent(HealthForum.this,AnswerResponse.class);
                        i.putExtra("Query",query);
                        i.putExtra("Person",person);
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void openPostQueryActivity(View view){
        //Toast.makeText(HealthForum.this, "This functionality is coming soon",Toast.LENGTH_LONG).show();
        mUser.reload();
        if(mUser.isEmailVerified()) {
            Intent intent = new Intent(this, PostQueryExperience.class);
            intent.putExtra("Person", person);
            startActivity(intent);
        }
        else{
            Toast.makeText(HealthForum.this,"Verification of email is needed",Toast.LENGTH_LONG).show();
        }
    }

    public void openAnswerQueryActivity(View view){
        //Toast.makeText(HealthForum.this, "This functionality is coming soon",Toast.LENGTH_LONG).show();
        mUser.reload();
        if(mUser.isEmailVerified()){
            Intent intent = new Intent(this,AnswerQuery.class);
            intent.putExtra("Person",person);
            startActivity(intent);
        }
        else{
            Toast.makeText(HealthForum.this,"Verification of email is needed",Toast.LENGTH_LONG).show();
        }

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
            Toast.makeText(HealthForum.this, "This functionality is coming soon",Toast.LENGTH_LONG).show();
        } else if (id == R.id.medical_tests) {

            sdm.displayMgr.displayTestSearchPage(this);

        } else if (id == R.id.medicines) {

            sdm.displayMgr.displayMedicineSerachPage(this);

        } else if (id == R.id.my_questions) {
            mUser.reload();
            if(mUser.isEmailVerified()) {
                Intent intent = new Intent(this, MyQuestions.class);
                intent.putExtra("Person", person);
                startActivity(intent);
            }
            else{
                Toast.makeText(HealthForum.this,"Verification of email is needed",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.my_experiences) {
            mUser.reload();
            if(mUser.isEmailVerified()) {
                Intent intent = new Intent(this,MyAnswers.class);
                intent.putExtra("Person", person);
                startActivity(intent);
            }
            else{
                Toast.makeText(HealthForum.this,"Verification of email is needed",Toast.LENGTH_LONG).show();
            }
        }
        else if(id == R.id.verify){
            mUser.reload();
            mUser.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(HealthForum.this,"Please Verify by going " +
                                                "through your email",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
