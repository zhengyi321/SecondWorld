package com.et.secondworld.mapview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.et.secondworld.bean.AddArticleBean;
import com.et.secondworld.dialog.PayAwayDialog;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.ArrayList;
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
public class MapPostExtendActivity extends AppCompatActivity {

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
        Map<String,String> placeMap = rvAdapter.getPlaceMap();
        String place = "";
        if(placeMap != null) {
            for (Object key : placeMap.keySet()) {
                place += placeMap.get(key) + ",";
            }
        }
        if(place.isEmpty()){
            Toast.makeText(this,"请至少选择1处至多5处地方进行推广",Toast.LENGTH_SHORT).show();
            return;
        }
//        PayAwayDialog selectTownDialog = new PayAwayDialog(this).Build.build(this,this);
//        selectTownDialog.show();
//        String cost = rvAdapter.getCost();
        PayAwayDialog selectTownDialog = new PayAwayDialog(this).Build.setOnPaySuccessfulListener(new PayAwayDialog.OnPaySuccessfulListener() {
            @Override
            public void isSuccessful(boolean isSuccessful) {
                if(isSuccessful){
                    addArticleToNet();
                    finish();
                }else {
                    finish();
//                    addArticleToNet();
                }
            }
        }).build(this,this,"100","紧急事件与求助","紧急事件与求助费用",false);
//        }).build(this,this,"0.01","紧急事件与求助","紧急事件与求助费用",false);
        selectTownDialog.show();
//        addArticleToNet();
//        Toast.makeText(this,"size:"+rvAdapter.getMap().size(),Toast.LENGTH_SHORT).show();
    }
    @BindView(R.id.tv_extend_remark)
    TextView tvExtendRemark;
    private String atids = "";
    private String lat ="";
    private String lon ="";
    private String city = "";
    private String addr = "";
    private String content = "";
    private String title = "";
    private String type = "";
    private String sections = "";
    private String village = "";
    private String types = "";
//    private String shopid = "";
    private MapPostExtendRVAdapter rvAdapter;
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
        initRV();
        initGetIntentData();
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

            dataList.add("按乡镇 >");
//        }
        rvAdapter = new MapPostExtendRVAdapter();

        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
        //设置布局管理器， 参数gridLayoutManager对象
        rvExtendLoc.setLayoutManager(gridLayoutManager);
        rvExtendLoc.setAdapter(rvAdapter);
        tvExtendFactPay.setText("实付：￥100" );
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
//        type = intent.getStringExtra("type");
        village = intent.getStringExtra("village");
        types = intent.getStringExtra("types");

        if(types.equals("help")){
            tvExtendRemark.setText("为防止有人传播谣言,故设此门槛给真正有需要的人，点击解决且审核无误后届时退还50%。敬请谅解!");
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
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        String payorder = xcCacheManager.readCache(xcCacheSaveName.tradeorder);
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
        map.put("payorder",payorder);
        map.put("atid",atids);
        map.put("village",village);
        map.put("platform","android");
        map.put("types",types);
        Map<String,String> placeMap = rvAdapter.getPlaceMap();
        String place = "";
        if(placeMap != null) {
            for (Object key : placeMap.keySet()) {
                place += placeMap.get(key) + ",";
            }
        }
        if(place.isEmpty()){
            Toast.makeText(this,"请至少选择一处至多3处地方进行推广",Toast.LENGTH_SHORT).show();
            return;
        }
        place += village+",";
//        Log.d("aaaa11",place);
        map.put("place",place);
        ArticleNetWork articleNetWork = new ArticleNetWork();
        articleNetWork.addMapArticleToNet(map, new Observer<AddArticleBean>() {
            @Override
            public void onCompleted() {
                Log.d("mappost11","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("mappost11",e+"");
            }

            @Override
            public void onNext(AddArticleBean addArticleBean) {
                Intent intent = new Intent();
                //把需要返回的数据存放在intent
//                    intent.putExtra("lat", lat);
//                    intent.putExtra("lon", lon);
                //设置返回数据
                setResult(RESULT_OK, intent);
                Toast.makeText(getBaseContext(),addArticleBean.getMsg(),Toast.LENGTH_SHORT).show();
                finish();
                if(addArticleBean.getIssuccess().equals("1")){


//                    finish();
                }
            }
        });
    }
}
