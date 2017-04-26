package com.silk.smartdoc.Controller;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.Answer;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Doctor;
import com.silk.smartdoc.Model.ForumQuery;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Patient;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Test;
import com.silk.smartdoc.View.MedicineResult;
import com.silk.smartdoc.View.MedicineResultsAdapter;

import java.util.ArrayList;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class DataManager {
    private SmartDocManager sdm;
    private Medicine allMedicines[];
    private DiagnosticCenter allCenters[];
    private ForumQuery forumQueries;
    private Patient allPatients[];
    private Doctor allDoctors[];

    DataManager(SmartDocManager sdm)
    {
        this.sdm=sdm;
    }

    public DiagnosticCenter[] getDiagnosticCenter(Test test){
        return new DiagnosticCenter[1];
    }

    ArrayList<Medicine> medResultArrayList;
    String searchValue;
    public ArrayList<Medicine> getReqMedicine(String medName){
        searchValue = medName;
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

                //medicinesList.setAdapter(new MedicineResultsAdapter(medResultArrayList,MedicineResult.this));
                //Log.e("dodo",medResultArrayList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return medResultArrayList;
    }
    public Query[] getAnswerQuery(Test test){
        return new Query[1];
    }
    public Query updateQuery(Answer queryAnswer){
        Query q = new Query();
        return q;
    }
    public void updateForumQuery(Query query){}
    public void addMedicine(Medicine medicine){}
    public void addDiagnosticCenter(DiagnosticCenter diagnosticCenter){}

}
