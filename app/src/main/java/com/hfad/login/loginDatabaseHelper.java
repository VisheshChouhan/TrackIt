package com.hfad.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class loginDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "loginCredentialDatabase";
    private static final int DB_VERSION = 1;

    public loginDatabaseHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE LASTCREDENTIAL (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USERNAME TEXT,"+
                "PASSWORD TEXT);");
        ContentValues credInfo = new ContentValues();
        credInfo.put("USERNAME", "BY_DEFAULT");
        credInfo.put("PASSWORD", "BY_DEFAULT");
        db.insert("LASTCREDENTIAL", null , credInfo);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
