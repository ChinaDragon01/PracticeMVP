package com.chinadragon.mvpdemo.base;

import java.lang.ref.WeakReference;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/4 2:01
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public abstract class BasePresenter<M, V> {
    public M mModel;
    //    public V mView;
    public WeakReference<V> mViewRef;

    public void attachModelView(M pM, V pV) {
        this.mModel = pM;
//        this.mView = pV;
        mViewRef = new WeakReference<V>(pV);

    }

    public V getView() {
        return null != mViewRef && null != mViewRef.get() ? mViewRef.get() : null;
    }

    public void dettachModelView() {
        if (null != mViewRef) {
            mViewRef.clear();
            mViewRef = null;
        }
        mModel = null;
    }
}
