package com.otb.lnyp.myswipelistheader;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;

/**
 * Created by lining on 2016/2/22.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.configAllowLog = true;
        LogUtils.configTagPrefix = "-abc";
    }
}
