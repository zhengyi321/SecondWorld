package com.et.secondworld;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.et.secondworld.utils.SMSUtils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/19
 **/
public class ForgetPassIdentifyActivity extends AppCompatActivity {


    @BindView(R.id.tv_login_forget_pass_identify_tel)
    TextView tvLoginForgetPassIdentifyTel;
    @OnClick(R.id.rly_login_forget_pass_identify_back)
    public void rlyLoginForgetPassIdentifyBackOnclick(){
        finish();
    }
    @OnClick(R.id.rly_login_forget_pass_identify_submit)
    public void rlyLoginForgetPassIdentifySubmitOnclick(){
        if(type == 0){
            type = 1;
            Thread thread = new MyThread();
            thread.start();
            tvLoginForgetPassIdentifySubmit.setText("确认");
            return;
        }
        Intent intent = new Intent(this,ResetPassActivity.class);
        intent.putExtra("tel",tel);
        startActivity(intent);
        finish();
    }
    int type = 0;
    @BindView(R.id.et_login_forget_pass_identify_one)
    EditText etLoginForgetPassIdentifyOne;
    @BindView(R.id.et_login_forget_pass_identify_two)
    EditText etLoginForgetPassIdentifyTwo;
    @BindView(R.id.et_login_forget_pass_identify_three)
    EditText etLoginForgetPassIdentifyThree;
    @BindView(R.id.et_login_forget_pass_identify_four)
    EditText etLoginForgetPassIdentifyFour;
    @BindView(R.id.tv_login_forget_pass_identify_resend)
    TextView tvLoginForgetPassIdentifyReSend;
    @OnClick(R.id.tv_login_forget_pass_identify_resend)
    public void tvLoginForgetPassIdentifyResendOnclick(){
        if(second == 0){
            second = 50;
            Thread thread = new MyThread();
            thread.start();
        }
    }

    @BindView(R.id.tv_login_forget_pass_identify_submit)
    TextView tvLoginForgetPassIdentifySubmit;


    private String tel = "";
    int second = 50;
    private String identifycode = "";
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_login_forget_pass_identify);
        init();
    }
    private void init() {
        ButterKnife.bind(this);
//        AdsNetWork adsNetWork = new AdsNetWork();
        initIdentify();
        getData();

    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    tvLoginForgetPassIdentifyReSend.setText("("+second+")后重发");
                    if(second == 0){
                        tvLoginForgetPassIdentifyReSend.setText("重发短信验证码");
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
            int random4 = new Random().nextInt(8999)+1000;
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
        etLoginForgetPassIdentifyOne.setFocusable(true);
        etLoginForgetPassIdentifyOne.setFocusableInTouchMode(true);
        etLoginForgetPassIdentifyOne.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        int length = etLoginForgetPassIdentifyOne.getText().length();
        etLoginForgetPassIdentifyOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginForgetPassIdentifyOne.length() > 0) {
                    etLoginForgetPassIdentifyTwo.setFocusable(true);
                    etLoginForgetPassIdentifyTwo.setFocusableInTouchMode(true);
                    etLoginForgetPassIdentifyTwo.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLoginForgetPassIdentifyTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginForgetPassIdentifyTwo.length() > 0) {
                    etLoginForgetPassIdentifyThree.setFocusable(true);
                    etLoginForgetPassIdentifyThree.setFocusableInTouchMode(true);
                    etLoginForgetPassIdentifyThree.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {
                    etLoginForgetPassIdentifyOne.setFocusable(true);
                    etLoginForgetPassIdentifyOne.setFocusableInTouchMode(true);
                    etLoginForgetPassIdentifyOne.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLoginForgetPassIdentifyThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginForgetPassIdentifyThree.length() > 0) {
                    etLoginForgetPassIdentifyFour.setFocusable(true);
                    etLoginForgetPassIdentifyFour.setFocusableInTouchMode(true);
                    etLoginForgetPassIdentifyFour.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {
                    etLoginForgetPassIdentifyTwo.setFocusable(true);
                    etLoginForgetPassIdentifyTwo.setFocusableInTouchMode(true);
                    etLoginForgetPassIdentifyTwo.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLoginForgetPassIdentifyFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etLoginForgetPassIdentifyFour.length() > 0) {
//                    etResetPassIdentifyFour.setFocusable(true);
//                    etResetPassIdentifyFour.setFocusableInTouchMode(true);
//                    etResetPassIdentifyFour.requestFocus();
//                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {
                    etLoginForgetPassIdentifyThree.setFocusable(true);
                    etLoginForgetPassIdentifyThree.setFocusableInTouchMode(true);
                    etLoginForgetPassIdentifyThree.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void getData(){
        Intent intent = getIntent();
        tel = intent.getStringExtra("tel");
        tvLoginForgetPassIdentifyTel.setText("+86 "+tel);
    }
}
