package com.chinadragon.mvpdemo.model;

import com.chinadragon.mvpdemo.contract.MainContract;
import com.chinadragon.mvpdemo.entity.BmiPersonInfo;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/4 16:39
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public class MainModelImpl implements MainContract.MainModel {
    private BmiPersonInfo mBmiPersonInfo;

    @Override
    public void savePersonInfo(BmiPersonInfo bmiPersonInfo) {
        mBmiPersonInfo = bmiPersonInfo;
    }

    @Override
    public BmiPersonInfo loadPersonInfo() {
        return mBmiPersonInfo;
    }
}
