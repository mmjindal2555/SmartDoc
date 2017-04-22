package com.silk.smartdoc.Controller;

import com.silk.smartdoc.Model.Answer;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Test;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class SearchManager {
    private SmartDocManager sdm;
    private Medicine medicineDetails[];
    private Test testDetails;

    public DiagnosticCenter[] searchDiagnosticCenter(Test test){
        return new DiagnosticCenter[1];
    }
    public Medicine[] searchMedicine(String medicineName){
        return new Medicine[1];
    }
    public Answer[] searchQueryForAnswer(Query query){
        return new Answer[1];
    }
}
