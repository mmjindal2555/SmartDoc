
package com.silk.smartdoc.Controller;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.silk.smartdoc.Model.Answer;
import com.silk.smartdoc.View.MedicineSearch;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class DisplayManager{
    private SmartDocManager sdm;
    public void displayDiagnosticCenter(){}
    public void displayMedicines(){}
    public void postQuery(){}
    public void displayAnswerQuery(){}
    public void displayAnswer(Answer answer[]){}
    ///new pages
    public void displayMedicineSerachPage(Context context)
    {
        //Intent intents = new Intent(context,MedicineSearch.class);
        context.startActivity(new Intent(context,MedicineSearch.class));
    }
}