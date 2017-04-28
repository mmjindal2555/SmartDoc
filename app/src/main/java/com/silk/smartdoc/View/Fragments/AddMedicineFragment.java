package com.silk.smartdoc.View.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;
import com.silk.smartdoc.View.AdminControl;
import com.silk.smartdoc.View.MedicineSearch;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Chemicals");
        chemArrayList = new ArrayList<String>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children= dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    String medName = child.child("name").getValue(String.class);
                    if(medName!=null)
                    chemArrayList.add(medName);
                }
                chemicalsSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item,chemArrayList));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }


}
