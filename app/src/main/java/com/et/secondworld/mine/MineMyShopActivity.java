package com.et.secondworld.mine;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.widget.dialog.MineShopTimesPayDialog;
import com.et.secondworld.widget.imageview.HeadBgImagePreActivity;
import com.et.secondworld.widget.imageview.ShopHeadBgImagePreActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.R;
import com.et.secondworld.bean.GetMineMyShopDataBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.forum.ForumModulSelectOneActivity;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.MineShopLocDialog;
import com.et.secondworld.widget.imageview.NormalImagePreActivity;
import com.et.secondworld.widget.scrollview.ScaleScrollView;

import java.text.ParseException;
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
 * @Date 2020/4/13
 **/
public class MineMyShopActivity extends AppCompatActivity {

//    @BindView(R.id.rv_mine_my_shop_shop_mark)
//    RecyclerView rvMineMyShopShopMark;
  /*  @BindView(R.id.rv_mine_my_shop_shop_article)
    RecyclerView rvMineMyShopShopArticle;*/
//    private MineMyShopShopMarkRVAdapter mineMyShopShopMarkRVAdapter;
//    private MineMyShopShopArticleRVAdapter mineMyShopShopArticleRVAdapter;

    @BindView(R.id.iv_mine_my_shop_times_pay)
    ImageView ivMineMyShopTimesPay;
    @OnClick(R.id.iv_mine_my_shop_times_pay)
    public void ivMineMyShopTimesPayOnclick(){
        if(firetype == 0) {
            MineShopTimesPayDialog mineShopTimesPayDialog = new MineShopTimesPayDialog(this).Build.build(this, articleid, 2 + "",spreadPrice);
            mineShopTimesPayDialog.show();
        }
    }

    @BindView(R.id.tv_mine_my_shop_history)
    TextView tvMineMyShopHistory;
    private long clickTime = 0;
    @OnClick(R.id.tv_mine_my_shop_history)
    public void tvMineMyShopHistoryOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineShopArticleHistoryActivity.class);
            startActivity(intent);
        }
    }
    @BindView(R.id.civ_mine_my_shop_logo)
    ImageView civMineMyShopLogo;
    @OnClick(R.id.civ_mine_my_shop_logo)
    public void civMineMyShopLogoOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        isResume = true;
            imgUrlList.clear();
            imgUrlList.add(xcCacheManager.readCache(xcCacheSaveName.shopHead));
            Intent intent = new Intent(this, ShopHeadBgImagePreActivity.class);
            intent.putStringArrayListExtra("imgurlList", (ArrayList<String>) imgUrlList);
            intent.putExtra("imagePosition", 0);
            intent.putExtra("type", "head");
//					ActivityOptionsCompat optionsCompat =
//							ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), v, "image");

            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(civMineMyShopLogo, 100, 100, 400, 400);
            startActivity(intent, compat.toBundle());
        }
    }
    @BindView(R.id.iv_mine_my_shop_bg)
    ImageView ivMineMyShopBg;
    @OnClick(R.id.iv_mine_my_shop_bg)
    public void ivMineMyShopBgOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        isResume = true;
            imgUrlList.clear();
            imgUrlList.add(xcCacheManager.readCache(xcCacheSaveName.shopBg));
            Intent intent = new Intent(this, ShopHeadBgImagePreActivity.class);
//            Log.d("imgurlsize11",imgUrlList.size()+"");
            intent.putStringArrayListExtra("imgurlList", (ArrayList<String>) imgUrlList);
            intent.putExtra("imagePosition", 0);
            intent.putExtra("type", "bg");
