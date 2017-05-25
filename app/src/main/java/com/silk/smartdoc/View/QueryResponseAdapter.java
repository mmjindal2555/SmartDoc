package com.silk.smartdoc.View;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.Statement;
import com.silk.smartdoc.R;
import com.silk.smartdoc.Model.Person;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKASH AGARWAL on 5/21/2017.
 */
public class QueryResponseAdapter extends BaseAdapter {
    List<Statement> mObjects;
    private Context mContext;
    private SparseBooleanArray mSelectedItemsIds;
    int upCount=0,downCount=0;
    Person person;
    public QueryResponseAdapter(List<Statement> objects, Context context,Person person){
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
    public Statement getItem(int position) {
        return this.mObjects.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    ArrayList<String> downVotesUserId,upVotesUserId;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //String question =
        final ViewHolder holder;


        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.answers_card, null);
            holder = new ViewHolder();
            holder.usernmae =(TextView)convertView.findViewById(R.id.userTextView);
            holder.question = (TextView)convertView.findViewById(R.id.queryTextView);
            //holder.numberOfAnswers = (TextView)convertView.findViewById(R.id.numberOfAnswers);
            convertView.setTag(holder);
            holder.upButton=(ImageView) convertView.findViewById(R.id.thumbsUpImageView1);
            holder.downButton=(ImageView) convertView.findViewById(R.id.thumbsDownImageView2);
            holder.upVoteTextView=(TextView) convertView.findViewById(R.id.upVoteTextView1);
            holder.downVoteTextView=(TextView) convertView.findViewById(R.id.downVoteTextView1);
            holder.profilePic = (ImageView)convertView.findViewById(R.id.profilePicImageView);
            holder.transparentLayer = (RelativeLayout)convertView.findViewById(R.id.transparent_layer_answer);
            final TextView up=holder.upVoteTextView;
            final TextView down=holder.downVoteTextView;
            //convertView.setLongClickable(true);
            final Statement ans=mObjects.get(position);
            db.child("Statement").child(ans.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    downCount=0;
                    if (dataSnapshot.hasChild("downVotes")) {

                        for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                            if(child.getValue(String.class).equals(person.getId()))
                                holder.downButton.setImageResource(R.drawable.ic_keyboard_arrow_down_blue);
                            downCount+=1;
                        }
                    }
                    upCount=0;
                    if(dataSnapshot.hasChild("upVotes")) {

                        for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                            if(child.getValue(String.class).equals(person.getId()))
                                holder.upButton.setImageResource(R.drawable.ic_keyboard_arrow_up_blue);
                            upCount+=1;
                        }
                    }
                    up.setText(upCount+"");
                    down.setText(downCount+"");

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            holder.upButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    downVotesUserId=new ArrayList<String>();
                    upVotesUserId = new ArrayList<String>();
                    db.child("Statement").child(ans.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("downVotes")) {
                                for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                                    downVotesUserId.add(child.getValue(String.class));
                                }
                                for(int ii=0;ii<downVotesUserId.size();ii++)
                                {
                                    if(downVotesUserId.get(ii).equalsIgnoreCase(person.getId()))
                                    {
                                        downVotesUserId.remove(person.getId());
                                        holder.downButton.setImageResource(R.drawable.ic_keyboard_arrow_down);
                                        break;
                                    }
                                }

                            }
                            if(dataSnapshot.hasChild("upVotes")) {
                                for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                                    upVotesUserId.add(child.getValue(String.class));
                                }
                            }
                            boolean wasAlreadyPressed = false;
                            for(int ii=0;ii<upVotesUserId.size();ii++)
                            {
                                if(upVotesUserId.get(ii).equalsIgnoreCase(person.getId()))
                                {
                                    upVotesUserId.remove(person.getId());
                                    holder.upButton.setImageResource(R.drawable.ic_keyboard_arrow_up);
                                    wasAlreadyPressed=true;
                                    break;
                                }
                            }
                            if(!wasAlreadyPressed){
                                holder.upButton.setImageResource(R.drawable.ic_keyboard_arrow_up_blue);
                                upVotesUserId.add(person.getId());
                            }
                            up.setText(upVotesUserId.size()+"");
                            down.setText(downVotesUserId.size()+"");
                            Statement statement = new Statement(ans.getUser_id(),ans.getId(),ans.getStatement(), ans.getTimestamp(),upVotesUserId
                                    ,downVotesUserId);

                            db.child("Statement").child(ans.getId()).setValue(statement);

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });

            holder.downButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downVotesUserId=new ArrayList<String>();
                    upVotesUserId = new ArrayList<String>();
                    db.child("Statement").child(ans.getId()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("upVotes")) {
                                for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                                    upVotesUserId.add(child.getValue(String.class));
                                }
                                for(int ii=0;ii<upVotesUserId.size();ii++)
                                {
                                    if(upVotesUserId.get(ii).equalsIgnoreCase(person.getId()))
                                    {
                                        holder.upButton.setImageResource(R.drawable.ic_keyboard_arrow_up);
                                        upVotesUserId.remove(person.getId());
                                        break;
                                    }
                                }

                            }
                            if(dataSnapshot.hasChild("downVotes")) {
                                for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                                    downVotesUserId.add(child.getValue(String.class));
                                }
                            }
                            boolean wasAlreadyPressed = false;
                            for(int ii=0;ii<downVotesUserId.size();ii++)
                            {
                                if(downVotesUserId.get(ii).equalsIgnoreCase(person.getId()))
                                {
                                    holder.downButton.setImageResource(R.drawable.ic_keyboard_arrow_down);
                                    downVotesUserId.remove(person.getId());
                                    wasAlreadyPressed=true;
                                    break;
                                }
                            }
                            if(!wasAlreadyPressed){
                                holder.downButton.setImageResource(R.drawable.ic_keyboard_arrow_down_blue);
                                downVotesUserId.add(person.getId());
                            }
                            up.setText(upVotesUserId.size()+"");
                            down.setText(downVotesUserId.size()+"");
                            Statement statement = new Statement(ans.getUser_id(),ans.getId(),ans.getStatement(), ans.getTimestamp(),upVotesUserId
                                    ,downVotesUserId);

                            db.child("Statement").child(ans.getId()).setValue(statement);

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });


        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        Statement o = mObjects.get(position);
        final String user = o.getUser_id();
        String ques = o.getStatement();
        if(o.getdownVotes()!=null && o.getdownVotes().size()>5){
            holder.transparentLayer.setVisibility(View.INVISIBLE);
        }
        db.child("Users").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                holder.usernmae.setText(dataSnapshot.child("name").getValue(String.class));
                String picUrl = dataSnapshot.child("gravatarUrl").getValue(String.class);
                if(picUrl!=null) {
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
        holder.question.setText(ques);

        return convertView;
    }

    public static class ViewHolder{

        TextView usernmae;
        TextView question;
        ImageView upButton;
        ImageView downButton;
        TextView upVoteTextView;
        TextView downVoteTextView;
        ImageView profilePic;
        RelativeLayout transparentLayer;
    }

    public Context getmContext(){
        return this.mContext;
    }
}
