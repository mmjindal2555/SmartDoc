package com.silk.smartdoc.View.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silk.smartdoc.Model.Chemical;
import com.silk.smartdoc.R;

import java.util.ArrayList;

/**
 * Created by Manish on 4/27/2017.
 */

public class AddChemicalFragment extends Fragment{

    EditText descriptionET;
    Switch isGenericSwitch;
    Button commitButton;
    EditText nameET;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_chemical_fragment, container, false);
        descriptionET = (EditText)view.findViewById(R.id.chemicalDescriptinText);
        isGenericSwitch = (Switch)view.findViewById(R.id.isGenericSwitch);
        commitButton = (Button)view.findViewById(R.id.commitAddChemical);
        nameET = (EditText)view.findViewById(R.id.chemicalNameET);

        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Chemicals");
                String description = descriptionET.getText().toString();
                boolean isGeneric = isGenericSwitch.isChecked();
                String name = nameET.getText().toString();
                if(description.equals("") || name.equals("")){
                    Toast.makeText(getActivity(),"Something is Wrong!!",Toast.LENGTH_LONG).show();
                }
                else {
                    Chemical chemical = new Chemical(name, description, isGeneric, new ArrayList<String>());
                    databaseReference.child(name).setValue(chemical);
                    descriptionET.setText("");
                    nameET.setText("");
                    isGenericSwitch.setChecked(false);
                }

            }
        });
        return view;

    }

}