//					ActivityOptionsCompat optionsCompat =
//							ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), v, "image");

            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(ivMineMyShopBg, 100, 100, 400, 400);
            startActivity(intent, compat.toBundle());
        }
    }
    @BindView(R.id.ssv_mine_my_shop)
    ScaleScrollView ssvMineMyShop;
    @BindView(R.id.v_mine_my_shop_secondView)
    View vMineMyShopSecondView;
    @BindView(R.id.iv_mine_my_shop_article_img)
    ImageView ivMineMyShopArticleImg;
   /* @BindView(R.id.tv_mine_my_shop_content)
    TextView tvMineMyShopContent;*/
    @BindView(R.id.rly_mine_my_shop_back)
    RelativeLayout rlyMineMyShopBack;
    @OnClick(R.id.rly_mine_my_shop_back)
    public void rlyMineMyShopBackOnclick(){
        finish();
    }
    @BindView(R.id.tv_mine_my_shop_name)
    TextView tvMineMyShopName;
    @BindView(R.id.rly_mine_my_shop_add)
    RelativeLayout rlyMineMyShopAdd;
    @OnClick(R.id.rly_mine_my_shop_add)
    public void rlyMineMyShopAddOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String role = xcCacheManager.readCache(xcCacheSaveName.role);

            if(role != null && role.equals("01")) {
                Toast.makeText(this,"禁言中",Toast.LENGTH_SHORT).show();
                return;

            }
            Intent intent = new Intent(this, ForumModulSelectOneActivity.class);
            startActivity(intent);
        }
    }
    @BindView(R.id.tv_mine_my_shop_loc)
    TextView tvMineMyShopLoc;
    @OnClick(R.id.tv_mine_my_shop_loc)
    public void tvMineMyShopLocOnclick(){
        MineShopLocDialog mineShopLocDialog = new MineShopLocDialog(this,tvMineMyShopLoc.getText().toString(),street).Build.build(this);
        mineShopLocDialog.show();
    }
    @BindView(R.id.tv_mine_my_shop_praise)
    TextView tvMineMyShopPraise;
//    @BindView(R.id.tv_mine_my_shop_guanzhu)
//    TextView tvMineMyShopGuanZhu;
    private String shopid = "";
    @BindView(R.id.tv_mine_my_shop_editdata)
    TextView tvMineMyShopEditData;
    @OnClick(R.id.tv_mine_my_shop_editdata)
    public void btMineMyShopEditDataOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineShopEditDataActivity.class);
            intent.putExtra("shopid", shopid);
//        Log.d("shopid111",shopid+"");
//        Toast.makeText(this,"shopid:"+shopid,Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }
    @BindView(R.id.lly_mine_my_shop_fans)
    LinearLayout llyMineMyShopFans;
    @BindView(R.id.tv_mine_my_shop_fans)
    TextView tvMineMyShopFans;
    @OnClick(R.id.lly_mine_my_shop_fans)
    public void llyMineMyShopFansOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineFansActivity.class);
            intent.putExtra("account", articleAccount);
            intent.putExtra("nick", tvMineMyShopName.getText().toString());
            intent.putExtra("type", "shop");
            startActivity(intent);
        }
    }
    @BindView(R.id.tv_mine_my_shop_content)
    TextView tvMineMyShopContent;
    @BindView(R.id.tv_mine_my_shop_businesshour_morning)
    TextView tvMineMyShopBusinessHourMorning;
    @BindView(R.id.tv_mine_my_shop_businesshour_afternoon)
    TextView tvMineMyShopBusinessHourAfternoon;
    @BindView(R.id.tv_mine_my_shop_title)
    TextView tvMineMyShopTitle;
    @BindView(R.id.tv_mine_my_shop_article_repost)
    TextView tvMineMyShopArticleRepost;
    @BindView(R.id.tv_mine_my_shop_article_comment)
    TextView tvMineMyShopArticleComment;
    @BindView(R.id.tv_mine_my_shop_article_praise)
    TextView tvMineMyShopArticlePraise;
    @BindView(R.id.iv_mine_my_shop_loading)
    ImageView ivMineMyShopLoading;
    @BindView(R.id.tv_mine_my_shop_article_browse)
    TextView tvMineMyShopArticleBrowse;
