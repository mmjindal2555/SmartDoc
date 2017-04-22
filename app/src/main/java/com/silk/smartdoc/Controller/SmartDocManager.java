package com.silk.smartdoc.Controller;

import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Test;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class SmartDocManager {
    private SearchManager searchMgr;
    private DisplayManager displayMgr;
    private DataManager dataMgr;
    private ForumManager forumMgr;

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
