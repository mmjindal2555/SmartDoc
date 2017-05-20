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
}
