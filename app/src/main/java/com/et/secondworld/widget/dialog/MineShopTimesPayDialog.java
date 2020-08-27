package com.et.secondworld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.dialog.PayAwayDialog;
import com.et.secondworld.network.SpreadNetWork;
import com.et.secondworld.utils.WindowUtils;
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
 * @Date 2020/4/28
 **/
public class MineShopTimesPayDialog extends Dialog {

    Context context;
    private Activity activity;
    private String shopid="";
    private int type = 0;//0为用户 1为店铺
    public interface OnFinishClickListener{
        public void getMaps(Map<Object, Object> map);
    }
    public interface OnDataListClickListener{
        public void getData(ArrayList<JsonBean> dataList);
    }
    private OnFinishClickListener onFinishClickListener;
    private OnDataListClickListener onDataListClickListener;
    public MineShopTimesPayDialog(Context context1) {
        super(context1);
        context = context1;
//        init(context);
//        setPopupWindow();
    }
    public MineShopTimesPayDialog(Context context1, int themeResId) {
        super(context1, themeResId);
        this.context = context1;
    }
    public Builder Build = new Builder(context);
    //用Builder模式来构造Dialog
    public  class Builder {
        private Context mContext;
        public Builder(Context mContext) {
            this.mContext = mContext;
        }
        private ArrayList<String> options1Items = new ArrayList<>();
         MineShopTimesPayDialog shopTimesPayDialog;
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
            options1Items.add("一天");
            options1Items.add("二天");
            ProvCityAreaOptionsPickerView pvOptions = new ProvCityAreaOptionsPickerView.Builder(view.getContext(), new ProvCityAreaOptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
              /*  String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);*/
                    String tx = "";
                    String province = options1Items.get(options1);
                    tvShopTimesPayDays.setText(province);

             /*       page = 1;
                    getData2FromNet();*/
//                    tvMineEditDataLocate.setText(tx);
                    /*   Toast.makeText(activity,tx,Toast.LENGTH_SHORT).show();*/
                }
            })

                    .setTitleText("选择天数")
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(15)
                    .setOutSideCancelable(true)// default is true
                    .build();

        pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器
//            pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
//        pvOptions.setDialogOutSideCancelable(true);
            pvOptions.show();
        }
        @BindView(R.id.tv_shop_times_pay_hours)
        TextView tvShopTimesPayHours;
        @OnClick(R.id.tv_shop_times_pay_hours)
        public void tvShopTimesPayHoursOnclick(){

        }
        @BindView(R.id.tv_shop_times_pay_days)
        TextView tvShopTimesPayDays;
        @OnClick(R.id.tv_shop_times_pay_days)
        public void tvShopTimesPayDaysOnclick(){

        }
        @BindView(R.id.tv_shop_times_pay_fact_pay)
        TextView tvShopTimesPayFactPay;
        @OnClick(R.id.rly_shop_times_pay)
        public void rlyShopTimesPayOnclick(){
            PayAwayDialog selectTownDialog = new PayAwayDialog(activity).Build.setOnPaySuccessfulListener(new PayAwayDialog.OnPaySuccessfulListener() {
                @Override
                public void isSuccessful(boolean isSuccessful) {
                    if(isSuccessful){
                        fireToNet();
                    }else {

                    }
                }
            }).build(activity,activity,""+price,"加火","文章加火",true);
//            }).build(activity,activity,""+0.01,"加火","文章加火",true);
            selectTownDialog.show();

        }
        private void fireToNet(){
            Map<String,Object>map = new HashMap<>();
            map.put("articleid",articleid);
            map.put("type",type);
            SpreadNetWork spreadNetWork = new SpreadNetWork();
            spreadNetWork.spreadTopToNet(map, new Observer<BaseBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseBean baseBean) {
                    Toast.makeText(getContext(),baseBean.getMsg(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        View view;
        String articleid = "";
        String type = "";
        int price = 0;
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public MineShopTimesPayDialog build(Context context,String articleid1,String type1,int price) {
            activity = (Activity)context;
            articleid = articleid1;
            this.price = price;

            type = type1;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            shopTimesPayDialog = new MineShopTimesPayDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            shopTimesPayDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
             view = mInflater.inflate(R.layout.dialog_shop_times_pay, null);
            ButterKnife.bind(this,view);
            tvShopTimesPayFactPay.setText("实付：￥"+price);
            shopTimesPayDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = shopTimesPayDialog.getWindow().getAttributes();
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(mContext);
//
//
            WindowUtils windowUtils = new WindowUtils(activity);
            layoutParams.width = (int)windowUtils.getWindowWidth();
//            layoutParams.height = (int)windowUtils.getWindowHeight()-82;
//            layoutParams.height = (int)windowUtils.getWindowHeight();
            layoutParams.gravity = Gravity.BOTTOM;
//            Log.d("width",""+windowUtils.getWindowWidth());
            shopTimesPayDialog.getWindow().setAttributes(layoutParams);


            shopTimesPayDialog.setContentView(view);
            initCheckBox();
            retShopTimesPayNums.setRegion(100,0);
            retShopTimesPayNums.setTextWatcher();
            return shopTimesPayDialog;
        }

        private void initCheckBox(){
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





}
