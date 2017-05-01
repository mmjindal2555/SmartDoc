package com.silk.smartdoc.Model;

import java.util.ArrayList;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class Test {
    private String name;
    private String description;
    private String id;
    private ArrayList<CentreAndPrice> centres;

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getId(){return id;}
    public ArrayList<CentreAndPrice> getCentres(){return centres;}

    public Test() {
        this.name ="";
        this.description="";
        this.id = "";
        this.centres = new ArrayList<>();
    }
    public Test(String name, String description,String id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Test(String name, String description, String id, ArrayList<CentreAndPrice> centres) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.centres = centres;
    }
}
