package com.et.secondworld.find;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.et.secondworld.R;
import com.et.secondworld.bean.ShopTimesPayTimeBean;
import com.et.secondworld.network.SpreadNetWork;
import com.et.secondworld.widget.dialog.MineShopTimesPayDialog;
import com.et.secondworld.widget.edittext.RegionNumberEditText;
import com.et.secondworld.widget.timeselect.ProvCityAreaOptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/15
 **/
public class ForumShopTimesPayActivity extends AppCompatActivity {
    private ArrayList<String> options1ItemsDay = new ArrayList<>();
    private ArrayList<String> options1ItemsHour = new ArrayList<>();
    private ArrayList<String> options1ItemsMinutes = new ArrayList<>();


    @BindView(R.id.cb_shop_times_pay_times)
    CheckBox cbShopTimesPayTimes;
    @BindView(R.id.cb_shop_times_pay_single)
    CheckBox cbShopTimesPaySingle;
    @BindView(R.id.lly_shop_times_pay_times)
    LinearLayout llyShopTimesPayTimes;
    @BindView(R.id.ret_shop_times_pay_nums)
    RegionNumberEditText retShopTimesPayNums;
    @BindView(R.id.tv_shop_times_pay_minutes)
    TextView tvShopTimesPayMinutes;
    @OnClick(R.id.tv_shop_times_pay_minutes)
    public void tvShopTimesPayMinutesOnclick(){

        ProvCityAreaOptionsPickerView pvOptions = new ProvCityAreaOptionsPickerView.Builder(this, new ProvCityAreaOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
              /*  String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);*/
                String tx = "";
                String province = options1ItemsMinutes.get(options1)+"分钟";
                tvShopTimesPayMinutes.setText(province);
                tvShopTimesPayDays.setText("0天");
                tvShopTimesPayHours.setText("0小时");
//                tvShopTimesPayMinutes.setText("0分钟");
             /*       page = 1;
                    getData2FromNet();*/
//                    tvMineEditDataLocate.setText(tx);
                /*   Toast.makeText(activity,tx,Toast.LENGTH_SHORT).show();*/
            }
        })

                .setTitleText("选择分钟数(分钟)")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(15)
                .setOutSideCancelable(true)// default is true
                .build();

        pvOptions.setPicker(options1ItemsMinutes);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//            pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
//        pvOptions.setDialogOutSideCancelable(true);
        pvOptions.show();
    }
    @BindView(R.id.tv_shop_times_pay_hours)
    TextView tvShopTimesPayHours;
    @OnClick(R.id.tv_shop_times_pay_hours)
    public void tvShopTimesPayHoursOnclick(){
        ProvCityAreaOptionsPickerView pvOptions = new ProvCityAreaOptionsPickerView.Builder(this, new ProvCityAreaOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
              /*  String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);*/
                String tx = "";
                String province = options1ItemsHour.get(options1)+"小时";
                tvShopTimesPayHours.setText(province);
                tvShopTimesPayDays.setText("0天");
//                tvShopTimesPayHours.setText("0小时");
                tvShopTimesPayMinutes.setText("0分钟");
             /*       page = 1;
                    getData2FromNet();*/
//                    tvMineEditDataLocate.setText(tx);
                /*   Toast.makeText(activity,tx,Toast.LENGTH_SHORT).show();*/
            }
        })

                .setTitleText("选择小时(小时)")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(15)
                .setOutSideCancelable(true)// default is true
                .build();

        pvOptions.setPicker(options1ItemsHour);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//            pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
//        pvOptions.setDialogOutSideCancelable(true);
        pvOptions.show();
    }
    @BindView(R.id.tv_shop_times_pay_days)
    TextView tvShopTimesPayDays;
    @OnClick(R.id.tv_shop_times_pay_days)
    public void tvShopTimesPayDaysOnclick(){
        ProvCityAreaOptionsPickerView pvOptions = new ProvCityAreaOptionsPickerView.Builder(this, new ProvCityAreaOptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
              /*  String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);*/
                String tx = "";
                String province = options1ItemsDay.get(options1)+"天";
                tvShopTimesPayDays.setText(province);
                tvShopTimesPayHours.setText("0小时");
                tvShopTimesPayMinutes.setText("0分钟");

             /*       page = 1;
                    getData2FromNet();*/
//                    tvMineEditDataLocate.setText(tx);
                /*   Toast.makeText(activity,tx,Toast.LENGTH_SHORT).show();*/
            }
        })

                .setTitleText("选择天数(天数)")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(15)
                .setOutSideCancelable(true)// default is true
                .build();

        pvOptions.setPicker(options1ItemsDay);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//            pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
//        pvOptions.setDialogOutSideCancelable(true);
        pvOptions.show();
    }
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.dialog_shop_times_pay);
        init();

    }
    private void init(){
        ButterKnife.bind(this);
        initdata();
        initCheckBox();
    }
    private void initdata(){
        Intent intent = getIntent();
        String articleid = intent.getStringExtra("articleid");
        int type = intent.getIntExtra("type",-1);
        Map<String,Object> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("type",type+"");
        SpreadNetWork spreadNetWork = new SpreadNetWork();
        spreadNetWork.getShopTimesPayTimeFromNet(map, new Observer<ShopTimesPayTimeBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ShopTimesPayTimeBean shopTimesPayTimeBean) {
                Log.d("vsss11",shopTimesPayTimeBean.getMsg());
                String day = shopTimesPayTimeBean.getDay();
                String hour = shopTimesPayTimeBean.getHour();
                String minutes = shopTimesPayTimeBean.getMin();
//                Log.d("vsss11",day);
                if(day != null){
                    for(int i = 0;i<=Integer.parseInt(day);i++){
                        options1ItemsDay.add((i)+"");
                    }
                }
                if(hour != null){
                    if(Integer.parseInt(hour) > 23){
                        for(int i = 1;i<=23;i++){
                            options1ItemsHour.add(i+"");
                        }
                    }else {
                        for(int i = 1;i<=Integer.parseInt(hour);i++){
                            options1ItemsHour.add(i+"");
                        }
                    }
                }
                if(minutes != null){
                    if(Integer.parseInt(minutes) > 60){
                        for(int i = 10;i<=59;i++){
                            options1ItemsMinutes.add(i+"");
                        }
                    }else {
                        options1ItemsMinutes.add("0");
                    }
                }
            }
        });
    }

    private void initCheckBox(){
//        options1ItemsDay.add("一天");
//        options1ItemsDay.add("二天");
        cbShopTimesPaySingle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbShopTimesPayTimes.setChecked(false);
                }else {
                    cbShopTimesPayTimes.setChecked(true);
                }
            }
        });
        cbShopTimesPayTimes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbShopTimesPaySingle.setChecked(false);
                    llyShopTimesPayTimes.setVisibility(View.VISIBLE);
                }else {
                    cbShopTimesPaySingle.setChecked(true);
                    llyShopTimesPayTimes.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
