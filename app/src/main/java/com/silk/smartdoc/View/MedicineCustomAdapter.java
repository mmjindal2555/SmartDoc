package com.silk.smartdoc.View;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.List;


public class MedicineCustomAdapter extends ArrayAdapter<Medicine> {

    public MedicineCustomAdapter(Context context, ArrayList<Medicine> resource) {
        super(context, R.layout.medicine_card,resource);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v ;
        LayoutInflater vi= LayoutInflater.from(getContext());
        v=vi.inflate(R.layout.medicine_card,parent,false);

        TextView MedicineNameView = (TextView) v.findViewById(R.id.genericName);
        TextView priceView = (TextView) v.findViewById(R.id.medicinePrice);
        TextView MedicineDescriptionView = (TextView) v.findViewById(R.id.manufacturer);

        Medicine msg=getItem(position);
        MedicineNameView.setText("Medicine Name: "+msg.medicineName);
        priceView.setText("Price: " + msg.price);
        MedicineDescriptionView.setText("Chemical Name: "+msg.chemicalName);

        return v;

    }
}

