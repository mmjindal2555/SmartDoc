package com.silk.smartdoc.Model;

import java.util.ArrayList;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class DiagnosticCenter {
    private String name;
    private String location;
    private String certification;
    private String url;
    private String id;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCertification() {
        return certification;
    }

    public String getUrl(){return url;}

    public String getId(){return id;}

    public DiagnosticCenter(){
        this.name = "";
        this.location = "";
        this.certification = "";
        this.url = "";
        this.id="";
    }
    public DiagnosticCenter(String name, String location, String certification, String url,String id) {

        this.name = name;
        this.location = location;
        this.certification = certification;
        this.url = url;
        this.id = id;
    }
}
