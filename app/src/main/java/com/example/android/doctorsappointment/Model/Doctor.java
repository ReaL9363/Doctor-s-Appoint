package com.example.android.doctorsappointment.Model;

/**
 * Created by ReaL PC on 11/14/2016.
 */

public class Doctor {
    private String mDoctorName;

    public Doctor(String mDoctorName) {
        this.mDoctorName = mDoctorName;
    }

    public Doctor() {

    }

    public String getmDoctorName() {
        return mDoctorName;
    }

    public void setmDoctorName(String mDoctorName) {
        this.mDoctorName = mDoctorName;
    }
}
