package test.com.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    private TextView mTv;
    private static StringBuilder mStringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        mTv = (TextView) findViewById(R.id.content);
    }

    public void doAnything(View view) {
    }

}
