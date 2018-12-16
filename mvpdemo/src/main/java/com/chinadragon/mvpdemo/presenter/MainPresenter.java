package com.chinadragon.mvpdemo.presenter;

import android.os.Handler;

import com.chinadragon.mvpdemo.contract.MainContract;
import com.chinadragon.mvpdemo.entity.BmiPersonInfo;

import java.text.DecimalFormat;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/4 16:44
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public class MainPresenter extends MainContract.Presenter {

    private MainContract.MainView mMainView;

    @Override
    public boolean savePersonInfo() {
        mMainView = getView();
        BmiPersonInfo personInfo = mMainView.getPersonInfo();
        if (null == personInfo) {
            mMainView.showError("请输入身高或者体重！");
            return false;
        } else {
            mModel.savePersonInfo(personInfo);
            return true;
        }
    }

    @Override
    public void loadPersonInfo() {
        new Handler().postDelayed(() -> {
            if (mMainView == null || mModel.loadPersonInfo() == null) {
                mMainView.showError("没有获取到数据");
            } else {

                mMainView.showBmiReport(showResults(mModel.loadPersonInfo()));
            }
        }, 100);

    }

    private String showResults(BmiPersonInfo bmiPersonInfo) {
        DecimalFormat nf = new DecimalFormat("0.00");
        double height = Double.parseDouble(bmiPersonInfo.height) / 100;
        double weight = Double.parseDouble(bmiPersonInfo.weight);
        double BMI = weight / (height * height);
        String result = null;
        if (BMI > 25) {
            result = "你的 BMI 值是" + nf.format(BMI) + "\n需要注意饮食";
        } else if (BMI < 20) {
            result = "你的 BMI 值是" + nf.format(BMI) + "\n需要多吃点";

        } else {
            result = "你的 BMI 值是" + nf.format(BMI) + "\n身材好棒哦";
        }
        return result;
    }
}
