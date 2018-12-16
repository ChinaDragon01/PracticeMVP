package com.chinadragon.mvpdemo.app;

import android.app.Application;

import com.chinadragon.commonutilslibrary.CommonUtils;
import com.chinadragon.mvpdemo.constant.AppConstants;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/2 20:07
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CommonUtils.init(this, AppConstants.LOG_NAME, AppConstants.LOG_MODE, null);
    }
}
