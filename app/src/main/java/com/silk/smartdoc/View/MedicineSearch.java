package com.silk.smartdoc.View;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.silk.smartdoc.R;

import java.util.ArrayList;

public class MedicineSearch extends AppCompatActivity {
    ListView searchListView;
    ArrayList<String> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_search);

        searchListView = (ListView)findViewById(R.id.searchListView);
        al = new ArrayList<String>();
        al.add("Peracetamol");
        al.add("Itraconazole");
        al.add("Terbanifine");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,al);
        searchListView.setAdapter(arrayAdapter);


        searchListView.setOnItemClickListener (new AdapterView.OnItemClickListener(){
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                   String value = searchListView.getItemAtPosition(i).toString();
                   Intent intent = new Intent(MedicineSearch.this,MedicineResult.class);
                   intent.putExtra("searchValue",value);
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
                for(String temp: al){
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
