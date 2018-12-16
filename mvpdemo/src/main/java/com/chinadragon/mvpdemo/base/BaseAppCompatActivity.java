package com.chinadragon.mvpdemo.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chinadragon.commonutilslibrary.LogUtil;
import com.chinadragon.commonutilslibrary.ToastUtil;
import com.chinadragon.mvpdemo.broadcastreceiver.BaseBroadcastreceiver;
import com.chinadragon.mvpdemo.utils.TUtil;

import butterknife.ButterKnife;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/2 16:05
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public abstract class BaseAppCompatActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity implements BaseActivityInterFace, BaseView, BaseBroadcastreceiver.BaseBroadCastReceiverInterface {

    private BaseBroadcastreceiver mBaseBroadcastreceiver;
    public P mPresenter;
    public M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (null != mPresenter) {
            mPresenter.attachModelView(mModel, this);
        }


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initView(savedInstanceState);
        initData();
        initEvent();
        if (mBaseBroadcastreceiver == null) {
            mBaseBroadcastreceiver = new BaseBroadcastreceiver(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(mBaseBroadcastreceiver, intentFilter);
        }
    }

    public abstract int getLayoutId();

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void showError(String tipText) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void closeProgress() {

    }

    public void logInfo(String text) {
        if (null == text) {
            return;
        }
        LogUtil.i(text);
    }

    public void logE(String text) {
        if (null == text) {
            return;
        }
        LogUtil.e(text);
    }

    public void showToastShort(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        ToastUtil.showShort(text);
    }

    @Override
    public void isNetworkAvailable(int type) {
//        switch (type) {
//            case NetWorkState.NETWORK_NONE:
//                LogUtil.i("===== NETWORK_NONE");
//                ToastUtil.showShort("app 当前没有网络可用");
//                break;
//            case NetWorkState.NETWORK_MOBILE:
//                LogUtil.i("===== NETWORK_MOBILE");
//                ToastUtil.showShort("app 切换到移动网络/流量");
//                break;
//            case NetWorkState.NETWORK_WIFI:
//                LogUtil.i("===== NETWORK_WIFI");
//                ToastUtil.showShort("app 已连接到 WLAN 网络" + NetWorkState.mNetWorkName);
//                break;
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ToastUtil.cancelToast();
    }

    @Override
    protected void onDestroy() {
        if (mBaseBroadcastreceiver != null) {
            unregisterReceiver(mBaseBroadcastreceiver);
            mBaseBroadcastreceiver = null;
        }
        if (null != mPresenter) {
            mPresenter.dettachModelView();
        }
        super.onDestroy();
    }

    /**
     *利用android的事件分发机制，点击EditText之外空白处隐藏软键盘输入窗口。
     *
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] location = {0, 0};
            v.getLocationInWindow(location);
            int left = location[0], top = location[1], right = left + v.getWidth(), bottom = top + v.getHeight();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            } else
                return true;
        }
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
