package com.silk.smartdoc.View;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.Person;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Statement;
import com.silk.smartdoc.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.silk.smartdoc.R.id.downVoteTextView;
import static com.silk.smartdoc.R.id.upVoteTextView;

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
            holder.usernmae =(TextView)convertView.findViewById(R.id.usernameTextView);
            holder.question = (TextView)convertView.findViewById(R.id.queryTextView);
            holder.numberOfAnswers = (TextView)convertView.findViewById(R.id.numberOfAnswers);
            convertView.setTag(holder);

            //convertView.setLongClickable(true);



        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }


        Query o = mObjects.get(position);
        String user = o.getQuestion().getUser_id();
        String ques = o.getQuestion().getStatement();

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

    }

    public Context getmContext(){
        return this.mContext;
    }

}
