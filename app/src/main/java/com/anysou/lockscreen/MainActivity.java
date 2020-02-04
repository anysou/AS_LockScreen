package com.anysou.lockscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Android一键锁屏实现  https://blog.csdn.net/zhuhuitao_struggle/article/details/80111363
 *
 * 获取系统管理权限   https://www.cnblogs.com/liyiran/p/5314854.html
 *
 * **/
public class MainActivity extends AppCompatActivity {

    private DevicePolicyManager policyManager;  //DPM 设备管理器
    private ComponentName componentName;        //组件名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //获取系统服务 获取设备管理应用的列表
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        LockScreen(null);
    }

    // 锁屏操作
    public void LockScreen(View v) {
        //如果没有激活设备管理员，提醒用户做事
        componentName = new ComponentName(this, LockReceiver.class);
        if (policyManager.isAdminActive(componentName)) {//判断是否有权限(激活了设备管理器)
            policyManager.lockNow();// 直接锁屏
            finish();
            // android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            activeManager();//激活设备管理器获取权限
            this.finish();
        }
    }

    // 解除绑定
    public void Bind(View v) {
        if (componentName != null) {
            policyManager.removeActiveAdmin(componentName);
            activeManager();
        }
    }

    //重写此方法用来在第一次激活设备管理器之后锁定屏幕 目前不起作用因为在打开activeManager进入设备管理器后finish掉了当前activity。
    //导致调不到此方法所以第一次激活后不会锁屏。
    @Override
    protected void onResume() {
        if (policyManager != null && policyManager.isAdminActive(componentName)) {
            policyManager.lockNow();
            finish();
        }
        super.onResume();
    }

    //使用隐式意图调用系统方法来激活指定的设备管理器
    private void activeManager() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏");
        startActivity(intent);
    }


}
