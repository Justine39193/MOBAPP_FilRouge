package fr.imt_atlantique.initiationandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DateActivity extends AppCompatActivity {

    static final int RESULT_OK = 1;
    static final int RESULT_CANCEL = 0;
    private Bundle intentData;

    private DatePicker mDatePicker;
    private TextView mDate;
    private int mDay;
    private int mMonth;
    private int mYear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        intentData = getIntent().getExtras();
        mDay =intentData.getInt("dayOfMonth");
        mMonth = intentData.getInt("month");
        mYear = intentData.getInt("year");

        mDate = (TextView) findViewById(R.id.dateText);
        mDate.setText(String.format("%02d", mDay) + "/" + String.format("%02d", mMonth + 1) + "/" + mYear);
        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        mDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDay = dayOfMonth;
                mMonth = monthOfYear;
                mYear = year;
                mDate.setText(String.format("%02d", mDay) + "/" + String.format("%02d", mMonth + 1) + "/" + mYear);

            }
        });
    }


    public void okDate(View view) {
        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.putExtra("day", mDay);
        backIntent.putExtra("month", mMonth);
        backIntent.putExtra("year", mYear);
        setResult(RESULT_OK, backIntent);
        finish();
    }

    public void cancelDate(View view) {
        setResult(RESULT_CANCEL);
        finish();
    }

}
