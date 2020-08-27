package com.et.secondworld;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.et.secondworld.bean.CancelGuanZhuBean;
import com.et.secondworld.bean.GetOtherShopDataBean;
import com.et.secondworld.find.ForumDetailThreeActivity;
import com.et.secondworld.forum.ForumDetailOneActivity;
import com.et.secondworld.mine.MineFansActivity;
import com.et.secondworld.mine.MineShopEditDataActivity;
import com.et.secondworld.network.GuanZhuFansNetWork;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.MineShopLocDialog;
import com.et.secondworld.widget.dialog.OthersShopMoreDialog;
import com.et.secondworld.widget.imageview.CircleImageView;
import com.et.secondworld.widget.imageview.NormalImagePreActivity;
import com.et.secondworld.widget.scrollview.ScaleScrollView;

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
 * @Date 2020/4/13
 **/
public class VisitOthersShopActivity extends AppCompatActivity {

//    @BindView(R.id.rv_visit_others_shop_shop_mark)
//    RecyclerView rvVisitOthersShopShopMark;
  /*  @BindView(R.id.rv_visit_others_shop_shop_article)
    RecyclerView rvVisitOthersShopShopArticle;*/
//    private VisitOthersShopShopMarkRVAdapter mineMyShopShopMarkRVAdapter;
//    private VisitOthersShopShopArticleRVAdapter mineMyShopShopArticleRVAdapter;
    @BindView(R.id.ssv_visit_others_shop)
    ScaleScrollView ssvVisitOthersShop;
    @BindView(R.id.v_visit_others_shop_secondView)
    View vVisitOthersShopSecondView;
    @BindView(R.id.civ_visit_others_shop_logo)
    CircleImageView civVisitOthersShopLogo;
    @BindView(R.id.iv_visit_others_shop_bg)
    ImageView ivVisitOthersShopBg;
    @BindView(R.id.iv_visit_others_shop_article_img)
    ImageView ivVisitOthersShopArticleImg;
   /* @BindView(R.id.tv_visit_others_shop_content)
    TextView tvVisitOthersShopContent;*/
    @BindView(R.id.rly_visit_others_shop_back)
    RelativeLayout rlyVisitOthersShopBack;
    @OnClick(R.id.rly_visit_others_shop_back)
    public void rlyVisitOthersShopBackOnclick(){
        finish();
    }
    @BindView(R.id.tv_visit_others_shop_name)
    TextView tvVisitOthersShopName;
  /*  @BindView(R.id.rly_visit_others_shop_add)
    RelativeLayout rlyVisitOthersShopAdd;
    @OnClick(R.id.rly_visit_others_shop_add)
    public void rlyVisitOthersShopAddOnclick(){
        Intent intent = new Intent(this, ForumModulSelectOneActivity.class);
        startActivity(intent);
    }*/
  private String street = "";
    @BindView(R.id.tv_visit_others_shop_loc)
    TextView tvVisitOthersShopLoc;
    @OnClick(R.id.tv_visit_others_shop_loc)
    public void tvVisitOthersShopLocOnclick(){
        MineShopLocDialog mineShopLocDialog = new MineShopLocDialog(this,tvVisitOthersShopLoc.getText().toString(),street).Build.build(this);
        mineShopLocDialog.show();
    }
    @OnClick(R.id.rly_visit_others_shop_more)
    public void rlyVisitOthersShopMoreOnclick(){
        OthersShopMoreDialog othersShopMoreDialog = new OthersShopMoreDialog(this,shopid,tel).Build.build(this);
        othersShopMoreDialog.show();
    }
    @BindView(R.id.tv_visit_others_shop_praise)
    TextView tvVisitOthersShopPraise;
    @BindView(R.id.iv_visit_others_shop_article_praise)
    ImageView ivVisitOthersShopPraise;
//    @BindView(R.id.tv_visit_others_shop_guanzhu)
//    TextView tvVisitOthersShopGuanZhu;
    private String shopid = "";
    @BindView(R.id.bt_visit_others_shop_editdata)
    Button btVisitOthersShopEditData;
    private long clickTime = 0;
    @OnClick(R.id.bt_visit_others_shop_editdata)
    public void btVisitOthersShopEditDataOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineShopEditDataActivity.class);
            intent.putExtra("shopid", shopid);
            startActivity(intent);
        }
    }
    @BindView(R.id.lly_visit_others_shop_fans)
    LinearLayout llyVisitOthersShopFans;
    private String tel = "";
    @BindView(R.id.tv_visit_others_shop_fans)
    TextView tvVisitOthersShopFans;
    @OnClick(R.id.lly_visit_others_shop_fans)
    public void llyVisitOthersShopFansOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, MineFansActivity.class);
            intent.putExtra("account", articleAccount);
            intent.putExtra("nick", tvVisitOthersShopName.getText().toString());
            intent.putExtra("type", "shop");
            startActivity(intent);
        }
    }

    @OnClick(R.id.ib_visit_others_shop_tel)
    public void ibVisitOthersShopTelOnclick(){

        callPhone(tel);
    }
    public void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    @BindView(R.id.tv_visit_others_shop_content)
    TextView tvVisitOthersShopContent;
    @BindView(R.id.tv_visit_others_shop_businesshour_morning)
    TextView tvVisitOthersShopBusinessHourMorning;
    @BindView(R.id.tv_visit_others_shop_businesshour_afternoon)
    TextView tvVisitOthersShopBusinessHourAfternoon;
    @BindView(R.id.tv_visit_others_shop_title)
    TextView tvVisitOthersShopTitle;
    @BindView(R.id.tv_visit_others_shop_article_repost)
    TextView tvVisitOthersShopArticleRepost;
    @BindView(R.id.tv_visit_others_shop_article_comment)
    TextView tvVisitOthersShopArticleComment;
    @BindView(R.id.tv_visit_others_shop_article_praise)
    TextView tvVisitOthersShopArticlePraise;
    @BindView(R.id.tv_visit_others_shop_article_browse)
    TextView tvVisitOthersShopArticleBrowse;
    @BindView(R.id.lly_visit_others_shop_article)
    LinearLayout llyVisitOthersShopArticle;
    @BindView(R.id.rly_visit_others_shop_guanzhu)
    RelativeLayout rlyVisitOthersShopGuanZhu;
    @OnClick(R.id.rly_visit_others_shop_guanzhu)
    public void rlyVisitOthersShopGuanZhuOnclick(){
        if(tvVisitOthersShopGuanZhu.getText().toString().equals("已关注")||tvVisitOthersShopGuanZhu.getText().toString().equals("互相关注")){
            QueryCancelDialog queryCancelDialog = new QueryCancelDialog(this).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
                @Override
                public void isQuery(boolean isQuery) {
                    if(isQuery) {
                        guanZhuSubmit();
                    }
                }
            }).build(this,"确定取消关注吗");
            queryCancelDialog.show();
        }else {
            guanZhuSubmit();
        }
