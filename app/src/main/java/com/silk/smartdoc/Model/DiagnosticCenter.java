package com.silk.smartdoc.Model;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class DiagnosticCenter {
    private String name;
    private String location;
    private String certification;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCertification() {
        return certification;
    }

    public DiagnosticCenter(){
        this.name = "";
        this.location = "";
        this.certification = "";
    }
    public DiagnosticCenter(String name, String location, String certification) {

        this.name = name;
        this.location = location;
        this.certification = certification;
    }
}
