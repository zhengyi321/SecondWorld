package com.et.secondworld;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.et.secondworld.mine.setting.MineSettingSercetPolicyActivity;
import com.et.secondworld.mine.setting.MineSettingServiceAgreeMentActivity;
import com.et.secondworld.network.AdsNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/19
 **/
public class LoginNoPassActivity extends AppCompatActivity {


    /* @BindView(R.id.et_login_forget_pass_identify_one)
     EditText etLoginForetPassIdentifyOne;
     @BindView(R.id.et_login_forget_pass_identify_two)
     EditText etLoginForetPassIdentifyTwo;*/
    @BindView(R.id.et_login_tel)
    EditText etLoginTel;
    @BindView(R.id.rly_login_get_identify)
    RelativeLayout rlyLoginGetIdentify;
    @OnClick(R.id.rly_login_get_identify)
    public void rlyLoginGetIdentifyOnclick(){
        identifyTelNum();

    }
    private long clickTime = 0;
    @OnClick(R.id.tv_login_policy)
    public void llyLoginPolicyOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingSercetPolicyActivity.class);
            startActivity(intent);
        }
    }
    @OnClick(R.id.tv_login_agree_ment)
    public void llyLoginAgreeMentOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingServiceAgreeMentActivity.class);
            startActivity(intent);
        }
    }
    @BindView(R.id.cb_login_agree)
    CheckBox cbLoginAgree;
    @OnClick(R.id.tv_login_pass)
    public void tvLoginPassOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @OnClick(R.id.rly_login_back)
    public void rlyLoginBackOnclick(){
        finish();
    }
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);
        init();
    }
    private void init() {
        ButterKnife.bind(this);
//        AdsNetWork adsNetWork = new AdsNetWork();



    }

    private void identifyTelNum(){
        String tel = etLoginTel.getText().toString();
        if(tel.length() != 11){
            Toast.makeText(this,"请正确输入11位手机号码",Toast.LENGTH_SHORT).show();
        }else{
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//
//            xcCacheManager.writeCache(xcCacheSaveName.userTel,tel);
            if(cbLoginAgree.isChecked()) {
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("tel", tel);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this,"请同意论坛协议",Toast.LENGTH_SHORT).show();
            }
        }
    }

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
}
