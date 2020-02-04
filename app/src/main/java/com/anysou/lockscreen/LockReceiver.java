package com.anysou.lockscreen;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

/**
 * Android
 *
* 设备管理器，可以设置锁屏和清除数据。请查看API指南中的Administration
*/

public class LockReceiver extends DeviceAdminReceiver {
    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        System.out.println("onReceive");
    }

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
        System.out.println("激活使用");
        super.onEnabled(context, intent);
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
        System.out.println("取消激活");
        super.onDisabled(context, intent);
    }
}
