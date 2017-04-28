package com.silk.smartdoc.View.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;
import com.silk.smartdoc.View.AdminControl;
import com.silk.smartdoc.View.MedicineSearch;

import org.w3c.dom.Comment;

import java.util.ArrayList;

/**
 * Created by Manish on 4/27/2017.
 */

public class AddMedicineFragment extends Fragment{

    Spinner chemicalsSpinner;
    ArrayList<String> chemArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_medicine_fragment, container, false);
        chemicalsSpinner = (Spinner)view.findViewById(R.id.chemicalsSpinner);
        return view;
    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Chemicals");
    chemArrayList = new ArrayList<Medicine>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> children= dataSnapshot.getChildren();
            for (DataSnapshot child: children) {
                String medName = child.getValue(String.class);
                medArrayList.add(medName);
            }
            chemicalsSpinner.setAdapter(new ArrayAdapter<String>(AddMedicineFragment.this, android.R.layout.simple_list_item_1, medArrayList));
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    });


}
