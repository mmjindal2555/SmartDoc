package com.silk.smartdoc.View;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.Map;

public class MedicineSearch extends AppCompatActivity {
    ListView searchListView;
    ArrayList<String> medArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_search);

        searchListView = (ListView) findViewById(R.id.searchListView);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("medicineList");
        medArrayList = new ArrayList<String>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    String medName = child.getValue(String.class);
                    medArrayList.add(medName);
                }
                searchListView.setAdapter(new ArrayAdapter<String>(MedicineSearch.this, android.R.layout.simple_list_item_1, medArrayList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("medicineList");
        final FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
                MedicineSearch.this,
                String.class,
                android.R.layout.simple_list_item_1,
                databaseReference
        ) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView text = (TextView)v.findViewById(android.R.id.text1);
                text.setText(model);
            }

        };
        */

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String value = searchListView.getItemAtPosition(i).toString();
                Intent intent = new Intent(MedicineSearch.this, MedicineResult.class);
                intent.putExtra("searchValue", value);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_medicine_search,menu);

        MenuItem searchItem = menu.findItem(R.id.medicine_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> tempList = new ArrayList<String>();
                for(String temp: medArrayList){
                    if(temp.toLowerCase().contains(newText.toLowerCase())){
                        tempList.add(temp);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MedicineSearch.this,android.R.layout.simple_expandable_list_item_1,tempList);
                searchListView.setAdapter(adapter);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
}
