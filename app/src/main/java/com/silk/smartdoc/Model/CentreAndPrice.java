package com.silk.smartdoc.Model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Manish on 4/30/2017.
 */

public class CentreAndPrice implements Comparable<CentreAndPrice>{
    public String centreId;
    public double price;

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

    @Override
    public int compareTo(@NonNull CentreAndPrice o) {
        return (int)(this.getPrice()-o.getPrice());
    }
}
