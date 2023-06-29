package com.hfad.login;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseCardAdapter extends RecyclerView.Adapter<CourseCardAdapter.ViewHolder>
{

    ArrayList<Courses> coursesArrayList;

    public CourseCardAdapter(ArrayList<Courses> coursesArrayList) {

        this.coursesArrayList = coursesArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;

        public ViewHolder(CardView cv) {
            super(cv);
            cardView = cv;
        }
    }
    @NonNull
    @Override
    public CourseCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.course_cardview, parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseCardAdapter.ViewHolder holder, int position)
    {
        CardView cardView = holder.cardView;

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cardView.getContext(), CourseActivity.class);
                intent.putExtra("CourseClass", coursesArrayList.get(position));
                cardView.getContext().startActivity(intent);
            }
        });


        TextView courseId = (TextView) cardView.findViewById(R.id.CourseId);
        TextView courseName = (TextView) cardView.findViewById(R.id.CourseName);
        TextView teacherName = cardView.findViewById(R.id.TeacherName);
        TextView courseType = cardView.findViewById(R.id.CourseType);

        courseId.setText(coursesArrayList.get(position).getCourseId());
        courseName.setText(coursesArrayList.get(position).getCourse());
        courseType.setText(coursesArrayList.get(position).getType());
        teacherName.setText(coursesArrayList.get(position).getFacultyName());

    }

    @Override
    public int getItemCount() {
        return coursesArrayList.size();
    }



}
