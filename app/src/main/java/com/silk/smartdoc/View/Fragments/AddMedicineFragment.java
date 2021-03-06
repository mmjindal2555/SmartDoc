package com.silk.smartdoc.View.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.silk.smartdoc.Model.Chemical;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;
import com.silk.smartdoc.View.AdminControl;
import com.silk.smartdoc.View.MedicineSearch;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Iterator;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Manish on 4/27/2017.
 */

public class AddMedicineFragment extends Fragment{

    Spinner chemicalsSpinner;
    ArrayList<String> chemArrayList;
    EditText medNameET;
    EditText manunfacturerNameET;
    EditText priceET;
    Button commitButton;
    ArrayList<String> medicines;
    DatabaseReference databaseReference;
    String medicineName;
    String chemicalName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_medicine_fragment, container, false);
        chemicalsSpinner = (Spinner)view.findViewById(R.id.spinnerx);
        medNameET = (EditText)view.findViewById(R.id.medNameET);
        manunfacturerNameET = (EditText)view.findViewById(R.id.manufacturerET);
        priceET = (EditText)view.findViewById(R.id.priceET);
        commitButton = (Button)view.findViewById(R.id.commitButton2);

        medicines = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chemicals");
        chemArrayList = new ArrayList<>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    String medName = child.child("name").getValue(String.class);
                    if(medName!=null)
                    chemArrayList.add(medName);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item,chemArrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                chemicalsSpinner.setAdapter(arrayAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                medicineName = medNameET.getText().toString();
                String manufacturerName = manunfacturerNameET.getText().toString();
                double price =0;
                price = Double.parseDouble(priceET.getText().toString());
                chemicalName = chemicalsSpinner.getSelectedItem().toString();

                if(medicineName.equals("") || manufacturerName.equals("") || price==0){
                    Toast.makeText(getActivity(),"Something is Wrong!!",Toast.LENGTH_LONG).show();
                }
                else {
                    Medicine medicine = new Medicine(chemicalName, medicineName, price, manufacturerName);
                    databaseReference.child("Medicines").child(medicineName).setValue(medicine);
                    databaseReference.child("Chemicals").child(chemicalName).child("medicines")
                            .addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            //medicines = dataSnapshot.getValue();
                            Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                            int i=0;
                            for (DataSnapshot child: children) {
                                String medName = child.getValue(String.class);
                                    medicines.add(medName);
                            }
                            if(medicines==null)
                                medicines = new ArrayList<String>();
                            medicines.add(medicineName);
                            databaseReference.child("Chemicals").child(chemicalName)
                                    .child("medicines").setValue(medicines);
                            medicines = new ArrayList<>();
                            Toast.makeText(getActivity(),"Medicine Is added",Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                    medNameET.setText("");
                    priceET.setText("");
                    manunfacturerNameET.setText("");
                }
            }
        });

        return view;
    }


}
