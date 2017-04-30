package com.silk.smartdoc.Model;

/**
 * Created by Manish on 4/30/2017.
 */

public class CentreAndPrice {
    private String centreId;
    private double price;

    public CentreAndPrice() {
        this.centreId = "";
        this.price = 0;
    }

    public String getCentreId() {

        return centreId;
    }

    public double getPrice() {
        return price;
    }

    public CentreAndPrice(String centreId, double price) {

        this.centreId = centreId;
        this.price = price;
    }
}
