package test.com.androidtest.logonscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.com.androidtest.ad.MainActivity;
import test.com.androidtest.R;
import test.com.androidtest.logonscreen.existservicemode.LogScreenManager;
import test.com.androidtest.logonscreen.existservicemode.LogService;

public class LogActivity extends AppCompatActivity {
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        startService(new Intent(this, LogService.class));
    }

    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, LogService.class));
    }

    public void addLog(View view) {
        LogScreenManager.log("i LogActivity message " + (++i));
    }

    public void jumpto(View view) {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
