package com.hfad.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student implements Serializable {
    String Name;
    String Email;
    String RollNo;
    String Password;
    String Branch;
    String Batch ;
    String Semester;


    HashMap<String, String> Attendance;
    ArrayList<Subject> ProgressList;

    //For data base


    //Temp class to store data of subjects



    public Student() {
    }

    public Student(String name, String email, String rollNo, String password, String branch, String batch, String semester, HashMap<String, String> attendance, ArrayList<Subject> progressList) {
        Name = name;
        Email = email;
        RollNo = rollNo;
        Password = password;
        Branch = branch;
        Batch = batch;
        Semester = semester;
        Attendance = attendance;
        ProgressList = progressList;
    }




    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public HashMap<String, String> getAttendance() {
        return Attendance;
    }

    public void setAttendance(HashMap<String, String> attendance) {
        Attendance = attendance;
    }

    public ArrayList<Subject> getProgressList() {
        return ProgressList;
    }

    public void setProgressList(ArrayList<Subject> progressList) {
        ProgressList = progressList;
    }


}
