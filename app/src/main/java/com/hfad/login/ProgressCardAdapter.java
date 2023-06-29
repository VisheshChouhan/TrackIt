package com.hfad.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProgressCardAdapter extends RecyclerView.Adapter<ProgressCardAdapter.ViewHolder> {
    ArrayList<String> subCode;
    ArrayList<String> subName;

    MaxMarks maxMarks;
    Student student;

    public ProgressCardAdapter(Student student, ArrayList<String> subCode, ArrayList<String> subName, MaxMarks maxMarks) {
        this.student = student;
        this.subCode = subCode;
        this.subName = subName;

        this.maxMarks = maxMarks;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView cardView;

        ViewHolder(CardView v)
        {
            super(v);
            cardView = v;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_cardview, parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CardView cv = holder.cardView;

        TextView subNameView = (TextView) cv.findViewById(R.id.CourseName);
        subNameView.setText(subName.get(position));


        TextView subCodeView = (TextView) cv.findViewById(R.id.CourseId);
        subCodeView.setText(subCode.get(position));

        TextView studentMarksMST1View = (TextView) cv.findViewById(R.id.StudentMarksMST1);
        TextView studentMarksMST2View = (TextView) cv.findViewById(R.id.StudentMarksMST2);
        TextView studentMarksMST3View = (TextView) cv.findViewById(R.id.StudentMarksMST3);
        TextView studentMarksEndSemView = (TextView) cv.findViewById(R.id.StudentMarksEndSem);


        ProgressBar progressBarMST1 = (ProgressBar) cv.findViewById(R.id.ProgressIndicatorMST1);
        ProgressBar progressBarMST2 = (ProgressBar) cv.findViewById(R.id.ProgressIndicatorMST2);
        ProgressBar progressBarMST3 = (ProgressBar) cv.findViewById(R.id.ProgressIndicatorMST3);
        ProgressBar progressBarEndSem = (ProgressBar) cv.findViewById(R.id.ProgressIndicatorEndSem);


        TextView maxMarksMST1View = (TextView) cv.findViewById(R.id.MaxMarksMST1);
        TextView maxMarksMST2View = (TextView) cv.findViewById(R.id.MaxMarksMST2);
        TextView maxMarksMST3View = (TextView) cv.findViewById(R.id.MaxMarksMST3);
        TextView maxMarksEndSemView = (TextView) cv.findViewById(R.id.MaxMarksEndSem);




        ArrayList<Subject> tempSubject = student.getProgressList();
        for(Subject temp: tempSubject)
        {
            if(temp.getId().equals(subCode.get(position)))
            {
                if(temp.getMST1().equals("-1"))
                {
                    CardView midsem1 = cv.findViewById(R.id.MIDSEM1);
                    midsem1.setVisibility(View.GONE);
                }
                else
                {
                    studentMarksMST1View.setText("Your Marks: "+temp.getMST1());
                    maxMarksMST1View.setText("Max Marks: "+maxMarks.getMST1());
                    progressBarMST1.setProgress(Integer.parseInt(temp.getMST1()));
                    progressBarMST1.setMax(Integer.parseInt(maxMarks.getMST1()));

                }


                if(temp.getMST2().equals("-1"))
                {
                    CardView midsem2 = cv.findViewById(R.id.MIDSEM2);
                    midsem2.setVisibility(View.GONE);
                }
                else
                {
                    studentMarksMST2View.setText("Your Marks: "+temp.getMST2());
                    maxMarksMST2View.setText("Max Marks: "+maxMarks.getMST2());
                    progressBarMST2.setProgress(Integer.parseInt(temp.getMST2()));
                    progressBarMST2.setMax(Integer.parseInt(maxMarks.getMST2()));
                }


                if(temp.getMST3().equals("-1"))
                {
                    CardView midsem3 = cv.findViewById(R.id.MIDSEM3);
                    midsem3.setVisibility(View.GONE);
                }
                else
                {
                    studentMarksMST3View.setText("Your Marks: "+temp.getMST3());
                    maxMarksMST3View.setText("Max Marks: "+maxMarks.getMST3());
                    progressBarMST3.setProgress(Integer.parseInt(temp.getMST3()));
                    progressBarMST3.setMax(Integer.parseInt(maxMarks.getMST3()));
                }

                if(temp.getEndSEM().equals("-1"))
                {
                    CardView EndSem = cv.findViewById(R.id.EndSem);
                    EndSem.setVisibility(View.GONE);
                }
                else
                {
                    studentMarksEndSemView.setText("Your Marks: "+temp.getEndSEM());
                    progressBarEndSem.setProgress(Integer.parseInt(temp.getEndSEM()));
                    maxMarksEndSemView.setText("Max Marks: "+maxMarks.getEndSEM());
                    progressBarEndSem.setMax(Integer.parseInt(maxMarks.getEndSEM()));
                }

            }
        }



















    }

    @Override
    public int getItemCount() {
        return subCode.size();
    }


}
