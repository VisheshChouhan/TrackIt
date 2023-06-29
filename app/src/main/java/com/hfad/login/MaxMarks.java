package com.hfad.login;

import java.io.Serializable;

public class MaxMarks implements Serializable {
    String MST1, MST2, MST3, QUIZ1, QUIZ2, QUIZ3, EndSEM;



    public MaxMarks() {
        this.MST1 = "-1";
        this.MST2 = "-1";
        this.MST3 = "-1";
        this.QUIZ1 = "-1";
        this.QUIZ2 = "-1";
        this.QUIZ3 = "-1";
        EndSEM = "-1";
    }

    public String getMST1() {
        return MST1;
    }

    public void setMST1(String MST1) {
        this.MST1 = MST1;
    }

    public String getMST2() {
        return MST2;
    }

    public void setMST2(String MST2) {
        this.MST2 = MST2;
    }

    public String getMST3() {
        return MST3;
    }

    public void setMST3(String MST3) {
        this.MST3 = MST3;
    }

    public String getQUIZ1() {
        return QUIZ1;
    }

    public void setQUIZ1(String QUIZ1) {
        this.QUIZ1 = QUIZ1;
    }

    public String getQUIZ2() {
        return QUIZ2;
    }

    public void setQUIZ2(String QUIZ2) {
        this.QUIZ2 = QUIZ2;
    }

    public String getQUIZ3() {
        return QUIZ3;
    }

    public void setQUIZ3(String QUIZ3) {
        this.QUIZ3 = QUIZ3;
    }

    public String getEndSEM() {
        return EndSEM;
    }

    public void setEndSEM(String endSEM) {
        EndSEM = endSEM;
    }
}
