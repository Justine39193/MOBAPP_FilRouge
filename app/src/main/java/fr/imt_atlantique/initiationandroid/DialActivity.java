package fr.imt_atlantique.initiationandroid;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DialActivity extends AppCompatActivity {

    private TextView phoneNb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        phoneNb = (TextView) findViewById(R.id.phoneNb);

        Bundle data = getIntent().getExtras();
        phoneNb.setText(data.getString("phone"));
    }

    public void callNb(View view) {
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:"+ phoneNb.getText().toString()));
        try {
            startActivity(dialIntent);
        } catch (ActivityNotFoundException e){
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage(R.string.dialError);
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

    public void cancelDial(View view) {
        finish();
    }
}
