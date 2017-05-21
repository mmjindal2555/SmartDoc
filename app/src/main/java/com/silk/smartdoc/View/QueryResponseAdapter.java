package com.silk.smartdoc.View;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Statement;
import com.silk.smartdoc.R;

import java.util.List;

/**
 * Created by AKASH AGARWAL on 5/21/2017.
 */
public class QueryResponseAdapter extends BaseAdapter {
    List<Statement> mObjects;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;
    public QueryResponseAdapter(List<Statement> objects, Context context){
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
    public Statement getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.query_card, null);
            holder = new ViewHolder();
            holder.usernmae =(TextView)convertView.findViewById(R.id.usernameTextView);
            holder.question = (TextView)convertView.findViewById(R.id.queryTextView);
            //holder.numberOfAnswers = (TextView)convertView.findViewById(R.id.numberOfAnswers);
            convertView.setTag(holder);
            //convertView.setLongClickable(true);

        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        Statement o = mObjects.get(position);
        String user = o.getUser_id();
        String ques = o.getStatement();

        holder.usernmae.setText(user);
        holder.question.setText(ques);
        /*
        if(o.getAnswer()==null)
            holder.numberOfAnswers.setText("0");
        else
            holder.numberOfAnswers.setText(o.getAnswer().size()+"");
            */


        return convertView;
    }

    public static class ViewHolder{

        TextView usernmae;
        TextView question;
    }

    public Context getmContext(){
        return this.mContext;
    }
}
