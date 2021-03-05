package fr.imt_atlantique.initiationandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    static final String LASTNAME ="prefLast";
    static final String FIRSTNAME = "prefFirst";
    static final String BIRTHDATE = "prefDate";
    static final String BIRTHPLACE = "prefPlace";
    static final String DEPT = "prefDept";
    static final String PHONEARRAY = "prefPhones";
    static final String USER = "user";

    private TextView mLast;
    private TextView mFirst;
    private TextView mDate;
    private TextView mPlace;
    private Spinner mDept;
    private Button mValidate;

    private int currentDay;
    private int currentMonth;
    private int currentYear;
    private Calendar c;

    private LinearLayout mPhoneLayout;
    private LinearLayout mAddNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing the different views & objects
        setContentView(R.layout.activity_main);
        mLast = (TextView) findViewById(R.id.lastname);
        mFirst = (TextView) findViewById(R.id.firstname);
        mDate = (TextView) findViewById(R.id.birthDate);
        mPlace = (TextView) findViewById(R.id.birthPlace);
        mDept = (Spinner) findViewById(R.id.dept);
        mValidate = (Button) findViewById(R.id.validateButton);

        mPhoneLayout = (LinearLayout) findViewById(R.id.phoneLayout);
        mAddNumber = (LinearLayout) findViewById(R.id.addNumber);

        c = Calendar.getInstance();
        currentYear = c.get(Calendar.YEAR);
        currentMonth = c.get(Calendar.MONTH);
        currentDay = c.get(Calendar.DAY_OF_MONTH);

        Log.i("Lifecycle", "onCreate method");

        if(savedInstanceState != null){
            mLast.setText(savedInstanceState.getCharSequence(LASTNAME).toString());
            mFirst.setText(savedInstanceState.getCharSequence(FIRSTNAME).toString());
            mDate.setText(savedInstanceState.getCharSequence(BIRTHPLACE).toString());
            mPlace.setText(savedInstanceState.getCharSequence(BIRTHPLACE).toString());
            mDept.setSelection(savedInstanceState.getInt(DEPT));

            String[] phoneArray = savedInstanceState.getStringArray(PHONEARRAY);
            for(int i = 0; i < phoneArray.length; i++){
                LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
                LinearLayout phoneView = (LinearLayout) inflater.inflate(R.layout.phone_number, null);
                mPhoneLayout.addView(phoneView, mPhoneLayout.getChildCount() - 1);
                TextView currentNumber = (TextView) phoneView.findViewById(R.id.newNumber);
                currentNumber.setText(phoneArray[i]);
            }
        }
        else {
            mDate.setText(String.format("%02d", currentDay) + "/" + String.format("%02d", currentMonth + 1) + "/" + currentYear);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void validate(View view){
        if(mLast.getText() == null || mFirst.getText() == null || mPlace.getText() == null || mDept.getSelectedItemId() == 0){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage(R.string.missingItem);
            alertBuilder.setTitle(R.string.warningTitle);
            alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        }
        else {
            Intent mIntent = new Intent(this, DisplayActivity.class);
            User mUser = new User(mLast.getText().toString(),
                    mFirst.getText().toString(),
                    mDate.getText().toString(),
                    mPlace.getText().toString(),
                    mDept.getSelectedItem().toString(),
                    phoneToArray(mPhoneLayout));
            mIntent.putExtra(USER, mUser);
            startActivity(mIntent);
        }

        /*String[] phoneArray = phoneToArray(mPhoneLayout);
        String phoneNbs = "";
        for (int i = 0; i < phoneArray.length; i++) {
            phoneNbs = phoneNbs + " #" + i + ", " + phoneArray[i] +  "; ";
        }
        String textToShow = getString(R.string.registered) + " "
                + mFirst.getText() + " " + mLast.getText() + ", "
                + mDate.getText() + ", "
                + mPlace.getText() + ", "
                + mDept.getSelectedItem().toString() + "\n"
                + getString(R.string.allNumbers) + phoneNbs;
        Snackbar snackMessage = Snackbar.make((View) findViewById(R.id.mainLayout), textToShow, Snackbar.LENGTH_INDEFINITE);
        snackMessage.setAction(R.string.dismiss, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackMessage.dismiss(); //Not necessary!
            }
        })
                .show();
        Log.i("Display", textToShow);*/
    }

    public void resetAction(MenuItem item) {
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
        mPhoneLayout.removeAllViews();
        mPhoneLayout.addView(mAddNumber,0);
    }

    public void addNumber(View view) {
        //Add a new layout by inflating it (and retrieving the pre-created layout)
        LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
        LinearLayout phoneView = (LinearLayout) inflater.inflate(R.layout.phone_number, null);
        mPhoneLayout.addView(phoneView, mPhoneLayout.getChildCount() - 1);
    }

    public void deleteNumber(View view){
        mPhoneLayout.removeView((View) view.getParent());
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

    public void setBirthDate(View view) {
        Intent dateIntent = new Intent();
        dateIntent.setAction(Intent.ACTION_PICK);
        dateIntent.putExtra("dayOfMonth", currentDay);
        dateIntent.putExtra("month", currentMonth);
        dateIntent.putExtra("year", currentYear);
        startActivityForResult(dateIntent, 0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle", "onStart method");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle", "onStop method");
        Bundle outState = new Bundle();
        onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle", "onResume method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onResume method");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i("Lifecycle", "onPause method");
    }

    @Override
    protected  void onRestart(){
        super.onRestart();
        Log.i("Lifecycle", "onRestart method");
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putCharSequence(LASTNAME, mLast.getText());
        outState.putCharSequence(FIRSTNAME, mFirst.getText());
        outState.putCharSequence(BIRTHDATE, mDate.getText());
        outState.putCharSequence(BIRTHPLACE, mPlace.getText());
        outState.putInt(DEPT, mDept.getSelectedItemPosition());

        String[] phoneNbs = phoneToArray(mPhoneLayout);
        outState.putStringArray(PHONEARRAY, phoneNbs);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        mPhoneLayout.removeAllViews();
        mPhoneLayout.addView(mAddNumber,0);
        //Retrieve the previous info:
        mLast.setText(savedInstanceState.getCharSequence(LASTNAME).toString());
        mFirst.setText(savedInstanceState.getCharSequence(FIRSTNAME).toString());
        mDate.setText(savedInstanceState.getCharSequence(BIRTHDATE).toString());
        mPlace.setText(savedInstanceState.getCharSequence(BIRTHPLACE).toString());
        mDept.setSelection(savedInstanceState.getInt(DEPT));

        String[] phoneArray = savedInstanceState.getStringArray(PHONEARRAY);
        for(int i = 0; i < phoneArray.length; i++){
            LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            LinearLayout phoneView = (LinearLayout) inflater.inflate(R.layout.phone_number, null);
            mPhoneLayout.addView(phoneView, mPhoneLayout.getChildCount() - 1);
            TextView currentNumber = (TextView) phoneView.findViewById(R.id.newNumber);
            currentNumber.setText(phoneArray[i]);
        }

    }

    public void wikiSearch(MenuItem item) {
        String url = getString(R.string.wiki_url) + mPlace.getText().toString();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_WEB_SEARCH);
        sendIntent.putExtra(SearchManager.QUERY, url);
        try {
            startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage(R.string.wikiError);
            alertBuilder.setTitle(R.string.errorTitle);
            alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        }

    }

    public void sharePlace(MenuItem item) {
        if(mPlace.getText().length() == 0){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage(R.string.noPlaceWarning);
            alertBuilder.setTitle(R.string.warningTitle);
            alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        }
        else {
            String msg = getString(R.string.shareMsg) + " " + mPlace.getText().toString() + ".";
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
            shareIntent.setType("text/plain");

            String chooserTitle = getResources().getString(R.string.shareChooser);
            Intent chooser = Intent.createChooser(shareIntent, chooserTitle);

            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }

            try {
                startActivity(shareIntent);
            } catch (ActivityNotFoundException e) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setMessage(R.string.shareError);
                alertBuilder.setTitle(R.string.errorTitle);
                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            currentDay = data.getExtras().getInt("day");
            currentMonth = data.getExtras().getInt("month");
            currentYear = data.getExtras().getInt("year");
            mDate.setText(String.format("%02d", currentDay) + "/" + String.format("%02d", currentMonth + 1) + "/" + currentYear);
        }
    }
}