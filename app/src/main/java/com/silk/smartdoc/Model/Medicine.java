package com.silk.smartdoc.Model;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class Medicine {
    public String chemicalName;
    public String name;
    public double price;

    public Medicine(){
        this.chemicalName="";
        this.name = "";
        this.price = 0;
    }
    public Medicine(String chemicalName,  String medicineName, double price) {
        this.chemicalName = chemicalName;
        this.name = medicineName;
        this.price = price;
    }

    public String getChemicalName() {
        return chemicalName;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}