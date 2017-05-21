package com.silk.smartdoc.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.KeyPairBoolData;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.List;

public class AnswerQuery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_query);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer_query, menu);
        return true;
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
