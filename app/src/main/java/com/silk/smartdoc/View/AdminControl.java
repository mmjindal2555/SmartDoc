package com.silk.smartdoc.View;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.silk.smartdoc.View.Fragments.AddCentre;
import com.silk.smartdoc.View.Fragments.AddTest;
import com.silk.smartdoc.R;
import com.silk.smartdoc.View.Fragments.UpdateTest;
import com.silk.smartdoc.View.Fragments.AddChemicalFragment;
import com.silk.smartdoc.View.Fragments.AddMedicineFragment;
import com.silk.smartdoc.View.Fragments.RemoveMedicineFragment;
import com.silk.smartdoc.View.Fragments.UpdateMedicineFragment;

public class AdminControl extends AppCompatActivity {

    Button addChemicalButton;
    Button addMedicineButton;
    Button updateMedicineButton;
    Button removeMedicineButton;
    Button addTestButton;
    Button addCentreButton;
    Button updateCentreButton;
    Button removeTests;

    FragmentManager fragmentManager;
    AddChemicalFragment addChemicalFragment;
    AddMedicineFragment addMedicineFragment;
    UpdateMedicineFragment updateMedicineFragment;
    RemoveMedicineFragment removeMedicineFragment;
    AddTest addTestFragment;
    AddCentre addCentreFragment;
    UpdateTest updateTestFragment;

    int medicineButtonClicked = 1;
    int testButtonClicked = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);
        addChemicalButton = (Button)findViewById(R.id.addChemButton);
        addMedicineButton = (Button)findViewById(R.id.addMedButton);
        updateMedicineButton = (Button)findViewById(R.id.updateMedButton);
        removeMedicineButton = (Button)findViewById(R.id.removeMedButton);

        addCentreButton = (Button)findViewById(R.id.addCentreButton);
        addTestButton = (Button)findViewById(R.id.addTestButton);
        updateCentreButton = (Button)findViewById(R.id.updateCentreButton);


        // setting toolbar and statusbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("You Rule!");
        toolbar.setBackgroundColor(getResources().getColor(R.color.accentcolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getWindow().setStatusBarColor(getResources().getColor(R.color.accentdarkcolor));
        setSupportActionBar(toolbar);

        addChemicalButton.setBackground(getDrawable(R.drawable.button_pressed));
        medicineButtonClicked = 1;
        testButtonClicked = 1;

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        addChemicalFragment = new AddChemicalFragment();
        addMedicineFragment = new AddMedicineFragment();
        updateMedicineFragment = new UpdateMedicineFragment();
        removeMedicineFragment = new RemoveMedicineFragment();
        addTestFragment = new AddTest();
        addCentreFragment = new AddCentre();
        updateTestFragment = new UpdateTest();

        setTestButtonsLook(1);

        fragmentTransaction.add(R.id.fragment_container, addChemicalFragment);
        fragmentTransaction.add(R.id.test_fragment_container, addCentreFragment);

        fragmentTransaction.commit();

        addChemicalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMedicineButtonsLook(1);
                setTestButtonsLook(testButtonClicked);
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
                setTestButtonsLook(testButtonClicked);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, addMedicineFragment);
                fragmentTransaction.commit();

            }
        });
        updateMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMedicineButtonsLook(3);
                setTestButtonsLook(testButtonClicked);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, updateMedicineFragment);
                fragmentTransaction.commit();

            }
        });
        removeMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMedicineButtonsLook(4);
                setTestButtonsLook(testButtonClicked);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, removeMedicineFragment);
                fragmentTransaction.commit();

            }
        });
        addTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTestButtonsLook(2);
                setMedicineButtonsLook(medicineButtonClicked);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.test_fragment_container, addTestFragment);
                fragmentTransaction.commit();
            }
        });
        addCentreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTestButtonsLook(1);
                setMedicineButtonsLook(medicineButtonClicked);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.test_fragment_container, addCentreFragment);
                fragmentTransaction.commit();
            }
        });
        updateCentreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTestButtonsLook(3);
                setMedicineButtonsLook(medicineButtonClicked);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.test_fragment_container, updateTestFragment);
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
    private void setTestButtonsLook(int newButtonIndex) {
        switch(testButtonClicked){
            case 2: addTestButton.setBackground(getDrawable(R.drawable.button_unpressed)); break;
            case 3: updateCentreButton.setBackground(getDrawable(R.drawable.button_unpressed)); break;
            case 1: addCentreButton.setBackground(getDrawable(R.drawable.button_unpressed)); break;
            //case 4: removeMedicineButton.setBackground(getDrawable(R.drawable.button_unpressed)); break;

        }
        switch(newButtonIndex){
            case 2: addTestButton.setBackground(getDrawable(R.drawable.button_pressed)); break;
            case 3: updateCentreButton.setBackground(getDrawable(R.drawable.button_pressed)); break;
            case 1: addCentreButton.setBackground(getDrawable(R.drawable.button_pressed)); break;
            //case 4: removeMedicineButton.setBackground(getDrawable(R.drawable.button_pressed)); break;

        }
        testButtonClicked = newButtonIndex;
    }
}
