package test.com.xiaomiad;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xiaomi.ad.adView.BannerAd;
import com.xiaomi.ad.common.pojo.AdEvent;

/**
 * Created by fangzheyuan on 16-7-13.
 */
public class BannerActivity extends Activity {

    public static final String TAG = "AD-BannerActivity";

    private static final String BANNER_POS_ID = "802e356f1726f9ff39c69308bfd6f06a";
    BannerAd mBannerAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        final ViewGroup container = (ViewGroup) findViewById(R.id.container);

        final Button fetchBtn = (Button) findViewById(R.id.fetchAd);
        mBannerAd = new BannerAd(getApplicationContext(), container, new BannerAd.BannerListener() {
            @Override
            public void onAdEvent(AdEvent adEvent) {
                if (adEvent.mType == AdEvent.TYPE_CLICK) {
                    Log.d(TAG, "ad has been clicked!");
                } else if (adEvent.mType == AdEvent.TYPE_SKIP) {
                    Log.d(TAG, "x button has been clicked!");
                } else if (adEvent.mType == AdEvent.TYPE_VIEW) {
                    Log.d(TAG, "ad has been showed!");
                }
            }
        });

        fetchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mBannerAd.show(BANNER_POS_ID);
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        mBannerAd.recycle();
        super.onDestroy();
    }
}
