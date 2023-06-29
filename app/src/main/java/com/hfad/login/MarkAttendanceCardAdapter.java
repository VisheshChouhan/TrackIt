package com.hfad.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MarkAttendanceCardAdapter extends RecyclerView.Adapter<MarkAttendanceCardAdapter.ViewHolder> {

    private ArrayList<String> subName;
    private ArrayList<String> subCode;
    private ArrayList<String> currAttendance;
    private ArrayList<String> totalClassesTaken;
    Student student;

    @Override
    public int getItemCount() {
        return subName.size();
    }

    public MarkAttendanceCardAdapter(Student student, ArrayList<String> subName, ArrayList<String> subCode, ArrayList<String> currAttendance,ArrayList<String> totalClassesTaken) {
        this.student = student;
        this.subName = subName;
        this.subCode = subCode;
        this.currAttendance = currAttendance;
        this.totalClassesTaken = totalClassesTaken;
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
    public MarkAttendanceCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.mark_attendance_cardview,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        TextView subNameview = (TextView) cardView.findViewById(R.id.subNameView);
        subNameview.setText(subName.get(position));

        TextView subCodeView = (TextView) cardView.findViewById(R.id.subCodeView);
        subCodeView.setText(subCode.get(position));

        TextView attendanceProgressView = (TextView) cardView.findViewById(R.id.Attendance_progress);
        attendanceProgressView.setText("Attended Classes: " + currAttendance.get(position) + "\n Total Classes: "+totalClassesTaken.get(position));

        Button enter = (Button) cardView.findViewById(R.id.stage2EntryButton);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enterStageTwo;

                try {
                    enterStageTwo = new ReadCourseEnterStage().execute(subCode.get(position)).get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if(enterStageTwo.equals("1"))
                {
                    Intent intent = new Intent(cardView.getContext(), StageTwoActivity.class);
                    intent.putExtra("StudentClass",student);
                    intent.putExtra("SubName", subName.get(position));
                    intent.putExtra("subCode", subCode.get(position));
                    intent.putExtra("totalClassesTaken",totalClassesTaken.get(position));
                    cardView.getContext().startActivity(intent);

                }
                else
                {
                    Toast.makeText(cardView.getContext(), "You can't enter now",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }



    //To read the entering stage of current subject
    private class ReadCourseEnterStage extends AsyncTask<String, Void, String>
    {
        DatabaseReference courseReference;

        String subCode;
        String Result;




        @Override
        protected String doInBackground(String... strings ) {
            subCode = strings[0];


            DatabaseReference courseReference = FirebaseDatabase.getInstance().getReference("Courses");
            try {
                DataSnapshot snapshot = Tasks.await(courseReference.get());

                if (snapshot.exists()) {

                    Result = snapshot.child(subCode).child("enterStageTwo").getValue(String.class);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
                // Handle execution exception
            } catch (InterruptedException e) {
                e.printStackTrace();
                // Handle interruption exception
            }


            return Result;

        }
    }
}
