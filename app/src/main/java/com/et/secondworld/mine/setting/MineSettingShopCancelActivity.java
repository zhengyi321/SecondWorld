package com.et.secondworld.mine.setting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.et.secondworld.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/16
 **/
public class MineSettingShopCancelActivity extends AppCompatActivity {

    @OnClick(R.id.rly_mine_setting_shop_cancel_back)
    public void rlyMineSettingShopCancelBackOnclick(){
        finish();
    }
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_setting_shop_cancel);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
    }
}
