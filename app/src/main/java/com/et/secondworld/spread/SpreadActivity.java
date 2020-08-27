package com.et.secondworld.spread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.et.secondworld.R;
import com.et.secondworld.adapter.ExtendRVAdapter;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.GetPayBean;
import com.et.secondworld.dialog.PayAwayDialog;
import com.et.secondworld.mine.setting.MineSettingSercetPolicyActivity;
import com.et.secondworld.mine.setting.MineSettingServiceAgreeMentActivity;
import com.et.secondworld.network.PayNetWork;
import com.et.secondworld.network.SpreadNetWork;
import com.et.secondworld.widget.alipay.pay.ZhiFuBaoUtil;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.wxpay.VXPayUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/13
 **/
public class SpreadActivity extends AppCompatActivity {

    @BindView(R.id.rv_extend_loc)
    RecyclerView rvExtendLoc;
    @BindView(R.id.rly_extend_back)
    RelativeLayout rlyExtendBack;
    @OnClick(R.id.rly_extend_back)
    public void rlyExtendBackOnclick(){
        finish();
    }
    @BindView(R.id.iv_extend_img)
    ImageView ivExtendImg;
    List<String> imagePaths;
    @BindView(R.id.tv_extend_title)
    TextView tvExtendTitle;
    @BindView(R.id.tv_extend_nick)
    TextView tvExtendNick;
    @BindView(R.id.cb_extend_agreement)
    CheckBox cbExtendAgreeMent;
    @BindView(R.id.tv_extend_gold)
    TextView tvExtendGold;
    @BindView(R.id.tv_extend_fact_pay)
    TextView tvExtendFactPay;
    private String GET_IMGS ="GET_IMGS";
    @BindView(R.id.rly_extend_pay)
    RelativeLayout rlyExtendPay;
    @OnClick(R.id.rly_extend_pay)
    public void rlyExtendPayOnclick(){
//        Map<String,String> placeMap = rvAdapter.getPlaceMap();
//        String place = "";
//        if(placeMap != null) {
//            for (Object key : placeMap.keySet()) {
//                place += placeMap.get(key) + ",";
//            }
//        }
//        if(place.isEmpty()){
//            Toast.makeText(this,"请至少选择一处地方进行推广",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(!cbExtendAgreeMent.isChecked()){
//            Toast.makeText(this,"请同意协议",Toast.LENGTH_SHORT).show();
//            return;
//        }
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String role = xcCacheManager.readCache(xcCacheSaveName.role);
        Log.d("role11",role);
      if(rvAdapter.gettype() == 0) {
            if (role != null &&  role.indexOf("104")>=0 || role.indexOf("105")>=0||  role.indexOf("108")>=0|| role.indexOf("109")>=0) {
                return;
            }
        }
        if(rvAdapter.gettype() == 1){
            if(role != null && role.indexOf("109")>=0||role.indexOf("105")>=0){
                return;
            }
        }
          if(rvAdapter.gettype() == 2){
            if(role != null && role.indexOf("109")>=0||role.indexOf("105")>=0){
                return;
            }
        }
        /*if(rvAdapter.gettype() == 0 || rvAdapter.gettype() == 1){
            return;
        }*/
        if(!cbExtendAgreeMent.isChecked()){
            Toast.makeText(this,"请同意协议",Toast.LENGTH_SHORT).show();
            return;
        }

        /*if(role != null && role.equals("100")||role.equals("101")||role.equals("102")){
            spreadArticle();

            return;
        }*/
        String cost = rvAdapter.getCost();
        PayAwayDialog selectTownDialog = new PayAwayDialog(this).Build.setOnPaySuccessfulListener(new PayAwayDialog.OnPaySuccessfulListener() {
            @Override
            public void isSuccessful(boolean isSuccessful) {
                if(isSuccessful){
                    spreadArticle();
                    finish();
                }else {
//                    spreadArticle();
                    finish();
                }
            }
        }).build(this,this,cost,"推广","论坛推广",false);
//        }).build(this,this,"0.01","推广","论坛推广",false);
        selectTownDialog.show();
//        spreadArticle();
//        zhiFuBaoUtil.zhiFuBaoPay("0.01","测试","后台测试");
//        addArticleToNet();
//        Toast.makeText(this,"size:"+rvAdapter.getMap().size(),Toast.LENGTH_SHORT).show();
    }
    private ZhiFuBaoUtil zhiFuBaoUtil;
    private VXPayUtil vxPayUtil;
    private String hyId,baojiaId,status;
//    private String type="zfbpay";
    private String payTitle = "";
    private String price = "";
    private String orderInfo = "";
    /*支付宝支付*/
    /*public void zhiFuBaoPay(String money,String title,String content){
        String outTradeNo = getOutTradeNo();
        *//*Toast.makeText(activity, " onCompleted mPopView:"+goodsName+price, Toast.LENGTH_LONG).show();*//*


        *//*String passback_params = "{ \"dingdanid\" = \""+price"+\",\n \"lx\" = \"1\"\n}";*//*
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        PayNetWork payNetWork = new PayNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("money",money);
        map.put("title",title);
        map.put("content",content);
        map.put("accountid",accountid);
        map.put("platform","android");
        payNetWork.getZhiFuBaoPayDataFromNet(map, new Observer<GetPayBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetPayBean getPayBean) {
                orderInfo = getPayBean.getData();
                if((orderInfo != null)&&(!orderInfo.isEmpty())) {
                    zhiFuBaoUtil.payV3(null, orderInfo);

                }
            }
        });

//        String passback_params = "{\"dingdanid\":\""+baojiaId+"\",\"lx\":\"1\"}";
//        try {
//            passback_params = URLEncoder.encode(passback_params, "utf8");
            *//*Toast.makeText(activity,passback_params,Toast.LENGTH_LONG).show();*//*

            *//*zhiFuBaoUtil.payV2(null, "定金支付", "0.1",outTradeNo,passback_params);*//*
//            zhiFuBaoUtil.payV3(null);

//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        zhiFuBaoUtil.setOnPaySuccessfulListener(new ZhiFuBaoUtil.OnPaySuccessfulListener() {
            @Override
            public void isSuccessful(boolean isSuccessful) {

                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                xcCacheManager.writeCache(xcCacheSaveName.payStatus,"1");

//                activity.finish();
            }
        });
        *//*去支付金钱*//*
        *//*Toast.makeText(activity," 我成功啦111 isSuccessful:"+helpMeBuyBean.getOrderNo(),Toast.LENGTH_LONG).show();*//*


    }*/
    /*支付宝支付*/

