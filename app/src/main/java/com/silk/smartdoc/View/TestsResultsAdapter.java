package com.silk.smartdoc.View;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silk.smartdoc.Model.CentreAndPrice;
import com.silk.smartdoc.Model.DiagnosticCenter;
import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;

import java.util.List;

/**
 * Created by Manish on 4/30/2017.
 */

public class TestsResultsAdapter extends BaseAdapter {

    List<DiagnosticCenter> mObjects;
    private Context mContext;
    List<CentreAndPrice> centreAndPrices;

    TestsResultsAdapter(List<DiagnosticCenter> objects, List<CentreAndPrice> centreAndPrices, Context context){
        //super(context,R.layout.contents_layout,objects);
        this.mObjects = objects;
        mContext = context;
        this.centreAndPrices = centreAndPrices;
    }
    @Override
    public int getCount() {
        return this.mObjects.size();
    }
    @Override
    public DiagnosticCenter getItem(int position) {
        return this.mObjects.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TestsResultsAdapter.ViewHolder holder;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.test_center_card, null);
            holder = new ViewHolder();
            holder.price =(TextView)convertView.findViewById(R.id.testPrice);
            holder.certifications = (TextView)convertView.findViewById(R.id.certifications);
            holder.centreName = (TextView)convertView.findViewById(R.id.medicineName);
            holder.url = (TextView)convertView.findViewById(R.id.urlTV);
            holder.address = (TextView)convertView.findViewById(R.id.addressTV);
            convertView.setTag(holder);
            //convertView.setLongClickable(true);

        }
        else{
            holder=(TestsResultsAdapter.ViewHolder)convertView.getTag();
        }
        DiagnosticCenter object = mObjects.get(position);


        holder.centreName.setText(object.getName());
        //holder.price.setText("\u20B9"+object.getPrice());
        holder.certifications.setText(object.getCertification());
        holder.url.setText(object.getUrl());
        holder.address.setText(object.getLocation());

        return convertView;
    }

    public static class ViewHolder{

        TextView centreName;
        TextView certifications;
        TextView price;
        TextView address;
        TextView url;
    }
}
