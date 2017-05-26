package com.silk.smartdoc.View;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import static com.silk.smartdoc.R.id.listView;
import static com.silk.smartdoc.R.id.my_questions;
import static com.silk.smartdoc.R.id.up;

public class AnswerResponse extends AppCompatActivity {
    ArrayList<String> downVotesUserId,upVotesUserId;
    TextView upVoteTextView,downVoteTextView;
    int upCount=0;
    int downCount=0;
    TextView userNameTextView;
    ImageView upVotesImage;
    ImageView downVotesImage;
    TextView numOfAnswers;
    ImageView profilePic;
    ImageView msgImgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_response);
         userNameTextView  = (TextView) findViewById(R.id.userTextView);
        TextView queryTextView = (TextView) findViewById(R.id.queryTextView);
        upVotesImage = (ImageView) findViewById(R.id.thumbsUpImageView);
        downVotesImage = (ImageView) findViewById(R.id.thumbsDownImageView);
        upVoteTextView = (TextView) findViewById(R.id.upVoteTextView2);
        downVoteTextView = (TextView) findViewById(R.id.downVoteTextView2);
        numOfAnswers = (TextView)findViewById(R.id.numberOfAnswers);
        profilePic = (ImageView)findViewById(R.id.profilePicImageView);
        msgImgView = (ImageView)findViewById(R.id.imageView3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.answer_response_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("SmartDoc");
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
        //
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final Query query = extras.getParcelable("Query");
        final Person person = extras.getParcelable("Person");

        //Intent intent = getIntent();
        //Query query = intent.getParcelableExtra("Query");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName_questionPosted = dataSnapshot.child(query.getQuestion()
                        .getUser_id()).child("name").getValue(String.class);
                userNameTextView.setText(userName_questionPosted);
                String picUrl = dataSnapshot.child(query.getQuestion().getUser_id()).
                        child("gravatarUrl").getValue(String.class);
                picUrl = picUrl.substring(0,picUrl.length()-3)+"retro";
                if(picUrl!=null) {
                    Picasso.with(AnswerResponse.this)
                            .load(picUrl)
                            .placeholder(R.drawable.ic_user)
                            .into(profilePic);
                }
                String msgPicUrl = dataSnapshot.child(person.getId()).child("gravatarUrl")
                        .getValue(String.class);
                msgPicUrl = msgPicUrl.substring(0,msgPicUrl.length()-3)+"retro";
                if(msgPicUrl!=null) {
                    Picasso.with(AnswerResponse.this)
                            .load(msgPicUrl)
                            .placeholder(R.drawable.ic_user)
                            .into(msgImgView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        String query_Posted = query.getQuestion().getStatement();

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
            listView.setAdapter(new QueryResponseAdapter(statements, AnswerResponse.this,person));


        db.child(ques_id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user = dataSnapshot.child("user_id").getValue(String.class);
                if (dataSnapshot.hasChild("downVotes")) {
                    downCount=0;
                    for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                        if(user.equals(child.getValue(String.class)))
                            upVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_up_blue);
                        downCount+=1;
                    }
                }
                if(dataSnapshot.hasChild("upVotes")) {
                    upCount=0;
                    for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                        if(user.equals(child.getValue(String.class)))
                            downVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_down_blue);
                        upCount+=1;
                    }
                }
                if(dataSnapshot.hasChild("answer")){
                    numOfAnswers.setText(dataSnapshot.child("answer").getChildrenCount()+"");
                }
                else{
                    numOfAnswers.setText("0");
                }
                upVoteTextView.setText(upCount+"");
                downVoteTextView.setText(downCount+"");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //If user Posts his/her answer
        ImageView postAnswerButton = (ImageView) findViewById(R.id.postAnswerButton);
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
                    Statement statement = new Statement(person.getId(),id,answer, new Date(),new ArrayList<String>(),new ArrayList<String>());
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
                downVotesUserId = new ArrayList<String>();
                upVotesUserId = new ArrayList<String>();
                db.child(ques_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("downVotes").getChildrenCount()>0 ) {
                            for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                                downVotesUserId.add(child.getValue(String.class));
                            }
                            for(int ii=0;ii<downVotesUserId.size();ii++)
                            {
                                if(downVotesUserId.get(ii).equalsIgnoreCase(person.getId()))
                                {

                                    downVotesUserId.remove(person.getId());
                                    downVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_down);
                                    break;
                                }
                            }

                        }
                        if(dataSnapshot.child("upVotes").getChildrenCount()>0) {
                            for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                                upVotesUserId.add(child.getValue(String.class));
                            }
                        }
                            boolean wasAlreadyPressed = false;
                            for(int ii=0;ii<upVotesUserId.size();ii++)
                            {
                                if(person.getId()!=null && upVotesUserId.get(ii).equalsIgnoreCase(person.getId()))
                                {
                                    upVotesUserId.remove(person.getId());
                                    upVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_up);
                                    wasAlreadyPressed=true;
                                    break;
                                }
                            }
                            if(!wasAlreadyPressed){
                                upVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_up_blue);
                                upVotesUserId.add(person.getId());
                            }
                            upVoteTextView.setText(upVotesUserId.size()+"");
                            downVoteTextView.setText(downVotesUserId.size()+"");
                            Statement statement = new Statement(ques.getUser_id(),ques_id,question, ques.getTimestamp(),upVotesUserId
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
                                if(person.getId()!=null && upVotesUserId.get(ii).equalsIgnoreCase(person.getId()))
                                {
                                    upVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_up);
                                    upVotesUserId.remove(person.getId());
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
                            if(person.getId()!=null && downVotesUserId.get(ii).equalsIgnoreCase(person.getId()))
                            {
                                downVotesUserId.remove(person.getId());
                                downVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_down);
                                wasAlreadyPressed=true;
                                break;
                            }
                        }
                        if(!wasAlreadyPressed){
                            downVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_down_blue);
                            downVotesUserId.add(person.getId());
                        }
                        Statement statement = new Statement(ques.getUser_id(),ques_id,question, ques.getTimestamp(),upVotesUserId
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
                ImageView upImg=(ImageView) findViewById(R.id.thumbsDownImageView);
            }
        });
    }
}
