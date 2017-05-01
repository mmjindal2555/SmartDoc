package com.silk.smartdoc.Controller;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.Answer;
import com.silk.smartdoc.Model.Chemical;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Test;
import com.silk.smartdoc.R;

import java.util.ArrayList;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class SearchManager{
    private SmartDocManager sdm;
    private Medicine medicineDetails[];
    private Test testDetails;

    SearchManager(SmartDocManager sdm)
    {
        this.sdm=sdm;
    }

    public DiagnosticCenter[] searchDiagnosticCenter(Test test){
        return new DiagnosticCenter[1];
    }



    // function to search for the chemical name
    public String searchMedicineForChemicalName(DataSnapshot dataSnapshot, String medicineName){

        String chemicalName = "";
        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            Medicine medicine = postSnapshot.getValue(Medicine.class);

            String medName = medicine.getName();
            if (medName.equalsIgnoreCase(medicineName)) {
                chemicalName = medicine.getChemicalName();
                return chemicalName;
            }
        }
        return chemicalName;
    }

    // function to search for the Chemical object having a specific chemical name
    public Chemical searchForChemicalObject(DataSnapshot dataSnapshot, String chemicalName) {
        Chemical chemical = new Chemical();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            String name = postSnapshot.child("name").getValue(String.class);
            String desc = postSnapshot.child("description").getValue(String.class);
            Boolean gene = postSnapshot.child("generic").getValue(Boolean.class);
            ArrayList<String> medNames = new ArrayList<String>();

            for (DataSnapshot childPostSnapshot : postSnapshot.child("medicines").getChildren()) {
                String medName = childPostSnapshot.getValue(String.class);
                medNames.add(medName);
            }

            chemical = new Chemical(name, desc, gene, medNames);
            if(chemical.getName().equalsIgnoreCase(chemicalName))
                return chemical;

        }
        return chemical;
    }

    // function to get arrayList of medicines having names defined in medicineNames arrayList
    public ArrayList<Medicine> searchForMedicine(DataSnapshot dataSnapshot, ArrayList<String> medicineNames){
        ArrayList<Medicine> medResultArrayList= new ArrayList<>();
        Medicine medicine;
        for(String medicineName : medicineNames){
            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                medicine = postSnapshot.getValue(Medicine.class);
                String medName = medicine.getName();
                if(medName.equalsIgnoreCase(medicineName)) {
                    medResultArrayList.add(medicine);
                }
            }
        }

        return medResultArrayList;
    }

    public Answer[] searchQueryForAnswer(Query query){
        return new Answer[1];
    }
}
