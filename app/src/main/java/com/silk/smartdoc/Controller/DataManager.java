package com.silk.smartdoc.Controller;

import com.silk.smartdoc.Model.Answer;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Doctor;
import com.silk.smartdoc.Model.ForumQuery;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.Model.Patient;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Test;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class DataManager {
    private SmartDocManager sdm;
    private Medicine allMedicines[];
    private DiagnosticCenter allCenters[];
    private ForumQuery forumQueries;
    private Patient allPatients[];
    private Doctor allDoctors[];

    public DiagnosticCenter[] getDiagnosticCenter(Test test){
        return new DiagnosticCenter[1];
    }
    public Medicine[] getMedicine(String medicineName){
        return new Medicine[1];
    }
    public Query[] getAnswerQuery(Test test){
        return new Query[1];
    }
    public Query updateQuery(Answer queryAnswer){
        Query q = new Query();
        return q;
    }
    public void updateForumQuery(Query query){}
    public void addMedicine(Medicine medicine){}
    public void addDiagnosticCenter(DiagnosticCenter diagnosticCenter){}

}
