package com.chinadragon.mvpdemo.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinadragon.commonutilslibrary.NetWorkState;
import com.chinadragon.commonutilslibrary.ScreenUtil;
import com.chinadragon.commonutilslibrary.time.WeakHandlerUtil;
import com.chinadragon.commonutilslibrary.widget.CustomDialog;
import com.chinadragon.mvpdemo.R;
import com.chinadragon.mvpdemo.base.BaseAppCompatActivity;
import com.chinadragon.mvpdemo.contract.MainContract;
import com.chinadragon.mvpdemo.entity.BmiPersonInfo;
import com.chinadragon.mvpdemo.model.MainModelImpl;
import com.chinadragon.mvpdemo.presenter.MainPresenter;

import butterknife.BindView;

public class MainActivity extends BaseAppCompatActivity<MainPresenter, MainModelImpl> implements MainContract.MainView {

    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.submit)
    Button submit;
    private static final int MIN_CLICK_TIME = 2000;
    private long clikcLastTime;
    private static final int MENU_ABOUT = Menu.FIRST;
    private static final int MENU_Quit = Menu.FIRST + 1;
    private int isNetworkAvailable;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        // 设置一下属性，可以自动弹出软键盘
        etHeight.setFocusable(true);
        etHeight.setFocusableInTouchMode(true);
        etHeight.requestFocus();
        new WeakHandlerUtil().postDelayed(() -> {
            // 由于界面未加载完成而无法弹出,需要适当延迟,比如延迟300毫秒:
            InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            im.showSoftInput(etHeight, 0);
        }, 300);

    }

    @Override
    public void initEvent() {
        super.initEvent();
        submit.setOnClickListener(v -> {
            boolean savePersonInfoResult = mPresenter.savePersonInfo();
            logInfo("savePersonInfoResult = " + savePersonInfoResult);
            if (savePersonInfoResult) {
                mPresenter.loadPersonInfo();
            }
        });
    }

    @Override
    public void isNetworkAvailable(int type) {
        super.isNetworkAvailable(type);
        isNetworkAvailable = type;

//        switch (type) {
//            case NetWorkState.NETWORK_NONE:
//                logInfo("===== NETWORK_NONE");
//                showToastShort("app 当前没有网络可用");
//                break;
//            case NetWorkState.NETWORK_MOBILE:
//                logInfo("===== NETWORK_MOBILE");
//                showToastShort("app 切换到移动网络/流量");
//                break;
//            case NetWorkState.NETWORK_WIFI:
//                logInfo("===== NETWORK_WIFI");
//                showToastShort("app 已连接到 WLAN 网络" + NetWorkState.mNetWorkName);
//                break;
//        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public BmiPersonInfo getPersonInfo() {
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();

        if (TextUtils.isEmpty(height) || TextUtils.isEmpty(weight)) {
            return null;
        } else {
            BmiPersonInfo bmiPersonInfo = new BmiPersonInfo(height, weight);
            return bmiPersonInfo;
        }
    }

    @Override
    public void showBmiReport(String bmiReport) {
        showDialog(bmiReport);
        logInfo("" + bmiReport);
    }

    @Override
    public void showError(String tipText) {
        super.showError(tipText);
        showToastShort(tipText);
    }

    private final void showDialog(String bmiReport) {
        CustomDialog customDialog = new CustomDialog(MainActivity.this, R.layout.dialog_bmi_result, Gravity.CENTER, (int) (ScreenUtil.getScreenWidth() * 0.7), LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvDialogCenter = customDialog.findViewById(R.id.tv_dialog_center);
        TextView tvBmiResult = customDialog.findViewById(R.id.tv_bmi_result);
        tvBmiResult.setText(bmiReport);
        tvDialogCenter.setOnClickListener(v -> customDialog.dialogDismiss(customDialog));
        customDialog.setCancelable(true);
        customDialog.setCanceledOnTouchOutside(true);
        customDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ABOUT, 0, getString(R.string.about_bmi)).setIcon(R.mipmap.help_browser);
        menu.add(0, MENU_Quit, 0, getString(R.string.finish)).setIcon(R.mipmap.emblem_unreadable);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ABOUT:
                menuAboutBMI();
                break;
            case MENU_Quit:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private final void menuAboutBMI() {
        if (NetWorkState.NETWORK_NONE == isNetworkAvailable) {
            showToastShort(getString(R.string.no_net));
        } else {
            Uri uri = Uri.parse(getString(R.string.ok_uri));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        logInfo("onKeyDown keyCode = " + keyCode + "，event.getAction = " + event.getAction());
        if (KeyEvent.ACTION_DOWN == event.getAction() && keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - clikcLastTime >= MIN_CLICK_TIME) {
                showToastShort(getString(R.string.key_back_tip));
                clikcLastTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
