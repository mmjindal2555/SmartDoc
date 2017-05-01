package com.silk.smartdoc.Controller;


import com.google.firebase.database.DataSnapshot;
import com.silk.smartdoc.Model.Answer;
import com.silk.smartdoc.Model.CentreAndPrice;
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

    public ArrayList<String> getAllTestName(DataSnapshot dataSnapshot)
    {
        ArrayList<String> test;
        test=new ArrayList<String>();
        Iterable<DataSnapshot> children= dataSnapshot.getChildren();
        for (DataSnapshot child: children) {
            String testName = child.child("name").getValue(String.class);
            test.add(testName);
        }
        return test;
    }

    public ArrayList<CentreAndPrice> getCentreIdAndPrice(DataSnapshot dataSnapshot,String searchValue)
    {
        ArrayList<CentreAndPrice> centreAndPrice;
        centreAndPrice=new ArrayList<CentreAndPrice>();
        String testName;
        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
            testName = postSnapshot.child("name").getValue(String.class);
            if(testName.equalsIgnoreCase(searchValue))
            {
                for(DataSnapshot childPostSnapshot : postSnapshot.child("centres").getChildren()){
                    centreAndPrice.add(new CentreAndPrice(childPostSnapshot.child("centreId").getValue(String.class),
                            childPostSnapshot.child("price").getValue(double.class)));
                }
                break;
            }
        }
        return centreAndPrice;
    }

    public String isTestExit(DataSnapshot dataSnapshot,String searchValue)
    {
        String description = null,testName;
        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
            testName = postSnapshot.child("name").getValue(String.class);
            if(testName.equalsIgnoreCase(searchValue))
            {
                description=postSnapshot.child("description").getValue(String.class);
                break;
            }

        }
        return description;
    }

    public ArrayList<DiagnosticCenter> isDiagnosticCentersResultArrayList(DataSnapshot dataSnapshot,ArrayList<CentreAndPrice> centreAndPrices)
    {
        ArrayList<DiagnosticCenter> diagnosticCentersResultArrayList=new ArrayList<DiagnosticCenter>();
        for(CentreAndPrice cp:centreAndPrices){
            String id = cp.getCentreId();
            double pri = cp.getPrice();
            DiagnosticCenter diagnosticCenter=new DiagnosticCenter(
                    dataSnapshot.child(id).child("name").getValue(String.class),
                    dataSnapshot.child(id).child("location").getValue(String.class),
                    dataSnapshot.child(id).child("certification").getValue(String.class),
                    dataSnapshot.child(id).child("url").getValue(String.class),
                    id);
            diagnosticCentersResultArrayList.add(diagnosticCenter);
        }
        return diagnosticCentersResultArrayList;
    }
}
