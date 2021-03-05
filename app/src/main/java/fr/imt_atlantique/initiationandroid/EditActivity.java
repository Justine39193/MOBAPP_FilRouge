package fr.imt_atlantique.initiationandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    static final int EDITFIRST = 2;

    private TextView mFirst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mFirst = (TextView) findViewById(R.id.firstEdit);
        if(savedInstanceState != null){
            mFirst.setText(savedInstanceState.getString("first"));
        }
        else{
            Bundle data = getIntent().getExtras();
            mFirst.setText(data.getString("first"));
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("first", mFirst.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mFirst.setText(savedInstanceState.getString("first"));
    }

    public void validateEdit(View view) {
        Log.i("Display", mFirst.getText().toString());
        Intent backIntent = new Intent();
        backIntent.putExtra("first", mFirst.getText().toString());
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }

    public void cancelEdit(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
