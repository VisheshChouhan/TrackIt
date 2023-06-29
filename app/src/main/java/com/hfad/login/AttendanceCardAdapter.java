package com.hfad.login;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class AttendanceCardAdapter extends RecyclerView.Adapter<AttendanceCardAdapter.ViewHolder>{

    private ArrayList<String> subName;
    private ArrayList<String> subCode;
    private ArrayList<Integer> Attended;
    private ArrayList<String> total;

    public AttendanceCardAdapter(ArrayList<String> subname, ArrayList<String> subCode, ArrayList<Integer> Attended, ArrayList<String> total)
    {
        this.subName = subname;
        this.subCode = subCode;
        this.Attended = Attended;
        this.total =total;
    }

    @Override
    public int getItemCount() {
        return subCode.size() ;
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
    public AttendanceCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_cardview,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(subCode.size() > 0) {


            CardView cardView = holder.cardView;

            TextView subjectName = (TextView) cardView.findViewById(R.id.subNameView);
            subjectName.setText(subName.get(position));

            TextView subjectCodeView = (TextView) cardView.findViewById(R.id.subCodeView);
            subjectCodeView.setText(subCode.get(position));

            int currAttend = Attended.get(position);
            int totalClasses = Integer.parseInt(total.get(position));

            //for Attended
            TextView attendedView = (TextView) cardView.findViewById(R.id.currAttendanceView);
            attendedView.setText("Attended : " + Integer.toString(currAttend));

            //for total
            TextView totalView = (TextView) cardView.findViewById(R.id.totalClassesView);
            totalView.setText("Total : " + Integer.toString(totalClasses));

            //For Pie Chart
            //PieChart pieChart = (PieChart) cardView.findViewById(R.id)
            PieChart pieChart = (PieChart) cardView.findViewById(R.id.pie_chart);

            ArrayList<PieEntry> entries = new ArrayList<>();

            entries.add(new PieEntry(currAttend, "Attended"));
            entries.add(new PieEntry(totalClasses - currAttend, "Not Attended"));

            PieDataSet pieDataSet = new PieDataSet(entries, "");

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.rgb(51, 214, 36));
            colors.add(Color.rgb(194, 2, 12));

            pieDataSet.setColors(colors);

            PieData pieData = new PieData(pieDataSet);
            pieDataSet.setSliceSpace(5f);
            pieDataSet.setValueTextSize(10f);
            pieDataSet.setValueTextColor(Color.rgb(255, 255, 255));


            pieChart.setData(pieData);
            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(51f);
            pieChart.setHoleRadius(41f);
            pieChart.animateY(1000, Easing.EaseInOutCubic);
        }
    }
}
