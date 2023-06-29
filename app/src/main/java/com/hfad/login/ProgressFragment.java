package com.hfad.login;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressFragment extends Fragment {
    Student student;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressFragment newInstance(String param1, String param2) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RecyclerView progressRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_progress, container, false);



        MaxMarks maxMark;
        ArrayList<String> subNames;
        ArrayList<String> codes = new ArrayList<>();


        student = (Student) getArguments().getSerializable("StudentClass");
        ArrayList<Subject> ProgressList = student.getProgressList();

        for(Subject sub: ProgressList)
        {
            codes.add(sub.getId());
        }

        try {
            subNames = new ReadCourses().execute(codes).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        try {
            maxMark = new ReadCoursesMarks().execute(codes).get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ProgressCardAdapter adapter = new ProgressCardAdapter(student, codes, subNames, maxMark);
        progressRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        progressRecycler.setLayoutManager(layoutManager);


        // Inflate the layout for this fragment
        return progressRecycler;
    }




    private class ReadCoursesMarks extends AsyncTask<ArrayList<String>, Void, MaxMarks>
    {
        DatabaseReference courseReference;
        ArrayList<String> courses;
        ArrayList<String> maxMark;


        MaxMarks maxMarks;


        @Override
        protected void onPreExecute() {
            courseReference = FirebaseDatabase.getInstance().getReference("Courses");
            maxMark = new ArrayList<>();


        }

        @Override
        protected MaxMarks doInBackground(ArrayList<String>... arrayLists ) {
            courses = arrayLists[0];

            for(String st : courses)
            {
                try {
                    DataSnapshot snapshot = Tasks.await(courseReference.get());

                    if (snapshot.exists()) {

                        maxMarks = snapshot.child(st).child("maxMarks").getValue(MaxMarks.class);

                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    // Handle execution exception
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // Handle interruption exception
                }

            }
            return maxMarks;

        }
    }




    private class ReadCourses extends AsyncTask<ArrayList<String>, Void, ArrayList<String>>
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
        protected ArrayList<String> doInBackground(ArrayList<String>... arrayLists ) {
            courses = arrayLists[0];

            for(String st : courses)
            {
                try {
                    DataSnapshot snapshot = Tasks.await(courseReference.get());

                    if (snapshot.exists()) {

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





            return subNames;

        }
    }
}