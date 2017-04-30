package com.silk.smartdoc.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Test;
import com.silk.smartdoc.R;

import java.util.ArrayList;

public class TestSearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search_results);
        ArrayList<Test> testResultArrayList;
        ArrayList<DiagnosticCenter> diagnosticCentersResultArrayList;

        ListView diagnosticCentreListView = (ListView)findViewById(R.id.searchResultListView);
        Toolbar toolbar=(Toolbar)findViewById(R.id.test_search_result_toolbar);

        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        Bundle data=getIntent().getExtras();

        final String searchValue = data.getString("searchKey");
        toolbar.setTitle(searchValue);
        setSupportActionBar(toolbar);

        DatabaseReference databaseReferenceTest = FirebaseDatabase.getInstance().getReference().child("Tests");
        testResultArrayList = new ArrayList<Test>();

        databaseReferenceTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }
        );



    }
}
