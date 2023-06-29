package com.hfad.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<ChatMessage> chatMessages;
    Student student;

    public ChatAdapter(Student student, ArrayList<ChatMessage> chatMessages)
    {
        this.student = student;
        this.chatMessages = chatMessages;

    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        FrameLayout frameLayout;

        public ViewHolder(FrameLayout v) {
            super(v);
            frameLayout = v;
        }
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout cv = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        FrameLayout frameLayout = holder.frameLayout;
        CardView cardView = frameLayout.findViewById(R.id.receivedCardView);
        cardView.getBackground().setAlpha(0);


        TextView ReceivedMessageView = (TextView) frameLayout.findViewById(R.id.textview_chat_receieved);
        TextView SentMessageView = (TextView) frameLayout.findViewById(R.id.textview_chat_sent);
        TextView userNameView = (TextView) frameLayout.findViewById(R.id.userName);
        View divider = (View) frameLayout.findViewById(R.id.divider);


        //Make text selectable


        if(student.getName().equals(chatMessage.getUser()))
        {
            SentMessageView.setText(chatMessage.getMessage());
            /*SentMessageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SentMessageView.setBackgroundColor(R.color.yellow2);
                    return true;
                }
            });*/

            ReceivedMessageView.setVisibility(View.GONE);
            userNameView.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);

        }
        else
        {

            ReceivedMessageView.setText(chatMessage.getMessage());
            userNameView.setText(chatMessage.getUser());
            SentMessageView.setVisibility(View.GONE);

        }






    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


}
