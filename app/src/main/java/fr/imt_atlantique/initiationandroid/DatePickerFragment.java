package fr.imt_atlantique.initiationandroid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView mDate;
    private int mDay;
    private int mMonth;
    private  int mYear;

    public DatePickerFragment(TextView date, int day, int month, int year){
        this.mDate = date;
        this.mDay = day;
        this.mMonth = month;
        this.mYear = year;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDate.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", month+1) + "/" + year);
    }
}


