package com.silk.smartdoc.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

        final String searchValue = searchData.getString("searchValue");
        toolbar.setTitle(searchValue);
        setSupportActionBar(toolbar);

        description = (TextView) findViewById(R.id.descriptionTextView);
        genericIcon = (ImageView) findViewById(R.id.genericIconImageView);


        DatabaseReference databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Medicines");
        medResultArrayList = new ArrayList<Medicine>();
        databaseReferenceMed.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Medicine medicine = postSnapshot.getValue(Medicine.class);

                    String medName = medicine.getName();
                    if(medName.equalsIgnoreCase(searchValue)) {
                        final String chemiName = medicine.getChemicalName();


                        DatabaseReference databaseReferenceChem = FirebaseDatabase.getInstance().getReference().child("Chemicals");
                        chemResultArrayList = new ArrayList<Chemical>();
                        databaseReferenceChem.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                    String name = postSnapshot.child("name").getValue(String.class);
                                    String desc = postSnapshot.child("description").getValue(String.class);
                                    Boolean gene = postSnapshot.child("generic").getValue(Boolean.class);
                                    ArrayList<String> medNames = new ArrayList<String>();

                                    for(DataSnapshot childPostSnapshot : postSnapshot.child("medicines").getChildren()){
                                        String medName = childPostSnapshot.getValue(String.class);
                                        medNames.add(medName);
                                    }

                                    Chemical chemical = new Chemical(name,desc,gene,medNames);

                                    String medName = chemical.getName();
                                    if(medName.equalsIgnoreCase(chemiName)) {

                                        if(chemical.isGeneric()== true){
                                            genericIcon.setImageResource(R.drawable.ic_check_circle);
                                        }
                                        else {
                                            genericIcon.setImageResource(R.drawable.ic_cancel);
                                        }
                                        description.setText(chemical.getDescription());

                                        medNames = chemical.getMedicineIds();

                                        for(final String medSearch : medNames){
                                            DatabaseReference databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Medicines");
                                            medResultArrayList = new ArrayList<Medicine>();
                                            databaseReferenceMed.addValueEventListener(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                                        Medicine medicine = postSnapshot.getValue(Medicine.class);

                                                        String medName = medicine.getName();
                                                        if(medName.equalsIgnoreCase(medSearch)) {
                                                            medResultArrayList.add(medicine);
                                                        }
                                                    }
                                                    medicinesList.setAdapter(new MedicineResultsAdapter(medResultArrayList,MedicineResult.this));
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReferenceChem = FirebaseDatabase.getInstance().getReference().child("Chemicals");
        chemResultArrayList = new ArrayList<Chemical>();
        databaseReferenceChem.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String name = postSnapshot.child("name").getValue(String.class);
                    String desc = postSnapshot.child("description").getValue(String.class);
                    Boolean gene = postSnapshot.child("generic").getValue(Boolean.class);
                    ArrayList<String> medNames = new ArrayList<String>();

                    for(DataSnapshot childPostSnapshot : postSnapshot.child("medicines").getChildren()){
                        String medName = childPostSnapshot.getValue(String.class);
                        medNames.add(medName);
                    }

                    Chemical chemical = new Chemical(name,desc,gene,medNames);

                    String medName = chemical.getName();
                    if(medName.equalsIgnoreCase(searchValue)) {

                        if(chemical.isGeneric()== true){
                            genericIcon.setImageResource(R.drawable.ic_check_circle);
                        }
                        else {
                            genericIcon.setImageResource(R.drawable.ic_cancel);
                        }
                        description.setText(chemical.getDescription());

                        medNames = chemical.getMedicineIds();

                        for(final String medSearch : medNames){
                            DatabaseReference databaseReferenceMed = FirebaseDatabase.getInstance().getReference().child("Medicines");
                            medResultArrayList = new ArrayList<Medicine>();
                            databaseReferenceMed.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                        Medicine medicine = postSnapshot.getValue(Medicine.class);

                                        String medName = medicine.getName();
                                        Log.e("Dodo",medName);
                                        if(medName.equalsIgnoreCase(medSearch)) {
                                            medResultArrayList.add(medicine);
                                        }
                                    }
                                    medicinesList.setAdapter(new MedicineResultsAdapter(medResultArrayList,MedicineResult.this));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}