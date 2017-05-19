package com.silk.smartdoc.View.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.R;

public class AddCentre extends Fragment {

    EditText nameET;
    EditText addressET;
    EditText certifyET;
    EditText urlET;
    Button commitButton;

    DatabaseReference db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_centre, container, false);
        nameET = (EditText)view.findViewById(R.id.centreNameET);
        addressET = (EditText)view.findViewById(R.id.addressET);
        certifyET = (EditText)view.findViewById(R.id.certifiedET);
        urlET = (EditText)view.findViewById(R.id.urlET);
        commitButton = (Button)view.findViewById(R.id.commit5);
        db = FirebaseDatabase.getInstance().getReference().child("Centres");
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String address = addressET.getText().toString();
                String certify = certifyET.getText().toString();
                String url = urlET.getText().toString();

                if(name.equals("") || address.equals("") || certify.equals("")){
                    Toast.makeText(getActivity(),"Name, Address & certifications are needed",Toast.LENGTH_LONG).show();
                }
                else{
                    String id = db.push().getKey();
                    DiagnosticCenter dc = new DiagnosticCenter(name,address,certify,url,id);
                    db.child(id).setValue(dc);
                    nameET.setText("");
                    addressET.setText("");
                    certifyET.setText("");
                    urlET.setText("");
                    Toast.makeText(getActivity(),"Centre added!",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;

    }
}
