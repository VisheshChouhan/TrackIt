package com.hfad.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;



import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    Student student;
    FirebaseFirestore db;
    ArrayList<ChatMessage> tempChat;
    ChatAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Department Group");
        setSupportActionBar(toolbar);

        //catching passed extras
        student = (Student) getIntent().getSerializableExtra("StudentClass");
        db = FirebaseFirestore.getInstance();



        tempChat = new ArrayList<>();
        adapter = new ChatAdapter(student, tempChat);

        //Getting data out from firestore

        db.collection(student.getBranch()+"/a/posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                tempChat.clear();
                for(QueryDocumentSnapshot doc: value)
                {
                    tempChat.add(doc.toObject(ChatMessage.class));
                    Log.d("value", doc.toObject(ChatMessage.class).getMessage());



                }
                tempChat.sort(new Comparator<ChatMessage>() {
                    @Override
                    public int compare(ChatMessage o1, ChatMessage o2) {
                        return o1.getTimeStamp().compareTo(o2.getTimeStamp());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });


        //tempChat.add(new ChatMessage("hi","me", new Date()));
        //tempChat.add(new ChatMessage("hello","me", new Date()));
        //tempChat.add(new ChatMessage("namaste","me", new Date()));




        RecyclerView chatRecycler = (RecyclerView) findViewById(R.id.list_chat);


        chatRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(ChatActivity.this, 1);
        chatRecycler.setLayoutManager(layoutManager);




    }

    public void sendMessage(View view)
    {
        EditText messageEditText = (EditText) findViewById(R.id.edittext_chat);
        String message = messageEditText.getText().toString().trim();
        if(!message.isEmpty())
        {
            messageEditText.setText(null);

            RecyclerView chats = (RecyclerView) findViewById(R.id.list_chat);



            ChatMessage sentMessage = new ChatMessage(message, student.getName(), new Date());
            tempChat.add(sentMessage);
            //adapter.notifyDataSetChanged();

            db.collection(student.getBranch()).document("a").collection("posts").add(sentMessage);

        }


    }




}