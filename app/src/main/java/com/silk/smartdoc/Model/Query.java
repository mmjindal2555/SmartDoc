package com.silk.smartdoc.Model;

import java.util.ArrayList;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class Query {
    Statement question;
    ArrayList<Statement> answer;
    ArrayList<String> tags;
    String id;
    public Query(Statement question, ArrayList<Statement> answer,ArrayList<String> tags, String id )
    {
        this.question = question;
        this.answer = answer;
        this.tags = tags;
        this.id = id;
    }
}
