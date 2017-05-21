package com.silk.smartdoc.Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AKASH AGARWAL on 4/29/2017.
 */

public class Statement {
    String user_id;
    String id;
    String statement;
    ArrayList<String> votes;
    Date timestamp;
    public Statement(String user_id,String id,String statement, Date timestamp,ArrayList<String> votes)
    {
        this.user_id=user_id;
        this.id=id;
        this.statement=statement;
        this.timestamp=timestamp;
        this.votes=votes;
    }
    public Statement(){}
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
}
