package com.et.secondworld.mine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.et.secondworld.LoginActivity;
import com.et.secondworld.MainActivity;
import com.et.secondworld.R;
import com.et.secondworld.mine.setting.MineSettingAboutUSActivity;
import com.et.secondworld.mine.setting.MineSettingMessageNoticeActivity;
import com.et.secondworld.mine.setting.MineSettingHelpAndBackActivity;
import com.et.secondworld.mine.setting.MineSettingManageActivity;
import com.et.secondworld.mine.setting.MineSettingSercetPolicyActivity;
import com.et.secondworld.mine.setting.MineSettingServiceAgreeMentActivity;
import com.et.secondworld.mine.setting.MineSettingShopCancelActivity;
import com.et.secondworld.mine.setting.MineSettingTradeRecordActivity;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.QueryCancelDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/16
 **/
public class MineSettingActivity extends AppCompatActivity {

    @BindView(R.id.rly_mine_setting_back)
    RelativeLayout rlyMineSettingBack;
    @OnClick(R.id.rly_mine_setting_back)
    public void rlyMineSettingBackOnclick(){
        finish();
    }
    @BindView(R.id.rly_mine_setting_account_manage)
    RelativeLayout rlyMineSettingAccountManage;
    private long clickTime = 0;
    @OnClick(R.id.rly_mine_setting_account_manage)
    public void rlyMineSettingAccountManageOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingManageActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.rly_mine_setting_perfect_data)
    public void rlyMineSettingPerfectDataOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MinePerfectDataActivity.class);
            startActivity(intent);
        }
    }

    @BindView(R.id.rly_mine_setting_message_notice)
    RelativeLayout rlyMineSettingMessageNotice;
    @OnClick(R.id.rly_mine_setting_message_notice)
    public void rlyMineSettingMessageNoticeOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingMessageNoticeActivity.class);
            startActivity(intent);
        }
    }

    @BindView(R.id.rly_mine_setting_trade_record)
    RelativeLayout rlyMineSettingTradeRecord;
    @OnClick(R.id.rly_mine_setting_trade_record)
    public void rlyMineSettingTradeRecordOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingTradeRecordActivity.class);
            startActivity(intent);
        }
    }
    @BindView(R.id.rly_mine_setting_help_and_back)
    RelativeLayout rlyMineSettingHelpAndBack;
    @OnClick(R.id.rly_mine_setting_help_and_back)
    public void rlyMineSettingHelpAndBackOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingHelpAndBackActivity.class);
            startActivity(intent);
        }
    }

    @BindView(R.id.rly_mine_setting_service_agreement)
    RelativeLayout rlyMineSettingServiceAgreement;
    @OnClick(R.id.rly_mine_setting_service_agreement)
    public void rlyMineSettingServiceAgreementOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingServiceAgreeMentActivity.class);
            startActivity(intent);
        }
    }
    @BindView(R.id.rly_mine_setting_secret_policy)
    RelativeLayout rlyMineSettingSecretPolicy;
    @OnClick(R.id.rly_mine_setting_secret_policy)
    public void rlyMineSettingSecretPolicyOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingSercetPolicyActivity.class);
            startActivity(intent);
        }
    }

    @BindView(R.id.rly_mine_setting_about_us)
    RelativeLayout rlyMineSettingAboutUS;
    @OnClick(R.id.rly_mine_setting_about_us)
    public void rlyMineSettingAboutUSOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingAboutUSActivity.class);
            startActivity(intent);
        }
    }

    @BindView(R.id.rly_mine_setting_shop_cancel)
    RelativeLayout rlyMineSettingShopCancel;
    @OnClick(R.id.rly_mine_setting_shop_cancel)
    public void rlyMineSettingShopCancelOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingShopCancelActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.rly_mine_setting_login_out)
    public void rlyMineSettingLoginOutOnclick(){
        QueryCancelDialog queryCancelDialog = new QueryCancelDialog(this).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
            @Override
            public void isQuery(boolean isQuery) {
                if(isQuery){
                    XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                    xcCacheManager.writeCache(xcCacheSaveName.account,"");
                    xcCacheManager.writeCache(xcCacheSaveName.shopId,"");
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);

                    finish();
                }
            }
        }).build(this,"确定退出登陆?");
        queryCancelDialog.show();

//        String account = xcCacheManager.readCache(xcCacheSaveName.account);
//        if(account == null || account.isEmpty() || account == "") {
//
//            return;
//        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_setting);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initStatusBar();
    }


    /*沉浸式状态栏*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initStatusBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.blue);
//        switch (type) {
//            case "index":
//                tintManager.setStatusBarTintResource(R.color.color_main_index_topbar_blue_bg);
//                break;
//            case "release":
//                tintManager.setStatusBarTintResource(R.color.color_main_release_topbar_blue_bg);
//                break;
//            case "advice":
//                tintManager.setStatusBarTintResource(R.color.color_main_advice_content_white_bg);
//                break;
//            case "message":
//                tintManager.setStatusBarTintResource(R.color.color_main_message_content_white_bg);
//                break;
//            case "mine":
//                tintManager.setStatusBarTintResource(R.mipmap.top_big_blue_bg);
//                break;
//        }
    }
    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void setTranslucentStatus(boolean on) {
       /* Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.white ));


    }

    @Override
    protected void onResume(){
        super.onResume();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();


        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(account == null || account.isEmpty() || account == "") {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
    }
}
