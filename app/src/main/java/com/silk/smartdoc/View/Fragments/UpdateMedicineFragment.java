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

/**
 * Created by Manish on 4/27/2017.
 */

public class UpdateMedicineFragment extends Fragment{

    Spinner searchedMedsSpinner;
    EditText medNameET;
    EditText newPriceET;
    Button commitButton;
    ArrayList<String> medicines;
    Button medSearchButton;
    String medSequence;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.update_medicine_fragment, container, false);
        searchedMedsSpinner = (Spinner)view.findViewById(R.id.searchedMedsSpinner);
        medNameET = (EditText)view.findViewById(R.id.medsearchET2);
        newPriceET = (EditText)view.findViewById(R.id.newPriceET);
        commitButton = (Button)view.findViewById(R.id.commitButton4);
        medSearchButton = (Button)view.findViewById(R.id.searchMedButon);

        medSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medSequence = medNameET.getText().toString();
                if(!(medSequence.equals(""))){
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Medicines");
                    medicines = new ArrayList<String>();
                    medicines.add("");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                            for (DataSnapshot child: children) {
                                String medName = child.child("name").getValue(String.class);
                                if(medName!=null && medName.contains(medSequence))
                                    medicines.add(medName);
                            }
                            searchedMedsSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_spinner_item,medicines));
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
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Medicines");
                if(searchedMedsSpinner.getSelectedItem()!=null) {
                    String medicineToDelete = searchedMedsSpinner.getSelectedItem().toString();
                    String priceString = newPriceET.getText().toString();
                    double price = Double.parseDouble(priceString);
                    databaseReference.child(medicineToDelete).child("price").setValue(price);
                    Toast.makeText(getActivity(),"Updated Medicine",Toast.LENGTH_LONG).show();
                    medNameET.setText("");
                    newPriceET.setText("");
                    searchedMedsSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item,new ArrayList<String>()));

                }
                else{
                    Toast.makeText(getActivity(),"Select medicine first!!",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