//    @BindView(R.id.lly_mine_my_shop_article)
//    LinearLayout llyMineMyShopArticle;
   /* @BindView(R.id.iv_mine_my_shop_article_img)
    ImageView ivMineMyShopArticleImg;*/
   private List<String> imgUrlList = new ArrayList<>();
    @OnClick(R.id.iv_mine_my_shop_article_img)
    public void ivMineMyShopArticleImgOnclick(){
        /*startImagePre(0,ivMineMyShopArticleImg);*/
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            if (modules == null) {
                return;
            }
            if (modules.equals("M1") || modules.equals("M2")) {
                Intent intent = new Intent(this, ForumDetailOneActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);

                startActivity(intent);
            }
            if (modules.equals("M3")) {
                Intent intent = new Intent(this, ForumDetailThreeActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);

                startActivity(intent);
            }
        }
    }

    private void startImagePre(int position,ImageView view){

        Intent intent =new Intent(this, NormalImagePreActivity.class);
        intent.putStringArrayListExtra("imgurlList",(ArrayList<String>) imgUrlList);
        intent.putExtra("imagePosition",position);
//					ActivityOptionsCompat optionsCompat =
//							ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), v, "image");

        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view,100,100,400,400);
        startActivity(intent,compat.toBundle());
    }
    @OnClick(R.id.lly_mine_my_shop_article)
    public void llyMineMyShopArticleOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            if (modules == null) {
                return;
            }
            if (modules.equals("M1") || modules.equals("M2")) {
                Intent intent = new Intent(this, ForumDetailOneActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);

                startActivity(intent);
            }
            if (modules.equals("M3")) {
                Intent intent = new Intent(this, ForumDetailThreeActivity.class);
                intent.putExtra("articleAccount", articleAccount);
                intent.putExtra("articleid", articleid);

                intent.putExtra("title", title);

                startActivity(intent);
            }
        }
    }
    @BindView(R.id.iv_mine_my_shop_article_praise)
    ImageView ivMineMyShopArticlePraise;
    private String articleAccount = "";
    private String articleid = "";
    private String title = "";
    private String modules = "";
    private String street = "";
    private int spreadPrice = 0;
    int firetype = 0;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mine_my_shop);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        initZoomScrollView();
        Glide.with(this).load(R.mipmap.pageloading).into(ivMineMyShopLoading);
//        getShopDataFromNet();
        initStatusBar();
