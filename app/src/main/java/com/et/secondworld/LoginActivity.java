package com.et.secondworld;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.et.secondworld.bean.RegisterBean;
import com.et.secondworld.mine.setting.MineSettingSercetPolicyActivity;
import com.et.secondworld.mine.setting.MineSettingServiceAgreeMentActivity;
import com.et.secondworld.network.AdsNetWork;
import com.et.secondworld.network.RegisterLoginNetWork;
import com.et.secondworld.utils.UniversalID;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/11
 **/
public class LoginActivity extends AppCompatActivity {


   /* @BindView(R.id.et_login_forget_pass_identify_one)
    EditText etLoginForetPassIdentifyOne;
    @BindView(R.id.et_login_forget_pass_identify_two)
    EditText etLoginForetPassIdentifyTwo;*/
   @BindView(R.id.et_login_tel)
    EditText etLoginTel;
   @BindView(R.id.et_login_pass)
   EditText etLoginPass;
    private long clickTime = 0;
   @OnClick(R.id.tv_login_agree_ment)
   public void tvLoginAgreeMentOnclick(){
       if(System.currentTimeMillis() - clickTime > 2000) {

           clickTime = System.currentTimeMillis();
           Intent intent = new Intent(this, MineSettingServiceAgreeMentActivity.class);
           startActivity(intent);
       }
   }
   @OnClick(R.id.tv_login_policy)
   public void tvLoginPolicyOnclick(){
       if(System.currentTimeMillis() - clickTime > 2000) {

           clickTime = System.currentTimeMillis();
           Intent intent = new Intent(this, MineSettingSercetPolicyActivity.class);
           startActivity(intent);
       }
   }
   @BindView(R.id.cb_login_agree)
    CheckBox cbLoginAgree;

   @BindView(R.id.rly_login_login)
    RelativeLayout rlyLoginLogin;
   @OnClick(R.id.rly_login_login)
   public void rlyLoginLoginOnclick(){
       /*identifyTelNum();*/
       if(!cbLoginAgree.isChecked()) {
           Toast.makeText(this,"请同意论坛协议",Toast.LENGTH_SHORT).show();
           return;
       }
       if(System.currentTimeMillis() - clickTime > 2000) {

           clickTime = System.currentTimeMillis();
           login();
       }

   }
   @OnClick(R.id.rly_login_forget_pass)
   public void rlyLoginForgetPassOnclick(){
       if(!cbLoginAgree.isChecked()) {
           Toast.makeText(this,"请同意论坛协议",Toast.LENGTH_SHORT).show();
           return;
       }
       if(System.currentTimeMillis() - clickTime > 2000) {

           clickTime = System.currentTimeMillis();
           Intent intent = new Intent(this, ForgetPassActivity.class);
           startActivity(intent);
       }
//       finish();
   }

   @OnClick(R.id.rly_login_forget_register)
   public void rlyLoginForgetRegisterOnclick(){
       if(!cbLoginAgree.isChecked()) {
           Toast.makeText(this,"请同意论坛协议",Toast.LENGTH_SHORT).show();
           return;
       }
       if(System.currentTimeMillis() - clickTime > 2000) {

           clickTime = System.currentTimeMillis();
           Intent intent = new Intent(this, LoginNoPassActivity.class);
           startActivity(intent);
       }
//       finish();
   }
   @OnClick(R.id.rly_login_no_pass_login)
   public void rlyLoginNoPassLoginOnclick(){
       if(!cbLoginAgree.isChecked()) {
           Toast.makeText(this,"请同意论坛协议",Toast.LENGTH_SHORT).show();
           return;
       }
       if(System.currentTimeMillis() - clickTime > 2000) {

           clickTime = System.currentTimeMillis();
           Intent intent = new Intent(this, LoginNoPassActivity.class);
           startActivity(intent);
       }
//       finish();
   }
    private LocalBroadcastManager localBroadcastManager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_login_pass);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        ButterKnife.bind(this);

