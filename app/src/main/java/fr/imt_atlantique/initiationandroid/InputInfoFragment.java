package fr.imt_atlantique.initiationandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputInfoFragment extends Fragment {

    static final String LASTNAME ="prefLast";
    static final String FIRSTNAME = "prefFirst";
    static final String BIRTHDATE = "prefDate";
    static final String BIRTHPLACE = "prefPlace";
    static final String DEPT = "prefDept";
    static final int DATE_ACTIVITY = 0;

    private TextView mLast;
    private TextView mFirst;
    private TextView mDate;
    private TextView mPlace;
    private Spinner mDept;

    private int currentDay;
    private int currentMonth;
    private int currentYear;
    private Calendar c;
    private Button setDateBtn;

    private MainActivity rootActivity;
    private View rootView;

    public InputInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param last the user's lastname
     * @param first the user's firstname
     * @param date the user's birth date
     * @param place the user's birth city
     * @param dept the user's birth department
     * @return A new instance of fragment InputInfoFragment.
     */
    public static InputInfoFragment newInstance(String last, String first, String date, String place, int dept) {
        InputInfoFragment fragment = new InputInfoFragment();
        Bundle args = new Bundle();
        args.putString(LASTNAME, last);
        args.putString(FIRSTNAME, first);
        args.putString(BIRTHDATE, date);
        args.putString(BIRTHPLACE, place);
        args.putInt(DEPT, dept);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mLast.setText(savedInstanceState.getString(LASTNAME));
            mFirst.setText(savedInstanceState.getString(FIRSTNAME));
            mDate.setText(savedInstanceState.getString(BIRTHDATE));
            mPlace.setText(savedInstanceState.getString(BIRTHPLACE));
            mDept.setSelection(savedInstanceState.getInt(DEPT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_input_info, container, false);
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        rootActivity = (MainActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLast = (TextView) rootView.findViewById(R.id.lastname);
        mFirst = (TextView) rootView.findViewById(R.id.firstname);
        mDate = (TextView) rootView.findViewById(R.id.birthDate);
        mPlace = (TextView) rootView.findViewById(R.id.birthPlace);
        mDept = (Spinner) rootView.findViewById(R.id.dept);
        setDateBtn = (Button) rootView.findViewById(R.id.setDateBtn);

        c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH);
        currentDay = c.get(Calendar.DAY_OF_MONTH);

        if(savedInstanceState != null){
            mLast.setText(savedInstanceState.getString(LASTNAME));
            mFirst.setText(savedInstanceState.getString(FIRSTNAME));
            mDate.setText(savedInstanceState.getString(BIRTHDATE));
            mPlace.setText(savedInstanceState.getString(BIRTHPLACE));
            mDept.setSelection(savedInstanceState.getInt(DEPT));
        }
        else{
            mDate.setText(String.format("%02d", currentDay) + "/" + String.format("%02d", currentMonth + 1) + "/" + currentYear);
        }

        setDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dateIntent = new Intent();
                dateIntent.setAction(Intent.ACTION_PICK);
                dateIntent.putExtra("dayOfMonth", currentDay);
                dateIntent.putExtra("month", currentMonth);
                dateIntent.putExtra("year", currentYear);
                startActivityForResult(dateIntent, DATE_ACTIVITY);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(LASTNAME, mLast.getText());
        outState.putCharSequence(FIRSTNAME, mFirst.getText());
        outState.putCharSequence(BIRTHDATE, mDate.getText());
        outState.putCharSequence(BIRTHPLACE, mPlace.getText());
        outState.putInt(DEPT, mDept.getSelectedItemPosition());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null){
        mLast.setText(savedInstanceState.getString(LASTNAME));
        mFirst.setText(savedInstanceState.getString(FIRSTNAME));
        mDate.setText(savedInstanceState.getString(BIRTHDATE));
        mPlace.setText(savedInstanceState.getString(BIRTHPLACE));
        mDept.setSelection(savedInstanceState.getInt(DEPT));}
    }

    public Boolean isCompleted(){
        return (mLast.getText() == null || mFirst.getText() == null || mPlace.getText() == null || mDept.getSelectedItemId() == 0);
    }

    public void resetFields(){
        mLast.setText("");
        mFirst.setText("");
        mPlace.setText("");
        currentDay = c.get(Calendar.DAY_OF_MONTH);
        currentMonth = c.get(Calendar.MONTH);
        currentYear = c.get(Calendar.YEAR);
        mDate.setText(String.format("%02d", currentDay)
                +"/" + String.format("%02d", currentMonth + 1)+ "/"
                + currentYear);
        mDept.setSelection(0);
    }

    public String getLast(){
        return mLast.getText().toString();
    }

    public String getFirst(){
        return mFirst.getText().toString();
    }

    public String getDate(){
        return mDate.getText().toString();
    }

    public String getPlace(){
        return mPlace.getText().toString();
    }

    public String getDept(){
        return mDept.getSelectedItem().toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DATE_ACTIVITY & resultCode == Activity.RESULT_OK){
            currentDay = data.getExtras().getInt("day");
            currentMonth = data.getExtras().getInt("month");
            currentYear = data.getExtras().getInt("year");
            mDate.setText(String.format("%02d", currentDay) + "/" + String.format("%02d", currentMonth + 1) + "/" + currentYear);
        }
    }

}