//        guanZhuSubmit();
    }
    @BindView(R.id.tv_visit_others_shop_guanzhu)
    TextView tvVisitOthersShopGuanZhu;

    private void guanZhuSubmit(){
        Map<String,String> map = new HashMap<>();
        GuanZhuFansNetWork guanZhuFansNetWork= new GuanZhuFansNetWork();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        map.put("account",shopid);
        map.put("followid",account);
           /* if(isFans == 0){
                map.put("type","1");
            }
            if(isFans == 1){
                map.put("type","0");
            }*/
        guanZhuFansNetWork.cancelGuanZhuToNet(map, new Observer<CancelGuanZhuBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CancelGuanZhuBean cancelGuanZhuBean) {
                if(cancelGuanZhuBean.getIssuccess().equals("1")) {

                    rlyVisitOthersShopGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                    tvVisitOthersShopGuanZhu.setText("已关注");
                    tvVisitOthersShopGuanZhu.setTextColor(Color.GRAY);
//                        notifyDataSetChanged();
                }else {
                    rlyVisitOthersShopGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                    tvVisitOthersShopGuanZhu.setText("+关注");
                    tvVisitOthersShopGuanZhu.setTextColor(Color.WHITE);

//                            dataList.get(pos).setIsfans(0);
//                        dataList.get(pos).setIsfriends(0);
                }
                Toast.makeText(getBaseContext(), cancelGuanZhuBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
   /* @BindView(R.id.iv_visit_others_shop_article_img)
    ImageView ivVisitOthersShopArticleImg;*/
   private List<String> imgUrlList = new ArrayList<>();
    @OnClick(R.id.iv_visit_others_shop_article_img)
    public void ivVisitOthersShopArticleImgOnclick(){
        /*startImagePre(0,ivVisitOthersShopArticleImg);*/
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
    @OnClick(R.id.lly_visit_others_shop_article)
    public void llyVisitOthersShopArticleOnclick(){
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
    @BindView(R.id.iv_visit_others_shop_loading)
    ImageView ivVisitOthersShopLoading;
    @BindView(R.id.rly_visit_others_shop_loading)
    RelativeLayout rlyVisitOthersShopLoading;
    private String articleAccount = "";
    private String articleid = "";
    private String title = "";
    private String modules = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_visit_others_shop);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        rlyVisitOthersShopLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivVisitOthersShopLoading);
        getIntentData();
        getShopDataFromNet();
        initZoomScrollView();
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
        ssvVisitOthersShop.setTargetView(ivVisitOthersShopBg,vVisitOthersShopSecondView);
        ssvVisitOthersShop.setNeedScroll(true);
//        ssvMineMyShop.setOnScrollChangeListener(this);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
    }

    private void getShopDataFromNet(){
        rlyVisitOthersShopLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivVisitOthersShopLoading);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        ShopNetWork shopNetWork = new ShopNetWork();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        Map<String,Object> map = new HashMap<>();
        Log.d("visitothersshop1",account+"");
        Log.d("visitothersshop1",shopid+"");
        map.put("account",account);
        map.put("shopid",shopid);
        shopNetWork.getOtherShopDataFromNet(map, new Observer<GetOtherShopDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetOtherShopDataBean getOtherShopDataBean) {
//                Log.d("visitothersshop1",getOtherShopDataBean.getMsg());
                rlyVisitOthersShopLoading.setVisibility(View.GONE);
                if(getOtherShopDataBean.getIssuccess().equals("1")){

                    if(getOtherShopDataBean.getLogo()!=null && !getOtherShopDataBean.getLogo().isEmpty()) {
                        Glide.with(getBaseContext())
                                .load(getOtherShopDataBean.getLogo())
                                .apply(new RequestOptions()
                                        .dontAnimate()
                                        .fallback(R.mipmap.head)
                                        .circleCrop()
                                )
                                // 重点在这行
                                .into(civVisitOthersShopLogo);
                    }
//                    Glide.with(getBaseContext())
//                            .load(getOtherShopDataBean.getShopbg())
//                            .apply(new RequestOptions()
//                                    .dontAnimate()
//                                    .fallback(R.mipmap.wodebg)
//                                    .skipMemoryCache(true)
//                                    .diskCacheStrategy(DiskCacheStrategy.NONE))
//                            // 重点在这行
//                            .into(ivVisitOthersShopBg);
//                    shopid = getVisitOthersShopDataBean.getShopid();
                    street = getOtherShopDataBean.getStreet();
                    if(getOtherShopDataBean.getShopbg() != null && !getOtherShopDataBean.getShopbg().isEmpty()) {
                        ImageLoader.getInstance().displayImage(getOtherShopDataBean.getShopbg(), ivVisitOthersShopBg, ImageLoaderUtils.options);
                    }
                    String content = getOtherShopDataBean.getContent();
                    if(content != null) {
                        tvVisitOthersShopContent.setText(content);
                    }else {
                        tvVisitOthersShopContent.setVisibility(View.GONE);
                    }
//                    ImageLoader.getInstance().displayImage(getOtherShopDataBean.getLogo(),civVisitOthersShopLogo, ImageLoaderUtils.options);
                    String articleImg = getOtherShopDataBean.getArticleimg();
                    if(articleImg == null || articleImg.isEmpty()){
                        ivVisitOthersShopArticleImg.setVisibility(View.GONE);
                        tvVisitOthersShopContent.setVisibility(View.VISIBLE);

                    }else {
                        imgUrlList.add(getOtherShopDataBean.getArticleimg());
                        Glide.with(getBaseContext())
                                .load(getOtherShopDataBean.getArticleimg())
                                .apply(new RequestOptions()
                                        .dontAnimate()
                                        .fallback(R.mipmap.allshopbg)
                                        .skipMemoryCache(true)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                                // 重点在这行
                                .into(ivVisitOthersShopArticleImg);
//                        ImageLoader.getInstance().displayImage(getVisitOthersShopDataBean.getArticleimg(), ivVisitOthersShopArticleImg, ImageLoaderUtils.options);
                    }
                    if(getOtherShopDataBean.getIsguanzhu().equals("1")){
//                        rlyVisitOthersShopGuanZhu.set
                        rlyVisitOthersShopGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                        tvVisitOthersShopGuanZhu.setText("已关注");
                        tvVisitOthersShopGuanZhu.setTextColor(Color.GRAY);
                    }else {
                        rlyVisitOthersShopGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                        tvVisitOthersShopGuanZhu.setText("+关注");
                        tvVisitOthersShopGuanZhu.setTextColor(Color.WHITE);
                    }
                    if(getOtherShopDataBean.getShopname() != null) {
                        tvVisitOthersShopName.setText(getOtherShopDataBean.getShopname());
                    }
                    if(getOtherShopDataBean.getLocate() != null) {
                        tvVisitOthersShopLoc.setText(getOtherShopDataBean.getLocate());
                    }
                    int ispraised = getOtherShopDataBean.getIspraised();
                    if(ispraised == 1){
                        ivVisitOthersShopPraise.setBackgroundResource(R.mipmap.praised);
                    }else {
                        ivVisitOthersShopPraise.setBackgroundResource(R.mipmap.praiseicon);
                    }
                    modules = getOtherShopDataBean.getModules();
                    articleAccount = getOtherShopDataBean.getShopid();
//                    xcCacheManager.writeCache(xcCacheSaveName.shopId,articleAccount);
                    articleid = getOtherShopDataBean.getArticleid();
                    title = getOtherShopDataBean.getTitle();
                    tel = getOtherShopDataBean.getTel();
                    tvVisitOthersShopTitle.setText(getOtherShopDataBean.getTitle());
                    tvVisitOthersShopArticleRepost.setText(getOtherShopDataBean.getRepost()+"");
                    tvVisitOthersShopArticleComment.setText(getOtherShopDataBean.getComment()+"");
                    tvVisitOthersShopArticlePraise.setText(getOtherShopDataBean.getParise()+"");
                    tvVisitOthersShopArticleBrowse.setText(getOtherShopDataBean.getReaders()+"");
                    tvVisitOthersShopPraise.setText(getOtherShopDataBean.getPraise()+"");
                    String businesshour = getOtherShopDataBean.getBusinesshour();
                    if(businesshour != null){
                        String[] hour = businesshour.split(" ");
                        if(hour.length > 1) {
                            tvVisitOthersShopBusinessHourMorning.setText(hour[0]);
                            tvVisitOthersShopBusinessHourAfternoon.setText(hour[1]);
                        }
                    }
//                    tvVisitOthersShopBusinessHour.setText(getVisitOthersShopDataBean.getBusinesshour()+"");
                    tvVisitOthersShopFans.setText(getOtherShopDataBean.getFans()+"");

                }else {

                }
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
//        initData();
    }


//    @SuppressLint("WrongConstant")
//    private void initShopMark(){
        //布局管理器对象 参数1.上下文 2.规定一行显示几列的参数常量
       /* GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        //设置RecycleView显示的方向是水平还是垂直 GridLayout.HORIZONTAL水平  GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL );
        //设置布局管理器， 参数gridLayoutManager对象
        rvVisitOthersShopShopMark.setLayoutManager(gridLayoutManager);
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 10;i++){
            dataList.add("");
        }
        mineMyShopShopMarkRVAdapter = new VisitOthersShopShopMarkRVAdapter();

        rvVisitOthersShopShopMark.setAdapter(mineMyShopShopMarkRVAdapter);

        mineMyShopShopMarkRVAdapter.replaceAll(dataList);*/
//    }
 /*   private void initShopArticle(){
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 1;i++){
            dataList.add("");
        }
        mineMyShopShopArticleRVAdapter = new VisitOthersShopShopArticleRVAdapter();
        rvVisitOthersShopShopArticle.setLayoutManager(new LinearLayoutManager(this));
        rvVisitOthersShopShopArticle.setAdapter(mineMyShopShopArticleRVAdapter);

        mineMyShopShopArticleRVAdapter.replaceAll(dataList);
    }*/

}
