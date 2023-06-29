package com.hfad.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class StageTwoActivity extends AppCompatActivity {


    Student student;
    String subCode;
    int currentAttendance, totalNumberOfClasses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_two);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //int currentAttendance;




        student = (Student) getIntent().getSerializableExtra("StudentClass");


        subCode = getIntent().getStringExtra("subCode");
        String tempAttend = student.getAttendance().get(subCode);

        currentAttendance = Integer.parseInt(tempAttend);

        totalNumberOfClasses = Integer.parseInt(getIntent().getStringExtra("totalClassesTaken"));


        //PieChart pieChart = findViewById(R.id)
        PieChart pieChart = findViewById(R.id.pie_chart);

        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(currentAttendance, "Attended"));
        entries.add(new PieEntry( totalNumberOfClasses-currentAttendance, "Not Attended"));

        PieDataSet pieDataSet = new PieDataSet(entries, "");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(51, 214, 36));
        colors.add(Color.rgb(194, 2, 12));

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setSliceSpace(5f);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setValueTextColor(Color.rgb(255,255,255));


        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(51f);
        pieChart.setHoleRadius(41f);
        pieChart.animateY(1000, Easing.EaseInOutCubic);







        TextView currAttendanceTextView = findViewById(R.id.currAttendanceView);
        currAttendanceTextView.setText("Your Attendance: "+currentAttendance);
        TextView totalClassesTextView = findViewById(R.id.totalClassesView);
        totalClassesTextView.setText("Total Classes: "+totalNumberOfClasses);

    }

    @Override
    protected void onPause() {
        super.onPause();



        DatabaseReference courseReference = FirebaseDatabase.getInstance().getReference("Courses");
        Query checkUserDatabase = courseReference.orderByChild("courseId").equalTo(subCode);



        String UserRollNo =  student.getRollNo();
        DatabaseReference studentReference = FirebaseDatabase.getInstance().getReference("Students");
        //studentReference.child(UserName).child("attendance").setValue(Integer.toString(currentAttendance-5));
        Query retrieveStudentAttendance = studentReference.orderByChild("rollNo").equalTo(UserRollNo);


        // to retrieve attendance

        /*retrieveStudentAttendance.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentAttendance = snapshot.child(UserRollNo).child("attendance").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {

                    String exitStageTwo = snapshot.child(subCode).child("exitStageTwo").getValue(String.class);


                    if(exitStageTwo.equals("0"))
                    {
                        studentReference.child(UserRollNo).child("attendance").child(subCode).setValue(Integer.toString(currentAttendance-5));

                        //Toast.makeText(StageTwoActivity.this, "You successfully exited", Toast.LENGTH_SHORT).show();

                        //Intent intent = new Intent(StageTwoActivity.this, homePageActivity.class);
                        //startActivity(intent);
                    }
                    else
                    {
                        //Toast.makeText(StageTwoActivity.this, "You Can't exit now.", Toast.LENGTH_SHORT).show();


                    }
                }
                else
                {
                    Toast.makeText(StageTwoActivity.this, "Not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    public void submitAttendance(View view)
    {

        String UserRollNo =  student.getRollNo();

        EditText passwdEntry = findViewById(R.id.StudentPassword);
        String passwd = passwdEntry.getText().toString().trim();

        DatabaseReference studentReference = FirebaseDatabase.getInstance().getReference("Students");
        Query retrieveStudentAttendance = studentReference.orderByChild("rollNo").equalTo(UserRollNo);



        /*retrieveStudentAttendance.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentAttendance = snapshot.child(UserName).child("attendance").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/




        DatabaseReference courseReference = FirebaseDatabase.getInstance().getReference("Courses");
        Query checkUserDatabase = courseReference.orderByChild("course").equalTo(subCode);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    passwdEntry.setError(null);
                    String passwordFromDB = snapshot.child(subCode).child("passwd").getValue(String.class);
                    String exitStageTwo = snapshot.child(subCode).child("exitStageTwo").getValue(String.class);


                    if(passwordFromDB.equals(passwd))
                    {


                        studentReference.child(UserRollNo).child("attendance").child(subCode).setValue(Integer.toString(currentAttendance+1));
                        Toast.makeText(StageTwoActivity.this, "Your Attendance is successfully marked", Toast.LENGTH_SHORT).show();

                        //Intent intent = new Intent(StageTwoActivity.this, homePageActivity.class);
                        //startActivity(intent);
                    }
                    else if(!passwordFromDB.equals(passwd) && exitStageTwo.equals("0"))
                    {


                        //studentReference.child(UserName).child("attendance").setValue(Integer.toString(currentAttendance+1));
                        Toast.makeText(StageTwoActivity.this, "You entered wrong code.", Toast.LENGTH_SHORT).show();

                        //Intent intent = new Intent(StageTwoActivity.this, homePageActivity.class);
                        //startActivity(intent);
                    }
                    else if (exitStageTwo.equals("1"))
                    {
                        Toast.makeText(StageTwoActivity.this, "You can't mark your attendance now\n Attendance is closed.", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(StageTwoActivity.this, "Your Attendance is not successfully marked", Toast.LENGTH_SHORT).show();


                    }
                }
                else
                {
                    Toast.makeText(StageTwoActivity.this, "This Course doesn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void exit(View view)
    {


        DatabaseReference courseReference = FirebaseDatabase.getInstance().getReference("Courses");
        Query checkUserDatabase = courseReference.orderByChild("courseId").equalTo(subCode);


        String UserRollNo =  student.getRollNo();

        DatabaseReference studentReference = FirebaseDatabase.getInstance().getReference("Students");
        Query retrieveStudentAttendance = studentReference.orderByChild("rollNo").equalTo(UserRollNo);



        /*retrieveStudentAttendance.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String currentAttendance = snapshot.child(UserRollNo).child("attendance").child(subCode).getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {

                    String exitStageTwo = snapshot.child(subCode).child("exitStageTwo").getValue(String.class);


                    if(exitStageTwo.equals("1"))
                    {
                        studentReference.child(UserRollNo).child("attendance").child(subCode).setValue(Integer.toString(currentAttendance+1));
                        onBackPressed();

                        Toast.makeText(StageTwoActivity.this, "You successfully exited", Toast.LENGTH_SHORT).show();

                        //Intent intent = new Intent(StageTwoActivity.this, homePageActivity.class);
                        //startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(StageTwoActivity.this, "You Can't exit now.", Toast.LENGTH_SHORT).show();


                    }
                }
                else
                {
                    Toast.makeText(StageTwoActivity.this, "Not working", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





   /* private class ReadCourses extends AsyncTask<ArrayList<String>, Void, ArrayList<ArrayList<String>>>
    {
        DatabaseReference courseReference;
        ArrayList<String> courses;
        ArrayList<String> total;
        ArrayList<String> subNames;






        @Override
        protected void onPreExecute() {
            courseReference = FirebaseDatabase.getInstance().getReference("Courses");
            total = new ArrayList<>();
            subNames = new ArrayList<>();



        }

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(ArrayList<String>... arrayLists ) {
            courses = arrayLists[0];

            for(String st : courses)
            {
                try {
                    DataSnapshot snapshot = Tasks.await(courseReference.get());

                    if (snapshot.exists()) {

                        String ttl = snapshot.child(st).child("totalClassesTaken").getValue(String.class);
                        total.add(ttl);

                        String name = snapshot.child(st).child("course").getValue(String.class);
                        subNames.add(name);




                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    // Handle execution exception
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // Handle interruption exception
                }

            }


            ArrayList<ArrayList<String>> result = new ArrayList<>();
            result.add(total);
            result.add(subNames);


            return result;

        }
    }*/










}