package com.hfad.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class homePageActivity extends AppCompatActivity {

    //String UserName =  getIntent().getStringExtra("UserName");
    Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //String UserName =  getIntent().getStringExtra("UserName");
        student = (Student) getIntent().getSerializableExtra("StudentClass");
        String UserName = (String) student.getName();

        TextView usernameView = findViewById(R.id.userName);
        usernameView.setText(UserName);

        TextView attendanceView = findViewById(R.id.Attendance_count);
        attendanceView.setText("34 off 45 days.");


        //retrieving the courses student is enrolled in
        ArrayList<String> codes = new ArrayList<>();
        HashMap<String, String> attendance =  student.getAttendance();
        for(Map.Entry<String, String> e: attendance.entrySet())
        {
            codes.add(e.getKey());

        }

        RecyclerView courseRecyclerView = (RecyclerView) findViewById(R.id.courseRecyclerView);
        ArrayList<Courses> coursesArrayList = new ArrayList<>();
        CourseCardAdapter adapter = new CourseCardAdapter(coursesArrayList);




        //Reading courses data from database
        DatabaseReference courseReference = FirebaseDatabase.getInstance().getReference("Courses");
        courseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(String st: codes)
                {

                    if (snapshot.exists()) {

                        coursesArrayList.add(snapshot.child(st).getValue(Courses.class));
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(homePageActivity.this, 1, LinearLayoutManager.HORIZONTAL,false);
        courseRecyclerView.setLayoutManager(layoutManager);
        courseRecyclerView.setAdapter(adapter);








        //Temporary writing data in firestore
        /*FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<ChatMessage> tempChat = new ArrayList<>();
        tempChat.add(new ChatMessage("hi","me", new Date()));
        tempChat.add(new ChatMessage("hello","me", new Date()));
        tempChat.add(new ChatMessage("namaste","me", new Date()));
        db.collection(student.getBranch()).document("a").collection("posts").document().set(new ChatMessage("hi",student.getName(), new Date()));
        db.collection(student.getBranch()).document("a").collection("posts").add(new ChatMessage("hello", student.getName(), new Date()));
        */









        // temp data feeding
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        String CourseName = "Artificial Intelligence";
        String facultyName = "Miss Himani Mishra";
        String courseId = "CO34298";
        String type = "Elective";
        Courses courses = new Courses(CourseName, facultyName, courseId, type);
        DatabaseReference ref = database.getReference("Courses");
        ref.child(courseId).setValue(courses);*/

       /* FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Scheme");
        String code = "CO24507";
        TempSub sub = new TempSub(code);
        ref.child("Computer Science and Engineering").child("3").child(code).setValue(sub);*/


    }

    public void markAttendance(View view)
    {

        Intent intent = new Intent(homePageActivity.this, MarkAttendanceActivity.class);
        intent.putExtra("StudentClass",student);
        startActivity(intent);

    }

    public void openProfile(View view)
    {
        Intent intent = new Intent(homePageActivity.this, ProfilePage.class);
        intent.putExtra("StudentClass",student);
        startActivity(intent);
    }



    public void openCommunity(View view)
    {
        Intent intent = new Intent(homePageActivity.this, CommunityActivity.class);
        intent.putExtra("StudentClass",student);
        startActivity(intent);
    }



}