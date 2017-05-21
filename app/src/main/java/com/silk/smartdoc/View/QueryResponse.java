package com.silk.smartdoc.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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
        ArrayList<Statement> statements = query.getAnswer();
        ListView listView =(ListView) findViewById(R.id.searchResultListView);
        listView.setAdapter(new QueryResponseAdapter(statements,QueryResponse.this));
    }
}
