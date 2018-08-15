package test.com.androidtest.logonscreen.existservicemode;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 创建日期：18/7/20 on 上午9:59.
 * 作者：liuxun
 * 描述：
 */

public class LogService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogScreenManager.enable(this);
    }

    public void stop() {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogScreenManager.disable(this);
    }
}
