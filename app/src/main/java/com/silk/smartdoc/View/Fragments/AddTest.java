package com.silk.smartdoc.View.Fragments;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silk.smartdoc.Model.Test;
import com.silk.smartdoc.R;

public class AddTest extends Fragment {
    EditText testNameET;
    EditText descriptionET;
    Button commitButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_test, container, false);
        testNameET = (EditText)view.findViewById(R.id.testNameET);
        descriptionET = (EditText)view.findViewById(R.id.testDescriptionET);
        commitButton = (Button)view.findViewById(R.id.commitButton6);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String testName = testNameET.getText().toString();
                String description = descriptionET.getText().toString();
                if(testName.equals("")||description.equals("")){
                    Toast.makeText(getActivity(),"Enter all the details",Toast.LENGTH_LONG).show();
                }
                else{
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Tests");
                    String id = db.push().getKey();
                    Test test = new Test(testName,description,id);
                    db.child(id).setValue(test);
                    testNameET.setText("");
                    descriptionET.setText("");
                    Toast.makeText(getActivity(),"Test Added",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
