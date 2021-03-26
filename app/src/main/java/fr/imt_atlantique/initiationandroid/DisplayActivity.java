package fr.imt_atlantique.initiationandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import fr.imt_atlantique.initiationandroid.displayIntents.EditActivity;
import fr.imt_atlantique.initiationandroid.displayIntents.EditFragment;

public class DisplayActivity extends AppCompatActivity implements EditFragment.OnEditInterface, DisplayFragment.OnActionInterface {

    static final String USER = "user";
    private User mUser;

    private FragmentTransaction ft;

    private DisplayFragment displayFrag;
    private EditFragment editFrag;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        displayFrag = new DisplayFragment();
        editFrag = new EditFragment();
        if(savedInstanceState != null){
            displayFrag = DisplayFragment.newInstance(savedInstanceState.getString(InputInfoFragment.LASTNAME),
                    savedInstanceState.getString(InputInfoFragment.FIRSTNAME), savedInstanceState.getString(InputInfoFragment.BIRTHDATE),
                    savedInstanceState.getString(InputInfoFragment.BIRTHPLACE), savedInstanceState.getString(InputInfoFragment.DEPT),
                    savedInstanceState.getStringArray(InputPhoneFragment.PHONEARRAY));
            editFrag = EditFragment.newInstance(savedInstanceState.getString(InputInfoFragment.FIRSTNAME));
            if(displayFrag.isAdded()){
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.displayLayout, displayFrag);
                ft.commit();
            }
            else if(editFrag.isAdded()){
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.displayLayout, editFrag);
                ft.commit();
            }
        }

        else if (savedInstanceState == null){
            Bundle data = getIntent().getExtras();
            mUser = (User) data.getParcelable(USER);
            Log.i("Display", "DisplayActivity: User's lastname is: " + mUser.getUserLast());
            editFrag = EditFragment.newInstance(mUser.getUserFirst());
            displayFrag = DisplayFragment.newInstance(mUser.getUserLast(), mUser.getUserFirst(), mUser.getUserDate(),
                    mUser.getUserPlace(), mUser.getUserDept(), mUser.getUserPhones());
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.displayLayout, displayFrag);
            ft.commit();
        }


    }

    @Override
    public void onViewLast(String last) {

    }

    @Override
    public void onEditFirst(String first) {
        editFrag = EditFragment.newInstance(first);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.displayLayout, editFrag);
        ft.addToBackStack(editFrag.getClass().toString());
        ft.commit();
    }

    @Override
    public void onDialNumber(String number) {

    }

    @Override
    public void onEditOk(String name) {
        displayFrag = DisplayFragment.newInstance(mUser.getUserLast(), name, mUser.getUserDate(),
                mUser.getUserPlace(), mUser.getUserDept(), mUser.getUserPhones());
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.displayLayout, displayFrag);
        ft.addToBackStack(displayFrag.getClass().toString());
        ft.commit();
    }

    @Override
    public void onEditCancel() {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.displayLayout, displayFrag);
        ft.addToBackStack(displayFrag.getClass().toString());
        ft.commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(USER, mUser);
        if (displayFrag.isAdded()){
            displayFrag.onSaveInstanceState(outState);
        }
        else if (editFrag.isAdded()){
            editFrag.onSaveInstanceState(outState);
        }

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mUser = (User) savedInstanceState.getParcelable(USER);
        if(displayFrag.isAdded()){
            displayFrag.onViewStateRestored(savedInstanceState);
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(editFrag);
            ft.add(R.id.displayLayout, displayFrag);
            ft.commit();
        }
        else if(editFrag.isAdded()){
            editFrag.onViewStateRestored(savedInstanceState);
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(displayFrag);
            ft.add(R.id.displayLayout, editFrag);
            ft.commit();
        }


    }
}
