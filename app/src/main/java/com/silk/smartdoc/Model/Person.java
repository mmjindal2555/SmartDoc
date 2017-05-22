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
    private ArrayList<String> myQuestions;
    private ArrayList<String> myExperience;

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

    public  ArrayList<String> getMyQuestions() {
        return myQuestions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMyQuestions(ArrayList<String> myQuestions) {
        this.myQuestions = myQuestions;
    }

    public void setMyExperience(ArrayList<String> myExperience) {
        this.myExperience = myExperience;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public  ArrayList<String> getMyExperience() {
        return myExperience;
    }

    public String getName() {

        return name;
    }
    public boolean getIsDoctor() {

        return isDoctor;
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
        this.myQuestions = new ArrayList<String>();
        this.myExperience = new ArrayList<String>();
        this.id="";

    }

    public Person(String name, String username, String password, Date dateOfBirth, String sex,
                  String email, boolean isDoctor, String registrationNumber,
                  ArrayList<String> myQuestions,  ArrayList<String> myExperience,String id) {
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

    /*public Person(String name, String username, String password, Date dateOfBirth, String sex,
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
    }*/



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
