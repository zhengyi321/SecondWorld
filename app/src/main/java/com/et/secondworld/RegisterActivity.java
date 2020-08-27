package com.et.secondworld;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.et.secondworld.utils.SMSUtils;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.et.secondworld.bean.RegisterBean;
import com.et.secondworld.network.RegisterLoginNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/15
 **/
public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.rly_login_register_submit)
    RelativeLayout rlyLoginRegisterSubmit;
    @OnClick(R.id.rly_login_register_submit)
    public void rlyLoginRegisterSubmitOnclick(){
//        Toast.makeText(getApplicationContext(),"111",Toast.LENGTH_LONG).show();
//        registerToNet();
//        Toast.makeText(getApplicationContext(),"222",Toast.LENGTH_LONG).show();
        String identify = etLoginRegisterIdentifyOne.getText().toString()+etLoginRegisterIdentifyTwo.getText().toString()+etLoginRegisterIdentifyThree.getText().toString()+etLoginRegisterIdentifyFour.getText().toString();
        if(identifycode.equals(identify)) {
            Intent intent = new Intent(this, RegisterByPassActivity.class);
            intent.putExtra("tel", tel);
            startActivity(intent);
            this.finish();
        }
    }
    @BindView(R.id.tv_login_register_tel)
    TextView tvLoginRegisterTel;
    @BindView(R.id.et_login_register_identify_one)
    EditText etLoginRegisterIdentifyOne;
    @BindView(R.id.et_login_register_identify_two)
    EditText etLoginRegisterIdentifyTwo;
    @BindView(R.id.et_login_register_identify_three)
    EditText etLoginRegisterIdentifyThree;
    @BindView(R.id.et_login_register_identify_four)
    EditText etLoginRegisterIdentifyFour;
    @BindView(R.id.tv_login_register_resend)
    TextView tvLoginRegisterReSend;
    @OnClick(R.id.tv_login_register_resend)
    public void tvLoginRegisterReSendOnclick(){
        if(second == 0){
            second = 50;
//            int random4 = new Random().nextInt(9999);
//            identifycode = ""+random4;
//            SMSUtils.sendSMS(identifycode,tel);
            Thread thread = new MyThread();
            thread.start();
        }
    }
    @OnClick(R.id.rly_login_register_back)
    public void rlyLoginRegisterBackOnclick(){
        finish();
    }
    private String tel ="";
    int second = 50;
    private String identifycode = "";
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_login_register_identify);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        getData();
        tvLoginRegisterTel.setText("+86 "+tel);

        Thread thread = new MyThread();
        thread.start();
        initIdentify();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    tvLoginRegisterReSend.setText("("+second+")后重发");
                    if(second == 0){
                        tvLoginRegisterReSend.setText("重发短信验证码");
                    }
                    /* pgMainSplash.setVisibility(View.GONE);*/
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public class MyThread extends Thread{

        @Override
        public void run(){
            int random4 = new Random().nextInt(9999);
            identifycode = ""+random4;
            SMSUtils.sendSMS(identifycode,tel);
            try {
                for(;second>0;second--) {
                    sleep(1000);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    private void initIdentify(){
        etLoginRegisterIdentifyOne.setFocusable(true);
        etLoginRegisterIdentifyOne.setFocusableInTouchMode(true);
        etLoginRegisterIdentifyOne.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        int length = etLoginRegisterIdentifyOne.getText().length();
        etLoginRegisterIdentifyOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginRegisterIdentifyOne.length() > 0) {
                    etLoginRegisterIdentifyTwo.setFocusable(true);
                    etLoginRegisterIdentifyTwo.setFocusableInTouchMode(true);
                    etLoginRegisterIdentifyTwo.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLoginRegisterIdentifyTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginRegisterIdentifyTwo.length() > 0) {
                    etLoginRegisterIdentifyThree.setFocusable(true);
                    etLoginRegisterIdentifyThree.setFocusableInTouchMode(true);
                    etLoginRegisterIdentifyThree.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {
                    etLoginRegisterIdentifyOne.setFocusable(true);
                    etLoginRegisterIdentifyOne.setFocusableInTouchMode(true);
                    etLoginRegisterIdentifyOne.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLoginRegisterIdentifyThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginRegisterIdentifyThree.length() > 0) {
                    etLoginRegisterIdentifyFour.setFocusable(true);
                    etLoginRegisterIdentifyFour.setFocusableInTouchMode(true);
                    etLoginRegisterIdentifyFour.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {
                    etLoginRegisterIdentifyTwo.setFocusable(true);
                    etLoginRegisterIdentifyTwo.setFocusableInTouchMode(true);
                    etLoginRegisterIdentifyTwo.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLoginRegisterIdentifyFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginRegisterIdentifyFour.length() > 0) {
//                    etResetPassIdentifyFour.setFocusable(true);
//                    etResetPassIdentifyFour.setFocusableInTouchMode(true);
//                    etResetPassIdentifyFour.requestFocus();
//                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {
                    etLoginRegisterIdentifyThree.setFocusable(true);
                    etLoginRegisterIdentifyThree.setFocusableInTouchMode(true);
                    etLoginRegisterIdentifyThree.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void getData(){
        Intent intent  = getIntent();
        tel = intent.getStringExtra("tel");
    }

    private void registerToNet(){
        RegisterLoginNetWork registerLoginNetWork = new RegisterLoginNetWork();
        Map<String,String> paramMap = new HashMap<>();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        Log.d("aa11",xcCacheManager.readCache(xcCacheSaveName.userTel));
        paramMap.put("account",xcCacheManager.readCache(xcCacheSaveName.userTel));
//        Toast.makeText(getApplicationContext(),"222"+paramMap.get("account"),Toast.LENGTH_LONG).show();
        paramMap.put("platform","android");

        registerLoginNetWork.registerToNet(paramMap,new Observer<RegisterBean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getApplicationContext(),"onCompleted",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable e) {
//                Toast.makeText(getApplicationContext(),"222"+e,Toast.LENGTH_LONG).show();
//                Log.d("aa11",e+"");
            }

            @Override
            public void onNext(RegisterBean registerBean) {
                Toast.makeText(getApplicationContext(),""+registerBean.getMsg(),Toast.LENGTH_LONG).show();
                if(registerBean.getIssuccess().equals("1")||registerBean.getIssuccess().equals("2")){
                    xcCacheManager.writeCache(xcCacheSaveName.guid,registerBean.getId());
                    xcCacheManager.writeCache(xcCacheSaveName.account,registerBean.getId());
                    xcCacheManager.writeCache(xcCacheSaveName.shopId,registerBean.getShopid());
                    regHuanXin(registerBean.getId());
                    regHuanXin(registerBean.getShopid());


                    finish();
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
}