//        initShopMark();
//        initShopArticle();
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
    private void initZoomScrollView(){
        ssvMineMyShop.setTargetView(ivMineMyShopBg,vMineMyShopSecondView);
        ssvMineMyShop.setNeedScroll(true);
//        ssvMineMyShop.setOnScrollChangeListener(this);
    }

    private void getShopDataFromNet(){
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        ShopNetWork shopNetWork = new ShopNetWork();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        Map<String,Object> map = new HashMap<>();
//        Log.d("accountid11",account);
        map.put("account",account);
        shopNetWork.getMyShopDataFromNet(map, new Observer<GetMineMyShopDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onNext(GetMineMyShopDataBean getMineMyShopDataBean) {
                if(getMineMyShopDataBean.getIssuccess().equals("1")){
                    String time = getMineMyShopDataBean.getTime();
                    int ispraised = getMineMyShopDataBean.getIspraised();
                    if(ispraised == 1){
                        ivMineMyShopArticlePraise.setBackgroundResource(R.mipmap.praised);
                    }else {
                        ivMineMyShopArticlePraise.setBackgroundResource(R.mipmap.praiseicon);
                    }
                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    spreadPrice = getMineMyShopDataBean.getSpreadprice();
//                    Log.d("hours22",time + "");
                    Glide.with(getBaseContext())
                            .load(getMineMyShopDataBean.getLogo())
                            .apply(new RequestOptions()

                                    .fallback(R.mipmap.imghead)
                                    .error(R.mipmap.imghead)
                                    .circleCrop()
                                    )
////                                    .skipMemoryCache(true)
////                                    .diskCacheStrategy(DiskCacheStrategy.NONE))
//                            // 重点在这行
                            .into(civMineMyShopLogo);
                    street = getMineMyShopDataBean.getStreet();
//                    Log.d("zz222",getMineMyShopDataBean.getShopbg());
                    Glide.with(getBaseContext())
                            .load(getMineMyShopDataBean.getShopbg())
                            .apply(new RequestOptions()
                                    .centerInside()
                                    .error(R.mipmap.imghead)
                                    .fallback(R.mipmap.imghead)

                            )
                    .into(ivMineMyShopBg);

//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                            // 重点在这行

//                    ImageLoader.getInstance().displayImage(getMineMyShopDataBean.getShopbg(),ivMineMyShopBg,ImageLoaderUtils.options);
//                    Glide.with(getBaseContext())
//                            .load(getMineMyShopDataBean.getShopbg())
//                            .apply(new RequestOptions()
//                                    .fallback(R.mipmap.shopbg)
//                                    .error(R.mipmap.shopbg)
//                            )
//                            // 重点在这行
//                            .into(ivMineMyShopBg);

                    shopid = getMineMyShopDataBean.getShopid();
//                    Log.d("dd11d11",shopid);
//                    Toast.makeText(getBaseContext(),"shopid:"+getMineMyShopDataBean.getShopid(),Toast.LENGTH_LONG).show();
                    xcCacheManager.writeCache(xcCacheSaveName.shopName,getMineMyShopDataBean.getShopname());
                    xcCacheManager.writeCache(xcCacheSaveName.shopLocate,getMineMyShopDataBean.getLocate());
                    xcCacheManager.writeCache(xcCacheSaveName.shopTel,getMineMyShopDataBean.getTel());
                    xcCacheManager.writeCache(xcCacheSaveName.businessHour,getMineMyShopDataBean.getBusinesshour());
                    if(getMineMyShopDataBean.getLogo() == null || getMineMyShopDataBean.getLogo().isEmpty()){
                        xcCacheManager.writeCache(xcCacheSaveName.shopHead,"");
                    }else {
                        xcCacheManager.writeCache(xcCacheSaveName.shopHead, getMineMyShopDataBean.getLogo());
                    }
                    if(getMineMyShopDataBean.getShopbg() == null || getMineMyShopDataBean.getShopbg().isEmpty()){
                        xcCacheManager.writeCache(xcCacheSaveName.shopBg,"");
                    }else {
                        xcCacheManager.writeCache(xcCacheSaveName.shopBg, getMineMyShopDataBean.getShopbg());
                    }
//                    xcCacheManager.writeCache(xcCacheSaveName.shopBg,getMineMyShopDataBean.getShopbg());

                    xcCacheManager.writeCache(xcCacheSaveName.shopFirst,getMineMyShopDataBean.getIsfirst()==null?"":getMineMyShopDataBean.getIsfirst());
//                    String shopname = xcCacheManager.readCache(xcCacheSaveName.shopName);
//                    String shopLocate = xcCacheManager.readCache(xcCacheSaveName.shopLocate);
//                    String shopTel = xcCacheManager.readCache(xcCacheSaveName.shopTel);
//                    String shopBusinessHour = xcCacheManager.readCache(xcCacheSaveName.businessHour);
//                    String shophead = xcCacheManager.readCache(xcCacheSaveName.shopHead);
//                    String shopbg = xcCacheManager.readCache(xcCacheSaveName.shopBg);
//                    ImageLoader.getInstance().displayImage(getMineMyShopDataBean.getShopbg(),ivMineMyShopBg, ImageLoaderUtils.options);
//                    ImageLoader.getInstance().displayImage(getMineMyShopDataBean.getLogo(),civMineMyShopLogo, ImageLoaderUtils.options);
                    String articleImg = getMineMyShopDataBean.getArticleimg();
                    String content = getMineMyShopDataBean.getContent();
                    if(content != null) {
                        tvMineMyShopContent.setText(content);
                    }else {
                        tvMineMyShopContent.setVisibility(View.GONE);
                    }
//                    Log.d("articleimg111",articleImg);
                    if(articleImg == null || articleImg.isEmpty()){
                        ivMineMyShopArticleImg.setVisibility(View.GONE);
                        tvMineMyShopContent.setVisibility(View.VISIBLE);

                    }else {
                        if(imgUrlList.size() == 0) {
                            imgUrlList.add(getMineMyShopDataBean.getArticleimg());
                        }
                        Glide.with(getBaseContext())
                                .load(getMineMyShopDataBean.getArticleimg())
                                .apply(new RequestOptions()
                                        .dontAnimate()
                                        .fallback(R.mipmap.allshopbg))
//                                        .skipMemoryCache(true))
//                                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                                // 重点在这行
                                .into(ivMineMyShopArticleImg);
//                        ImageLoader.getInstance().displayImage(getMineMyShopDataBean.getArticleimg(), ivMineMyShopArticleImg, ImageLoaderUtils.options);
                    }

                    if(getMineMyShopDataBean.getShopname() != null) {
                        tvMineMyShopName.setText(getMineMyShopDataBean.getShopname());
                    }
//                    tvMineMyShopLoc.setText("北白象镇大港新村中港路131号");
                    if(getMineMyShopDataBean.getLocate() != null) {
                        tvMineMyShopLoc.setText(getMineMyShopDataBean.getLocate());
                    }
                    modules = getMineMyShopDataBean.getModules();
                    articleAccount = getMineMyShopDataBean.getShopid();
                    xcCacheManager.writeCache(xcCacheSaveName.shopId,shopid);
                    articleid = getMineMyShopDataBean.getArticleid();
                    title = getMineMyShopDataBean.getTitle();
                    tvMineMyShopTitle.setText(getMineMyShopDataBean.getTitle());
                    tvMineMyShopArticleRepost.setText(getMineMyShopDataBean.getRepost()+"");
                    tvMineMyShopArticleComment.setText(getMineMyShopDataBean.getComment()+"");
                    tvMineMyShopArticlePraise.setText(getMineMyShopDataBean.getPraise()+"");
                    tvMineMyShopArticleBrowse.setText(getMineMyShopDataBean.getReaders()+"");
                    tvMineMyShopPraise.setText(getMineMyShopDataBean.getPraise()+"");
                    String businesshour = getMineMyShopDataBean.getBusinesshour();
                    if(businesshour != null){
                        String[] hour = businesshour.split(" ");
                        if(hour.length > 1) {
                            tvMineMyShopBusinessHourMorning.setText(hour[0]);
                            tvMineMyShopBusinessHourAfternoon.setText(hour[1]);
                        }
                    }
//                    tvMineMyShopBusinessHour.setText(getMineMyShopDataBean.getBusinesshour()+"");
                    tvMineMyShopFans.setText(getMineMyShopDataBean.getFans()+"");
                    ivMineMyShopLoading.setVisibility(View.GONE);
                    Date nows=new Date();
                    Date date= null;
                    try {
                        date = dateFormat.parse(time);
                        long hous=(nows.getTime()-date.getTime())/(60*60*1000);
                        Log.d("hours22",hous + "");
                        if(hous >= 72){
                            firetype = 1;
                            ivMineMyShopTimesPay.setBackgroundResource(R.mipmap.firemieicon);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        getShopDataFromNet();
//        initData();
    }

    private void initData(){
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String shopname = xcCacheManager.readCache(xcCacheSaveName.shopName);
        String shopLocate = xcCacheManager.readCache(xcCacheSaveName.shopLocate);
        String shopTel = xcCacheManager.readCache(xcCacheSaveName.shopTel);
        String shopBusinessHour = xcCacheManager.readCache(xcCacheSaveName.businessHour);
        String shophead = xcCacheManager.readCache(xcCacheSaveName.shopHead);
        String shopbg = xcCacheManager.readCache(xcCacheSaveName.shopBg);
        if(shopname != null && !shopname.isEmpty()){
            tvMineMyShopName.setText(shopname);
        }
        if(shopLocate != null && !shopLocate.isEmpty()){
            tvMineMyShopLoc.setText(shopLocate);
        }

        if(shopBusinessHour != null && !shopBusinessHour.isEmpty()){
//            if(businesshour != null){
                String[] hour = shopBusinessHour.split(" ");
                if(hour.length > 1) {
                    tvMineMyShopBusinessHourMorning.setText(hour[0]);
                    tvMineMyShopBusinessHourAfternoon.setText(hour[1]);
                }
//            }
        }

        Glide.with(getBaseContext())
                .load(shophead)
                .apply(new RequestOptions()
                        .circleCrop()
                        .error(R.mipmap.imghead)
                        .fallback(R.mipmap.imghead))

//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                // 重点在这行
                .into(civMineMyShopLogo);
        Glide.with(getBaseContext())
                .load(shopbg)
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(R.mipmap.imghead)
                        .fallback(R.mipmap.imghead))

//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                // 重点在这行
                .into(ivMineMyShopBg);
//        ImageLoader.getInstance().displayImage(shopbg,ivMineMyShopBg,ImageLoaderUtils.options);
      /*  Glide.with(getBaseContext())
                .load(shopbg)
                .apply(new RequestOptions()
//                        .dontAnimate()
                        .fallback(R.mipmap.wodebg))
//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                // 重点在这行
                .into(ivMineMyShopBg);*/
    }
//    @SuppressLint("WrongConstant")
//    private void initShopMark(){
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
       /* GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
        //设置布局管理器， 参数gridLayoutManager对象
        rvMineMyShopShopMark.setLayoutManager(gridLayoutManager);
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            dataList.add("");
        }
        mineMyShopShopMarkRVAdapter = new MineMyShopShopMarkRVAdapter();

        rvMineMyShopShopMark.setAdapter(mineMyShopShopMarkRVAdapter);

        mineMyShopShopMarkRVAdapter.replaceAll(dataList);*/
//    }
 /*   private void initShopArticle(){
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 1;i++){
            dataList.add("");
        }
        mineMyShopShopArticleRVAdapter = new MineMyShopShopArticleRVAdapter();
        rvMineMyShopShopArticle.setLayoutManager(new LinearLayoutManager(this));
        rvMineMyShopShopArticle.setAdapter(mineMyShopShopArticleRVAdapter);

        mineMyShopShopArticleRVAdapter.replaceAll(dataList);
    }*/

}
