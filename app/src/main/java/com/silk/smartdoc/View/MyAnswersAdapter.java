package com.silk.smartdoc.View;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.silk.smartdoc.Model.Query;
import com.silk.smartdoc.Model.Statement;
import com.silk.smartdoc.R;
import com.squareup.picasso.Picasso;

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
        TextView upVoteTextView,downVoteTextView;
        int upCount=0;
        int downCount=0;
        ImageView upVotesImage,userPicImageView;
        ImageView downVotesImage;
        TextView numOfAnswers;
        ImageView profilePic;
        ImageView thumbsUpImageView1;
        final ImageView thumbsDownImageView2;
        TextView downVoteTextView1;
        TextView upVoteTextView1;

        final int pos = position;
        final ViewHolder holder;
        Query o = mObjects.get(pos);
        final String user = o.getQuestion().getUser_id();
        String ques = o.getQuestion().getStatement();
        String answer = "No answers available";
        ArrayList<Statement> arrayList_answers = o.getAnswer();
        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.myanswers_card, null);
            holder = new ViewHolder();
            holder.userTV =(TextView)convertView.findViewById(R.id.userTextView);
            holder.queryTV = (TextView)convertView.findViewById(R.id.queryTextView);
            holder.answerTV = (TextView)convertView.findViewById(R.id.answerTextView);
            holder.upVotesImage = (ImageView)convertView.findViewById(R.id.thumbsUpImageView);
            holder.downVotesImage = (ImageView)convertView.findViewById(R.id.thumbsDownImageView);
            holder.upVoteTextView = (TextView)convertView.findViewById(R.id.upVoteTextView2);
            holder.downVoteTextView = (TextView)convertView.findViewById(R.id.downVoteTextView2);
            holder.numOfAnswers = (TextView)convertView.findViewById(R.id.numberOfAnswers);
            holder.profilePic = (ImageView)convertView.findViewById(R.id.profilePicImageView);
            holder.userPicImageView = (ImageView)convertView.findViewById(R.id.userPicImageView);

            holder.thumbsUpImageView1 = (ImageView)convertView.findViewById(R.id.thumbsUpImageView1);
            holder.thumbsDownImageView2 = (ImageView)convertView.findViewById(R.id.thumbsDownImageView2);
            holder.downVoteTextView1 =(TextView)convertView.findViewById(R.id.downVoteTextView1);
            holder.upVoteTextView1 =(TextView)convertView.findViewById(R.id.upVoteTextView1);


            //holder.transparentLayer = (RelativeLayout)convertView.findViewById(R.id.transparent_layer_answer);
            convertView.setTag(holder);

        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String picUrl = dataSnapshot.child(user).
                        child("gravatarUrl").getValue(String.class);
                if(picUrl!=null) {
                    Picasso.with(mContext)
                            .load(picUrl)
                            .into(holder.profilePic);
                }

                String msgPicUrl = dataSnapshot.child(user_id).child("gravatarUrl")
                        .getValue(String.class);
                if(msgPicUrl!=null) {
                    Picasso.with(mContext)
                            .load(msgPicUrl)
                            .into(holder.userPicImageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Statement");
        String q_id = o.getId();
        ArrayList<Statement> ans=o.getAnswer();
        final Statement ques2=o.getQuestion();
        final String question=ques2.getStatement();
        final String ques_id=ques2.getId();

        ArrayList<Statement> statements = o.getAnswer();


        db.child(ques_id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int upCount=0;
                int downCount=0;
                if (dataSnapshot.hasChild("downVotes")) {
                    downCount=0;
                    for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                        downCount+=1;
                    }
                }
                if(dataSnapshot.hasChild("upVotes")) {
                    upCount=0;
                    for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                        upCount+=1;
                    }
                }
                holder.upVoteTextView.setText(upCount+"");
                holder.downVoteTextView.setText(downCount+"");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("Query").child(q_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("answer")){
                    holder.numOfAnswers.setText(dataSnapshot.child("answer").getChildrenCount()+"");
                }
                else{
                    holder.numOfAnswers.setText("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });













        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            String name;
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                    name = dataSnapshot.child("name").getValue(String.class);
                    holder.userTV.setText(name);
             }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        Statement answer_Statement = null;

        for(Statement statement : arrayList_answers)
        {
            String temp = statement.getUser_id();
            if(temp.equals(user_id)) {
                answer = statement.getStatement();
                answer_Statement = statement;
            }
        }
        final Statement ans_Statement = answer_Statement;
        final String ans1 = answer;

        final DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child("Statement");
        final String answer_id= answer_Statement.getId();
        db2.child(answer_id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int upCount=0;
                int downCount=0;
                if (dataSnapshot.hasChild("downVotes")) {
                    downCount=0;
                    for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                        downCount+=1;
                    }
                }
                if(dataSnapshot.hasChild("upVotes")) {
                    upCount=0;
                    for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                        upCount+=1;
                    }
                }
                holder.upVoteTextView1.setText(upCount+"");
                holder.downVoteTextView1.setText(downCount+"");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        holder.thumbsUpImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> downVotesUserId,upVotesUserId;
                downVotesUserId = new ArrayList<String>();
                upVotesUserId = new ArrayList<String>();
                db.child(answer_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("downVotes").getChildrenCount()>0 ) {
                            for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                                downVotesUserId.add(child.getValue(String.class));
                            }
                            for(int ii=0;ii<downVotesUserId.size();ii++)
                            {
                                if(downVotesUserId.get(ii).equalsIgnoreCase(user_id))
                                {

                                    downVotesUserId.remove(user_id);
                                    holder.thumbsDownImageView2.setImageResource(R.drawable.ic_keyboard_arrow_down);
                                    break;
                                }
                            }

                        }
                        if(dataSnapshot.child("upVotes").getChildrenCount()>0) {
                            for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                                upVotesUserId.add(child.getValue(String.class));
                            }
                        }
                        boolean wasAlreadyPressed = false;
                        for(int ii=0;ii<upVotesUserId.size();ii++)
                        {
                            if(user_id!=null && upVotesUserId.get(ii).equalsIgnoreCase(user_id))
                            {
                                upVotesUserId.remove(user_id);
                                holder.thumbsUpImageView1.setImageResource(R.drawable.ic_keyboard_arrow_up);
                                wasAlreadyPressed=true;
                                break;
                            }
                        }
                        if(!wasAlreadyPressed){
                            holder.thumbsUpImageView1.setImageResource(R.drawable.ic_keyboard_arrow_up_blue);
                            upVotesUserId.add(user_id);
                        }
                        holder.upVoteTextView1.setText(upVotesUserId.size()+"");
                        holder.downVoteTextView1.setText(downVotesUserId.size()+"");
                        Statement statement = new Statement(ans_Statement.getUser_id(),answer_id,ans1, ans_Statement.getTimestamp(),upVotesUserId
                                ,downVotesUserId);

                        db2.child(answer_id).setValue(statement);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });


        holder.thumbsDownImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> downVotesUserId2 = new ArrayList<String>();
                final ArrayList<String> upVotesUserId2 = new ArrayList<String>();
                db2.child(answer_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("upVotes")) {
                            for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                                upVotesUserId2.add(child.getValue(String.class));
                            }
                            for(int ii=0;ii<upVotesUserId2.size();ii++)
                            {
                                if(user_id!=null && upVotesUserId2.get(ii).equalsIgnoreCase(user_id))
                                {
                                    holder.thumbsUpImageView1.setImageResource(R.drawable.ic_keyboard_arrow_up);
                                    upVotesUserId2.remove(user_id);
                                    break;
                                }
                            }

                        }
                        if(dataSnapshot.hasChild("downVotes")) {
                            for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                                downVotesUserId2.add(child.getValue(String.class));
                            }
                        }
                        boolean wasAlreadyPressed = false;
                        for(int ii=0;ii<downVotesUserId2.size();ii++)
                        {
                            if(user_id!=null && downVotesUserId2.get(ii).equalsIgnoreCase(user_id))
                            {
                                downVotesUserId2.remove(user_id);
                                holder.thumbsDownImageView2.setImageResource(R.drawable.ic_keyboard_arrow_down);
                                wasAlreadyPressed=true;
                                break;
                            }
                        }
                        if(!wasAlreadyPressed){
                            holder.thumbsDownImageView2.setImageResource(R.drawable.ic_keyboard_arrow_down_blue);
                            downVotesUserId2.add(user_id);
                        }
                        Statement statement = new Statement(ans_Statement.getUser_id(),answer_id,ans1, ans_Statement.getTimestamp(),upVotesUserId2
                                ,downVotesUserId2);
                        holder.upVoteTextView1.setText(upVotesUserId2.size()+"");
                        holder.downVoteTextView1.setText(downVotesUserId2.size()+"");
                        db2.child(answer_id).setValue(statement);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });









        holder.upVotesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> downVotesUserId,upVotesUserId;
                downVotesUserId = new ArrayList<String>();
                upVotesUserId = new ArrayList<String>();
                db.child(ques_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("downVotes").getChildrenCount()>0 ) {
                            for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                                downVotesUserId.add(child.getValue(String.class));
                            }
                            for(int ii=0;ii<downVotesUserId.size();ii++)
                            {
                                if(downVotesUserId.get(ii).equalsIgnoreCase(user_id))
                                {

                                    downVotesUserId.remove(user_id);
                                    holder.downVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_down);
                                    break;
                                }
                            }

                        }
                        if(dataSnapshot.child("upVotes").getChildrenCount()>0) {
                            for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                                upVotesUserId.add(child.getValue(String.class));
                            }
                        }
                        boolean wasAlreadyPressed = false;
                        for(int ii=0;ii<upVotesUserId.size();ii++)
                        {
                            if(user_id!=null && upVotesUserId.get(ii).equalsIgnoreCase(user_id))
                            {
                                upVotesUserId.remove(user_id);
                                holder.upVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_up);
                                wasAlreadyPressed=true;
                                break;
                            }
                        }
                        if(!wasAlreadyPressed){
                            holder.upVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_up_blue);
                            upVotesUserId.add(user_id);
                        }
                        holder.upVoteTextView.setText(upVotesUserId.size()+"");
                        holder.downVoteTextView.setText(downVotesUserId.size()+"");
                        Statement statement = new Statement(ques2.getUser_id(),ques_id,question, ques2.getTimestamp(),upVotesUserId
                                ,downVotesUserId);

                        db.child(ques_id).setValue(statement);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });


        holder.downVotesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> downVotesUserId,upVotesUserId;
                final ArrayList<String> downVotesUserId2 = new ArrayList<String>();
                final ArrayList<String> upVotesUserId2 = new ArrayList<String>();
                db.child(ques_id).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("upVotes")) {
                            for (DataSnapshot child : dataSnapshot.child("upVotes").getChildren()) {
                                upVotesUserId2.add(child.getValue(String.class));
                            }
                            for(int ii=0;ii<upVotesUserId2.size();ii++)
                            {
                                if(user_id!=null && upVotesUserId2.get(ii).equalsIgnoreCase(user_id))
                                {
                                    holder.upVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_up);
                                    upVotesUserId2.remove(user_id);
                                    break;
                                }
                            }

                        }
                        if(dataSnapshot.hasChild("downVotes")) {
                            for (DataSnapshot child : dataSnapshot.child("downVotes").getChildren()) {
                                downVotesUserId2.add(child.getValue(String.class));
                            }
                        }
                        boolean wasAlreadyPressed = false;
                        for(int ii=0;ii<downVotesUserId2.size();ii++)
                        {
                            if(user_id!=null && downVotesUserId2.get(ii).equalsIgnoreCase(user_id))
                            {
                                downVotesUserId2.remove(user_id);
                                holder.downVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_down);
                                wasAlreadyPressed=true;
                                break;
                            }
                        }
                        if(!wasAlreadyPressed){
                            holder.downVotesImage.setImageResource(R.drawable.ic_keyboard_arrow_down_blue);
                            downVotesUserId2.add(user_id);
                        }
                        Statement statement = new Statement(ques2.getUser_id(),ques_id,question, ques2.getTimestamp(),upVotesUserId2
                                ,downVotesUserId2);
                        holder.upVoteTextView.setText(upVotesUserId2.size()+"");
                        holder.downVoteTextView.setText(downVotesUserId2.size()+"");
                        db.child(ques_id).setValue(statement);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });












        holder.queryTV.setText(ques);
        holder.answerTV.setText(answer);

        return convertView;
    }

    public static class ViewHolder{

        TextView userTV;
        TextView queryTV;
        TextView answerTV;
        ImageView upVotesImage;
        ImageView downVotesImage;
        ImageView profilePic;
        TextView upVoteTextView;
        TextView numOfAnswers;
        TextView downVoteTextView;
        ImageView userPicImageView;
        RelativeLayout transparentLayer;

        ImageView thumbsUpImageView1;
        ImageView thumbsDownImageView2;
        TextView downVoteTextView1;
        TextView upVoteTextView1;
    }

    public Context getmContext(){
        return this.mContext;
    }
}
