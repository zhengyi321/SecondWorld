package com.et.secondworld;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.et.secondworld.adapter.ExtendRVAdapter;
import com.et.secondworld.bean.AddArticleBean;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.dialog.PayAwayDialog;
import com.et.secondworld.mine.setting.MineSettingSercetPolicyActivity;
import com.et.secondworld.mine.setting.MineSettingServiceAgreeMentActivity;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.utils.CustomerViewUtils;
import com.et.secondworld.utils.SysUtils;
import com.et.secondworld.utils.TimeUtil;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.textview.CountDownTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/14
 **/
public class ExtendActivity extends AppCompatActivity {

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
    @BindView(R.id.tv_extend_benefit)
    TextView tvExtendBenefit;
    @BindView(R.id.tv_extend_fact_pay)
    TextView tvExtendFactPay;
    private String GET_IMGS ="GET_IMGS";
    @BindView(R.id.rly_extend_pay)
    RelativeLayout rlyExtendPay;
    @OnClick(R.id.rly_extend_pay)
    public void rlyExtendPayOnclick(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String role = xcCacheManager.readCache(xcCacheSaveName.role);
        if(rvAdapter.gettype() == 0) {
            if (role != null && role.equals("02") ||role.equals("101") || role.equals("102") || role.equals("104") || role.equals("105")|| role.equals("106")) {
                return;
            }
        }
        if(rvAdapter.gettype() == 1){
            if(role != null && role.equals("02")||role.equals("102")||role.equals("105")){
                return;
            }
        }
        if(rvAdapter.gettype() == 2){
            if(role != null && role.equals("02")||role.equals("102")||role.equals("105")){
                return;
            }
        }
//        if(rvAdapter.gettype() == 0 || rvAdapter.gettype() == 1){
//            return;
//        }
        if(!cbExtendAgreeMent.isChecked()){
            Toast.makeText(this,"请同意协议",Toast.LENGTH_SHORT).show();
            return;
        }

//        if(role != null && role.equals("100")||role.equals("101")||role.equals("102")){
//            addArticleToNet();
//            updateShopFirst();
//            return;
//        }


//        PayAwayDialog selectTownDialog = new PayAwayDialog(this).Build.build(this,this);
//        selectTownDialog.show();
        String cost = rvAdapter.getCost();
        if(cost.equals("0.0")){
            addArticleToNet();
            updateShopFirst();
        }else {
            PayAwayDialog selectTownDialog = new PayAwayDialog(this).Build.setOnPaySuccessfulListener(new PayAwayDialog.OnPaySuccessfulListener() {
                @Override
                public void isSuccessful(boolean isSuccessful) {
                    if (isSuccessful) {
                        addArticleToNet();
                        updateShopFirst();
                        finish();
                    } else {
                        addArticleToNet();
                        updateShopFirst();
                        finish();
                    }
                }
            }).build(this, this, cost, "推广", "店铺推广", true);
//            }).build(this, this, "0.01", "推广", "店铺推广", true);
//        Log.d("costzz11",cost+"");

            selectTownDialog.show();
        }
//        Toast.makeText(this,"size:"+rvAdapter.getMap().size(),Toast.LENGTH_SHORT).show();
    }

    private void updateShopFirst(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
        String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
        Map<String,Object>map = new HashMap<>();
        ShopNetWork shopNetWork = new ShopNetWork();
        map.put("shopid",shopid);
        map.put("type",rvAdapter.gettype());
        shopNetWork.updateShopFirstToNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                String isFirst = xcCacheManager.readCache(xcCacheSaveName.shopFirst);
                if(rvAdapter.gettype() == 1 || rvAdapter.gettype() == 2){
                    if(isFirst == null || isFirst.indexOf("province") < 0) {
                        isFirst += "province,";
                    }
                }
                if(rvAdapter.gettype() == 3){
                    if(isFirst == null || isFirst.indexOf("area") < 0) {
                        isFirst += "area,";
                    }
//                    isFirst += "area,";
                }
                if(rvAdapter.gettype() == 4){
//                    isFirst += "town,";
                    if(isFirst == null || isFirst.indexOf("town") < 0) {
                        isFirst += "town,";
                    }
                }
                xcCacheManager.writeCache(xcCacheSaveName.shopFirst,isFirst);
                int count = 3;
                if(isFirst.indexOf("province")>=0){
                    count--;
                }
                if(isFirst.indexOf("area")>=0){
                    count--;
                }
                if(isFirst.indexOf("town")>=0){
                    count--;
                }
                cdtvExtendVipTime.setText(count+"次");
            }
        });
    }
    @BindView(R.id.rly_extend_data)
    RelativeLayout rlyExtendData;
    @BindView(R.id.tv_extend_contact_gov)
    TextView tvExtendContactGov;
    @BindView(R.id.cdtv_extend_viptime)
    CountDownTextView cdtvExtendVipTime;
    @BindView(R.id.tv_extend_pay)
    TextView tvExtendPay;

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
    private String title = "";
    private String type = "";
    private String sections = "";
