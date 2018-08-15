package test.com.androidtest.logonscreen.existservicemode;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

import static android.content.Context.WINDOW_SERVICE;

/**
 * 创建日期：18/7/19 on 下午7:00.
 * 作者：liuxun
 * 描述：
 */

public class LogOnScreenHelper {
    private static final String TAG = "LogScrren";
    private TextView mTextView;
    private final Queue<String> mLines = new ArrayDeque<>();
    private WindowManager mWm;
    private LogOptions mOptions;
    private ScrollView mScrollView;

    void initView(final Context context) {
        mOptions = getDefautOpitons();
        creatTextViewAndSet(context);
    }

    private void creatTextViewAndSet(final Context context) {
        TextView textView = new TextView(context);
        WeakReference<TextView> textViewWeakReference = new WeakReference<TextView>(textView);
        mTextView = textViewWeakReference.get();
        mScrollView = new ScrollView(context);
        mWm = (WindowManager) context.getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        if (mWm != null) {
            params.width = mWm.getDefaultDisplay().getWidth() / 2;
            params.height = mWm.getDefaultDisplay().getHeight();
            params.gravity = Gravity.RIGHT | Gravity.TOP;
            mWm.addView(mScrollView, params);

            mScrollView.addView(mTextView);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mTextView.getLayoutParams());
            params.gravity = Gravity.VERTICAL_GRAVITY_MASK;
            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
            mTextView.setLayoutParams(layoutParams);
        } else {
        }
    }

    private static LogOptions getDefautOpitons() {
        return new LogOptions.Builder()
                .numberOfLines(50)
                .backgroundColor(Color.parseColor("#00000000"))
                .textColor(Color.RED)
                .textSize(13)
                .build();

    }

    void displayText(String text) {
        Log.d("thread", "isMainThread = " + (Thread.currentThread() == Looper.getMainLooper().getThread()));
        addData(text);
        if (mLines.size() > mOptions.numberOfLines) {
            deleteData();
        }
        redrawAndCheckViewheight();
    }

    /**
     * 检查view 的高度，如果高于屏幕的高度就删除数据直到可见为止
     */
    private synchronized void redrawAndCheckViewheight() {
        if (mTextView.getHeight() > mWm.getDefaultDisplay().getHeight() * 9 / 10) {
            deleteData();
            redraw(mLines);
        } else {
            redraw(mLines);
        }
    }


    private void deleteData() {
        mLines.poll();
    }

    /**
     * 1. 对于过长的数据需要裁剪
     */
    private void addData(String text) {
        if (text.length() > 200) {
            text = text.substring(0, 200);
        }
        mLines.add(text);
    }

    private void redraw(Collection<String> texts) {
        mTextView.setTextSize(mOptions.textSize);
        mTextView.setTextColor(mOptions.textColor);
        Spannable spannable = new SpannableString(TextUtils.join("\n", texts));
        spannable.setSpan(new BackgroundColorSpan(mOptions.backgroundColor), 0, spannable.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spannable);
    }

    void onRelese(Context context) {
        if (mTextView != null) {
            WindowManager wm = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            if (wm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (mTextView.isAttachedToWindow()) {
                        wm.removeView(mScrollView);
                    }
                } else {
                    wm.removeView(mScrollView);
                }
            }
        }
    }
}
