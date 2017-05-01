package com.silk.smartdoc.View.Fragments;

import android.app.Fragment;
import android.os.Bundle;
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
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Manish on 4/27/2017.
 */

public class RemoveMedicineFragment extends Fragment{

    EditText medSearchET;
    Button medSearchButton;
    Spinner medSpinner;
    Button commitButton;
    ArrayList<String> medicines;
    String medSequence;
    String keyOfMedicineToDelete;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.remove_medicine_fragment, container, false);
        medSearchButton = (Button)view.findViewById(R.id.gomedButon);
        medSearchET = (EditText) view.findViewById(R.id.medsearchET);
        medSpinner = (Spinner)view.findViewById(R.id.medicineSpinner);
        commitButton = (Button)view.findViewById(R.id.commitButton3);

        medSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medSequence = medSearchET.getText().toString();
                if(!(medSequence.equals(""))){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Medicines");
                    medicines = new ArrayList<>();
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                            for (DataSnapshot child: children) {
                                String medName = child.child("name").getValue(String.class);
                                if(medName!=null && medName.contains(medSequence))
                                    medicines.add(medName);
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                                    android.R.layout.simple_spinner_item,medicines);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            medSpinner.setAdapter(arrayAdapter);
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
                databaseReference = FirebaseDatabase.getInstance().getReference();
                if(!(medSpinner.getSelectedItem()==null)) {
                    final String medicineToDelete = medSpinner.getSelectedItem().toString();
                    databaseReference.child("Medicines").child(medicineToDelete).removeValue();
                    databaseReference.child("Chemicals").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                            for (DataSnapshot child: children) {
                                String name = child.child("name").getValue(String.class);
                                for(DataSnapshot childPostSnapshot : child.child("medicines").getChildren()){
                                    String medName = childPostSnapshot.getValue(String.class);
                                    if(medName.equals(medicineToDelete)){
                                        keyOfMedicineToDelete = childPostSnapshot.getKey();
                                        DatabaseReference dbr = FirebaseDatabase.getInstance()
                                                .getReference().child("Chemicals").child(name)
                                                .child("medicines").child(keyOfMedicineToDelete);
                                        dbr.removeValue();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    medSearchET.setText("");
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item,new ArrayList<String>());
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    medSpinner.setAdapter(arrayAdapter);
                    Toast.makeText(getActivity(),"Medicine is deleted.",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}