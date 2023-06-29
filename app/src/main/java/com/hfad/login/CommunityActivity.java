package com.hfad.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class CommunityActivity extends AppCompatActivity {

    Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        student = (Student) getIntent().getSerializableExtra("StudentClass");
        Button communityButton = (Button) findViewById(R.id.communityButton);
        communityButton.setText(student.getBranch());
    }

    public void goToChat(View view)
    {
        student = (Student) getIntent().getSerializableExtra("StudentClass");
        Intent intent = new Intent(CommunityActivity.this, ChatActivity.class);
        intent.putExtra("StudentClass",student);
        startActivity(intent);

    }
}