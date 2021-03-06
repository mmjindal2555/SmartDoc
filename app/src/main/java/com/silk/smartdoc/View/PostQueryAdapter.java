package com.silk.smartdoc.View;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.silk.smartdoc.R.id.downVoteTextView;
import static com.silk.smartdoc.R.id.upVoteTextView;


public class PostQueryAdapter extends BaseAdapter {
    List<Query> mObjects;
    private Context mContext;
    Person person;
    private SparseBooleanArray mSelectedItemsIds;
    DatabaseReference reference;
    HashSet tagsAdded;
    public PostQueryAdapter(List<Query> objects, Context context,Person person){
        //super(context,R.layout.contents_layout,objects);
        this.mObjects = objects;
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
        this.person=person;
        this.reference = FirebaseDatabase.getInstance().getReference();
        tagsAdded = new HashSet();
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
        final ViewHolder holder;
        Query o = mObjects.get(position);
        final String user = o.getQuestion().getUser_id();
        String ques = o.getQuestion().getStatement();
        ArrayList<String> tags = o.getTags();
        HashSet<String> uniqueTags = new HashSet<String>(tags);
        tags = new ArrayList<>(uniqueTags);

        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.query_card, null);
            holder = new ViewHolder();
            holder.usernmae =(TextView)convertView.findViewById(R.id.usernameTextView);
            holder.question = (TextView)convertView.findViewById(R.id.queryTextView);
            holder.numberOfAnswers = (TextView)convertView.findViewById(R.id.numberOfAnswers);
            holder.profilePic = (ImageView)convertView.findViewById(R.id.profilePicImageView);
            holder.tagsLayout = (FlowLayout) convertView.findViewById(R.id.tagsRelativeLayout);
            holder.transparentLayer = (RelativeLayout)convertView.findViewById(R.id.transparent_layer_question);
            holder.tagsTVs = new ArrayList<>();

            convertView.setTag(holder);
            //convertView.setLongClickable(true);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }



        //Random rnd = new Random();
            holder.tagsLayout.removeAllViews();
            int prevTagTextViewId = 0;
            for (int i = 0; i < tags.size(); i++) {

                TextView dynamicTextView = new TextView(mContext);
                //dynamicTextView.setTextColor(rnd.nextInt() | 0xff000000);
                dynamicTextView.setTextColor(0xff2f9d41);
                dynamicTextView.setText("  " + tags.get(i) + "  ");
                dynamicTextView.setBackgroundResource(R.drawable.tags_background);
                int curTagTextViewId = prevTagTextViewId + 1;
                dynamicTextView.setId(curTagTextViewId);
                holder.tagsTVs.add(dynamicTextView);
                holder.tagsLayout.addView(dynamicTextView, 0);
                prevTagTextViewId = curTagTextViewId;
            }

        
        reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.usernmae.setText(dataSnapshot.child(user).child("name").getValue(String.class));

                String picUrl = dataSnapshot.child(user).child("gravatarUrl").getValue(String.class);
                if(picUrl!=null) {
                    picUrl = picUrl.substring(0,picUrl.length()-3)+"retro";
                    Picasso.with(mContext)
                            .load(picUrl)
                            .placeholder(R.drawable.ic_user)
                            .into(holder.profilePic);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(o.getQuestion().getdownVotes()!=null && o.getQuestion().getdownVotes().size()>5){
            holder.transparentLayer.setVisibility(View.VISIBLE);
        }
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
        FlowLayout tagsLayout;
        RelativeLayout transparentLayer;
        ImageView profilePic;
        ArrayList<TextView> tagsTVs;
    }
    public Context getmContext(){
        return this.mContext;
    }

}
