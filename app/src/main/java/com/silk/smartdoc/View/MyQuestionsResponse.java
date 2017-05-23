package com.silk.smartdoc.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Statement;
import com.silk.smartdoc.R;

import java.util.ArrayList;

public class MyQuestionsResponse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_questions_response);

        TextView userNameTextView = (TextView) findViewById(R.id.usernameTextView);
        TextView queryTextView = (TextView) findViewById(R.id.queryTextView);
        //
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final Query query = extras.getParcelable("Query");
        final Person person = extras.getParcelable("Person");
        //
        //Intent intent = getIntent();
        //Query query = intent.getParcelableExtra("Query");
        String userName_questionPosted = query.getQuestion().getUser_id();
        String query_Posted = query.getQuestion().getStatement();
        userNameTextView.setText(userName_questionPosted);
        queryTextView.setText(query_Posted);

        ArrayList<Statement> statements = query.getAnswer();
        ListView listView = (ListView) findViewById(R.id.searchResultListView);
        if (statements != null)
            listView.setAdapter(new QueryResponseAdapter(statements, MyQuestionsResponse.this,person));
    }
}
