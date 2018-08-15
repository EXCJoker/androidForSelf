package test.com.androidtest.logonscreen.existservicemode;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;


/**
 * 创建日期：18/7/19 on 下午7:09.
 * 作者：liuxun
 * 注：1. 因为屏幕大小限制的原因，展示的日志尽量短，如果过长会被裁剪{@link LogOnScreenHelper#addData(String)}
 * 2. 同意因为屏幕大小的限制，尽量只展示需要实时性较高的数据
 */

public class LogScreenManager {

    private static final String TAG = "LogScrren";
    private static LogOnScreenHelper mLogOnScreenHelper;
    private static boolean isEnable = false;

    private static final Handler UI_HANDLER = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (null != mLogOnScreenHelper) {
                String message = (String) msg.obj;
                mLogOnScreenHelper.displayText(message);
            }
        }
    };

    private static void init(Context context) {
        if (mLogOnScreenHelper == null) {
            mLogOnScreenHelper = new LogOnScreenHelper();
        }
        mLogOnScreenHelper.initView(context);
    }

    /**
     * @param context Context
     */
    public static void enable(Context context) {
        isEnable = true;
        checkPermission(context);
        init(context);
    }

    public static void disable(Context context) {
        if (isEnable) {
            isEnable = false;
            if (mLogOnScreenHelper != null) {
                mLogOnScreenHelper.onRelese(context);
            }
        }
    }

    /**
     * Logs a String message to the screen. This String will be overlayed on top of the
     * UI elements currently displayed on screen. As a side effect, this message will
     * also be logged to the standard output via {@link Log}.
     *
     * @param message String to be displayed
     */
    public static void log(String message) {
        if (isEnable) {
            Log.i(TAG, message);
            Message msg = UI_HANDLER.obtainMessage(0, message);
            msg.sendToTarget();
        } else {
        }
    }

    private static void checkPermission(Context context) {
        String permission = "android.permission.SYSTEM_ALERT_WINDOW";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                throw new IllegalStateException("in order to use Galgo, " +
                        "please add the permission " + permission + " to your AndroidManifest.xml");
            } else {
            }
        } else {
            int status = context.checkCallingOrSelfPermission(permission);
            if (status == PackageManager.PERMISSION_DENIED) {
                throw new IllegalStateException("in order to use Galgo, " +
                        "please add the permission " + permission + " to your AndroidManifest.xml");
            }
        }
    }
}
