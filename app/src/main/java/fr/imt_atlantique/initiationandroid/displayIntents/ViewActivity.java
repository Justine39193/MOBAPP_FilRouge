package fr.imt_atlantique.initiationandroid.displayIntents;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import fr.imt_atlantique.initiationandroid.R;

public class ViewActivity extends AppCompatActivity {

    private TextView mLast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        mLast = (TextView) findViewById(R.id.lastView);

        Bundle data = getIntent().getExtras();
        mLast.setText(data.getString("last"));
    }

    public void endView(View view) {
        finish();
    }
}
