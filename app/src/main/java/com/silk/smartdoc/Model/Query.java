package com.silk.smartdoc.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class Query implements Parcelable {
    Statement question;
    ArrayList<Statement> answer= new ArrayList<>();
    ArrayList<String> tags = new ArrayList<>();
    String id;
    public Query()
    {

    }
    public Query(Statement question, ArrayList<Statement> answer,ArrayList<String> tags, String id )
    {
        this.question = question;
        this.answer = answer;
        this.tags = tags;
        this.id = id;
    }

    public Statement getQuestion() {
        return question;
    }

    public void setQuestion(Statement question) {
        this.question = question;
    }

    public ArrayList<Statement> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<Statement> answer) {
        this.answer = answer;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(question);
        dest.writeString(id);
        dest.writeTypedList(answer);
        dest.writeList(tags);


    }

    public Query(Parcel parcel){
        question = (Statement) parcel.readValue(Statement.class.getClassLoader());
        id = parcel.readString();
        parcel.readTypedList(answer,Statement.CREATOR);
        parcel.readList(tags, String.class.getClassLoader());


    }

    public static final Parcelable.Creator<Query> CREATOR = new Parcelable.Creator<Query>(){

        @Override
        public Query createFromParcel(Parcel parcel) {
            return new Query(parcel);
        }

        @Override
        public Query[] newArray(int size) {
            return new Query[0];
        }
    };
}
