
package com.silk.smartdoc.Controller;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.silk.smartdoc.Model.Answer;
import com.silk.smartdoc.View.MedicineResult;
import com.silk.smartdoc.View.MedicineSearch;
import com.silk.smartdoc.View.TestSearchActivity;
import com.silk.smartdoc.View.TestSearchResultsActivity;

/**
 * Created by dodobhoot on 4/22/2017.
 */
public class DisplayManager{
    private SmartDocManager sdm;
    public void displayDiagnosticCenter(){}

    public void postQuery(){}
    public void displayAnswerQuery(){}
    public void displayAnswer(Answer answer[]){}
    ///new pages
    public void displayMedicineSerachPage(Context context)
    {
        //Intent intents = new Intent(context,MedicineSearch.class);
        context.startActivity(new Intent(context,MedicineSearch.class));
    }
    public void displayMedicineResult(Context context, String value){
        context.startActivity(new Intent(context,MedicineResult.class).putExtra("searchValue", value));
    }

    public void displayTestSearchPage(Context context)
    {
        context.startActivity(new Intent(context, TestSearchActivity.class));
    }

    public void displayTestResult(Context context,String value)
    {
        context.startActivity(new Intent(context, TestSearchResultsActivity.class).putExtra("searchkey",value));
    }
}
