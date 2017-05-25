package com.silk.smartdoc.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.KeyPairBoolData;
import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Statement;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class PostQueryExperience extends AppCompatActivity {
    Person person;
    ListView listView;
    TextView emptyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_query_experience);
        Toolbar toolbar = (Toolbar) findViewById(R.id.post_query_experience_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Post Query");
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));

        final EditText otherTagET = (EditText)findViewById(R.id.otherTagEditText);
        final Switch otherTagSwitch = (Switch)findViewById(R.id.otherTagSwitch);
        listView = (ListView) findViewById(R.id.listView);
        emptyText = (TextView) findViewById(R.id.emptyTV);
        listView.setEmptyView(emptyText);




        /**
         * Getting array of String to Bind in Spinner
         */
        //final List<String> list = Arrays.asList(getResources().getStringArray(R.array.sports_array));

        final List<String> list = new ArrayList<String>();
        DatabaseReference databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Tags");

        databaseReferenceMed.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                for (DataSnapshot child: children) {

                    String medName = child.getValue(String.class);
                    if(medName!=null)
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
                final ImageView commitButton = (ImageView) findViewById(R.id.postButton);

                /***
                 * -1 is no by default selection
                 * 0 to length will select corresponding values
                 */
                searchMultiSpinnerLimit.setItems(listArray1, -1, new SpinnerListener() {
                    ArrayList<String> tags = new ArrayList<String>();

                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {
                        otherTagSwitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(otherTagSwitch.isChecked()==true){
                                    otherTagET.setEnabled(true);

                                }
                                else {
                                    otherTagET.setEnabled(false);
                                    otherTagET.setText("");
                                }
                            }
                        });


                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected()) {
                                tags.add(items.get(i).getName());
                            }
                        }





                        //Fetch from DataBase and filter by tags
                        final ArrayList<Query> queries = new ArrayList<Query>();

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Query query = queries.get(position);
                                Intent i = new Intent(PostQueryExperience.this,AnswerResponse.class);
                                i.putExtra("Query",query);
                                i.putExtra("Person",person);
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
                                listView.setAdapter(new PostQueryAdapter(queries,PostQueryExperience.this,person));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        commitButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(!otherTagET.getText().toString().trim().equals("")) {
                                    String otherTags=otherTagET.getText().toString();
                                    StringTokenizer st = new StringTokenizer(otherTags,",");
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Tags");
                                    while(st.hasMoreTokens()) {
                                        String tagsOthers=st.nextToken().trim();
                                        tags.add(tagsOthers);
                                        String id = db.push().getKey();
                                        db.child(id).setValue(tagsOthers);
                                    }
                                }

                                EditText queryText = (EditText) findViewById(R.id.answerText);
                                String query = queryText.getText().toString();
                                if(queryText.equals("")||tags.size()==0){
                                    Toast.makeText(PostQueryExperience.this,"Enter all the details",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    //Statement
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Statement");
                                    String id = db.push().getKey();
                                    Intent loginIntent = getIntent();

                                    final Person person = loginIntent.getParcelableExtra("Person");
                                    Statement statement = new Statement(person.getId(),id,query, new Date(),null,null);


                                    db.child(id).setValue(statement);



                                    //Query
                                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Query");
                                    String q_id = db1.push().getKey();
                                    final Query query1 = new Query(statement,new ArrayList<Statement>() ,tags, q_id );
                                    db1.child(q_id).setValue(query1);
                                    queryText.setText("");
                                    otherTagET.setText("");
                                    tags = new ArrayList<String>();
                                    Toast.makeText(PostQueryExperience.this,"Query posted",Toast.LENGTH_LONG).show();

                                    ///////////////////////////////
                                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                                    //reference = reference;

                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                                            ArrayList<String> myExperience = new ArrayList<String>();;
                                            ArrayList<String> myQuestion = new ArrayList<String>();;
                                            String email = person.getEmail() ;
                                            String user_id = person.getId();
                                            for (DataSnapshot child: children) {
                                                String id=child.child("id").getValue(String.class);
                                                user_id = person.getId();
                                                if(id.equals(user_id))
                                                {
                                                    myExperience = (ArrayList<String>) child.child("myExperience").getValue();
                                                    if(myExperience==null)
                                                        myExperience = new ArrayList<String>();
                                                    myQuestion =(ArrayList<String>) child.child("myQuestions").getValue();
                                                    if(myQuestion==null)
                                                        myQuestion = new ArrayList<String>();
                                                    myQuestion.add(query1.getId());
                                                    Person p = new Person(person.getName(), email, person.getPassword(), person.getDateOfBirth()
                                                            , person.getSex(), email, person.getIsDoctor(), person.getRegistrationNumber(),
                                                            myQuestion,myExperience,
                                                            user_id);
                                                    //person.setMyExperience(myExperience);
                                                    reference.child(user_id).setValue(p);
                                                    break;

                                                }
                                            }

                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    /*/Person
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                                    String email = person.getEmail() ;
                                    String user_id = person.getId();
                                    ArrayList<String> myQuestion = person.getMyQuestions();
                                    if(myQuestion==null)
                                        myQuestion = new ArrayList<String>();
                                    myQuestion.add(query1.getId());
                                    ArrayList<String> myExperience = person.getMyExperience();
                                    if(myExperience==null)
                                        myExperience = new ArrayList<String>();
                                    Person p = new Person(person.getName(), email, person.getPassword(), person.getDateOfBirth()
                                            , person.getSex(), email, person.getIsDoctor(), person.getRegistrationNumber(),
                                            myQuestion,myExperience,
                                            user_id);
                                    //person.setMyQuestions(myQuestion);
                                    ref.child(user_id).setValue(p);*/

                                }
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