//    private String shopid = "";
    private ExtendRVAdapter rvAdapter;
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
        initGetIntentData();
        initRV();
        rlyExtendData.setVisibility(View.GONE);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String role = xcCacheManager.readCache(xcCacheSaveName.role);
        String account = xcCacheManager.readCache(xcCacheSaveName.shopId);
        String isFirst = xcCacheManager.readCache(xcCacheSaveName.shopFirst);
//                int selectNums = 50;
//        if(isFirst== null || isFirst.indexOf("province")<0|| isFirst.indexOf("area")<0|| isFirst.indexOf("town")<0){
////            selectnum = 2;
//            int count = 3;
//            if(isFirst.indexOf("province")>=0){
//                count--;
//            }
//            if(isFirst.indexOf("area")>=0){
//                count--;
//            }
//            if(isFirst.indexOf("town")>=0){
//                count--;
//            }
//            tvExtendPay.setText("体验");
//            tvExtendBenefit.setText("体验卡");
//            if(isFirst.indexOf("province")>=0) {
//                cdtvExtendVipTime.setText(count + "次(全国,省市除外)");
//            }else {
//                cdtvExtendVipTime.setText(count + "次");
//            }
//        }else {
            if (role != null && role.indexOf("02")>=0||role.indexOf("100")>=0||role.indexOf("101")>=0||role.indexOf("102")>=0||role.indexOf("106")>=0) {
//            Toast.makeText(getContext(),"禁言中",Toast.LENGTH_SHORT).show();
//            return;
//                                    tvExtendBenefit.setText("贵宾卡");
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("accountid", account);
//            Log.d("zzz11",account);
                ArticleNetWork articleNetWork = new ArticleNetWork();
                articleNetWork.isHasFreeTimeFromNet(paramMap, new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.getIssuccess().equals("0")) {
                            Log.d("zzz22", "this is basebean");
//                            Date nowtime = TimeUtil.getCurrentTimeDate();
//                            long l = nowtime.getTime();
//                            long day = l / (24 * 60 * 60 * 1000);
//                            long hour = (l / (60 * 60 * 1000) - day * 24);
//                            long second = l / 1000;
//                            long second2 = hour * 60 * 60;
//                            Log.d("zzz22", second + "");
//                            Log.d("zzz22", second2 + "");
                            rvAdapter.isFinish = false;
                            final String label = " ";
                            cdtvExtendVipTime.setNormalText("倒计时控件")
                                    .setBeforeIndex(label.length())
                                    .setCountDownClickable(false)
                                    .setIsShowComplete(true)
                                    .setShowFormatTime(true)
                                    .setOnCountDownTickListener(new CountDownTextView.OnCountDownTickListener() {
                                        @Override
                                        public void onTick(long untilFinished, String showTime, CountDownTextView tv) {
//                                        tv.setText(CustomerViewUtils.getMixedText(label + showTime, tv.getTimeIndexes(), true));
                                            tv.setText(label + showTime);
                                        }
                                    })
                                    .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                                        @Override
                                        public void onFinish() {
                                            rvAdapter.isFinish = true;
                                            cdtvExtendVipTime.setText("可使用");
                                            cdtvExtendVipTime.setTextColor(Color.parseColor("#ff5ac6de"));
                                        }
                                    });
                            long countDowmNumber = SysUtils.parseLong(SysUtils.getSafeString(baseBean.getMsg()));
