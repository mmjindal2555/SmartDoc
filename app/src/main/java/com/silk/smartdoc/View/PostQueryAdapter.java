package com.silk.smartdoc.View;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by AKASH AGARWAL on 5/21/2017.
 */
public class PostQueryAdapter extends BaseAdapter {
    List<Query> mObjects;
    private Context mContext;
    Person person;
    private SparseBooleanArray mSelectedItemsIds;
    public PostQueryAdapter(List<Query> objects, Context context,Person person){
        //super(context,R.layout.contents_layout,objects);
        this.mObjects = objects;
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
        this.person=person;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        //String question =
        ViewHolder holder;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.query_card, null);
            holder = new ViewHolder();
            holder.usernmae =(TextView)convertView.findViewById(R.id.userTextView);
            holder.question = (TextView)convertView.findViewById(R.id.queryTextView);
            holder.numberOfAnswers = (TextView)convertView.findViewById(R.id.numberOfAnswers);
            holder.tags = (TextView)convertView.findViewById(R.id.tagsTextView);
            convertView.setTag(holder);

            //convertView.setLongClickable(true);



        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }


        Query o = mObjects.get(position);
        String user = o.getQuestion().getUser_id();
        String ques = o.getQuestion().getStatement();
        String allTags = "";
        ArrayList<String> tags = o.getTags();
        Set<String> uniqueTags = new HashSet<String>(tags);
        for (int i = 0; i < uniqueTags.size(); i++) {
            allTags +=(tags.get(i));
            allTags +=" ,";
        }
        holder.tags.setText(allTags);
        holder.usernmae.setText(user);
        holder.question.setText(ques);
        if(o.getAnswer()==null)
            holder.numberOfAnswers.setText("0");
        else
            holder.numberOfAnswers.setText(o.getAnswer().size()+"");


        return convertView;
    }

    public static class ViewHolder{

        TextView usernmae;
        TextView question;
        TextView numberOfAnswers;
        TextView tags;

    }

    public Context getmContext(){
        return this.mContext;
    }

}
