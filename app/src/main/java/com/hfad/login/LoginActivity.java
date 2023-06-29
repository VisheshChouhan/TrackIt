package com.hfad.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //test area
        //******************************************************************
        //Intent intent = new Intent(LoginActivity.this, homePageActivity.class);
        //startActivity(intent);

        //create a cursor
        SQLiteOpenHelper credDatabaseHelper = new loginDatabaseHelper(this);
        try {
            SQLiteDatabase db = credDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("LASTCREDENTIAL",
                    new String[] {"USERNAME", "PASSWORD"},
                    "_id = 1", null,null,null,null,null);
            if(cursor.moveToFirst())
            {
                String lastUserName = cursor.getString(0);
                String lastPassword = cursor.getString(1);

                if(!lastUserName.equals("BY_DEFAULT"))
                {
                    checkuser(lastUserName, lastPassword);
                }


            }

        }catch (SQLiteException e)
        {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }



        //******************************************************************

        loginUsername = findViewById(R.id.Login_Username);
        loginPassword = findViewById(R.id.Login_Password);
        loginButton = findViewById(R.id.login);
        signupRedirectText = findViewById(R.id.redirectToSignup);



        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!validateUsername() | !validatePassword())
                {

                }
                else
                {
                    //Reading the user Input data
                    String username = loginUsername.getText().toString().trim();
                    String User_passwd = loginPassword.getText().toString().trim();
                    checkuser(username, User_passwd);
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean validateUsername()
    {
        String username = loginUsername.getText().toString();
        if(username.isEmpty())
        {
            loginUsername.setError("Username cannot be empty");
            return false;
        }
        else
        {
            loginUsername.setError(null);
            return true;
        }
    }

    public boolean validatePassword()
    {
        String passwd = loginPassword.getText().toString();
        if(passwd.isEmpty())
        {
            loginPassword.setError("Password cannot be empty");
            return false;
        }
        else
        {
            loginPassword.setError(null);
            return true;
        }
    }


    public void checkuser(String userName, String userPass)
    {
        String username = userName;
        String User_passwd = userPass;
        updateLastLoggedUser(username, User_passwd);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
        Query checkUserDatabase = reference.orderByChild("rollNo").equalTo(username);


        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    loginUsername.setError(null);
                    Student student = snapshot.child(username).getValue(Student.class);
                    String passwordFromDB = student.getPassword();
                    //String userAttendance = snapshot.child(username).child("attendance").getValue(String.class);


                    if(passwordFromDB.equals(User_passwd))
                    {
                        loginUsername.setError(null);
                        Intent intent = new Intent(LoginActivity.this, homePageActivity.class);
                        //intent.putExtra("UserName", username);
                        //intent.putExtra("userAttendance",userAttendance);

                        //passing student class to intent
                        intent.putExtra("StudentClass",student);
                        startActivity(intent);
                    }
                    else
                    {
                        loginPassword.setError("Invalid Credentials");
                        loginPassword.requestFocus();
                    }
                }
                else
                {
                    loginUsername.setError("User doesn't Exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    //Update the last logged in user
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
}