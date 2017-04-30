package com.silk.smartdoc.View;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silk.smartdoc.Model.Medicine;
import com.silk.smartdoc.R;

import java.util.List;

/**
 * Created by Manish on 4/26/2017.
 */
public class MedicineResultsAdapter extends BaseAdapter {
    List<Medicine> mObjects;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;
    MedicineResultsAdapter(List<Medicine> objects, Context context){
        //super(context,R.layout.contents_layout,objects);
        this.mObjects = objects;
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
    }
    @Override
    public int getCount() {
        return this.mObjects.size();
    }
    @Override
    public Medicine getItem(int position) {
        return this.mObjects.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.medicine_card, null);
            holder = new ViewHolder();
            holder.price =(TextView)convertView.findViewById(R.id.testPrice);
            holder.manufacturer = (TextView)convertView.findViewById(R.id.certifications);
            holder.medicineName = (TextView)convertView.findViewById(R.id.medicineName);
            convertView.setTag(holder);
            //convertView.setLongClickable(true);

        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        Medicine object = mObjects.get(position);


        holder.medicineName.setText(object.getName());
        holder.price.setText("\u20B9"+object.getPrice());
        holder.manufacturer.setText(object.getChemicalName());


        return convertView;
    }

    public static class ViewHolder{

        TextView medicineName;
        TextView manufacturer;
        TextView price;
    }
}
