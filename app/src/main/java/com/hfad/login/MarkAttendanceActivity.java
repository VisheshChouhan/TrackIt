package com.hfad.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MarkAttendanceActivity extends AppCompatActivity {

    Student student;
    String currentAttendance, totalNumberOfClasses;
    String enterStageTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*String course = "Operating Systems";
        DatabaseReference courseReference = FirebaseDatabase.getInstance().getReference("Courses");
        Query checkUserDatabase = courseReference.orderByChild("course").equalTo(course);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalNumberOfClasses = snapshot.child(course).child("totalClassesTaken").getValue(String.class);
                enterStageTwo = snapshot.child(course).child("enterStageTwo").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String userAttendance = getIntent().getStringExtra("userAttendance");


        TextView textView = findViewById(R.id.Attendance_progress);
        String text = "You have attended "+userAttendance+" classes till now.";
        //textView.setText(text);*/

        student = (Student) getIntent().getSerializableExtra("StudentClass");



        MarkAttendanceFragment frag = new MarkAttendanceFragment();

        frag.setArguments(getIntent().getExtras());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.markAttendanceHolder,frag);
        transaction.commit();



    }

    public void EnterStageTwo(View view)
    {


        String UserName =  getIntent().getStringExtra("UserName").trim();
        /*#*******************************************************************************************#*/
        //Code to retrieve the current attendance of student;

        //DatabaseReference studentReference = FirebaseDatabase.getInstance().getReference("users");


        /*#*******************************************************************************************#*/

        /*#*******************************************************************************************#*/
        //Code to retrieve the total classes of the subject;



        /*#*******************************************************************************************#*/



        String userAttendance = getIntent().getStringExtra("userAttendance");
        Intent intent = new Intent(MarkAttendanceActivity.this, StageTwoActivity.class);
        intent.putExtra("UserName",UserName);
        intent.putExtra("currAttendance",currentAttendance);
        intent.putExtra("totalClasses",totalNumberOfClasses);
        intent.putExtra("userAttendance",userAttendance);
        if(enterStageTwo.equals("1"))
        {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(MarkAttendanceActivity.this, "Attendance has not started yet.", Toast.LENGTH_SHORT).show();
        }

    }
}