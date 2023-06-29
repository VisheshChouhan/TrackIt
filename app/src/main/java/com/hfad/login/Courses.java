package com.hfad.login;

import java.io.Serializable;
import java.util.ArrayList;

public class Courses implements Serializable
{
    String Course, FacultyName, passwd, courseId, enterStageTwo, exitStageTwo, totalClassesTaken;
    String type;
    MaxMarks maxMarks;

    public Courses() {
    }

    public Courses(String course, String facultyName, String courseid, String type) {
        Course = course;
        FacultyName = facultyName;
        this.passwd = "helloooooooooooooooooooooooooooooooooooooo786348273";
        this.courseId = courseid;
        this.type = type;
        enterStageTwo= "0";
        exitStageTwo = "0";
        totalClassesTaken = "0";
        maxMarks = new MaxMarks();



    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getFacultyName() {
        return FacultyName;
    }

    public void setFacultyName(String facultyName) {
        FacultyName = facultyName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getEnterStageTwo() {
        return enterStageTwo;
    }

    public void setEnterStageTwo(String enterStageTwo) {
        this.enterStageTwo = enterStageTwo;
    }

    public String getExitStageTwo() {
        return exitStageTwo;
    }

    public void setExitStageTwo(String exitStageTwo) {
        this.exitStageTwo = exitStageTwo;
    }

    public String getTotalClassesTaken() {
        return totalClassesTaken;
    }

    public void setTotalClassesTaken(String totalClassesTaken) {
        this.totalClassesTaken = totalClassesTaken;
    }

    public MaxMarks getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(MaxMarks maxMarks) {
        this.maxMarks = maxMarks;
    }
}
