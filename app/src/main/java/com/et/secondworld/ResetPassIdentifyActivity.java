package com.et.secondworld;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
public class ResetPassIdentifyActivity extends AppCompatActivity {


    @BindView(R.id.tv_reset_pass_identify_tel)
    TextView tvLoginForgetPassIdentifyTel;
    @BindView(R.id.tv_reset_pass_identify_submit)
    TextView tvResetPassIdentifySubmit;
    @BindView(R.id.et_reset_pass_identify_one)
    EditText etResetPassIdentifyOne;
    @BindView(R.id.et_reset_pass_identify_two)
    EditText etResetPassIdentifyTwo;
    @BindView(R.id.et_reset_pass_identify_three)
    EditText etResetPassIdentifyThree;
    @BindView(R.id.et_reset_pass_identify_four)
    EditText etResetPassIdentifyFour;
    @BindView(R.id.tv_reset_pass_identify_resend)
    TextView tvResetPassIdentifyReSend;
    @OnClick(R.id.tv_reset_pass_identify_resend)
    public void tvResetPassIdentifyReSendOnclick(){
        if(second == 0){
            second = 50;
            Thread thread = new MyThread();
            thread.start();
        }
    }
    @BindView(R.id.rly_reset_pass_identify_back)
    RelativeLayout rlyResetPassIdentifyBack;
    @OnClick(R.id.rly_reset_pass_identify_back)
    public void rlyResetPassIdentifyBackOnclick(){
        this.finish();
    }


    @OnClick(R.id.rly_reset_pass_identify_submit)
    public void rlyLoginForgetPassIdentifySubmitOnclick(){
        if(type == 0){
            type = 1;
            Thread thread = new MyThread();
            thread.start();
            tvResetPassIdentifySubmit.setText("确认");
            return;
        }
        String identify = etResetPassIdentifyOne.getText().toString()+etResetPassIdentifyTwo.getText().toString()+etResetPassIdentifyThree.getText().toString()+etResetPassIdentifyFour.getText().toString();
        if(identifycode.equals(identify)) {
            Intent intent = new Intent(this, ResetPassActivity.class);
            intent.putExtra("tel", tel);
            startActivity(intent);
            finish();
        }
    }
    int type = 0;
    private String tel = "";
    int second = 50;
    private String identifycode = "";
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_reset_pass_identify);
        init();
    }
    private void init() {
        ButterKnife.bind(this);
//        AdsNetWork adsNetWork = new AdsNetWork();

        getData();
        initIdentify();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    tvResetPassIdentifyReSend.setText("("+second+")后重发");
                    if(second == 0){
                        tvResetPassIdentifyReSend.setText("重发短信验证码");
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
        etResetPassIdentifyOne.setFocusable(true);
        etResetPassIdentifyOne.setFocusableInTouchMode(true);
        etResetPassIdentifyOne.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        int length = etResetPassIdentifyOne.getText().length();
        etResetPassIdentifyOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etResetPassIdentifyOne.length() > 0) {
                    etResetPassIdentifyTwo.setFocusable(true);
                    etResetPassIdentifyTwo.setFocusableInTouchMode(true);
                    etResetPassIdentifyTwo.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResetPassIdentifyTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etResetPassIdentifyTwo.length() > 0) {
                    etResetPassIdentifyThree.setFocusable(true);
                    etResetPassIdentifyThree.setFocusableInTouchMode(true);
                    etResetPassIdentifyThree.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {
                    etResetPassIdentifyOne.setFocusable(true);
                    etResetPassIdentifyOne.setFocusableInTouchMode(true);
                    etResetPassIdentifyOne.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResetPassIdentifyThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etResetPassIdentifyThree.length() > 0) {
                    etResetPassIdentifyFour.setFocusable(true);
                    etResetPassIdentifyFour.setFocusableInTouchMode(true);
                    etResetPassIdentifyFour.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {
                    etResetPassIdentifyTwo.setFocusable(true);
                    etResetPassIdentifyTwo.setFocusableInTouchMode(true);
                    etResetPassIdentifyTwo.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResetPassIdentifyFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etResetPassIdentifyFour.length() > 0) {
//                    etResetPassIdentifyFour.setFocusable(true);
//                    etResetPassIdentifyFour.setFocusableInTouchMode(true);
//                    etResetPassIdentifyFour.requestFocus();
//                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {
                    etResetPassIdentifyThree.setFocusable(true);
                    etResetPassIdentifyThree.setFocusableInTouchMode(true);
                    etResetPassIdentifyThree.requestFocus();
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
