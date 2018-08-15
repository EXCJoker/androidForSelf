package test.com.androidtest.ad;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.InMobiNative;
import com.inmobi.sdk.InMobiSdk;

import org.json.JSONObject;

import java.util.Map;

/**
 * 创建日期：18/6/5 on 下午5:38.
 * 作者：liuxun
 * 描述：
 */

public class InMobiTest {
    private Activity mActivity;

    public void getasdd(Context context) {
        String version = InMobiSdk.getVersion();
        InMobiSdk.init(context, "APPID?");
        InMobiInterstitial interstitial = new InMobiInterstitial(context, 12323, new InMobiInterstitial.InterstitialAdListener2() {
            @Override
            public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {

            }

            @Override
            public void onAdReceived(InMobiInterstitial inMobiInterstitial) {

            }

            @Override
            public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {

            }

            @Override
            public void onAdRewardActionCompleted(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {

            }

            @Override
            public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {

            }

            @Override
            public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {

            }

            @Override
            public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {

            }

            @Override
            public void onAdInteraction(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {

            }

            @Override
            public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {

            }

            @Override
            public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {

            }
        });
        interstitial.load();
        boolean ready = interstitial.isReady();
        interstitial.show();

        InMobiBanner inMobiBanner = new InMobiBanner(mActivity, 123);

        inMobiBanner.setListener(new InMobiBanner.BannerAdListener() {
            @Override
            public void onAdLoadSucceeded(InMobiBanner inMobiBanner) {

            }

            @Override
            public void onAdLoadFailed(InMobiBanner inMobiBanner, InMobiAdRequestStatus inMobiAdRequestStatus) {

            }

            @Override
            public void onAdDisplayed(InMobiBanner inMobiBanner) {

            }

            @Override
            public void onAdDismissed(InMobiBanner inMobiBanner) {

            }

            @Override
            public void onAdInteraction(InMobiBanner inMobiBanner, Map<Object, Object> map) {

            }

            @Override
            public void onUserLeftApplication(InMobiBanner inMobiBanner) {

            }

            @Override
            public void onAdRewardActionCompleted(InMobiBanner inMobiBanner, Map<Object, Object> map) {

            }
        });
        inMobiBanner.load();

        InMobiNative inMobiNative = new InMobiNative(context, 123, new InMobiNative.NativeAdListener() {
            @Override
            public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNative) {

            }

            @Override
            public void onAdLoadFailed(@NonNull InMobiNative inMobiNative, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {

            }

            @Override
            public void onAdFullScreenDismissed(InMobiNative inMobiNative) {

            }

            @Override
            public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {

            }

            @Override
            public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {

            }

            @Override
            public void onUserWillLeaveApplication(InMobiNative inMobiNative) {

            }

            @Override
            public void onAdImpressed(@NonNull InMobiNative inMobiNative) {

            }

            @Override
            public void onAdClicked(@NonNull InMobiNative inMobiNative) {

            }

            @Override
            public void onMediaPlaybackComplete(@NonNull InMobiNative inMobiNative) {

            }

            @Override
            public void onAdStatusChanged(@NonNull InMobiNative inMobiNative) {

            }

            @Override
            public void onUserSkippedMedia(@NonNull InMobiNative inMobiNative) {

            }
        });
        inMobiNative.load();

        inMobiNative.isReady();
        inMobiNative.isAppDownload();
        JSONObject adMetaInfo = inMobiNative.getAdMetaInfo();

    }
}
