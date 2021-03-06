package com.silk.smartdoc.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.R;

import java.util.ArrayList;

public class MyAnswers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_answers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_experiences_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("SmartDoc");
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        Intent intent = getIntent();
        final Person person = intent.getParcelableExtra("Person");


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        //reference = reference;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                ArrayList<String> myExperiences = new ArrayList<String>();;
                String email = person.getEmail() ;
                String user_id = person.getId();
                for (DataSnapshot child: children) {
                    String id=child.child("id").getValue(String.class);
                    user_id = person.getId();
                    if(id.equals(user_id))
                    {
                        myExperiences =(ArrayList<String>) child.child("myExperience").getValue();
                        if(myExperiences==null)
                            myExperiences = new ArrayList<String>();
                        break;
                    }
                }
                final ListView listView = (ListView) findViewById(R.id.listView);
                TextView emptyText = (TextView) findViewById(R.id.emptyTV);
                emptyText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyAnswers.this,AnswerQuery.class);
                        intent.putExtra("Person",person);
                        startActivity(intent);
                    }
                });
                listView.setEmptyView(emptyText);
                //
                final ArrayList<String> al = myExperiences;
                //
                DatabaseReference databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Query");
                databaseReferenceMed.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final ArrayList<Query> query_al = new ArrayList<Query>();
                        Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                        for (DataSnapshot child : children)  {
                            for(String st:al){
                                String query_id = child.child("id").getValue(String.class);
                                if(query_id.equals(st))
                                {
                                    query_al.add(child.getValue(Query.class));
                                    //break;
                                }
                            }
                        }
                        //searchListView.setAdapter(new ArrayAdapter<String>(MedicineSearch.this, android.R.layout.simple_list_item_1, medArrayList));
                        listView.setAdapter(new MyAnswersAdapter(query_al,person.getId(),MyAnswers.this));
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
