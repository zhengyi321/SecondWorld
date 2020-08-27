package com.et.secondworld.shanhuxinxi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.et.secondworld.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShanHuXinXiActivity extends AppCompatActivity {

    @BindView(R.id.tv_shopname)
    TextView tvShopName;
    @OnClick(R.id.tv_shopname)
    public void tvShopNameOnclick(){
//        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanhuxinxi_rly);
        init();
    }
    private void init(){
        ButterKnife.bind(this);
    }
}
