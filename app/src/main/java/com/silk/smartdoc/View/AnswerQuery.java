package com.silk.smartdoc.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.KeyPairBoolData;
import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.List;

public class AnswerQuery extends AppCompatActivity {
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_query);
        Toolbar toolbar = (Toolbar) findViewById(R.id.answer_response_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Answer Query");
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
        final List<String> list = new ArrayList<String>();
        DatabaseReference databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Tags");

        databaseReferenceMed.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {

                    String medName = child.getValue(String.class);
                    if (medName != null)
                        list.add(medName);
                }

                final List<KeyPairBoolData> listArray1 = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    KeyPairBoolData h = new KeyPairBoolData();
                    h.setId(i + 1);
                    h.setName(list.get(i));
                    h.setSelected(false);
                    listArray1.add(h);
                }
                MultiSpinnerSearch searchMultiSpinnerLimit = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinnerLimit);


                /***
                 * -1 is no by default selection
                 * 0 to length will select corresponding values
                 */
                searchMultiSpinnerLimit.setItems(listArray1, -1, new SpinnerListener() {
                    ArrayList<String> tags = new ArrayList<String>();

                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {


                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected()) {
                                tags.add(items.get(i).getName());
                            }
                        }
                        //Fetch from DataBase and filter by tags
                        final ArrayList<Query> queries = new ArrayList<Query>();
                        final ListView listView = (ListView) findViewById(R.id.listView);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Query query = queries.get(position);
                                Intent loginIntent = getIntent();
                                person = loginIntent.getParcelableExtra("Person");
                                Intent i = new Intent(AnswerQuery.this,AnswerResponse.class);
                                Bundle extras = new Bundle();
                                extras.putParcelable("Person",person);
                                extras.putParcelable("Query",query);
                                i.putExtras(extras);
                                //i.putExtra("Query",query);
                                startActivity(i);
                            }
                        });
                        DatabaseReference databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Query");
                        databaseReferenceMed.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> children= dataSnapshot.getChildren();

                                for (DataSnapshot child: children) {
                                    ArrayList<String> questionTags = (ArrayList<String>)child.child("tags").getValue();

                                    for(String t : tags)
                                    {
                                        if(questionTags.contains(t))
                                        {
                                            queries.add(child.getValue(Query.class));
                                            break;
                                        }
                                    }
                                }
                                //searchListView.setAdapter(new ArrayAdapter<String>(MedicineSearch.this, android.R.layout.simple_list_item_1, medArrayList));
                                listView.setAdapter(new PostQueryAdapter(queries,AnswerQuery.this,person));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });


                searchMultiSpinnerLimit.setLimit(4, new MultiSpinnerSearch.LimitExceedListener() {
                    @Override
                    public void onLimitListener(KeyPairBoolData data) {
                        Toast.makeText(getApplicationContext(),
                                "Limit exceed ", Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
