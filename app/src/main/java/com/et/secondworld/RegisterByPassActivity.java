package com.et.secondworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.et.secondworld.bean.RegisterBean;
import com.et.secondworld.network.RegisterLoginNetWork;
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
 * @Date 2020/6/20
 **/
public class RegisterByPassActivity extends AppCompatActivity {


    @OnClick(R.id.rly_register_by_pass_save)
    public void rlyRegisterByPassSaveOnclick(){
        registerByPass();
    }
    private LocalBroadcastManager localBroadcastManager;
    @BindView(R.id.et_register_by_pass_pass1)
    EditText etRegisterByPassPass1;
    @BindView(R.id.et_register_by_pass_pass2)
    EditText etRegisterByPassPass2;
    @BindView(R.id.tv_register_by_pass_tel)
    TextView tvRegisterByPassTel;
    @OnClick(R.id.rly_register_by_pass_back)
    public void rlyRegisterByPassBackOnclick(){
        finish();
    }
    private String tel ="";
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login_set_pass);
        init();
    }


    private void init(){
        ButterKnife.bind(this);
        getData();
    }


    private void getData(){
        Intent intent = getIntent();
        tel = intent.getStringExtra("tel");
        tvRegisterByPassTel.setText("+86 "+tel);
    }


    private void registerByPass(){
        String pass1 = etRegisterByPassPass1.getText().toString();
        String pass2 = etRegisterByPassPass2.getText().toString();
        if(!pass1.equals(pass2)){
            Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterLoginNetWork registerLoginNetWork = new RegisterLoginNetWork();
        Map<String,Object> paramMap = new HashMap<>();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        Log.d("aa11",xcCacheManager.readCache(xcCacheSaveName.userTel));
        paramMap.put("account",tel);
//        Toast.makeText(getApplicationContext(),"222"+paramMap.get("account"),Toast.LENGTH_LONG).show();
        paramMap.put("platform","android");
        paramMap.put("pass",etRegisterByPassPass1.getText().toString());

        registerLoginNetWork.registerByPassToNet(paramMap,new Observer<RegisterBean>() {
            @Override
            public void onCompleted() {
//                Toast.makeText(getApplicationContext(),"onCompleted",Toast.LENGTH_LONG).show();
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
//                    xcCacheManager.writeCache(xcCacheSaveName.guid,registerBean.getId());
//                    xcCacheManager.writeCache(xcCacheSaveName.account,registerBean.getId());
//                    xcCacheManager.writeCache(xcCacheSaveName.shopId,registerBean.getShopid());
                    xcCacheManager.writeCache(xcCacheSaveName.userTel, tel);
                    regHuanXin(registerBean.getId());
                    regHuanXin(registerBean.getShopid());
                    localBroadcastManager = LocalBroadcastManager.getInstance(getBaseContext());
                    Intent intent2 = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
                    intent2.putExtra("account",registerBean.getId());
                    localBroadcastManager.sendBroadcast(intent2); // 发送本地广播

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
