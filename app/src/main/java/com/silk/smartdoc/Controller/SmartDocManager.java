package com.silk.smartdoc.Controller;

import android.app.Application;

import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Test;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class SmartDocManager  extends SmartDoc{
    public SearchManager searchMgr;
    public DisplayManager displayMgr;;
    public DataManager dataMgr;
    public ForumManager forumMgr;

    public SmartDocManager(){
        this.searchMgr = new SearchManager(this);
        this.displayMgr = new DisplayManager();
        this.dataMgr = new DataManager(this);
        this.forumMgr = new ForumManager();
    }

    public DiagnosticCenter[] searchDiagnosticCenters(Test test){
        return new DiagnosticCenter[1];
    }
    public Medicine[] searchMedicine(String medicineName){
        return new Medicine[1];
    }
    public void postQuery(Query query){}
    public void requestAnswerQuery(){}
    public boolean isAnswered(Query query){
        return false;
    }
}
