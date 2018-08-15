package test.com.androidtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import test.com.androidtest.logonscreen.existservicemode.LogScreenManager;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mContainer;
    private Button mLoadBtn;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = (RelativeLayout) findViewById(R.id.container_rl);
        mLoadBtn = (Button) findViewById(R.id.button_load_web);
        loadWebView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void load(View view) {
        mLoadBtn.setVisibility(View.GONE);
        mContainer.setVisibility(View.VISIBLE);

    }

    public void getParams(@NonNull String msg) {

    }

    private void loadWebView() {
        LandWebViewUtil landWebViewUtil = new LandWebViewUtil();
        landWebViewUtil.initWebView(this, mContainer);
        landWebViewUtil.loadBaseUrl();
    }

    public void addTextLog(View view) {
        LogScreenManager.log("i mainActivity message " + (++i));
    }
}
