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

import static com.silk.smartdoc.R.id.listView;

public class QueryResponse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_response);

        Intent intent = getIntent();
        Query query = intent.getParcelableExtra("Query");
        final Person person = intent.getParcelableExtra("Person");
        TextView userNameTextView = (TextView) findViewById(R.id.usernameTextView);
        TextView queryTextView = (TextView) findViewById(R.id.queryTextView);

        String userName_questionPosted = query.getQuestion().getUser_id();
        String query_Posted = query.getQuestion().getStatement();
        userNameTextView.setText(userName_questionPosted);
        queryTextView.setText(query_Posted);

        ArrayList<Statement> statements = query.getAnswer();
        ListView listView =(ListView) findViewById(R.id.searchResultListView);
        if(statements!=null)
            listView.setAdapter(new QueryResponseAdapter(statements,QueryResponse.this,person));
    }
}
