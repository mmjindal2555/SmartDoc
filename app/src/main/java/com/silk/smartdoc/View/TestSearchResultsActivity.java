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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search_results);
        ArrayList<Test> testResultArrayList;

        final ListView diagnosticCentreListView = (ListView)findViewById(R.id.searchResultListView);
        final Toolbar toolbar=(Toolbar)findViewById(R.id.test_search_result_toolbar);
        final TextView descriptionCard = (TextView)findViewById(R.id.testDescription);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        Bundle data=getIntent().getExtras();

        final String searchValue = data.getString("searchKey");


        DatabaseReference databaseReferenceTest = FirebaseDatabase.getInstance().getReference().child("Tests");
        testResultArrayList = new ArrayList<Test>();
        databaseReferenceTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<Double> centerPrice=new ArrayList<Double>();
                final ArrayList<String> centerId=new ArrayList<String>();
                String testId,testName;
                String testDescription;
                final String[] centerCertification = new String[1];
                final String[] centerLocation = new String[1];
                final String[] centerName = new String[1];
                final String[] centerUrl = new String[1];
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    testName = postSnapshot.child("name").getValue(String.class);
                    if(testName.equalsIgnoreCase(searchValue))
                    {
                        testId=postSnapshot.child("id").getValue(String.class);
                        testDescription=postSnapshot.child("description").getValue(String.class);
                        descriptionCard.setText(testDescription);
                        for(DataSnapshot childPostSnapshot : postSnapshot.child("centres").getChildren()){
                            centerPrice.add(childPostSnapshot.child("price").getValue(Double.class));
                            centerId.add(childPostSnapshot.child("centreId").getValue(String.class));
                        }
                        toolbar.setTitle(testName);
                        setSupportActionBar(toolbar);
                        break;
                    }

                }
                diagnosticCentersResultArrayList = new ArrayList<DiagnosticCenter>();
                centerAndPriceArrayList = new ArrayList<CentreAndPrice>();
                DatabaseReference databaseReferenceCentre = FirebaseDatabase.getInstance().getReference().child("Centres");
                databaseReferenceCentre.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<String> it1 = centerId.iterator();
                        Iterator<Double> it2 = centerPrice.iterator();
                        while(it1.hasNext() && it2.hasNext())
                        {
                            String var=it1.next();
                            Double price=it2.next();
                            centerCertification[0] =dataSnapshot.child(var).child("certification").getValue(String.class);

                            centerLocation[0] =dataSnapshot.child(var).child("location").getValue(String.class);
                            centerName[0] =dataSnapshot.child(var).child("name").getValue(String.class);
                            centerUrl[0] =dataSnapshot.child(var).child("url").getValue(String.class);
                            DiagnosticCenter diagnosticCenter=new DiagnosticCenter(centerName[0],centerLocation[0],centerCertification[0],centerUrl[0],var);
                            CentreAndPrice centreAndPrice = new CentreAndPrice(var,price);
                            diagnosticCentersResultArrayList.add(diagnosticCenter);
                            centerAndPriceArrayList.add(centreAndPrice);
                        }
                        diagnosticCentreListView.setAdapter(new TestsResultsAdapter(diagnosticCentersResultArrayList,centerAndPriceArrayList,TestSearchResultsActivity.this));

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