//                        Log.d("zzz22",countDowmNumber+"");
                            if (countDowmNumber > 0) {
//                            Log.d("zzz22","this is start");
                                cdtvExtendVipTime.startCountDown(countDowmNumber);
                            }
                        }
                    }
                });
//                tvExtendBenefit.setText("贵宾卡");
                tvExtendBenefit.setText("体验卡");

            }
//        }

        initData();
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
    @SuppressLint("WrongConstant")
    private void initRV(){
        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 8;i++){
        dataList.add("全国");
        dataList.add("按省市 >");
        dataList.add("按区县 >");
        dataList.add("按乡镇 >");



//        }
        rvAdapter = new ExtendRVAdapter(this,tvExtendFactPay,rlyExtendPay,tvExtendContactGov,false,type);

        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
        //设置布局管理器， 参数gridLayoutManager对象
        rvExtendLoc.setLayoutManager(gridLayoutManager);
        rvExtendLoc.setAdapter(rvAdapter);

        rvAdapter.replaceAll(dataList);
    }


    private void initGetIntentData(){
        Intent intent = getIntent();
        imagePaths = intent.getStringArrayListExtra(GET_IMGS);
        content = intent.getStringExtra("content");
        title = intent.getStringExtra("title");
        city = intent.getStringExtra("city");
        addr = intent.getStringExtra("addr");
        lat = intent.getStringExtra("lat");
        lon = intent.getStringExtra("lon");
        atids = intent.getStringExtra("atids");
        type = intent.getStringExtra("type");
        sections = intent.getStringExtra("sections");
        if(type != null && type.equals("M2")){
            tvExtendFactPay.setText("实付：￥ 10" );
        }

        if(type != null && type.equals("M3")){
            tvExtendFactPay.setText("实付：￥ 20" );
        }
    }
    private void initData(){
        if(imagePaths != null && imagePaths.size() > 0) {
            Glide.with(this)
                    .asBitmap()
                    .load(imagePaths.get(0))
                    .into(ivExtendImg);
        }
        tvExtendTitle.setText(title);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String nick = xcCacheManager.readCache(xcCacheSaveName.userName);
        tvExtendNick.setText(nick+"的帖子");
    }
    private Bitmap compressImageFromFile(String srcPath) {

        BitmapUtils bitmapUtils = new BitmapUtils();
        Bitmap bitmap = bitmapUtils.getimage(srcPath);
        bitmap = bitmapUtils.compressImage(bitmap);
        bitmap = bitmapUtils.comp(bitmap);

        return bitmap;
    }
    private void addArticleToNet(){

        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        String account = xcCacheManager.readCache(xcCacheSaveName.shopId);
        String tradeorder = xcCacheManager.readCache(xcCacheSaveName.tradeorder);
        String imgs = "";
        BitmapUtils bitmapUtils = new BitmapUtils();
        for(String item : imagePaths) {
            if(item.indexOf("http") >=0){
                imgs += item+",";
                continue;
            }
            if(item.isEmpty()){
                imgs +=  ",";
            }else {
                Bitmap bitmapHead = compressImageFromFile(item);
                //将图片显示到ImageView中
                imgs += bitmapUtils.bitmapConvertBase64(bitmapHead) + ",";
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("content",content);
        map.put("account",account);
        map.put("imgs",imgs);
        map.put("city",city);
        map.put("addr",addr);
        map.put("lat",lat);
        map.put("lon",lon);
        map.put("payorder",tradeorder);
        map.put("atid",atids);
        map.put("type",type);
        map.put("sections",sections);
        map.put("platform","android");
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
//
//                    Log.d("v1111",placeMapProvince.get(key)+"");
//                }
//        }
        ArticleNetWork articleNetWork = new ArticleNetWork();
        articleNetWork.addTypeArticleToNet(map, new Observer<AddArticleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddArticleBean addArticleBean) {
                Toast.makeText(getBaseContext(),addArticleBean.getMsg(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
