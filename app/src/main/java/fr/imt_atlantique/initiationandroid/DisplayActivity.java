package fr.imt_atlantique.initiationandroid;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {

    static final String USER = "user";
    private User mUser;

    private TextView mLast;
    private TextView mFirst;
    private TextView mDate;
    private TextView mPlace;
    private TextView mDept;
    private LinearLayout mPhoneLayout;
    private String[] mPhones;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Bundle data = getIntent().getExtras();
        mUser = (User) data.getParcelable(USER);
        Log.i("Display", "User's lastname is: " + mUser.getUserLast());

        mLast = (TextView) findViewById(R.id.userLast);
        mFirst = (TextView) findViewById(R.id.userFirst);
        mDate = (TextView) findViewById(R.id.userDate);
        mPlace = (TextView) findViewById(R.id.userPlace);
        mDept = (TextView) findViewById(R.id.userDept);
        mPhoneLayout = (LinearLayout) findViewById(R.id.phoneLayout);

        mLast.setText(mUser.getUserLast());
        mFirst.setText(mUser.getUserFirst());
        mDate.setText(mUser.getUserDate());
        mPlace.setText(mUser.getUserPlace());
        mDept.setText(mUser.getUserDept());

        mPhones = mUser.getUserPhones();
        if(mPhones.length > 0) {
            mPhoneLayout.removeAllViews();
            if (mPhones.length != 0) {
                for (int i = 0; i < mPhones.length; i++) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
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

    public void viewLast(View view){
        Intent viewIntent = new Intent();
        viewIntent.setAction(Intent.ACTION_VIEW);
        viewIntent.putExtra("last", mLast.getText().toString());
        startActivity(viewIntent);
    }

    public void editFirst(View view){
        Intent editIntent = new Intent();
        editIntent.setAction(Intent.ACTION_EDIT);
        editIntent.putExtra("first", mFirst.getText().toString());
        startActivityForResult(editIntent, EditActivity.EDITFIRST);
    }

    public void dialNumber(View view) {
        Log.i("Display", "display");
        TextView phoneNb = (TextView) view.findViewById(R.id.userPhone);
        Log.i("Display", phoneNb.getText().toString());
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.putExtra("phone", phoneNb.getText().toString());
        startActivity(dialIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EditActivity.EDITFIRST & resultCode == Activity.RESULT_OK){
            mFirst.setText(data.getExtras().getString("first"));
        }
    }
}
