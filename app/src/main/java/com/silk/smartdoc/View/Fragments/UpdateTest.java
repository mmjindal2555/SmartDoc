package com.silk.smartdoc.View.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.CentreAndPrice;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Test;
import com.silk.smartdoc.R;

import java.util.ArrayList;

public class UpdateTest extends Fragment {

    EditText centreSearchET;
    EditText testSearchET;
    EditText priceET;
    Spinner centreSpinner;
    Spinner testSpinner;
    Button searchCentre;
    Button searchTest;
    Button commitButton;
    String sequence;
    String testSequence;
    String selectedCentre;
    String selectedTest;
    String centreId;
    String testId;
    double price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_test, container, false);
        centreSearchET = (EditText)view.findViewById(R.id.centreSearchET2);
        testSearchET = (EditText)view.findViewById(R.id.testSearchET2);
        priceET = (EditText)view.findViewById(R.id.newTestPriceET);
        centreSpinner = (Spinner)view.findViewById(R.id.spinnerCentre1);
        testSpinner = (Spinner)view.findViewById(R.id.searchedTestsSpinner);
        commitButton = (Button)view.findViewById(R.id.commitButton7);
        searchCentre = (Button)view.findViewById(R.id.searchCentre2);
        searchTest = (Button)view.findViewById(R.id.searchTestButon);

        searchCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sequence = centreSearchET.getText().toString();
                if(!(sequence.equals(""))){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Centres");
                    ArrayList<DiagnosticCenter> centres = new ArrayList<DiagnosticCenter>();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                            ArrayList<String> cens = new ArrayList<>();
                            for (DataSnapshot child: children) {
                                DiagnosticCenter centre = child.getValue(DiagnosticCenter.class);
                                String name = centre.getName();
                                if(name!=null && name.contains(sequence))
                                    cens.add(centre.getName());
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_item,cens);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            centreSpinner.setAdapter(arrayAdapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        });
        searchTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testSequence = testSearchET.getText().toString();
                if(!(testSequence.equals(""))){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Tests");
                    ArrayList<Test> tests = new ArrayList<Test>();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                            ArrayList<String> ts = new ArrayList<>();
                            for (DataSnapshot child: children) {
                                Test t = child.getValue(Test.class);
                                String name = t.getName();
                                if(name!=null && name.contains(testSequence))
                                    ts.add(t.getName());
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item,ts);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            testSpinner.setAdapter(arrayAdapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
        });

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCentre = centreSpinner.getSelectedItem().toString();
                selectedTest = testSpinner.getSelectedItem().toString();
                price = Double.parseDouble(priceET.getText().toString());
                DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference();
                dbReference.child("Centres").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                        for (DataSnapshot child: children) {
                            DiagnosticCenter centre = child.getValue(DiagnosticCenter.class);
                            String name = centre.getName();
                            if(name!=null && name.equalsIgnoreCase(selectedCentre)){
                                centreId=centre.getId();
                                DatabaseReference dbr= FirebaseDatabase.getInstance().getReference();
                                dbr.child("Tests").addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                                        for (DataSnapshot child: children) {
                                            Test tst = child.getValue(Test.class);
                                            String name = tst.getName();
                                            if(name!=null && name.equals(selectedTest))
                                            {
                                                testId=tst.getId();

                                                CentreAndPrice centreAndPrice = new CentreAndPrice(centreId,price);
                                                ArrayList<CentreAndPrice> cndp = new ArrayList<>();
                                                if(tst.getCentres()!=null)
                                                    cndp = tst.getCentres();
                                                cndp.add(centreAndPrice);
                                                Test uTest = new Test(tst.getName(),tst.getDescription(),tst.getId(),cndp);
                                                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                                                db.child("Tests").child(testId).setValue(uTest);
                                                centreSearchET.setText("");
                                                testSearchET.setText("");
                                                priceET.setText("");
                                                Toast.makeText(getActivity(),"Updated!",Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


            }
        });


        return view;
    }

}
