package com.silk.smartdoc.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.Model.Chemical;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.List;


public class MedicineResult extends AppCompatActivity {

    ArrayList <Medicine> medResultArrayList;
    ArrayList <Chemical> chemResultArrayList;
    ListView medicinesList;
    TextView description;
    ImageView genericIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_result);
        medicinesList = (ListView)findViewById(R.id.medicine_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.medicine_result_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));


        Bundle searchData = getIntent().getExtras();

        // storing the search value
        final String searchValue = searchData.getString("searchValue");
        toolbar.setTitle(searchValue);
        setSupportActionBar(toolbar);

        // referencing the required view objects
        description = (TextView) findViewById(R.id.descriptionTextView);
        genericIcon = (ImageView) findViewById(R.id.genericIconImageView);

        // Logic if search value is a medicine
        DatabaseReference databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Medicines");
        medResultArrayList = new ArrayList<Medicine>();
        databaseReferenceMed.addValueEventListener(new ValueEventListener() {

           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               SmartDocManager sdm = (SmartDocManager) getApplicationContext();
               // passing snapshot to get the chemical name of the medicine
               final String chemicalName = sdm.searchMgr.searchMedicineForChemicalName(dataSnapshot, searchValue);

               DatabaseReference databaseReferenceChem = FirebaseDatabase.getInstance().getReference().child("Chemicals");
               databaseReferenceChem.addValueEventListener(new ValueEventListener() {
                   ArrayList<String> medNames;
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       SmartDocManager sdm = (SmartDocManager) getApplicationContext();
                       // passing snapshot to get the chemical having required chemical name
                       Chemical chemical = sdm.searchMgr.searchForChemicalObject(dataSnapshot, chemicalName);
                       String medName = chemical.getName();
                       if (medName.equalsIgnoreCase(chemicalName)) {
                           if (chemical.isGeneric()) {
                               genericIcon.setImageResource(R.drawable.ic_check_circle);
                           } else {
                               genericIcon.setImageResource(R.drawable.ic_cancel);
                           }
                           description.setText(chemical.getDescription());
                           medNames = chemical.getMedicineIds();
                           medResultArrayList = new ArrayList<Medicine>();


                           // passing snapshot and medNames to fetch arrayList of medicines
                           DatabaseReference databaseReferenceMed2 = FirebaseDatabase.getInstance().getReference().child("Medicines");
                           databaseReferenceMed2.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot dataSnapshot) {
                                   SmartDocManager sdm = (SmartDocManager) getApplicationContext();
                                   medResultArrayList = sdm.searchMgr.searchForMedicine(dataSnapshot, medNames);
                                   medicinesList.setAdapter(new MedicineResultsAdapter(medResultArrayList,MedicineResult.this));
                               }

                               @Override
                               public void onCancelled(DatabaseError databaseError) {

                               }
                           });
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

        // Logic if search value is a chemical name
        DatabaseReference databaseReferenceChem = FirebaseDatabase.getInstance().getReference().child("Chemicals");
        chemResultArrayList = new ArrayList<Chemical>();
        databaseReferenceChem.addValueEventListener(new ValueEventListener() {
            ArrayList<String> medNames;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SmartDocManager sdm = (SmartDocManager) getApplicationContext();

                // passing snapshot to get the chemical having required chemical name
                Chemical chemical = sdm.searchMgr.searchForChemicalObject(dataSnapshot, searchValue);
                String medName = chemical.getName();
                if (medName.equalsIgnoreCase(searchValue)) {
                    if (chemical.isGeneric()) {
                        genericIcon.setImageResource(R.drawable.ic_check_circle);
                    } else {
                        genericIcon.setImageResource(R.drawable.ic_cancel);
                    }
                    description.setText(chemical.getDescription());
                    medNames = chemical.getMedicineIds();
                    medResultArrayList = new ArrayList<Medicine>();


                    // passing snapshot and medNames to fetch arrayList of medicines
                    DatabaseReference databaseReferenceMed2 = FirebaseDatabase.getInstance().getReference().child("Medicines");
                    databaseReferenceMed2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            SmartDocManager sdm = (SmartDocManager) getApplicationContext();
                            medResultArrayList = sdm.searchMgr.searchForMedicine(dataSnapshot, medNames);
                            medicinesList.setAdapter(new MedicineResultsAdapter(medResultArrayList,MedicineResult.this));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}