package com.silk.smartdoc.Model;

import java.util.ArrayList;

/**
 * Created by Manish on 4/28/2017.
 */

public class Chemical {
    private String name;
    private String id;
    private String description;
    private boolean isGeneric;
    private ArrayList<String> medicineIds;

    public Chemical(){
        this.name = "";
        this.id = "";
        this.description = "";
        this.isGeneric = false;
        this.medicineIds = new ArrayList<>();
    }
    public Chemical(String name, String id, String description, boolean isGeneric, ArrayList<String> medicineIds) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.isGeneric = isGeneric;
        this.medicineIds = medicineIds;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isGeneric() {
        return isGeneric;
    }

    public ArrayList<String> getMedicineIds() {
        return medicineIds;
    }
}
