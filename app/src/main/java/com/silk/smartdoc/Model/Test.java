package com.silk.smartdoc.Model;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class Test {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Test() {
        this.name ="";
        this.description="";
    }

    public Test(String name, String description) {

        this.name = name;
        this.description = description;
    }
}
