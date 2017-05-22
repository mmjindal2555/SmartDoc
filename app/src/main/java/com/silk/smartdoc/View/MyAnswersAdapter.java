package com.silk.smartdoc.View;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Statement;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKASH AGARWAL on 5/21/2017.
 */
public class MyAnswersAdapter extends BaseAdapter {
    List<Query> mObjects;
    String user_id;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;
    public MyAnswersAdapter(List<Query> objects,String user_id, Context context){
        //super(context,R.layout.contents_layout,objects);
        this.mObjects = objects;
        this.user_id = user_id;
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
    }
    @Override
    public int getCount() {
        return this.mObjects.size();
    }
    @Override
    public Query getItem(int position) {
        return this.mObjects.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //String question =
        ViewHolder holder;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.myanswers_card, null);
            holder = new ViewHolder();
            holder.userTV =(TextView)convertView.findViewById(R.id.userTextView);
            holder.queryTV = (TextView)convertView.findViewById(R.id.queryTextView);
            holder.answerTV = (TextView)convertView.findViewById(R.id.answerTextView);
            convertView.setTag(holder);

        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        Query o = mObjects.get(position);
        String user = o.getQuestion().getUser_id();
        String ques = o.getQuestion().getStatement();
        String answer = "No answers available";
        ArrayList<Statement> arrayList_answers = o.getAnswer();
        for(Statement statement : arrayList_answers)
        {
            String temp = statement.getUser_id();
            if(temp.equals(user_id))
                answer = statement.getStatement();
        }

        holder.userTV.setText(user);
        holder.queryTV.setText(ques);
        holder.answerTV.setText(answer);

        return convertView;
    }

    public static class ViewHolder{

        TextView userTV;
        TextView queryTV;
        TextView answerTV;
    }

    public Context getmContext(){
        return this.mContext;
    }
}
