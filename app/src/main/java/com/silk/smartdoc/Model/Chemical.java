package com.silk.smartdoc.Model;

import java.util.ArrayList;

/**
 * Created by Manish on 4/28/2017.
 */

public class Chemical {
    private String name;
    private String description;
    private boolean isGeneric;
    private ArrayList<String> medicines;

    public Chemical(){
        this.name = "";
        this.description = "";
        this.isGeneric = false;
        this.medicines = new ArrayList<>();
    }
    public Chemical(String name, String description, boolean isGeneric, ArrayList<String> medicineIds) {
        this.name = name;
        this.description = description;
        this.isGeneric = isGeneric;
        this.medicines = medicineIds;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isGeneric() {
        return isGeneric;
    }

    public ArrayList<String> getMedicineIds() {
        return medicines;
    }
}