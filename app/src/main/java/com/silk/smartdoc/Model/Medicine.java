package com.silk.smartdoc.Model;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class Medicine {
    public String chemicalName;
    public String description;
    public String isGeneric;
    public String medicineId;
    public String medicineName;
    public String price;

    public Medicine(){
        this.chemicalName="";
        this.description="";
        this.isGeneric = "";
        this.medicineId = "";
        this.medicineName = "";
        this.price = "";
    }
    public Medicine(String chemicalName, String description, String isGeneric, String medicineId, String medicineName, String price) {
        this.chemicalName = chemicalName;
        this.description = description;
        this.isGeneric = isGeneric;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.price = price;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public String getDescription() {
        return description;
    }

    public String getIsGeneric() {
        return isGeneric;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getPrice() {
        return price;
    }
}
