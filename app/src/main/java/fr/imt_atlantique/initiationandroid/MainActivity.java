package fr.imt_atlantique.initiationandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.view.ViewGroup;
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

    static final String USER = "user";

    private Button mValidate;

    private InputInfoFragment infoFrag;
    private InputPhoneFragment phoneFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing the different views & objects
        setContentView(R.layout.activity_main);
        mValidate = (Button) findViewById(R.id.validateButton);

        if(savedInstanceState != null){
            infoFrag = InputInfoFragment.newInstance(savedInstanceState.getString(InputInfoFragment.LASTNAME),
                    savedInstanceState.getString(InputInfoFragment.FIRSTNAME),
                    savedInstanceState.getString(InputInfoFragment.BIRTHDATE),
                    savedInstanceState.getString(InputInfoFragment.BIRTHPLACE),
                    savedInstanceState.getInt(InputInfoFragment.DEPT));
            phoneFrag = InputPhoneFragment.newInstance(savedInstanceState.getStringArray(InputPhoneFragment.PHONEARRAY));
        }
        else if (savedInstanceState == null){
            infoFrag = new InputInfoFragment();
            phoneFrag = new InputPhoneFragment();
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragLayout, infoFrag);
        ft.add(R.id.fragLayout, phoneFrag);
        ft.commit();


        Log.i("Lifecycle", "onCreate method");


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void validate(View view){
        if(infoFrag.isCompleted()){
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
            User mUser = new User(infoFrag.getLast(),
                    infoFrag.getFirst(),
                    infoFrag.getDate(),
                    infoFrag.getPlace(),
                    infoFrag.getDept(),
                    phoneFrag.getPhones());
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
        infoFrag.resetFields();
        phoneFrag.resetPhones();
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



    public void wikiSearch(MenuItem item) {
        String url = getString(R.string.wiki_url) + infoFrag.getPlace();
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
        if(infoFrag.getPlace().length() == 0){
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
            String msg = getString(R.string.shareMsg) + " " + infoFrag.getPlace() + ".";
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        infoFrag.onSaveInstanceState(outState);
        phoneFrag.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null){
            infoFrag.onViewStateRestored(savedInstanceState);
            phoneFrag.onViewStateRestored(savedInstanceState);
        }

    }
}