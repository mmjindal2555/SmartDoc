package com.silk.smartdoc.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Controller.SmartDocManager;
import com.silk.smartdoc.Model.Chemical;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.List;


public class MedicineResult extends AppCompatActivity {

    ArrayList <Medicine> medResultArrayList;
    ArrayList <Chemical> chemResultArrayList;
    ListView medicinesList;
    TextView description;
    ImageView genericIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_result);
        medicinesList = (ListView)findViewById(R.id.medicine_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.medicine_result_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.primarycolor));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarcolor));


        Bundle searchData = getIntent().getExtras();

        final String searchValue = searchData.getString("searchValue");
        description = (TextView) findViewById(R.id.descriptionTextView);
        genericIcon = (ImageView) findViewById(R.id.genericIconImageView);

        SmartDocManager sdm = (SmartDocManager) getApplicationContext();
        medResultArrayList = sdm.searchMgr.searchMedicine(searchValue);

        if(medResultArrayList.size()==0 || medResultArrayList==null){
            chemResultArrayList = sdm.searchMgr.searchChemical(searchValue);
            for (Chemical chemical : chemResultArrayList){
                if(!chemical.isGeneric()){
                    genericIcon.setImageResource(R.drawable.ic_cancel);
                }
                else
                    genericIcon.setImageResource(R.drawable.ic_check_circle);
                description.setText(chemical.getDescription());
            }
            ArrayList <String> searchValues;
            searchValues = chemResultArrayList.get(0).getMedicineIds();
            ArrayList <Medicine> tempResultArrayList;
            for(String searchTokens:searchValues){
                tempResultArrayList = sdm.searchMgr.searchMedicine(searchTokens);
                medResultArrayList.addAll(0,tempResultArrayList);
            }
        }
        else{
            String chemSearchValue = medResultArrayList.get(0).getChemicalName();
            chemResultArrayList = sdm.searchMgr.searchChemical(chemSearchValue);
            for (Chemical chemical : chemResultArrayList){
                if(!chemical.isGeneric()){
                    genericIcon.setImageResource(R.drawable.ic_cancel);
                }
                else
                    genericIcon.setImageResource(R.drawable.ic_check_circle);
                description.setText(chemical.getDescription());
            }
        }
        toolbar.setTitle(searchValue);
        setSupportActionBar(toolbar);



        medicinesList.setAdapter(new MedicineResultsAdapter(medResultArrayList,MedicineResult.this));

    }
}