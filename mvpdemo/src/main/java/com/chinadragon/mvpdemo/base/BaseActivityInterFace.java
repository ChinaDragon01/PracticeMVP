package com.chinadragon.mvpdemo.base;

import android.os.Bundle;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/2 16:07
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public interface BaseActivityInterFace {
    void initView(Bundle savedInstanceState);
    void initData();
    void initEvent();
}
