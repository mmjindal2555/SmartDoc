package com.silk.smartdoc.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class AnswerResponse extends AppCompatActivity {
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_response);
        TextView userNameTextView = (TextView) findViewById(R.id.usernameTextView);
        TextView queryTextView = (TextView) findViewById(R.id.queryTextView);
        ImageView upVotesImage = (ImageView) findViewById(R.id.thumbsUpImageView);
        ImageView downVotesImage = (ImageView) findViewById(R.id.thumbsDownImageView);
        TextView upVoteTextView = (TextView) findViewById(R.id.upVoteTextView);
        TextView downVoteTextView = (TextView) findViewById(R.id.downVoteTextView);
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

        ArrayList<Statement> statements = query.getAnswer();
        ListView listView = (ListView) findViewById(R.id.searchResultListView);
        if (statements != null)
            listView.setAdapter(new QueryResponseAdapter(statements, AnswerResponse.this));

        //If user Posts his/her answer
        Button postAnswerButton = (Button) findViewById(R.id.postAnswerButton);
        postAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText answerText = (EditText) findViewById(R.id.answerText);
                String answer = answerText.getText().toString();
                if(answerText.toString().trim().equals("")||answerText==null){
                    Toast.makeText(AnswerResponse.this,"Enter your answer",Toast.LENGTH_LONG).show();
                }
                else{
                    //Statement
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Statement");
                    String id = db.push().getKey();
                    Statement statement = new Statement(person.getEmail(),id,answer, new Date(),new ArrayList<String>(),new ArrayList<String>());
                    db.child(id).setValue(statement);

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
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Statement");
                String q_id = query.getId();
                ArrayList<Statement> ans_id=query.getAnswer();
                Statement ques=query.getQuestion();
                String question=ques.getStatement();
                String ques_id=ques.getId();
                ArrayList<String> up = ques.getupVotes();
                if(up==null)
                {
                    up = new ArrayList<String>();
                    up.add(person.getEmail());
                    Statement statement = new Statement(person.getEmail(),ques_id,question, ques.getTimestamp(),up,ques.getdownVotes());
                    db.child(ques_id).setValue(statement);
                }
                else
                {
                    flag=false;

                    db.child(ques_id).child("upVotes").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                            for (DataSnapshot child: children) {
                                ArrayList<String> dbUserId = (ArrayList<String>) child.getValue();
                                for(int ii=0;ii<dbUserId.size()-1;ii++)
                                {
                                    if(dbUserId.get(ii).equalsIgnoreCase(person.getEmail()))
                                    {
                                        flag=true;
                                        break;
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });






                    if(flag)
                    {
                        up.remove(person.getEmail());
                        Statement statement = new Statement(person.getEmail(),ques_id,question, ques.getTimestamp(),up,ques.getdownVotes());
                        db.child(ques_id).setValue(statement);
                    }
                    else
                    {
                        up.add(person.getEmail());
                        Statement statement = new Statement(person.getEmail(),ques_id,question, ques.getTimestamp(),up,ques.getdownVotes());
                        db.child(ques_id).setValue(statement);
                    }
                }






            }
        });
    }
}
