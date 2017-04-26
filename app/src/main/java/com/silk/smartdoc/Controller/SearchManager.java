package com.silk.smartdoc.Controller;

import com.silk.smartdoc.Model.Answer;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Test;

import java.util.ArrayList;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class SearchManager {
    private SmartDocManager sdm;
    private Medicine medicineDetails[];
    private Test testDetails;

    SearchManager(SmartDocManager sdm)
    {
        this.sdm=sdm;
    }

    public DiagnosticCenter[] searchDiagnosticCenter(Test test){
        return new DiagnosticCenter[1];
    }
    public ArrayList<Medicine> searchMedicine(String medicineName){
        return sdm.dataMgr.getReqMedicine(medicineName);
    }
    public Answer[] searchQueryForAnswer(Query query){
        return new Answer[1];
    }
}
