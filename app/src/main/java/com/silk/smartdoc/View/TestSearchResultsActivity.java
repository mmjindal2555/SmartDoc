package com.silk.smartdoc.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.Model.CentreAndPrice;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Test;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class TestSearchResultsActivity extends AppCompatActivity {
    ArrayList<DiagnosticCenter> diagnosticCentersResultArrayList;
    ArrayList<CentreAndPrice> centerAndPriceArrayList;
    SmartDocManager sdm;
    ArrayList<CentreAndPrice> centreAndPrices;
    ListView diagnosticCentreListView;
    Toolbar toolbar;
    TextView descriptionCard;
    Bundle data;
    String searchValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search_results);
        ArrayList<Test> testResultArrayList;
        diagnosticCentreListView = (ListView)findViewById(R.id.searchResultListView);
        toolbar=(Toolbar)findViewById(R.id.test_search_result_toolbar);
        descriptionCard = (TextView)findViewById(R.id.testDescription);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
        data=getIntent().getExtras();
        searchValue = data.getString("searchKey");

        //Make a reference of child "Tests" from database
        DatabaseReference databaseReferenceTest = FirebaseDatabase.getInstance().getReference().child("Tests");
        centreAndPrices=new ArrayList<CentreAndPrice>();
        sdm=new SmartDocManager();
        databaseReferenceTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String testDescription;
                //check whether the given test exits or not
                testDescription=sdm.searchMgr.isTestExit(dataSnapshot,searchValue);
                //if exits then collect all the centerId and their respective price in the ArrayList<CentreAndPrice>
                centreAndPrices=sdm.searchMgr.getCentreIdAndPrice(dataSnapshot,searchValue);
                if(testDescription!=null)
                {
                    descriptionCard.setText(testDescription);
                    toolbar.setTitle(searchValue);
                    setSupportActionBar(toolbar);
                }
                diagnosticCentersResultArrayList = new ArrayList<DiagnosticCenter>();
                centerAndPriceArrayList = new ArrayList<CentreAndPrice>();
                DatabaseReference databaseReferenceCentre = FirebaseDatabase.getInstance().getReference().child("Centres");
                databaseReferenceCentre.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Collect the details of the diagnostic centre in the diagnosticCentersResultArrayList
                        diagnosticCentersResultArrayList=sdm.searchMgr.isDiagnosticCentersResultArrayList(dataSnapshot,centreAndPrices);
                        //set the adapter
                        diagnosticCentreListView.setAdapter(new TestsResultsAdapter(diagnosticCentersResultArrayList,centreAndPrices,TestSearchResultsActivity.this));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


}
}
