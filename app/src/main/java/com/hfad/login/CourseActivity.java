package com.hfad.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;


public class CourseActivity extends AppCompatActivity {

    Courses course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        course = (Courses) getIntent().getSerializableExtra("CourseClass");

        TextView teacherNameView = (TextView) findViewById(R.id.teacherNameView);
        TextView teacherPostView = (TextView) findViewById(R.id.teacherPostView);
        TextView courseIdView = (TextView) findViewById(R.id.courseIdView);
        TextView courseNameView = (TextView) findViewById(R.id.courseNameView);

        teacherNameView.setText(course.getFacultyName());
        courseIdView.setText(course.getCourseId());
        courseNameView.setText(course.getCourse());


    }
}