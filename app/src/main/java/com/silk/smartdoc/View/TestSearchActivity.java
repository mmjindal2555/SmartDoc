package com.silk.smartdoc.View;

import android.os.storage.StorageManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.R;

import java.util.ArrayList;

public class TestSearchActivity extends AppCompatActivity {
    ListView testSearchListView;
    ArrayList<String> testArrayList;
    SmartDocManager sdm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search);
        testSearchListView = (ListView) findViewById(R.id.searchTestListView);
        testArrayList = new ArrayList<String>();

        //connect to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        //make a reference of tests
        databaseReference = databaseReference.child("Tests");
        sdm= new SmartDocManager();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //collect all the tests name in testArrayList
                testArrayList=sdm.searchMgr.getAllTestName(dataSnapshot);
                //set all the tests name in the ListView
                testSearchListView.setAdapter(new ArrayAdapter<String>(TestSearchActivity.this,
                        android.R.layout.simple_list_item_1, testArrayList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Set on onClickListener
        testSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String value = testSearchListView.getItemAtPosition(i).toString();

                SmartDocManager sdm = (SmartDocManager)getApplicationContext();
                sdm.displayMgr.displayTestResult(TestSearchActivity.this,value);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_test_search, menu);

        MenuItem searchItem = menu.findItem(R.id.test_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> tempList = new ArrayList<String>();
                for(String temp: testArrayList){
                    if(temp.toLowerCase().contains(newText.toLowerCase())){
                        tempList.add(temp);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestSearchActivity.this,android.R.layout.simple_expandable_list_item_1,tempList);
                testSearchListView.setAdapter(adapter);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
