package com.silk.smartdoc.Model;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class Medicine {
    public String chemicalName;
    public String medicineId;
    public String name;
    public String price;

    public Medicine(){
        this.chemicalName="";
        this.medicineId = "";
        this.name = "";
        this.price = "";
    }
    public Medicine(String chemicalName, String medicineId, String medicineName, String price) {
        this.chemicalName = chemicalName;
        this.medicineId = medicineId;
        this.name = medicineName;
        this.price = price;
    }

    public String getChemicalName() {
        return chemicalName;
    }


    public String getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
