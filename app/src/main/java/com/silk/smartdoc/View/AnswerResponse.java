package com.silk.smartdoc.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Statement;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.Date;

import static com.silk.smartdoc.R.id.listView;
import static com.silk.smartdoc.R.id.my_questions;

public class AnswerResponse extends AppCompatActivity {
    boolean flag=false;
    boolean upFlag=false,downFlag=false;
    ArrayList<String> dbUserId;
    boolean downsFlag=false;
    ArrayList<String> downVotesUserId,upVotesUserId;
    TextView upVoteTextView,downVoteTextView;
    int upCount=0;
    int downCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_response);
        TextView userNameTextView = (TextView) findViewById(R.id.usernameTextView);
        TextView queryTextView = (TextView) findViewById(R.id.queryTextView);
        ImageView upVotesImage = (ImageView) findViewById(R.id.thumbsUpImageView);
        ImageView downVotesImage = (ImageView) findViewById(R.id.thumbsDownImageView);
        upVoteTextView = (TextView) findViewById(R.id.upVoteTextView2);
        downVoteTextView = (TextView) findViewById(R.id.downVoteTextView2);
        //
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final Query query = extras.getParcelable("Query");
        final Person person = extras.getParcelable("Person");

        //Intent intent = getIntent();
        //Query query = intent.getParcelableExtra("Query");
        String userName_questionPosted = query.getQuestion().getUser_id();
        String query_Posted = query.getQuestion().getStatement();
        userNameTextView.setText(userName_questionPosted);
        queryTextView.setText(query_Posted);

        final DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Statement");
        String q_id = query.getId();
        ArrayList<Statement> ans=query.getAnswer();
        final Statement ques=query.getQuestion();
        final String question=ques.getStatement();
        final String ques_id=ques.getId();

        ArrayList<Statement> statements = query.getAnswer();
        ListView listView = (ListView) findViewById(R.id.searchResultListView);
        if (statements != null)
            listView.setAdapter(new QueryResponseAdapter(statements, AnswerResponse.this));


        db.child(ques_id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("downVotes")) {
                    for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                        downCount+=1;
                    }
                }
                if(dataSnapshot.hasChild("upVotes")) {
                    for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                        upCount+=1;
                    }
                }
                upVoteTextView.setText(upCount+"");
                downVoteTextView.setText(downCount+"");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //If user Posts his/her answer
        Button postAnswerButton = (Button) findViewById(R.id.postAnswerButton);
        postAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText answerText = (EditText) findViewById(R.id.answerText);
                String answer = answerText.getText().toString();
                if(answerText.toString().trim().equals("")){
                    Toast.makeText(AnswerResponse.this,"Enter your answer",Toast.LENGTH_LONG).show();
                }
                else{
                    //Statement
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Statement");
                    String id = db.push().getKey();
                    Statement statement = new Statement(person.getEmail(),id,answer, new Date(),new ArrayList<String>(),new ArrayList<String>());
                    db.child(id).setValue(statement);

                    /*/Person
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                    String email = person.getEmail() ;
                    String user_id = person.getId();
                    ArrayList<String> myExperience = person.getMyExperience();
                    if(myExperience==null)
                        myExperience = new ArrayList<String>();
                    ArrayList<String> myQuestion = person.getMyQuestions();
                    if(myQuestion==null)
                        myQuestion = new ArrayList<String>();

                    myExperience.add(query.getId());
                    Person p = new Person(person.getName(), email, person.getPassword(), person.getDateOfBirth()
                            , person.getSex(), email, person.getIsDoctor(), person.getRegistrationNumber(),
                            myQuestion,myExperience,
                            user_id);
                    //person.setMyExperience(myExperience);
                    ref.child(user_id).setValue(p);*/



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
                                    myExperience.add(query.getId());

                                    myQuestion =(ArrayList<String>) child.child("myQuestions").getValue();
                                    if(myQuestion==null)
                                        myQuestion = new ArrayList<String>();
                                    break;

                                }
                            }
                            Person p = new Person(person.getName(), email, person.getPassword(), person.getDateOfBirth()
                                    , person.getSex(), email, person.getIsDoctor(), person.getRegistrationNumber(),
                                    myQuestion,myExperience,
                                    user_id);
                            //person.setMyExperience(myExperience);
                            reference.child(user_id).setValue(p);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //Query
                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Query");
                    String q_id = query.getId();
                    ArrayList<Statement> al = query.getAnswer();
                    if(al==null)
                        al = new ArrayList<Statement>();
                    al.add(statement);
                    Query query1 = new Query(query.getQuestion(),al ,query.getTags(), q_id );
                    db1.child(q_id).setValue(query1);
                    //db1.child(q_id).child("answer").setValue(statement);
                    answerText.setText("");
                    Toast.makeText(AnswerResponse.this,"Answer posted",Toast.LENGTH_LONG).show();
                }
            }
        });




        upVotesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downVotesUserId=new ArrayList<String>();
                upVotesUserId = new ArrayList<String>();
                db.child(ques_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("downVotes")) {
                            for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                                downVotesUserId.add(child.getValue(String.class));
                            }
                            for(int ii=0;ii<downVotesUserId.size();ii++)
                            {
                                if(downVotesUserId.get(ii).equalsIgnoreCase(person.getEmail()))
                                {
                                    downVotesUserId.remove(person.getEmail());
                                    break;
                                }
                            }

                        }
                        if(dataSnapshot.hasChild("upVotes")) {
                            for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                                upVotesUserId.add(child.getValue(String.class));
                            }
                        }
                            boolean wasAlreadyPressed = false;
                            for(int ii=0;ii<upVotesUserId.size();ii++)
                            {
                                if(upVotesUserId.get(ii).equalsIgnoreCase(person.getEmail()))
                                {
                                    upVotesUserId.remove(person.getEmail());
                                    wasAlreadyPressed=true;
                                    break;
                                }
                            }
                            if(!wasAlreadyPressed){
                                upVotesUserId.add(person.getEmail());
                            }
                            upVoteTextView.setText(upVotesUserId.size()+"");
                            downVoteTextView.setText(downVotesUserId.size()+"");
                            Statement statement = new Statement(person.getEmail(),ques_id,question, ques.getTimestamp(),upVotesUserId
                                    ,downVotesUserId);

                            db.child(ques_id).setValue(statement);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });


        downVotesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downVotesUserId=new ArrayList<String>();
                upVotesUserId = new ArrayList<String>();
                db.child(ques_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("upVotes")) {
                            for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                                upVotesUserId.add(child.getValue(String.class));
                            }
                            for(int ii=0;ii<upVotesUserId.size();ii++)
                            {
                                if(upVotesUserId.get(ii).equalsIgnoreCase(person.getEmail()))
                                {
                                    upVotesUserId.remove(person.getEmail());
                                    break;
                                }
                            }

                        }
                        if(dataSnapshot.hasChild("downVotes")) {
                            for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                                downVotesUserId.add(child.getValue(String.class));
                            }
                        }
                        boolean wasAlreadyPressed = false;
                        for(int ii=0;ii<downVotesUserId.size();ii++)
                        {
                            if(downVotesUserId.get(ii).equalsIgnoreCase(person.getEmail()))
                            {
                                downVotesUserId.remove(person.getEmail());
                                wasAlreadyPressed=true;
                                break;
                            }
                        }
                        if(!wasAlreadyPressed){
                            downVotesUserId.add(person.getEmail());
                        }
                        Statement statement = new Statement(person.getEmail(),ques_id,question, ques.getTimestamp(),upVotesUserId
                                ,downVotesUserId);
                        upVoteTextView.setText(upVotesUserId.size()+"");
                        downVoteTextView.setText(downVotesUserId.size()+"");
                        db.child(ques_id).setValue(statement);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
