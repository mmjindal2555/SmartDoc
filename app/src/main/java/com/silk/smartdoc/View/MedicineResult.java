package com.silk.smartdoc.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;


public class MedicineResult extends AppCompatActivity {

    ListView msgList;
    AdapterView.AdapterContextMenuInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_result);

        Bundle searchData = getIntent().getExtras();
        final String searchValue = searchData.getString("searchValue");

        msgList = (ListView) findViewById(R.id.MedicineDetails);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("medicine");
        final FirebaseListAdapter<Medicine> firebaseListAdapter = new FirebaseListAdapter<Medicine>(
                MedicineResult.this,
                Medicine.class,
                R.layout.medicine_card,
                databaseReference
        ) {
            TextView genericName;
            TextView manufacturer;
            TextView medicinePrice;
            @Override
            protected void populateView(View v, Medicine model, int position) {
                if(model.chemicalName.equalsIgnoreCase(searchValue)||model.medicineName.equalsIgnoreCase(searchValue)) {
                    genericName = (TextView) v.findViewById(R.id.genericName);
                    manufacturer = (TextView) v.findViewById(R.id.manufacturer);
                    medicinePrice = (TextView) v.findViewById(R.id.medicinePrice);

                    genericName.setText(model.chemicalName);
                    manufacturer.setText(model.medicineName);
                    medicinePrice.setText(model.price);
                }
            }
        };

        msgList.setAdapter(firebaseListAdapter);
    }
}
