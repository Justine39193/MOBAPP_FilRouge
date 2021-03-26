package fr.imt_atlantique.initiationandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.imt_atlantique.initiationandroid.displayIntents.EditActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayFragment extends Fragment {

    private TextView mLast;
    private TextView mFirst;
    private TextView mDate;
    private TextView mPlace;
    private TextView mDept;
    private LinearLayout mPhoneLayout;
    private String[] mPhones;

    private DisplayActivity rootActivity;
    private View rootView;

    private Button mViewBtn;
    private Button mEditBtn;

    public interface OnActionInterface{
        void onViewLast(String last);
        void onEditFirst(String first);
        void onDialNumber(String number);
    }

    public DisplayFragment() {
    // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param last user's lastname
     * @param first user's firstname
     * @param date user's birth date
     * @param place user's birth place
     * @param dept user's birth department
     * @param phones user's phone numbers
     * @return A new instance of fragment DisplayFragment.
     */
    public static DisplayFragment newInstance(String last, String first, String date, String place, String dept, String[] phones) {
        DisplayFragment fragment = new DisplayFragment();
        Bundle args = new Bundle();
        args.putString(InputInfoFragment.LASTNAME, last);
        args.putString(InputInfoFragment.FIRSTNAME, first);
        args.putString(InputInfoFragment.BIRTHDATE, date);
        args.putString(InputInfoFragment.BIRTHPLACE, place);
        args.putString(InputInfoFragment.DEPT, dept);
        args.putStringArray(InputPhoneFragment.PHONEARRAY, phones);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState != null) {
            mLast.setText(savedInstanceState.getString(InputInfoFragment.LASTNAME));
            mFirst.setText(savedInstanceState.getString(InputInfoFragment.FIRSTNAME));
            mDate.setText(savedInstanceState.getString(InputInfoFragment.BIRTHDATE));
            mPlace.setText(savedInstanceState.getString(InputInfoFragment.BIRTHPLACE));
            mDept.setText(savedInstanceState.getString(InputInfoFragment.DEPT));
            mPhones = savedInstanceState.getStringArray(InputPhoneFragment.PHONEARRAY);
            if(mPhones.length > 0) {
                mPhoneLayout.removeAllViews();
                if (mPhones.length != 0) {
                    for (int i = 0; i < mPhones.length; i++) {
                        LayoutInflater inflater = (LayoutInflater) rootActivity.getSystemService(rootActivity.LAYOUT_INFLATER_SERVICE);
                        LinearLayout phoneView = (LinearLayout) inflater.inflate(R.layout.number_layout, null);
                        mPhoneLayout.addView(phoneView);
                        TextView phoneNb = (TextView) phoneView.findViewById(R.id.userPhone);
                        phoneNb.setText(mPhones[i]);
                        Button dialBtn = (Button) phoneView.findViewById(R.id.dialBtn);
                        dialBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("Display", phoneNb.getText().toString());
                                Intent dialIntent = new Intent();
                                dialIntent.setAction(Intent.ACTION_DIAL);
                                dialIntent.putExtra("phone", phoneNb.getText().toString());
                                startActivity(dialIntent);
                            }
                        });
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_display, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLast = (TextView) rootView.findViewById(R.id.userLast);
        mFirst = (TextView) rootView.findViewById(R.id.userFirst);
        mDate = (TextView) rootView.findViewById(R.id.userDate);
        mPlace = (TextView) rootView.findViewById(R.id.userPlace);
        mDept = (TextView) rootView.findViewById(R.id.userDept);
        mPhoneLayout = (LinearLayout) rootView.findViewById(R.id.numberLayout);

        mViewBtn = (Button) rootView.findViewById(R.id.viewBtn);
        mViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent();
                viewIntent.setAction(Intent.ACTION_VIEW);
                viewIntent.putExtra(InputInfoFragment.LASTNAME, mLast.getText().toString());
                startActivity(viewIntent);
            }
        });

        mEditBtn = (Button) rootView.findViewById(R.id.editBtn);
        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootActivity.onEditFirst(mFirst.getText().toString());
            }
        });
        Bundle args = getArguments();
        if(args != null){
            mLast.setText(args.getString(InputInfoFragment.LASTNAME));
            mFirst.setText(args.getString(InputInfoFragment.FIRSTNAME));
            mDate.setText(args.getString(InputInfoFragment.BIRTHDATE));
            mPlace.setText(args.getString(InputInfoFragment.BIRTHPLACE));
            mDept.setText(args.getString(InputInfoFragment.DEPT));
            mPhones = args.getStringArray(InputPhoneFragment.PHONEARRAY);
            if (mPhones != null && mPhones.length > 0) {
                mPhoneLayout.removeAllViews();
                if (mPhones.length != 0) {
                    for (int i = 0; i < mPhones.length; i++) {
                        LayoutInflater inflater = (LayoutInflater) rootActivity.getSystemService(rootActivity.LAYOUT_INFLATER_SERVICE);
                        LinearLayout phoneView = (LinearLayout) inflater.inflate(R.layout.number_layout, null);
                        mPhoneLayout.addView(phoneView);
                        TextView phoneNb = (TextView) phoneView.findViewById(R.id.userPhone);
                        phoneNb.setText(mPhones[i]);
                        Button dialBtn = (Button) phoneView.findViewById(R.id.dialBtn);
                        dialBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("Display", phoneNb.getText().toString());
                                Intent dialIntent = new Intent();
                                dialIntent.setAction(Intent.ACTION_DIAL);
                                dialIntent.putExtra("phone", phoneNb.getText().toString());
                                startActivity(dialIntent);
                            }
                        });
                    }
                }
            }
        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        rootActivity = (DisplayActivity) getActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(InputInfoFragment.LASTNAME, mLast.getText().toString());
        outState.putString(InputInfoFragment.FIRSTNAME, mFirst.getText().toString());
        outState.putString(InputInfoFragment.BIRTHDATE, mDate.getText().toString());
        outState.putString(InputInfoFragment.BIRTHPLACE, mPlace.getText().toString());
        outState.putString(InputInfoFragment.DEPT, mDept.getText().toString());
        outState.putStringArray(InputPhoneFragment.PHONEARRAY, mPhones);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            onViewCreated(rootView, savedInstanceState);
        }
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EditActivity.EDITFIRST & resultCode == Activity.RESULT_OK){
            mFirst.setText(data.getExtras().getString("first"));
        }
    }*/

    public void setFirst(String name){
        if(mFirst != null){
            mFirst.setText(name);
        }

    }

    public String  getFirst(){ return mFirst.getText().toString(); }
}