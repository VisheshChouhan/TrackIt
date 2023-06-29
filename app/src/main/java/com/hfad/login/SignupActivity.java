package com.hfad.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity {

    EditText signupRollNo, signupName, signupEmail, signupPassword;
    Spinner signupBranch, signupBatch, signupSemester;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        signupName = findViewById(R.id.Signup_Name);
        signupEmail = findViewById(R.id.Signup_Email);
        signupRollNo = findViewById(R.id.Signup_RollNo);
        signupBranch = findViewById(R.id.branch);
        signupBatch = findViewById(R.id.batch);
        signupPassword = findViewById(R.id.Signup_Password);
        signupSemester = findViewById(R.id.semester);
        signupButton = findViewById(R.id.SignUp);
        loginRedirectText = findViewById(R.id.redirectLogin);


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                String name = signupName.getText().toString().trim();
                String Email = signupEmail.getText().toString().trim();
                String RollNo = signupRollNo.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String batch = signupBatch.getSelectedItem().toString().trim();
                String branch = signupBranch.getSelectedItem().toString().trim();
                String semester = signupSemester.getSelectedItem().toString().trim();


                HashMap<String, String> Attendance = new HashMap<>();
                ArrayList<Subject> ProgressList = new ArrayList<>();
                ArrayList<TempSub> tempSubs = new ArrayList<>();


                new ReadDatabase().execute();

                /*
                DatabaseReference schemeReference = FirebaseDatabase.getInstance().getReference("Scheme").child(branch).child(semester);



                try {
                    DataSnapshot snapshot = Tasks.await(schemeReference.get());

                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            TempSub tempSub = dataSnapshot.getValue(TempSub.class);
                            if (tempSub != null) {
                                ProgressList.add(new Subject(tempSub.getId()));
                            }
                        }

                        Student helper = new Student(name, Email, RollNo, password, branch, batch, semester, ProgressList);

                        database = FirebaseDatabase.getInstance();
                        reference = database.getReference("Students");
                        reference.child(RollNo).setValue(helper);

                        // Updating last logged in user
                        updateLastLoggedUser(name, password);

                        Toast.makeText(SignupActivity.this, "You have successfully signed up!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    // Handle execution exception
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // Handle interruption exception
                }


                 */
                /*schemeReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            TempSub tempSub = dataSnapshot.getValue(TempSub.class);
                            tempSubs.add(tempSub);
                            //Courses tempCourse = snapshot.child(tempSub.getId()).getValue(Courses.class);
                            Log.v("check",tempSub.getId());

                            //Subject sub = new Subject(tempSub.getId());
                            ProgressList.add(new Subject(tempSub.getId()));
                            //Toast.makeText(SignupActivity.this, tempSub.getId(), Toast.LENGTH_SHORT).show();


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

                /*schemeReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            TempSub tempSub = dataSnapshot.getValue(TempSub.class);
                            //Courses tempCourse = snapshot.child(tempSub.getId()).getValue(Courses.class);

                            Subject sub = new Subject(tempSub.getId());
                            ProgressList.add(sub);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/

                /*for(TempSub t: tempSubs)
                {
                    ProgressList.add(new Subject(t.getId()));
                    Log.v("value", t.getId());
                }*/


                /*Student helper = new Student(name, Email,RollNo,password,branch,batch,semester, ProgressList);

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("Students");
                reference.child(RollNo).setValue(helper);

                //Updating last logged in user
                updateLastLoggedUser(name, password);

                Toast.makeText(SignupActivity.this, "You have successfully signed up!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);


                startActivity(intent);*/
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    //This function update last logged in user
    public void updateLastLoggedUser(String user, String pass)
    {
        ContentValues cred = new ContentValues();
        cred.put("USERNAME", user);
        cred.put("PASSWORD", pass);

        SQLiteOpenHelper writeDatabaseHelper = new loginDatabaseHelper(this);
        try {
            SQLiteDatabase db = writeDatabaseHelper.getWritableDatabase();
            db.update("LASTCREDENTIAL",cred,"_id = ?",new String[] {Integer.toString(1)} );
            db.close();
        }catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();

        }

    }


    //Background task class
    private class ReadDatabase extends AsyncTask<Void, Void, Boolean>
    {
        DatabaseReference schemeReference;
        String name = signupName.getText().toString().trim();
        String Email = signupEmail.getText().toString().trim();
        String RollNo = signupRollNo.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        String batch = signupBatch.getSelectedItem().toString().trim();
        String branch = signupBranch.getSelectedItem().toString().trim();
        String semester = signupSemester.getSelectedItem().toString().trim();
        ArrayList<Subject> ProgressList = new ArrayList<>();
        //ArrayList<TempSub> tempSubs = new ArrayList<>();
        HashMap<String, String> Attendance = new HashMap<>();



        @Override
        protected void onPreExecute() {

            String branch = signupBranch.getSelectedItem().toString().trim();
            String semester = signupSemester.getSelectedItem().toString().trim();
            schemeReference = FirebaseDatabase.getInstance().getReference("Scheme").child(branch).child(semester);

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                DataSnapshot snapshot = Tasks.await(schemeReference.get());

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        TempSub tempSub = dataSnapshot.getValue(TempSub.class);
                        if (tempSub != null) {
                            ProgressList.add(new Subject(tempSub.getId()));
                            Attendance.put(tempSub.getId(), "0");

                        }
                    }

                    Student helper = new Student(name, Email, RollNo, password, branch, batch, semester,Attendance, ProgressList);

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Students");
                    reference.child(RollNo).setValue(helper);

                    // Updating last logged in user
                    updateLastLoggedUser(name, password);

                    //Toast.makeText(SignupActivity.this, "You have successfully signed up!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
                // Handle execution exception
            } catch (InterruptedException e) {
                e.printStackTrace();
                // Handle interruption exception
            }
            return null;
        }
    }
}