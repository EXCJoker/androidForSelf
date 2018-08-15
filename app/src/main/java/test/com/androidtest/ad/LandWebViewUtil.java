package test.com.androidtest.ad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 创建日期：17/12/28 on 下午3:37.
 * 作者：
 * 描述：
 */

public class LandWebViewUtil {
    private WebView mLandWebView;
    private Context mContext;

    public void initWebView(final Context context, RelativeLayout mLandpageRootLayout) {
        if (context == null) {
            return;
        }
        mContext = context;
        mLandWebView = new WebView(context);
        //设置WebView client
        setWebVieClient();
        //配置webView
        setupWebView();
        mLandWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        mLandWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mLandpageRootLayout.addView(mLandWebView, layoutParams);
        mLandWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.d("DASD", "URL = " + url);
                Toast.makeText(context, "downloadListener receive event", Toast.LENGTH_LONG).show();
            }
        });
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebSettings settings = mLandWebView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 设置缓存模式--无缓存
        settings.setAppCacheEnabled(false);// 关闭缓存
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setUseWideViewPort(true);//设置页面上的文本缩放百分比
        settings.setLoadWithOverviewMode(true);//设置加载进来的页面自适应手机屏幕
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);//设置渲染优先级
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //解决android 5.0 webview 不能加载http 和 https 混合内容问题
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setAllowFileAccess(true);//是否允许访问文件
        settings.setDomStorageEnabled(true);//DOM Storage   解决对某些标签的不支持出现白屏
    }

    private void setWebVieClient() {
        mLandWebView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return overrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
        mLandWebView.setWebChromeClient(new WebChromeClient() {
        });
    }

    private boolean overrideUrlLoading(WebView view, String url) {
        Log.d("DASD", "shouldOverrideUrlLoading URL = " + url);
        if (!isDeepLink(view, url)) {
            Log.d("DASD", "overrideUrlLoading  false ");
            view.loadUrl(url);
        }
            return true;
    }

    private boolean isDeepLink(WebView view, String url) {
        if (TextUtils.isEmpty(url) || url.contains("about:blank")) {
            return false;
        }

        if (!url.startsWith("http")) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("DASD", "isDeepLink e.printStackTrace= " + e.getMessage());
            }
            return true;
        }
        return false;
    }


    public void loadBaseUrl() {
        // 加载方式
        //       mLandWebView.loadData(encode(url), "text/html;charset=UTF-8", ""); 加载html 片段，需要encode 确保javaScript 没问题
        //        mLandWebView.loadUrl("file:///android_asset/jfhtml.html");
        // 加载地址

        //应用宝  下载链接点击不跳转，得点击右下角跳转
//        String url = "http://sj.qq.com/myapp/detail.htm?apkName=ctrip.android.view";

        //baidu listener
        //                String url = "https://mobile.baidu.com/item?docid=24041678&f0=home_homeCardRecommend%402_appBaseColumn%4010&advitem=source%2BEDITOR" +
        //                        "%40boardid%2B14450%40pos%2B11%40flow%2BBOARD%40trans%2BNULL%40global_optim%2B1" +
        //                        "%40access%2B0%40mode%2BNULL%40bid%2B1%40oriboardid%2B14450%40oripos%2B11%40exp%" +
        //                        "2Bexp1%40sample%2Badv%40searchid%2B3436706884982219677%40package%2Bcom.ledou.mhh" +
        //                        "y.bd%40packageid%2B3212592%40docid%2B24041678";

        //华为商场
                String url = "http://down2.uc.cn/wandj/down.php?id=211&pub=szweidaWDJ1";

        //小米商场  需要dom  有listener
//                String url = "http://app.mi.com/details?id=com.bingougame.tgsw.mi/";

        //安智市场
        //String url = "http://www.anzhi.com/pkg/a42e_com.tencent.tmgp.sgame.html";

        //        String url = "http://www.appchina.com/"; //应用汇  listener

        //        String url = "http://www.wandoujia.com/apps-com.taobao.taobao"; //豌豆荚 listener

//        String url = "http://d.91.com/Soft/iPhone/com.lego.city.rapidrescue-1.0.1-1.0.1.html"; //91市场 listener
        //String url = "http://www.liqucn.com/rj/155008.shtml"; //91市场 listener
        mLandWebView.loadUrl(url);

    }

    final String digits = "0123456789ABCDEF";

    public String encode(String s) {
        // Guess a bit bigger for encoded form
        StringBuilder buf = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
                    || (ch >= '0' && ch <= '9') || ".-*_".indexOf(ch) > -1) { //$NON-NLS-1$
                buf.append(ch);
            } else {
                byte[] bytes = new String(new char[]{ch}).getBytes();
                for (int j = 0; j < bytes.length; j++) {
                    buf.append('%');
                    buf.append(digits.charAt((bytes[j] & 0xf0) >> 4));
                    buf.append(digits.charAt(bytes[j] & 0xf));
                }
            }
        }
        return buf.toString();
    }
}
