package com.silk.smartdoc.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.silk.smartdoc.Model.Test;
import com.silk.smartdoc.R;

import java.util.ArrayList;

public class TestSearchResultsActivity extends AppCompatActivity {
    ArrayList<DiagnosticCenter> diagnosticCentersResultArrayList;
    ArrayList<CentreAndPrice> centerAndPriceArrayList;
    SmartDocManager sdm;
    ArrayList<CentreAndPrice> centreAndPrices;
    String[] centerCertification = new String[1];
    String[] centerLocation = new String[1];
    String[] centerName = new String[1];
    String[] centerUrl = new String[1];
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


        DatabaseReference databaseReferenceTest = FirebaseDatabase.getInstance().getReference().child("Tests");
        testResultArrayList = new ArrayList<Test>();
        centreAndPrices=new ArrayList<CentreAndPrice>();
        sdm=new SmartDocManager();
        databaseReferenceTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String testDescription;

                testDescription=sdm.searchMgr.isTestExit(dataSnapshot,searchValue);
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
                        diagnosticCentersResultArrayList=sdm.searchMgr.getDiagnosticCentersResultArrayList(dataSnapshot,centreAndPrices);
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
