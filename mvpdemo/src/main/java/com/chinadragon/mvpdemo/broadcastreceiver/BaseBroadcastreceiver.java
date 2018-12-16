package com.chinadragon.mvpdemo.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.chinadragon.commonutilslibrary.NetWorkState;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/2 20:26
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public class BaseBroadcastreceiver extends BroadcastReceiver {
    private BaseBroadCastReceiverInterface mBaseBroadCastReceiverInterface;

    public BaseBroadcastreceiver(BaseBroadCastReceiverInterface baseBroadCastReceiverInterface) {
        mBaseBroadCastReceiverInterface = baseBroadCastReceiverInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null){
            return;
        }

        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            mBaseBroadCastReceiverInterface.isNetworkAvailable(NetWorkState.isNetworkAvailable());
        }

    }
    public interface BaseBroadCastReceiverInterface {
        void isNetworkAvailable(int type);
    }
}
