package com.silk.smartdoc.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.silk.smartdoc.R;
import com.silk.smartdoc.View.Fragments.AddChemicalFragment;
import com.silk.smartdoc.View.Fragments.AddMedicineFragment;
import com.silk.smartdoc.View.Fragments.RemoveMedicineFragment;
import com.silk.smartdoc.View.Fragments.UpdateMedicineFragment;

public class AdminControl extends AppCompatActivity {

    Button addChemicalButton;
    Button addMedicineButton;
    Button updateMedicineButton;
    Button removeMedicineButton;

    FragmentManager fragmentManager;
    AddChemicalFragment addChemicalFragment;
    AddMedicineFragment addMedicineFragment;
    UpdateMedicineFragment updateMedicineFragment;
    RemoveMedicineFragment removeMedicineFragment;

    int medicineButtonClicked = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);
        addChemicalButton = (Button)findViewById(R.id.addChemButton);
        addMedicineButton = (Button)findViewById(R.id.addMedButton);
        updateMedicineButton = (Button)findViewById(R.id.updateMedButton);
        removeMedicineButton = (Button)findViewById(R.id.removeMedButton);


        // setting toolbar and statusbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("You Rule!");
        toolbar.setBackgroundColor(getResources().getColor(R.color.accentcolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getWindow().setStatusBarColor(getResources().getColor(R.color.accentdarkcolor));
        setSupportActionBar(toolbar);

        addChemicalButton.setBackground(getDrawable(R.drawable.button_pressed));
        medicineButtonClicked = 1;

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        addChemicalFragment = new AddChemicalFragment();
        addMedicineFragment = new AddMedicineFragment();
        updateMedicineFragment = new UpdateMedicineFragment();
        removeMedicineFragment = new RemoveMedicineFragment();
        fragmentTransaction.add(R.id.fragment_container, addChemicalFragment);

        fragmentTransaction.commit();

        addChemicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMedicineButtonsLook(1);
                addChemicalButton.setBackground(getDrawable(R.drawable.button_pressed));
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addChemicalFragment);

                fragmentTransaction.commit();
            }
        });
        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMedicineButtonsLook(2);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addMedicineFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        updateMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMedicineButtonsLook(3);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, updateMedicineFragment);
                fragmentTransaction.commit();

            }
        });
        removeMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMedicineButtonsLook(4);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, removeMedicineFragment);
                fragmentTransaction.commit();

            }
        });
    }

    private void setMedicineButtonsLook(int newButtonIndex) {


        switch(medicineButtonClicked){
            case 2: addMedicineButton.setBackground(getDrawable(R.drawable.button_unpressed)); break;
            case 3: updateMedicineButton.setBackground(getDrawable(R.drawable.button_unpressed)); break;
            case 1: addChemicalButton.setBackground(getDrawable(R.drawable.button_unpressed)); break;
            case 4: removeMedicineButton.setBackground(getDrawable(R.drawable.button_unpressed)); break;

        }
        switch(newButtonIndex){
            case 2: addMedicineButton.setBackground(getDrawable(R.drawable.button_pressed)); break;
            case 3: updateMedicineButton.setBackground(getDrawable(R.drawable.button_pressed)); break;
            case 1: addChemicalButton.setBackground(getDrawable(R.drawable.button_pressed)); break;
            case 4: removeMedicineButton.setBackground(getDrawable(R.drawable.button_pressed)); break;

        }
        medicineButtonClicked = newButtonIndex;
    }
}
