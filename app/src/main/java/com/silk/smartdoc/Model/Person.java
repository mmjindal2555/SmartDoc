package com.silk.smartdoc.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Property;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class Person implements Parcelable{
    private String name;
    private String username;
    private String password;
    private Date dateOfBirth;
    private String sex;
    private String email;
    private String id;

    public String getId(){return id;}
    public String getPassword() {
        return password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public  ArrayList<Query> getMyQuestions() {
        return myQuestions;
    }

    public  ArrayList<Query> getMyExperience() {
        return myExperience;
    }

    public String getName() {

        return name;
    }

    public Person() {
        this.name = "";
        this.username = "";
        this.password = "";
        this.dateOfBirth = new Date();
        this.sex = "";
        this.email = "";
        this.isDoctor = false;
        this.registrationNumber = "";
        this.myQuestions = new ArrayList<Query>();
        this.myExperience = new ArrayList<Query>();
        this.id="";

    }

    public Person(String name, String username, String password, Date dateOfBirth, String sex,
                  String email, boolean isDoctor, String registrationNumber,
                  ArrayList<Query> myQuestions,  ArrayList<Query> myExperience,String id) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.email = email;
        this.isDoctor = isDoctor;
        this.registrationNumber = registrationNumber;
        this.myQuestions = myQuestions;
        this.myExperience = myExperience;
        this.id = id;
    }

    private boolean isDoctor;
    private String registrationNumber;

    public Person(String name, String username, String password, Date dateOfBirth, String sex,
                  String email, boolean isDoctor, String registrationNumber,String id) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.email = email;
        this.isDoctor = isDoctor;
        this.registrationNumber = registrationNumber;
        this.id = id;
    }

    private ArrayList<Query> myQuestions;
    private ArrayList<Query> myExperience;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeValue(dateOfBirth);
        dest.writeString(sex);
        dest.writeString(email);
        dest.writeString(id);
    }

    public Person(Parcel parcel){
        name = parcel.readString();
        username = parcel.readString();
        password = parcel.readString();
        dateOfBirth = (Date) parcel.readValue(null);
        sex = parcel.readString();
        email = parcel.readString();
        id = parcel.readString();


    }
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>(){

        @Override
        public Person createFromParcel(Parcel parcel) {
            return new Person(parcel);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[0];
        }
    };

}
