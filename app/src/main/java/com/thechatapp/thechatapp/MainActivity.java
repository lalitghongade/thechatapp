package com.thechatapp.thechatapp;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.thechatapp.thechatapp.R.layout.activity_singlemessagelayout;

public class MainActivity extends AppCompatActivity {

    private EditText editMessage;
    private DatabaseReference mDatabase;
    private RecyclerView mMessageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMessage = (EditText) findViewById(R.id.editmessage);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");
        mMessageList =(RecyclerView) findViewById(R.id.messagerec);
        mMessageList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessageList.setLayoutManager(linearLayoutManager);
    }
    public void sendbuttonclick(View view){
        final String messageValue = editMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(messageValue)){
            final DatabaseReference newPost = mDatabase.push();
            Task<Void> content = newPost.child("content").setValue(messageValue);
        }
    }

        protected void onStart(){
            super.onStart();
            FirebaseRecyclerAdapter <Message,MessageViewHolder> FBRA =new FirebaseRecyclerAdapter<Message,MessageViewHolder>(Message.class,R.layout.activity_singlemessagelayout, MessageViewHolder.class,mDatabase) {
                @Override
                protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
                    viewHolder.setContent(model.getContent());

                }
            };
            mMessageList.setAdapter(FBRA);

    }


    }
    class MessageViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public MessageViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setContent(String content){
            TextView message_content = (TextView) mView.findViewById(R.id.messagetext);
            message_content.setText(content);
        }
    }
}
