package com.silk.smartdoc.Model;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class Medicine {
    private String chemicalName;
    private String name;
    private double price;
    private String manufacturer;


    public Medicine(){
        this.chemicalName="";
        this.name = "";
        this.price = 0;
        this.manufacturer = "";
    }
    public Medicine(String chemicalName,  String medicineName, double price,String manufacturer) {
        this.chemicalName = chemicalName;
        this.name = medicineName;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public String getManufacturer(){return manufacturer;}

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}