    /**
     * 要求外部订单号必须唯一。
     * @return
     */
   /* private  String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }*/
    @BindView(R.id.rly_extend_data)
    RelativeLayout rlyExtendData;
    @BindView(R.id.tv_extend_contact_gov)
    TextView tvExtendContactGov;

    private long clickTime = 0;
    @OnClick(R.id.tv_extend_agree_ment)
    public void tvExtendAgreeMentOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingServiceAgreeMentActivity.class);
            startActivity(intent);
        }
    }
    @OnClick(R.id.tv_extend_policy)
    public void tvExtendPolicyOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineSettingSercetPolicyActivity.class);
            startActivity(intent);
        }
    }
    private String atids = "";
    private String lat ="";
    private String lon ="";
    private String city = "";
    private String addr = "";
    private String content = "";
//    private String title = "";
    private String type = "";
    private String sections = "";
    //    private String shopid = "";
    private String articleid = "";
    private int articletype = 0;
    private ExtendRVAdapter rvAdapter;
    private String nick = "",title= "",img="";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_extend);
        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        rlyExtendData.setVisibility(View.GONE);
        zhiFuBaoUtil = new ZhiFuBaoUtil(this);
        vxPayUtil = new VXPayUtil(this);
        initRV();
        getData();
        initStatusBar();
    }
    /*沉浸式状态栏*/
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initStatusBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.blue);
//        switch (type) {
//            case "index":
//                tintManager.setStatusBarTintResource(R.color.color_main_index_topbar_blue_bg);
//                break;
//            case "release":
//                tintManager.setStatusBarTintResource(R.color.color_main_release_topbar_blue_bg);
//                break;
//            case "advice":
//                tintManager.setStatusBarTintResource(R.color.color_main_advice_content_white_bg);
//                break;
//            case "message":
//                tintManager.setStatusBarTintResource(R.color.color_main_message_content_white_bg);
//                break;
//            case "mine":
//                tintManager.setStatusBarTintResource(R.mipmap.top_big_blue_bg);
//                break;
//        }
    }
    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void setTranslucentStatus(boolean on) {
       /* Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);*/
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.white ));


    }
    private void getData(){
        Intent intent =getIntent();
        articleid = intent.getStringExtra("articleid");
        articletype = intent.getIntExtra("articletype",0);
        nick = intent.getStringExtra("nick");
        title = intent.getStringExtra("title");
        img = intent.getStringExtra("img");
        tvExtendNick.setText(nick+"的帖子");
        tvExtendTitle.setText(title);
        Glide.with(this).load(img).into(ivExtendImg);
    }



    @SuppressLint("WrongConstant")
    private void initRV(){
        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 8;i++){
        dataList.add("全国");
        dataList.add("按省市 >");
        dataList.add("按区县 >");
        dataList.add("按乡镇 >");






//        }
        rvAdapter = new ExtendRVAdapter(this,tvExtendFactPay,rlyExtendPay,tvExtendContactGov,true,"-1");

        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
        //设置布局管理器， 参数gridLayoutManager对象
        rvExtendLoc.setLayoutManager(gridLayoutManager);
        rvExtendLoc.setAdapter(rvAdapter);

        rvAdapter.replaceAll(dataList);
        rvAdapter.setType();
    }


    private void spreadArticle(){
        SpreadNetWork spreadNetWork = new SpreadNetWork();
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String payorder = xcCacheManager.readCache(xcCacheSaveName.tradeorder);
        Map<String,Object> map = new HashMap<>();
        map.put("spreadtype",rvAdapter.gettype());
        Map<String,String> placeMap = rvAdapter.getPlaceMap();
        String place = "";
        if(placeMap != null) {
            for (Object key : placeMap.keySet()) {
                place += placeMap.get(key) + ",";
            }
        }
//        Log.d("aaaa11",place);
        map.put("place",place);
        map.put("payorder",payorder);
        map.put("articleid",articleid);
        map.put("articletype",articletype);
        spreadNetWork.spreadToNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {
                Log.d("aaaa11","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("aaaa11",""+e);
            }

            @Override
            public void onNext(BaseBean baseBean) {
                Toast.makeText(getBaseContext(),baseBean.getMsg(),Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
