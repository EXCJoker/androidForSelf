/*
 * Copyright (C) 2014 Inaka.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Henrique Boregio (henrique@inakanetworks.com)
 */
package test.com.androidtest.logonscreen;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
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

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public class GalgoService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private TextView mTextView;
    private GalgoOptions mOptions;
    private final Queue<String> mLines = new ArrayDeque<>();
    private WindowManager mWm;

    public class LocalBinder extends Binder {
        public GalgoService getService() {
            return GalgoService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        this.mOptions = intent.getExtras().getParcelable(Galgo.ARG_OPTIONS);
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTextView = new TextView(this);
        ScrollView scrollView = new ScrollView(this);
        mWm = (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        if (mWm != null) {
            params.width = mWm.getDefaultDisplay().getWidth() / 2;
            params.height =mWm.getDefaultDisplay().getHeight();
            params.gravity = Gravity.RIGHT |Gravity.TOP;
            mWm.addView(scrollView, params);

            scrollView.addView(mTextView);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mTextView.getLayoutParams());
            params.gravity = Gravity.VERTICAL_GRAVITY_MASK;
            layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
            mTextView.setLayoutParams(layoutParams);
        }
    }

    public void displayText(String text) {
        Log.d("thread","isMainThread = " + (Thread.currentThread() == Looper.getMainLooper().getThread()));
        addData(text);
        if (mLines.size() > mOptions.numberOfLines) {
            deleteData();
        }
        redrawAndCheckViewheight();
    }

    /**
     * 检查view 的高度，如果高于屏幕的高度就删除数据直到可见为止
     */
    public synchronized void redrawAndCheckViewheight() {
        if (mTextView.getHeight() > mWm.getDefaultDisplay().getHeight() * 9 / 10) {
            deleteData();
            redraw(mLines);
        } else {
            redraw(mLines);
        }
    }


    public void deleteData() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTextView != null) {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            if (wm != null) {
                wm.removeView(mTextView);
            }
        }
    }
}