package com.silk.smartdoc.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AKASH AGARWAL on 4/29/2017.
 */

public class Statement implements Parcelable{
    String user_id;
    String id;
    String statement;
    ArrayList<String> votes;
    Date timestamp ;
    public Statement(String user_id,String id,String statement, Date timestamp,ArrayList<String> votes)
    {
        this.user_id=user_id;
        this.id=id;
        this.statement=statement;
        this.timestamp=timestamp;
        this.votes=votes;
    }
    public Statement(){
        votes = new ArrayList<String>();
         timestamp = new Date() ;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public ArrayList<String> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<String> votes) {
        this.votes = votes;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(id);
        dest.writeString(statement);

        dest.writeValue(timestamp);
        dest.writeList(votes);

    }

    public Statement(Parcel parcel){
        user_id = parcel.readString();
        id = parcel.readString();
        statement = parcel.readString();

        timestamp = (Date) parcel.readValue(null);
        parcel.readList(votes, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Statement> CREATOR = new Parcelable.Creator<Statement>(){

        @Override
        public Statement createFromParcel(Parcel parcel) {
            return new Statement(parcel);
        }

        @Override
        public Statement[] newArray(int size) {
            return new Statement[0];
        }
    };
}
