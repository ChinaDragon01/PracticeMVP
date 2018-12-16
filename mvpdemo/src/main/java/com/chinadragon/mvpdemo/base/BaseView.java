package com.chinadragon.mvpdemo.base;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/4 2:10
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public interface BaseView {
    void showError(String tipText);
    void showProgress();
    void closeProgress();
}
