package com.et.secondworld;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.RegisterBean;
import com.et.secondworld.network.RegisterLoginNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/19
 **/
public class ResetPassActivity  extends AppCompatActivity {


    @BindView(R.id.et_resetpass_pass1)
    EditText etResetPassPass1;
    @BindView(R.id.et_resetpass_pass2)
    EditText etResetPassPass2;
    @BindView(R.id.rly_resetpass_save)
    RelativeLayout rlyResetPassSave;
    @BindView(R.id.tv_resetpass_tel)
    TextView tvResetPassTel;
    @OnClick(R.id.rly_resetpass_back)
    public void rlyResetPassBackOnclick(){
        finish();
    }
    @OnClick(R.id.rly_resetpass_save)
    public void rlyResetPassSaveOnclick(){
        resetPass();
    }
    private String tel = "";
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login_reset_pass);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
//        AdsNetWork adsNetWork = new AdsNetWork();
        getData();

    }

    private void getData(){
        Intent intent = getIntent();
        tel = intent.getStringExtra("tel");
        tvResetPassTel.setText("+86 "+tel);
    }

    private void resetPass(){
        String pass1 = etResetPassPass1.getText().toString();
        String pass2 = etResetPassPass2.getText().toString();
        if(!pass1.equals(pass2)){
            Toast.makeText(this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("account",tel);
        map.put("platform","android");
        map.put("pass",pass1);
        RegisterLoginNetWork registerLoginNetWork = new RegisterLoginNetWork();
        registerLoginNetWork.resetPassToNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean registerBean) {
                if(registerBean.getIssuccess().equals("1")) {
                    Toast.makeText(getBaseContext(),registerBean.getMsg(),Toast.LENGTH_SHORT).show();
                    XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                    XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                    xcCacheManager.writeCache(xcCacheSaveName.account,"");
                    Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getBaseContext(),registerBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}