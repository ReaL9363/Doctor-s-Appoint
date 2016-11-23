package com.example.android.doctorsappointment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.doctorsappointment.Model.Doctor;

import java.util.List;

/**
 * Created by ReaL PC on 11/14/2016.
 */

public class DoctorAdapter extends ArrayAdapter<Doctor> {
    private static final String LOG_TAG = DoctorAdapter.class.getSimpleName();
    // private List<Doctor> doctorList;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        super(context, 0, doctorList);
        //this.activity = activity;
        //this.doctorList = doctorList;
    }
    /*@Override
    public int getCount() {
        return .size();
    }

    @Override
    public Object getItem(int location) {
        return doctorList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }*/
    /*public DoctorAdapter(Activity context, ArrayList<Doctor> doctors) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, doctors);
    }*/

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.doctor_list, parent, false);
        }
        Doctor currentDoctor = getItem(position);
        TextView tvDoctorName = (TextView) listItemView.findViewById(R.id.tvDoctorName);
        tvDoctorName.setText(currentDoctor.getmDoctorName());

        return listItemView;
    }
}