//        initStatusBar();
//        AdsNetWork adsNetWork = new AdsNetWork();

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
    private void login(){
        Map<String,Object> map = new HashMap<>();
        String tel = etLoginTel.getText().toString();
        map.put("account",tel);
        map.put("platform","android");
        map.put("uuuid", UniversalID.getUniversalID(this));
        map.put("pass",etLoginPass.getText().toString());
        RegisterLoginNetWork registerLoginNetWork = new RegisterLoginNetWork();
        registerLoginNetWork.loginToNet(map, new Observer<RegisterBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RegisterBean registerBean) {
                if(registerBean.getIssuccess().equals("1")) {
                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                    XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                    String account = registerBean.getId();
                    String shopid = registerBean.getShopid();
                    if(account == null || account.isEmpty()){
                        account = "";
                    }
                    if(shopid == null || shopid.isEmpty()){
                        shopid = "";
                    }

                    xcCacheManager.writeCache(xcCacheSaveName.guid, account);
                    xcCacheManager.writeCache(xcCacheSaveName.account, account);
                    xcCacheManager.writeCache(xcCacheSaveName.shopId, shopid);
                    xcCacheManager.writeCache(xcCacheSaveName.role, registerBean.getRole());
                    xcCacheManager.writeCache(xcCacheSaveName.userTel, tel);
                    regHuanXin(registerBean.getId());
                    regHuanXin(registerBean.getShopid());
                    localBroadcastManager = LocalBroadcastManager.getInstance(getBaseContext());
                    Intent intent2 = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                    intent2.putExtra("account",registerBean.getId());
                    localBroadcastManager.sendBroadcast(intent2); // 发送本地广播
                    String role = registerBean.getRole();
                    if(role != null && role.equals("00")){
                        Toast.makeText(getBaseContext(),"你被封号中",Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Toast.makeText(getBaseContext(),registerBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void regHuanXin(String registerid){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    if(registerid == null || !registerid.isEmpty()){
                        return;
                    }
                    EMClient.getInstance().createAccount(registerid,"11");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // save current user
//								Toast.makeText(getApplicationContext(),"恭喜注册成功！！！", Toast.LENGTH_SHORT).show();
                            Log.d("注册环信", "Regist: onSuccess");
                            /*           activity.finish();*/
                        }
                    });
                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode=e.getErrorCode();
                            if(errorCode== EMError.NETWORK_ERROR){
//									Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_ALREADY_EXIST){
//									Toast.makeText(getApplicationContext(),"用户已存在！", Toast.LENGTH_SHORT).show();
                                Log.d("注册环信", "用户已存在！");
                            }else if(errorCode == EMError.USER_AUTHENTICATION_FAILED){
//									Toast.makeText(getApplicationContext(),"注册失败，无权限！", Toast.LENGTH_SHORT).show();
                            }else if(errorCode == EMError.USER_ILLEGAL_ARGUMENT){
//								    Toast.makeText(getApplicationContext(),"用户名不合法",Toast.LENGTH_SHORT).show();
                            }else{
//									Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        }).start();
    }

/*
    private void identifyTelNum(){
       String tel = etLoginTel.getText().toString();
       if(tel.length() != 11){
           Toast.makeText(this,"请正确输入11位手机号码",Toast.LENGTH_SHORT).show();
       }else{
           XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
           XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);

           xcCacheManager.writeCache(xcCacheSaveName.userTel,tel);
           Intent intent = new Intent(this,RegisterActivity.class);
           startActivity(intent);
           finish();
       }
    }*/

//    https://www.jianshu.com/p/3c8f06992513
   /* private void identifyInput(){
        etLoginForetPassIdentifyOne.setFocusable(true);
        etLoginForetPassIdentifyOne.setFocusableInTouchMode(true);
        etLoginForetPassIdentifyOne.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        int length = etLoginForetPassIdentifyOne.getText().length();
        etLoginForetPassIdentifyOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    etLoginForetPassIdentifyTwo.setFocusable(true);
                    etLoginForetPassIdentifyTwo.setFocusableInTouchMode(true);
                    etLoginForetPassIdentifyTwo.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLoginForetPassIdentifyTwo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    etLoginForetPassIdentifyOne.setFocusable(true);
                    etLoginForetPassIdentifyOne.setFocusableInTouchMode(true);
                    etLoginForetPassIdentifyOne.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }*/
   @Override
    public void onResume(){
       super.onResume();
       XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
       XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
       String tel = xcCacheManager.readCache(xcCacheSaveName.userTel);
       etLoginTel.setText(tel);
   }
}
