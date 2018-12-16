package com.chinadragon.mvpdemo.contract;

import com.chinadragon.mvpdemo.base.BaseModel;
import com.chinadragon.mvpdemo.base.BasePresenter;
import com.chinadragon.mvpdemo.base.BaseView;
import com.chinadragon.mvpdemo.entity.BmiPersonInfo;

/**
 * **********************************************************************
 * Author: zhangbenlong
 * Time: 2018/9/4 15:14
 * Name:
 * Overview:
 * Usage:
 * **********************************************************************
 */
public interface MainContract {
    interface MainView extends BaseView {
        BmiPersonInfo getPersonInfo();
//        void showPersonInfo(BmiPersonInfo bmiPersonInfo);
        void showBmiReport(String bmiReport);
    }

    interface MainModel extends BaseModel {
        void savePersonInfo(BmiPersonInfo bmiPersonInfo);
        BmiPersonInfo loadPersonInfo();
    }


    abstract class Presenter extends BasePresenter<MainModel, MainView> {
        public abstract boolean savePersonInfo();
        public abstract void loadPersonInfo();
    }
}
