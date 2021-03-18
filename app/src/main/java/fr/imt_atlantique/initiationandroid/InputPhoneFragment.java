package fr.imt_atlantique.initiationandroid;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputPhoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputPhoneFragment extends Fragment {

    static final String PHONEARRAY = "prefPhones";

    private LinearLayout mPhoneLayout;
    private Button mAddNumberBtn;
    private LinearLayout mAddNumber;

    private View rootView;
    private MainActivity rootActivity;

    public InputPhoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param phones an array containing all the phone numbers
     * @return A new instance of fragment InputPhoneFragment.
     */
    public static InputPhoneFragment newInstance(String[] phones) {
        InputPhoneFragment fragment = new InputPhoneFragment();
        Bundle args = new Bundle();
        args.putStringArray(PHONEARRAY, phones);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String[] phoneArray = savedInstanceState.getStringArray(PHONEARRAY);
            for(int i = 0; i < phoneArray.length; i++) {
                LayoutInflater inflater = (LayoutInflater) rootActivity.getSystemService(rootActivity.LAYOUT_INFLATER_SERVICE);
                LinearLayout phoneView = (LinearLayout) inflater.inflate(R.layout.phone_number, null);
                mPhoneLayout.addView(phoneView, mPhoneLayout.getChildCount() - 1);
                TextView currentNumber = (TextView) phoneView.findViewById(R.id.newNumber);
                currentNumber.setText(phoneArray[i]);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_input_phone, container, false);
        mPhoneLayout = (LinearLayout) rootView.findViewById(R.id.addNumber);
        mAddNumber = (LinearLayout) rootView.findViewById(R.id.addNumber);
        mAddNumberBtn = (Button) rootView.findViewById(R.id.addNumberBtn);

        mAddNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add a new layout by inflating it (and retrieving the pre-created layout)
                LayoutInflater inflater = (LayoutInflater) rootActivity.getSystemService(rootActivity.LAYOUT_INFLATER_SERVICE);
                LinearLayout phoneView = (LinearLayout) inflater.inflate(R.layout.phone_number, null);
                mPhoneLayout.addView(phoneView, mPhoneLayout.getChildCount() - 1);
                Button delBtn = phoneView.findViewById(R.id.delNumber);
                delBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPhoneLayout.removeView((View) v.getParent());
                    }
                });
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public String[] phoneToArray(LinearLayout layout){
        int childCount = layout.getChildCount() -1;
        String[] phoneArray = new String[childCount];
        for(int i = 0; i< childCount; i++){
            TextView text = (TextView) layout.getChildAt(i).findViewById(R.id.newNumber);
            phoneArray[i] = text.getText().toString();
        }

        return  phoneArray;
    }

    public String[] getPhones(){
        return phoneToArray(mPhoneLayout);
    }

    public void resetPhones(){
        mPhoneLayout.removeAllViews();
        mPhoneLayout.addView(mAddNumber,0);
    }
    @Override
    public void onSaveInstanceState(Bundle outState){

        String[] phoneNbs = phoneToArray(mPhoneLayout);
        outState.putStringArray(PHONEARRAY, phoneNbs);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);

        mPhoneLayout.removeAllViews();
        mPhoneLayout.addView(mAddNumber,0);

        String[] phoneArray = savedInstanceState.getStringArray(PHONEARRAY);
        for(int i = 0; i < phoneArray.length; i++){
            LayoutInflater inflater = (LayoutInflater) rootActivity.getSystemService(rootActivity.LAYOUT_INFLATER_SERVICE);
            LinearLayout phoneView = (LinearLayout) inflater.inflate(R.layout.phone_number, null);
            mPhoneLayout.addView(phoneView, mPhoneLayout.getChildCount() - 1);
            TextView currentNumber = (TextView) phoneView.findViewById(R.id.newNumber);
            currentNumber.setText(phoneArray[i]);
        }

    }
}