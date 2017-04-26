package com.silk.smartdoc.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.List;


public class MedicineResult extends AppCompatActivity {


    AdapterView.AdapterContextMenuInfo info;
    ArrayList <Medicine> medResultArrayList;
    ListView medicinesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_result);

        Bundle searchData = getIntent().getExtras();
        final String searchValue = searchData.getString("searchValue");
        medicinesList = (ListView)findViewById(R.id.medicine_details);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("medicine");
        medResultArrayList = new ArrayList<Medicine>();

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Medicine medicine = postSnapshot.getValue(Medicine.class);

                    String medChemName = medicine.chemicalName;
                    String medName = medicine.medicineName;

                    if(medChemName.equalsIgnoreCase(searchValue) || medName.equalsIgnoreCase(searchValue))
                        medResultArrayList.add(medicine);

                }
                medicinesList.setAdapter(new MedicineResultsAdapter(medResultArrayList,MedicineResult.this));
                Log.e("dodo",medResultArrayList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}