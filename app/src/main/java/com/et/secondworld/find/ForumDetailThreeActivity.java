package com.et.secondworld.find;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.LoginActivity;
import com.et.secondworld.R;
import com.et.secondworld.TecentMapViewActivity;
import com.et.secondworld.VisitOthersShopActivity;
import com.et.secondworld.bean.AddBrowsHistoryBean;
import com.et.secondworld.bean.AddCancelZanBean;
import com.et.secondworld.bean.AddCommentBackBean;
import com.et.secondworld.bean.AddCommentBean;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.CancelGuanZhuBean;
import com.et.secondworld.bean.CollectArticleBean;
import com.et.secondworld.bean.GetCommentBean;
import com.et.secondworld.bean.GetForumDetailBean;
import com.et.secondworld.bean.GetPraiseCollectBean;
import com.et.secondworld.bean.GetShopEditDataBean;
import com.et.secondworld.dialog.ShareDialog;
import com.et.secondworld.forum.adapter.ForumDetailCommentRVAdapter;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.network.GuanZhuFansNetWork;
import com.et.secondworld.network.PraiseArticleNetWork;
import com.et.secondworld.network.ShopNetWork;
import com.et.secondworld.utils.BitmapUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.MineShopTimesPayDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.et.secondworld.widget.imageview.NormalImagePreActivity;

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
 * @Date 2020/4/10
 **/
public class ForumDetailThreeActivity extends AppCompatActivity {


    private List<String> imgUrlList = new ArrayList<>();
    @BindView(R.id.rly_forum_detail_three_back)
    RelativeLayout rlyForumDetailThreeBack;
    @OnClick(R.id.rly_forum_detail_three_back)
    public void rlyForumDetailThreeBackOnclick(){
        finish();
    }
    @OnClick(R.id.et_forum_detail_three_comment)
    public void etForumDetailTwoCommentOnclick(){
//        Toast.makeText(this,"sss",Toast.LENGTH_SHORT).show();
        rvAdapter.isOpen = true;
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        String role = xcCacheManager.readCache(xcCacheSaveName.role);
//
//        if(role != null && role.equals("01")) {
//            Toast.makeText(this,"禁言中",Toast.LENGTH_SHORT).show();
//            return;
//
//        }
    }
    @BindView(R.id.tv_forum_detail_three_time)
    TextView tvForumDetailThreeTime;
    @OnClick(R.id.lly_forum_detail_three)
    public void llyForumDetailThreeOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        map.clear();
        etForumDetailThreeComment.setHint("火不火靠你了~");
        tvForumDetailThreeCommentPublish.setVisibility(View.GONE);
        llyForumDetailThreeCommentSubmit.setVisibility(View.VISIBLE);
    }
    @BindView(R.id.tv_forum_detail_three_title)
    TextView tvForumDetailThreeTitle;
    @BindView(R.id.tv_forum_detail_three_titleone)
    TextView tvForumDetailThreeTitleOne;
    @BindView(R.id.tv_forum_detail_three_titletwo)
    TextView tvForumDetailThreeTitleTwo;
    @BindView(R.id.tv_forum_detail_three_titlethree)
    TextView tvForumDetailThreeTitleThree;
    @BindView(R.id.rly_forum_detail_three_share)
    RelativeLayout rlyForumDetailThreeShare;
    @OnClick(R.id.rly_forum_detail_three_share)
    public void rlyForumDetailThreeShareOnclick(){
        ShareDialog shareDialog = new ShareDialog(this,articleAccount,articleid,1,1,"","","").Build.build(this,nick,title,img);
        shareDialog.show();
    }
    @BindView(R.id.tv_forum_detail_three_titlefour)
    TextView tvForumDetailThreeTitleFour;
    @BindView(R.id.tv_forum_detail_three_titlefive)
    TextView tvForumDetailThreeTitleFive;
    @BindView(R.id.tv_forum_detail_three_titlesix)
    TextView tvForumDetailThreeTitleSix;
    @BindView(R.id.tv_forum_detail_three_contentone)
    TextView tvForumDetailThreeContentOne;
    @BindView(R.id.tv_forum_detail_three_contenttwo)
    TextView tvForumDetailThreeContentTwo;
    @BindView(R.id.tv_forum_detail_three_contentthree)
    TextView tvForumDetailThreeContentThree;
    @BindView(R.id.tv_forum_detail_three_contentfour)
    TextView tvForumDetailThreeContentFour;
    @BindView(R.id.tv_forum_detail_three_contentfive)
    TextView tvForumDetailThreeContentFive;
    @BindView(R.id.tv_forum_detail_three_contentsix)
    TextView tvForumDetailThreeContentSix;
    @BindView(R.id.rly_forum_detail_three_titleone)
    RelativeLayout rlyForumDetailThreeTitleOne;
    @BindView(R.id.rly_forum_detail_three_title)
    RelativeLayout rlyForumDetailThreeTitle;
    @BindView(R.id.rly_forum_detail_three_titletwo)
    RelativeLayout rlyForumDetailThreeTitleTwo;
    @BindView(R.id.rly_forum_detail_three_titlethree)
    RelativeLayout rlyForumDetailThreeTitleThree;
    @BindView(R.id.rly_forum_detail_three_titlefour)
    RelativeLayout rlyForumDetailThreeTitleFour;
    @BindView(R.id.rly_forum_detail_three_titlefive)
    RelativeLayout rlyForumDetailThreeTitleFive;
    @BindView(R.id.rly_forum_detail_three_titlesix)
    RelativeLayout rlyForumDetailThreeTitleSix;
    @BindView(R.id.iv_forum_detail_three_shop_times_pay)
    ImageView ivForumDetailThreeShopTimesPay;
    @OnClick(R.id.iv_forum_detail_three_shop_times_pay)
    public void ivForumDetailThreeShopTimesPayOnclick(){

//        MineShopTimesPayDialog mineShopTimesPayDialog = new MineShopTimesPayDialog(this).Build.build(this,articleid,type+"");
//        mineShopTimesPayDialog.show();
//        Intent intent = new Intent(this,ForumShopTimesPayActivity.class);
//        intent.putExtra("articleid",articleid);
//        intent.putExtra("type",type);
//        startActivity(intent);
    }
    @BindView(R.id.iv_forum_detail_three_img_ad)
    ImageView ivForumDetailThreeImgAd;
    @OnClick(R.id.iv_forum_detail_three_img_ad)
    public void ivForumDetailThreeImgAdOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(etForumDetailThreeComment)) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            rlyForumDetailThreeTitle.setFocusableInTouchMode(true);
            rlyForumDetailThreeTitle.requestFocus();
            map.clear();
            etForumDetailThreeComment.setHint("火不火靠你了~");
            tvForumDetailThreeCommentPublish.setVisibility(View.GONE);
            llyForumDetailThreeCommentSubmit.setVisibility(View.VISIBLE);
        }else {
            startImagePre(0, ivForumDetailThreeImgAd);
        }
    }
    @BindView(R.id.iv_forum_detail_three_img_one)
    ImageView ivForumDetailThreeImgOne;
    @OnClick(R.id.iv_forum_detail_three_img_one)
    public void ivForumDetailThreeImgOneOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(etForumDetailThreeComment)) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            rlyForumDetailThreeTitle.setFocusableInTouchMode(true);
            rlyForumDetailThreeTitle.requestFocus();
            map.clear();
            etForumDetailThreeComment.setHint("火不火靠你了~");
            tvForumDetailThreeCommentPublish.setVisibility(View.GONE);
            llyForumDetailThreeCommentSubmit.setVisibility(View.VISIBLE);
        }else {
            startImagePre(1, ivForumDetailThreeImgOne);
        }
//        startImagePre(1,ivForumDetailThreeImgOne);
    }
    @BindView(R.id.iv_forum_detail_three_img_two)
    ImageView ivForumDetailThreeImgTwo;
    @OnClick(R.id.iv_forum_detail_three_img_two)
    public void ivForumDetailThreeImgTwoOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(etForumDetailThreeComment)) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            rlyForumDetailThreeTitle.setFocusableInTouchMode(true);
            rlyForumDetailThreeTitle.requestFocus();
            map.clear();
            etForumDetailThreeComment.setHint("火不火靠你了~");
            tvForumDetailThreeCommentPublish.setVisibility(View.GONE);
            llyForumDetailThreeCommentSubmit.setVisibility(View.VISIBLE);
        }else {
            startImagePre(2, ivForumDetailThreeImgTwo);
        }
//        startImagePre(2,ivForumDetailThreeImgTwo);
    }
    @BindView(R.id.iv_forum_detail_three_img_three)
    ImageView ivForumDetailThreeImgThree;
    @OnClick(R.id.iv_forum_detail_three_img_three)
    public void ivForumDetailThreeImgThreeOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(etForumDetailThreeComment)) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            rlyForumDetailThreeTitle.setFocusableInTouchMode(true);
            rlyForumDetailThreeTitle.requestFocus();
            map.clear();
            etForumDetailThreeComment.setHint("火不火靠你了~");
            tvForumDetailThreeCommentPublish.setVisibility(View.GONE);
            llyForumDetailThreeCommentSubmit.setVisibility(View.VISIBLE);
        }else {
            startImagePre(3, ivForumDetailThreeImgThree);
        }
//        startImagePre(3,ivForumDetailThreeImgThree);
    }
    @BindView(R.id.iv_forum_detail_three_img_four)
    ImageView ivForumDetailThreeImgFour;
    @OnClick(R.id.iv_forum_detail_three_img_four)
    public void ivForumDetailThreeImgFourOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(etForumDetailThreeComment)) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            rlyForumDetailThreeTitle.setFocusableInTouchMode(true);
            rlyForumDetailThreeTitle.requestFocus();
            map.clear();
            etForumDetailThreeComment.setHint("火不火靠你了~");
            tvForumDetailThreeCommentPublish.setVisibility(View.GONE);
            llyForumDetailThreeCommentSubmit.setVisibility(View.VISIBLE);
        }else {
            startImagePre(4, ivForumDetailThreeImgFour);
        }
//        startImagePre(4,ivForumDetailThreeImgFour);
    }
    @BindView(R.id.iv_forum_detail_three_img_five)
    ImageView ivForumDetailThreeImgFive;
    @OnClick(R.id.iv_forum_detail_three_img_five)
    public void ivForumDetailThreeImgFiveOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(etForumDetailThreeComment)) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            rlyForumDetailThreeTitle.setFocusableInTouchMode(true);
            rlyForumDetailThreeTitle.requestFocus();
            map.clear();
            etForumDetailThreeComment.setHint("火不火靠你了~");
            tvForumDetailThreeCommentPublish.setVisibility(View.GONE);
            llyForumDetailThreeCommentSubmit.setVisibility(View.VISIBLE);
        }else {
            startImagePre(5, ivForumDetailThreeImgFive);
        }
//        startImagePre(5,ivForumDetailThreeImgFive);
    }
    @BindView(R.id.civ_forum_detail_three_head)
    ImageView civForumDetailThreeHead;
    @OnClick(R.id.civ_forum_detail_three_head)
    public void civForumDetailThreeHeadOnclick(){
        if(account!= null){
            if(account.equals(articleAccount)){
                return;
            }
        }
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, VisitOthersShopActivity.class);
            intent.putExtra("shopid", articleAccount);
            startActivity(intent);
        }
    }
    @BindView(R.id.tv_forum_detail_three_nick)
    TextView tvForumDetailThreeNick;
    @BindView(R.id.tv_forum_detail_three_loc)
    TextView tvForumDetailThreeLoc;
    private long clickTime = 0;
    @OnClick(R.id.tv_forum_detail_three_loc)
    public void tvForumDetailThreeLocOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, TecentMapViewActivity.class);
            intent.putExtra("addr",addr);
            startActivity(intent);
        }

    }
    @BindView(R.id.rly_forum_detail_three_guanzhu)
    RelativeLayout rlyForumDetailThreeGuanZhu;
    @OnClick(R.id.rly_forum_detail_three_guanzhu)
    public void rlyForumDetailThreeGuanZhuOnclick(){
        if(tvForumDetailThreeGuanZhu.getText().toString().equals("已关注")||tvForumDetailThreeGuanZhu.getText().toString().equals("互相关注")){
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
    }
    @BindView(R.id.tv_forum_detail_three_guanzhu)
    TextView tvForumDetailThreeGuanZhu;
    @BindView(R.id.et_forum_detail_three_comment)
    EditText etForumDetailThreeComment;
//    @OnClick(R.id.et_forum_detail_three_comment)
//    public void etForumDetailThreeCommentOnclick(){
//
//    }
    @BindView(R.id.lly_forum_detail_three_comment_submit)
    LinearLayout llyForumDetailThreeCommentSubmit;
    @BindView(R.id.tv_forum_detail_three_comment_publish)
    TextView tvForumDetailThreeCommentPublish;
    @OnClick(R.id.rly_forum_detail_three_comment_submit)
    public void llyForumDetailThreeCommentSubmitOnclick(){
//        Toast.makeText(this,"this is submit",Toast.LENGTH_LONG).show();
//        rvForumDetailThreeComment.requestFocus();
//        rlyForumDetailThreeNoComment.requestFocus();
//        Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
//        rvForumDetailThreeComment.setVisibility(View.VISIBLE);
//        rvForumDetailThreeComment.clearFocus();
//        rvForumDetailThreeComment.setFocusable(true);
//        rvForumDetailThreeComment.setFocusableInTouchMode(true);
//        rvForumDetailThreeComment.requestFocus();
//        tvForumDetailThreeAll.requestFocus();

//        rvForumDetailThreeComment.requestFocus();
//        rvForumDetailThreeComment.setFocusableInTouchMode(true);
//        etForumDetailThreeComment.requestFocus();
        rvForumDetailThreeComment.requestFocus();
        if(map.get("toaccountid") != null){
            commentBackComment();
        }else {
            submitComment();
        }

//        initComment();
    }
    @BindView(R.id.lly_forum_detail_three_collect)
    LinearLayout llyForumDetailThreeCollect;
    @OnClick(R.id.lly_forum_detail_three_collect)
    public void llyForumDetailThreeCollectOnclick(){
       collectArticle();
    }
    @BindView(R.id.lly_forum_detail_three_zan)
    LinearLayout llyForumDetailThreeZan;
    /*@BindView(R.id.iv_forum_detail_three_zan)
    ImageView ivForumDetailThreeZan;*/
    @BindView(R.id.tv_forum_detail_three_repost)
    TextView tvForumDetailThreeRepost;
    @BindView(R.id.tv_forum_detail_three_comments)
    TextView tvForumDetailThreeComments;
    @BindView(R.id.tv_forum_detail_three_good)
    TextView tvForumDetailThreeGood;
    @BindView(R.id.tv_forum_detail_three_readers)
    TextView tvForumDetailThreeReaders;
    @BindView(R.id.ib_forum_detail_three_zan)
    ImageButton ibForumDetailThreeZan;
    @BindView(R.id.iv_forum_detail_three_collect)
    ImageView ivForumDetailThreeCollect;
    @BindView(R.id.tv_forum_detail_three_comment_submit)
    TextView tvForumDetailThreeCommentSubmit;
    @BindView(R.id.tv_forum_detail_three_collect)
    TextView tvForumDetailThreeCollect;
    private int zanNums = 0;
    private int collectNums = 0;
    String thirdid = "";
    @OnClick(R.id.lly_forum_detail_three_zan)
    public void ibForumDetailThreeZanOnclick(){
        praiseArticle();

    }
    @BindView(R.id.tv_forum_detail_three_zan)
    TextView tvForumDetailThreeZan;
    @BindView(R.id.rly_forum_detail_three_no_comment)
    RelativeLayout rlyForumDetailThreeNoComment;
    @BindView(R.id.tv_forum_detail_three_no_comment)
    TextView tvForumDetailThreeNoComment;
    @BindView(R.id.tv_forum_detail_three_all)
    TextView tvForumDetailThreeAll;
//    private List<GetCommentBean.CommentlistBean> maxEightList = new ArrayList<>();
    @OnClick(R.id.tv_forum_detail_three_all)
    public void tvForumDetailThreeAllOnclick(){
//        rvAdapter.replaceAll(dataListTemp);
        tvForumDetailThreeAll.setTextSize(18);
        tvForumDetailThreeLouZhu.setTextSize(14);
        tvForumDetailThreeAll.setTextColor(Color.BLACK);
        tvForumDetailThreeLouZhu.setTextColor(Color.parseColor("#8E8E8E"));
        /*if(maxEightList.size() == 0){
            rlyForumDetailThreeNoComment.setVisibility(View.VISIBLE);
            tvForumDetailThreeNoComment.setText("快来发表你的评论吧！");
        }else {
            rlyForumDetailThreeNoComment.setVisibility(View.GONE);
        }
        rvAdapter.replaceAll(maxEightList);*/
        initComment();

    }
    @BindView(R.id.tv_forum_detail_three_louzhu)
    TextView tvForumDetailThreeLouZhu;
    @OnClick(R.id.tv_forum_detail_three_louzhu)
    public void tvForumDetailThreeLouZhuOnclick(){
        tvForumDetailThreeLouZhu.setTextSize(18);
        tvForumDetailThreeLouZhu.setTextColor(Color.BLACK);
        tvForumDetailThreeAll.setTextColor(Color.parseColor("#8E8E8E"));
        tvForumDetailThreeAll.setTextSize(14);
        louzhuComment();
    }
   /* @BindView(R.id.rv_forum_detail_three_content)
    RecyclerView rvForumDetailThreeContent;*/
    @BindView(R.id.rv_forum_detail_three_comment)
    RecyclerView rvForumDetailThreeComment;
    @BindView(R.id.iv_forum_detail_three_loading)
    ImageView ivForumDetailThreeLoading;
    @BindView(R.id.rly_forum_detail_three_loading)
    RelativeLayout rlyForumDetailThreeLoading;
    private ForumDetailCommentRVAdapter rvAdapter;
//    private ForumDetailThreeContentRVAdapter rvAdapterContent;
    private List<GetCommentBean.CommentlistBean> dataList = new ArrayList<>();
//    private List<GetCommentBean.CommentlistBean> dataListTemp = new ArrayList<>();
    String articleAccount = "";
    String articleid = "";
    String title = "";
    String account = "";
    String addr = "";
    private boolean isflag = true;
    int type = 0;
    private String isFriends = "0";
    private String isGuanZhu = "0";
    private String nick = "";
    private String img = "";
    private String head = "";
    private Map<String,Object> map = new HashMap<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_forum_detail_three);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);

        rlyForumDetailThreeLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivForumDetailThreeLoading);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(account == null || account.isEmpty() || account == "") {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String role = xcCacheManager.readCache(xcCacheSaveName.role);


        if(role != null && role.equals("01")) {
//            Toast.makeText(this,"禁言中",Toast.LENGTH_SHORT).show();
            etForumDetailThreeComment.setEnabled(false);
            etForumDetailThreeComment.setHint("禁言中");
//            return;

        }
        initContentData();
        initCommentRecycleView();
//        initContentRecycleView();
        initComment();
        getPraiseCollect();
        getArticleloc();
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

    private void getArticleloc(){
        Map<String,Object> map = new HashMap<>();
        map.put("shopid",articleAccount);
        ShopNetWork shopNetWork = new ShopNetWork();
        shopNetWork.getShopEditDataFromNet(map, new Observer<GetShopEditDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetShopEditDataBean getShopEditDataBean) {
                addr = getShopEditDataBean.getAddr();
                tvForumDetailThreeLoc.setText(addr);
            }
        });
    }
    private void initContentData(){
//        rlyForumDetailThreeLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.mipmap.pageloading).into(ivForumDetailThreeLoading);
        Intent intent = getIntent();
        articleAccount = intent.getStringExtra("articleAccount");
        articleid = intent.getStringExtra("articleid");
        title = intent.getStringExtra("title");
        type = intent.getIntExtra("type",-1);
//        Log.d("forumdetailAccount",articleAccount);
//        Log.d("forumdetailarticleid",articleid);
        etForumDetailThreeComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                toast("您输入的数据为："+s.toString());
                Log.d("etcomment",count+"");
                if(count > 0){
                    llyForumDetailThreeCommentSubmit.setVisibility(View.GONE);
                    tvForumDetailThreeCommentPublish.setVisibility(View.VISIBLE);
                }
                if(etForumDetailThreeComment.getText().length() == 0){
                    llyForumDetailThreeCommentSubmit.setVisibility(View.VISIBLE);
                    tvForumDetailThreeCommentPublish.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);

        if(shopid != null && articleAccount != null && articleAccount.equals(shopid)){
            account = shopid;
        }else {
            account = xcCacheManager.readCache(xcCacheSaveName.account);
        }
        if(account == null){
            account = "";
            rlyForumDetailThreeGuanZhu.setVisibility(View.GONE);
            etForumDetailThreeComment.setFocusable(false);

            etForumDetailThreeComment.setFocusableInTouchMode(false);
        }
        if(articleAccount != null &&articleAccount.equals(account)){
//             tvForumDetailTwoShare.setVisibility(View.GONE);
            rlyForumDetailThreeGuanZhu.setVisibility(View.GONE);

        }else {
            ivForumDetailThreeShopTimesPay.setVisibility(View.GONE);
        }
        if(type == -1){
            ivForumDetailThreeShopTimesPay.setVisibility(View.GONE);
        }
        if(account != null &&account.equals(articleAccount)){
//            llyForumDetailThreeCollect.setVisibility(View.GONE);
        }
        if(title != null) {
            tvForumDetailThreeTitle.setText(title);
        }
//        Log.d("forumdetailtitle",account);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("articleaccount",articleAccount);
        map.put("account",account);
//        Log.d("forumdetailNetWork",""+articleid);
//        Log.d("forumdetailNetWork",""+articleAccount);
//        Log.d("forumdetailNetWork",""+account);
        forumPostNetWork.getForumDetailThreeFromNet(map, new Observer<GetForumDetailBean>() {
            @Override
            public void onCompleted() {
//                Log.d("forumdetailNetWork","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
//                Log.d("forumdetailNetWork",""+e);
            }

            @Override
            public void onNext(GetForumDetailBean getForumDetailBean) {
//                Log.d("forumdetailNetWork","onCompleted"+getForumDetailBean.getMsg());
                if(getForumDetailBean.getIssuccess().equals("1")){
//                    Log.d("forumdetailNetWork","step1");
                    if(getForumDetailBean.getNick() != null) {
                        nick = getForumDetailBean.getNick();
                        tvForumDetailThreeNick.setText(getForumDetailBean.getNick());
//                        Log.d("forumdetailNetWork","step2");
                    }
                    head = getForumDetailBean.getHead();
                    String sections = getForumDetailBean.getSections();
                    Glide.with(getBaseContext()).load(getForumDetailBean.getHead()).apply(new RequestOptions().circleCrop().fallback(R.mipmap.imghead)
                            .error(R.mipmap.imghead)).into(civForumDetailThreeHead);
//                    ImageLoader.getInstance().displayImage(getForumDetailBean.getHead(),civForumDetailThreeHead, ImageLoaderUtils.options);
//                    Log.d("forumdetailNetWork","step3");
                    String time = getForumDetailBean.getTimes();
                    if(time != null){
                        tvForumDetailThreeTime.setText(time);
                    }
                    isFriends = getForumDetailBean.getIsfriend();
                    isGuanZhu = getForumDetailBean.getIsguanzhu();
                    if (getForumDetailBean.getIsguanzhu().equals("0")) {
                        if (getForumDetailBean.getIsfriend().equals("0")) {
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                            tvForumDetailThreeGuanZhu.setText("关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.WHITE);
                        }else{
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvForumDetailThreeGuanZhu.setText("已关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.GRAY);
//                            tvForumDetailThreeGuanZhu.setTextColor(Color.parseColor("#BEBEBE"));
                        }
                    } else {
                        if (getForumDetailBean.getIsfriend().equals("0")) {
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                            tvForumDetailThreeGuanZhu.setText("关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.WHITE);

                        } else {
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvForumDetailThreeGuanZhu.setText("互相关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.GRAY);
//                            tvForumDetailThreeGuanZhu.setTextColor(Color.parseColor("#BEBEBE"));
                        }
                    }
                    if(getForumDetailBean.getImglist() == null){
                        return;
                    }
                    int imgsize = getForumDetailBean.getImglist().size();
                    if(imgUrlList.size() == 0) {
                        for (GetForumDetailBean.ImglistBean item : getForumDetailBean.getImglist()) {
                            imgUrlList.add(item.getUrl());
                        }
                    }
                    if(imgsize > 0){
                        img = imgUrlList.get(0);
                    }
//                    imgUrlList.addAll(getForumDetailBean.getImglist());
                    String content = getForumDetailBean.getContent();

                    if(content == null){
                        return;
                    }
                    String temp = "";
                    Log.d("forumDetail33",imgsize+" "+content);
                    if(imgsize == 0){
                        rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                        tvForumDetailThreeContentOne.setVisibility(View.VISIBLE);
                        tvForumDetailThreeContentOne.setText(content);

                    }else {
                        switch (imgsize){
                            case 1:
                                rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentOne.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgAd.setVisibility(View.VISIBLE);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl()).apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1)).into(ivForumDetailThreeImgAd);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailThreeImgAd,ImageLoaderUtils.options);
                                tvForumDetailThreeContentOne.setText(content);
                                break;
                            case 2:
                                rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentOne.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgAd.setVisibility(View.VISIBLE);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgAd);
//
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailThreeImgAd,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleTwo.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentTwo.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgOne.setVisibility(View.VISIBLE);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(1).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgOne);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailThreeImgOne,ImageLoaderUtils.options);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailThreeImgTwo,ImageLoaderUtils.options);
                                if(sections !=null && !sections.isEmpty()&&!sections.equals("-1")){
                                    String[] tempSections = sections.split(",");
                                    try{
                                        int first = 0;
                                         int second = Integer.parseInt(tempSections[0]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentOne.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[1]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentTwo.setText(temp);
                                    }catch (Exception e){

                                    }
                                }else {



                                     temp = content.substring(0,content.length()/2);
                                    tvForumDetailThreeContentOne.setText(temp);
                                    temp = content.substring(content.length()/2,content.length());
                                    tvForumDetailThreeContentTwo.setText(temp);
                                }
                                break;
                            case 3:
                                rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentOne.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgAd.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailThreeImgAd,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleTwo.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentTwo.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgOne.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailThreeImgOne,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleThree.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentThree.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgTwo.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailThreeImgTwo,ImageLoaderUtils.options);

                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgAd);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(1).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgOne);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(2).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgTwo);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailThreeImgThree,ImageLoaderUtils.options);
                                if(sections !=null && !sections.isEmpty()&&!sections.equals("-1")){
                                    String[] tempSections = sections.split(",");
//                                    for(String item :tempSections){
//                                        Log.d("forumDetail33",imgsize+" "+item);
//                                    }
                                    try{
                                        int first = 0;
                                        int second = Integer.parseInt(tempSections[0]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentOne.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[1]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentTwo.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[2]);
                                        temp = content.substring(first,second);
//                                        Log.d("forumDetail33",imgsize+" "+Integer.parseInt(tempSections[1])+Integer.parseInt(tempSections[2]));
                                        tvForumDetailThreeContentThree.setText(temp);
                                    }catch (Exception e){
//                                        Log.d("forumDetail33",imgsize+" "+e);
                                    }
                                }else {
                                    temp = content.substring(0, content.length() / 3);
                                    tvForumDetailThreeContentOne.setText(temp);
                                    temp = content.substring((content.length() * 1) / 3, (content.length() * 2) / 3);
                                    tvForumDetailThreeContentTwo.setText(temp);
                                    temp = content.substring((content.length() * 2) / 3, content.length());
                                    tvForumDetailThreeContentThree.setText(temp);
                                }
                                break;
                            case 4:
                                rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentOne.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgAd.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailThreeImgAd,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleTwo.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentTwo.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgOne.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailThreeImgOne,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleThree.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentThree.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgTwo.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailThreeImgTwo,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleFour.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentFour.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgThree.setVisibility(View.VISIBLE);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgAd);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(1).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgOne);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(2).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgTwo);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(3).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgThree);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(3).getUrl(),ivForumDetailThreeImgThree,ImageLoaderUtils.options);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(3).getUrl(),ivForumDetailThreeImgFour,ImageLoaderUtils.options);
                                if(sections !=null && !sections.isEmpty()&&!sections.equals("-1")){
                                    String[] tempSections = sections.split(",");
                                    try{
                                        int first = 0;
                                        int second = Integer.parseInt(tempSections[0]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentOne.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[1]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentTwo.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[2]);
                                        temp = content.substring(first,second);
//                                        Log.d("forumDetail33",imgsize+" "+Integer.parseInt(tempSections[1])+Integer.parseInt(tempSections[2]));
                                        tvForumDetailThreeContentThree.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[3]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentFour.setText(temp);
                                    }catch (Exception e){

                                    }
                                }else {
                                    temp = content.substring(0, content.length() / 4);
                                    tvForumDetailThreeContentOne.setText(temp);
                                    temp = content.substring((content.length() * 1) / 4, (content.length() * 2) / 4);
                                    tvForumDetailThreeContentTwo.setText(temp);
                                    temp = content.substring((content.length() * 2) / 4, (content.length() * 3) / 4);
                                    tvForumDetailThreeContentThree.setText(temp);
                                    temp = content.substring((content.length() * 3) / 4, content.length());
                                    tvForumDetailThreeContentFour.setText(temp);
                                }
                                break;
                            case 5:
                                rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentOne.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgAd.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailThreeImgAd,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleTwo.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentTwo.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgOne.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailThreeImgOne,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleThree.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentThree.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgTwo.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailThreeImgTwo,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleFour.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentFour.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgThree.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(3).getUrl(),ivForumDetailThreeImgThree,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleFive.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentFive.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgFour.setVisibility(View.VISIBLE);

                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgAd);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(1).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgOne);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(2).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgTwo);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(3).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgThree);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(4).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgFour);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(4).getUrl(),ivForumDetailThreeImgFour,ImageLoaderUtils.options);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(3).getUrl(),ivForumDetailThreeImgFive,ImageLoaderUtils.options);
                                if(sections !=null && !sections.isEmpty()&&!sections.equals("-1")){
                                    String[] tempSections = sections.split(",");
                                    try{
                                        int first = 0;
                                        int second = Integer.parseInt(tempSections[0]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentOne.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[1]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentTwo.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[2]);
                                        temp = content.substring(first,second);
//                                        Log.d("forumDetail33",imgsize+" "+Integer.parseInt(tempSections[1])+Integer.parseInt(tempSections[2]));
                                        tvForumDetailThreeContentThree.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[3]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentFour.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[4]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentFive.setText(temp);
                                    }catch (Exception e){

                                    }
                                }else {
                                    temp = content.substring(0, content.length() / 5);
                                    tvForumDetailThreeContentOne.setText(temp);
                                    temp = content.substring((content.length() * 1) / 5, (content.length() * 2) / 5);
                                    tvForumDetailThreeContentTwo.setText(temp);
                                    temp = content.substring((content.length() * 2) / 5, (content.length() * 3) / 5);
                                    tvForumDetailThreeContentThree.setText(temp);
                                    temp = content.substring((content.length() * 3) / 5, (content.length() * 4) / 5);
                                    tvForumDetailThreeContentFour.setText(temp);
                                    temp = content.substring((content.length() * 4) / 5, content.length());
                                    tvForumDetailThreeContentFive.setText(temp);
                                }
                                break;
                            case 6:
                                rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentOne.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgAd.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgOne.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailThreeImgAd,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleTwo.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentTwo.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgTwo.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailThreeImgOne,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleThree.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentThree.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgThree.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailThreeImgTwo,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleFour.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentFour.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgFour.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(3).getUrl(),ivForumDetailThreeImgThree,ImageLoaderUtils.options);
                                rlyForumDetailThreeTitleFive.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentFive.setVisibility(View.VISIBLE);
                                ivForumDetailThreeImgFive.setVisibility(View.VISIBLE);
                                rlyForumDetailThreeTitleSix.setVisibility(View.VISIBLE);
                                tvForumDetailThreeContentSix.setVisibility(View.VISIBLE);
//                                ivForumDetailThreeImgSix.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(4).getUrl(),ivForumDetailThreeImgFour,ImageLoaderUtils.options);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(5).getUrl(),ivForumDetailThreeImgFive,ImageLoaderUtils.options);

                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgAd);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(1).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgOne);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(2).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgTwo);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(3).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgThree);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(4).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgFour);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(5).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailThreeImgFive);
                                if(sections !=null && !sections.isEmpty()&&!sections.equals("-1")){
                                    String[] tempSections = sections.split(",");
                                    try{
                                        int first = 0;
                                        int second = Integer.parseInt(tempSections[0]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentOne.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[1]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentTwo.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[2]);
                                        temp = content.substring(first,second);
//                                        Log.d("forumDetail33",imgsize+" "+Integer.parseInt(tempSections[1])+Integer.parseInt(tempSections[2]));
                                        tvForumDetailThreeContentThree.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[3]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentFour.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[4]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentFive.setText(temp);
                                        first = second;
                                        second += Integer.parseInt(tempSections[5]);
                                        temp = content.substring(first,second);
                                        tvForumDetailThreeContentSix.setText(temp);
                                    }catch (Exception e){

                                    }
                                }else {
                                    temp = content.substring(0, content.length() / 6);
                                    tvForumDetailThreeContentOne.setText(temp);
                                    temp = content.substring((content.length() * 1) / 6, (content.length() * 2) / 6);
                                    tvForumDetailThreeContentTwo.setText(temp);
                                    temp = content.substring((content.length() * 2) / 6, (content.length() * 3) / 6);
                                    tvForumDetailThreeContentThree.setText(temp);
                                    temp = content.substring((content.length() * 3) / 6, (content.length() * 4) / 6);
                                    tvForumDetailThreeContentFour.setText(temp);
                                    temp = content.substring((content.length() * 4) / 6, (content.length() * 5) / 6);
                                    tvForumDetailThreeContentFive.setText(temp);
                                    temp = content.substring((content.length() * 5) / 6, content.length());
                                    tvForumDetailThreeContentSix.setText(temp);
                                }
                                break;
                        }
                    }
                    if(imgsize > 6){
                        rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                        tvForumDetailThreeContentOne.setVisibility(View.VISIBLE);
                        ivForumDetailThreeImgAd.setVisibility(View.VISIBLE);
                        ivForumDetailThreeImgOne.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailThreeImgAd,ImageLoaderUtils.options);
                        rlyForumDetailThreeTitleTwo.setVisibility(View.VISIBLE);
                        tvForumDetailThreeContentTwo.setVisibility(View.VISIBLE);
                        ivForumDetailThreeImgTwo.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailThreeImgOne,ImageLoaderUtils.options);
                        rlyForumDetailThreeTitleThree.setVisibility(View.VISIBLE);
                        tvForumDetailThreeContentThree.setVisibility(View.VISIBLE);
                        ivForumDetailThreeImgThree.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailThreeImgTwo,ImageLoaderUtils.options);
                        rlyForumDetailThreeTitleFour.setVisibility(View.VISIBLE);
                        tvForumDetailThreeContentFour.setVisibility(View.VISIBLE);
                        ivForumDetailThreeImgFour.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(3).getUrl(),ivForumDetailThreeImgThree,ImageLoaderUtils.options);
                        rlyForumDetailThreeTitleFive.setVisibility(View.VISIBLE);
                        tvForumDetailThreeContentFive.setVisibility(View.VISIBLE);
                        ivForumDetailThreeImgFive.setVisibility(View.VISIBLE);
                        rlyForumDetailThreeTitleSix.setVisibility(View.VISIBLE);
                        tvForumDetailThreeContentSix.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(4).getUrl(),ivForumDetailThreeImgFour,ImageLoaderUtils.options);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(5).getUrl(),ivForumDetailThreeImgFive,ImageLoaderUtils.options);

                        Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                .into(ivForumDetailThreeImgAd);
                        Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(1).getUrl())
                                .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                .into(ivForumDetailThreeImgOne);
                        Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(2).getUrl())
                                .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                .into(ivForumDetailThreeImgTwo);
                        Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(3).getUrl())
                                .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                .into(ivForumDetailThreeImgThree);
                        Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(4).getUrl())
                                .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                .into(ivForumDetailThreeImgFour);
                        Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(5).getUrl())
                                .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                .into(ivForumDetailThreeImgFive);
                        if(sections !=null && !sections.isEmpty()&&!sections.equals("-1")){
                            String[] tempSections = sections.split(",");
                            try{
                                int first = 0;
                                int second = Integer.parseInt(tempSections[0]);
                                temp = content.substring(first,second);
                                tvForumDetailThreeContentOne.setText(temp);
                                first = second;
                                second += Integer.parseInt(tempSections[1]);
                                temp = content.substring(first,second);
                                tvForumDetailThreeContentTwo.setText(temp);
                                first = second;
                                second += Integer.parseInt(tempSections[2]);
                                temp = content.substring(first,second);
//                                        Log.d("forumDetail33",imgsize+" "+Integer.parseInt(tempSections[1])+Integer.parseInt(tempSections[2]));
                                tvForumDetailThreeContentThree.setText(temp);
                                first = second;
                                second += Integer.parseInt(tempSections[3]);
                                temp = content.substring(first,second);
                                tvForumDetailThreeContentFour.setText(temp);
                                first = second;
                                second += Integer.parseInt(tempSections[4]);
                                temp = content.substring(first,second);
                                tvForumDetailThreeContentFive.setText(temp);
                                first = second;
                                second += Integer.parseInt(tempSections[5]);
                                temp = content.substring(first,second);
                                tvForumDetailThreeContentSix.setText(temp);
                            }catch (Exception e){

                            }
                        }else {
                            temp = content.substring(0, content.length() / 6);
                            tvForumDetailThreeContentOne.setText(temp);
                            temp = content.substring((content.length() * 1) / 6, (content.length() * 2) / 6);
                            tvForumDetailThreeContentTwo.setText(temp);
                            temp = content.substring((content.length() * 2) / 6, (content.length() * 3) / 6);
                            tvForumDetailThreeContentThree.setText(temp);
                            temp = content.substring((content.length() * 3) / 6, (content.length() * 4) / 6);
                            tvForumDetailThreeContentFour.setText(temp);
                            temp = content.substring((content.length() * 4) / 6, (content.length() * 5) / 6);
                            tvForumDetailThreeContentFive.setText(temp);
                            temp = content.substring((content.length() * 5) / 6, content.length());
                            tvForumDetailThreeContentSix.setText(temp);
                        }
                    }

/*
                    if(getForumDetailBean.getContent() != null) {
                        tvForumDetailTwoContent.setText(getForumDetailBean.getContent());
                    }*/
//                    tvForumDetailThreeRepost.setText(getForumDetailBean.getRepost()+"");
//                    tvForumDetailThreeComments.setText(getForumDetailBean.getComment()+"");
//                    tvForumDetailThreeGood.setText(getForumDetailBean.getGood()+"");
//                    tvForumDetailThreeReaders.setText(getForumDetailBean.getReaders()+"");
//                    Log.d("forumdetailNetWork",""+getForumDetailBean.getImglist());

//                    icvForumDetailTwo.setImageResources(getForumDetailBean.getImglist(),null,null,imageCycleViewListener);
                }
                if(isflag) {
                    initBrowsHistory();
                    isflag = false;
                }
                rlyForumDetailThreeLoading.setVisibility(View.GONE);

//                Log.d("forumdetailNetWork","step4");
            }
        });


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
  /*  private void initContentRecycleView(){
        rvAdapterContent = new ForumDetailThreeContentRVAdapter();
        ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            dataList.add("");
        }

        rvForumDetailThreeContent.setLayoutManager(new LinearLayoutManager(this));

        rvForumDetailThreeContent.setAdapter(rvAdapterContent);
        rvAdapterContent.replaceAll(dataList);
    }*/
    private void initCommentRecycleView(){
        rvAdapter = new ForumDetailCommentRVAdapter(dataList,articleid,etForumDetailThreeComment,this,map,account);
//        ArrayList<String> dataList = new ArrayList<>();
//        for(int i = 0;i < 4;i++){

//        }
        rvForumDetailThreeComment.setLayoutManager(new LinearLayoutManager(this));
        rvForumDetailThreeComment.setAdapter(rvAdapter);
        rvAdapter.setArticleAccount(articleAccount);
//        rvForumDetailThreeComment.setItemViewCacheSize(100);
//        rvAdapter.replaceAll(dataList);
    }

    private void initBrowsHistory(){
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,Object> mapParam = new HashMap<>();
        mapParam.put("articleid",articleid);
        mapParam.put("account",account);
        mapParam.put("nick",nick);
        mapParam.put("head",head);
        forumPostNetWork.addBrowsHistoryToNet(mapParam, new Observer<AddBrowsHistoryBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddBrowsHistoryBean addBrowsHistoryBean) {

            }
        });
    }



    private void initComment(){
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map=new HashMap<>();
        map.put("articleid",articleid);
        forumPostNetWork.getArticleCommentFromNet(map, new Observer<GetCommentBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetCommentBean getCommentBean) {
                if(getCommentBean.getIssuccess().equals("1")){
                    dataList = getCommentBean.getCommentlist();
//                    List<GetCommentBean.CommentlistBean> commentList = new ArrayList<>();
//                    maxEightList.clear();
//                    commentList.addAll(dataList);
//
//
//                    if(commentList.size() > 0) {
//                        for(int j = 0;j< (commentList.size() >=8?8:commentList.size());j++) {
//                            int maxPos = 0;
//                            GetCommentBean.CommentlistBean maxItem = commentList.get(0);
//                            for (int i = 0; i < commentList.size(); i++) {
////                                Log.d("commentsort1","good:"+commentList.get(i).getPraisecount());
//                                if (maxItem.getPraisecount() < commentList.get(i).getPraisecount()) {
//                                    maxItem = commentList.get(i);
//                                    maxPos = i;
//                                }
//                            }
////                            Log.d("commentsort1","maxPos:"+maxPos+" maxItem:"+maxItem.getPraisecount());
//                            commentList.remove(maxPos);
//                            maxEightList.add(maxItem);
//
//                        }
////                        Log.d("commentsort1","commentlist:"+commentList.size()+" maxeightlist:"+maxEightList.size());
//
//                        for(int i=0;i<commentList.size();i++){
//                            maxEightList.add(commentList.get(i));
//                        }
//
//                    }
                    List<GetCommentBean.CommentlistBean> commentList = new ArrayList<>();
                    if(dataList.size() > 0){
                        for(int j = 0;j< (dataList.size() >=8?8:dataList.size());j++) {
                            int maxPos = 0;
                            GetCommentBean.CommentlistBean maxItem = dataList.get(0);
                            for (int i = 0; i < dataList.size(); i++) {
//                                Log.d("commentsort1","good:"+commentList.get(i).getPraisecount());
                                if (maxItem.getPraisecount() < dataList.get(i).getPraisecount()) {
                                    maxItem = dataList.get(i);
                                    maxPos = i;
                                }
                            }
//                            Log.d("commentsort1","maxPos:"+maxPos+" maxItem:"+maxItem.getPraisecount());
                            dataList.remove(maxPos);
                            commentList.add(0,maxItem);
//
                        }
                    }
                    for(int i=0;i<commentList.size();i++){
                        dataList.add(0,commentList.get(i));
                    }
                    if(dataList.size() == 0){
//                    if(maxEightList.size() == 0){
                        rlyForumDetailThreeNoComment.setVisibility(View.VISIBLE);
                    }else {
                        rlyForumDetailThreeNoComment.setVisibility(View.GONE);
                    }
//                    rvAdapter.replaceAll(maxEightList);
                    rvAdapter.replaceAll(dataList);



//                    dataListTemp.clear();
//                    dataListTemp.addAll(getCommentBean.getCommentlist());
//                    List<GetCommentBean.CommentlistBean> commentList = new ArrayList<>();
//
//                    commentList.addAll(dataListTemp);
//                    maxEightList.clear();
//
//                    if(commentList.size() > 0) {
//                        for(int j = 0;j< (commentList.size() >=8?8:commentList.size());j++) {
//                            int maxPos = 0;
//                            GetCommentBean.CommentlistBean maxItem = commentList.get(0);
//                            for (int i = 0; i < commentList.size(); i++) {
////                                Log.d("commentsort1","good:"+commentList.get(i).getPraisecount());
//                                if (maxItem.getPraisecount() < commentList.get(i).getPraisecount()) {
//                                    maxItem = commentList.get(i);
//                                    maxPos = i;
//                                }
//                            }
////                            Log.d("commentsort1","maxPos:"+maxPos+" maxItem:"+maxItem.getPraisecount());
//                            commentList.remove(maxPos);
//                            maxEightList.add(maxItem);
//
//                        }
////                        Log.d("commentsort1","commentlist:"+commentList.size()+" maxeightlist:"+maxEightList.size());
//
//                        for(int i=0;i<commentList.size();i++){
//                            maxEightList.add(commentList.get(i));
//                        }
//
//                    }
//                    if(maxEightList.size() == 0){
//                        rlyForumDetailThreeNoComment.setVisibility(View.VISIBLE);
//                    }else {
//                        rlyForumDetailThreeNoComment.setVisibility(View.GONE);
//                    }
////                    dataList.clear();
//                    rvAdapter.replaceAll(maxEightList);
//                    rvAdapter.replaceAll(getCommentBean.getCommentlist());
                    int commentsize = dataList.size();
                    tvForumDetailThreeCommentSubmit.setText(commentsize+"");
                }
            }
        });
    }
    private void louzhuComment(){
        List<GetCommentBean.CommentlistBean> louzhouList = new ArrayList<>();
//        List<GetCommentBean.CommentlistBean> tempLouZhuList = new ArrayList<>();
        if(dataList.size() == 0){
            rlyForumDetailThreeNoComment.setVisibility(View.VISIBLE);
            tvForumDetailThreeNoComment.setText("楼主还没有评论，快来戳他吧！");
        }else {
            rlyForumDetailThreeNoComment.setVisibility(View.GONE);
        }
//        if(louzhouList.size()>0) {
        if(dataList.size()>0) {
//            String louzhuAccount = louzhouList.get(0).getAccount();
//            Log.d("louzhucommentAccount",louzhouList.size()+"");
//            String louzhuAccount = articleAccount;
            for (int i = 0; i < dataList.size(); ) {
                String temp = dataList.get(i).getAccount();

                if (articleAccount.equals(temp)) {
//                    Log.d("louzhucomment",temp);
                    louzhouList.add(dataList.get(i));
//                    dataList.remove(i);
//                    continue;
                }
                i++;

            }
            dataList.clear();
            dataList.addAll(louzhouList);
//            for(int i=dataList.size()-1;i >= 0;i--){
//
//            }
//            if(dataList.size() == 0){
//                rlyForumDetailTwoNoComment.setVisibility(View.VISIBLE);
//                tvForumDetailTwoNoComment.setText("楼主还没有评论，快来戳他吧！");
//            }else {
//                rlyForumDetailTwoNoComment.setVisibility(View.GONE);
//            }
            rvAdapter.replaceAll(dataList);
        }
//        louzhouList.addAll(dataListTemp);
//        Log.d("detailthree1",louzhouList.size()+"");
//        if(louzhouList.size() == 0){
//            rlyForumDetailThreeNoComment.setVisibility(View.VISIBLE);
//            tvForumDetailThreeNoComment.setText("楼主还没有评论，快来戳他吧！");
//        }else {
//            rlyForumDetailThreeNoComment.setVisibility(View.GONE);
//        }
//        if(louzhouList.size()>0) {
////            String louzhuAccount = louzhouList.get(0).getAccount();
////            Log.d("louzhucommentAccount",louzhouList.size()+"");
////            String louzhuAccount = articleAccount;
//            for (int i = 0; i < louzhouList.size(); ) {
//                String temp = louzhouList.get(i).getAccount();
//
//                if(!articleAccount.equals(temp)){
////                    Log.d("louzhucomment",temp);
//                    louzhouList.remove(i);
//                    continue;
//                }
//                i++;
//
//            }
//            for(int i=louzhouList.size()-1;i >= 0;i--){
//                tempLouZhuList.add(louzhouList.get(i));
//            }
//            if(tempLouZhuList.size() == 0){
//                rlyForumDetailThreeNoComment.setVisibility(View.VISIBLE);
//                tvForumDetailThreeNoComment.setText("楼主还没有评论，快来戳他吧！");
//            }else {
//                rlyForumDetailThreeNoComment.setVisibility(View.GONE);
//            }
//            rvAdapter.replaceAll(tempLouZhuList);
//        }
    }
    private void submitComment(){
        String content = etForumDetailThreeComment.getText().toString();
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(content.equals("")||content.isEmpty()){
            return;
        }
        if(thirdid == null){
            thirdid = "";
        }
//        Log.d("submitcomment1",account);
        Map<String,String> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("account",account);
        map.put("content",content);
        map.put("platform","android");
        map.put("articleaccount",articleAccount);
        map.put("thirdid",thirdid);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        forumPostNetWork.addArticleCommentFromNet(map, new Observer<AddCommentBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddCommentBean addCommentBean) {
                if(addCommentBean.getIssuccess().equals("1")) {
                    Toast.makeText(getBaseContext(), addCommentBean.getMsg(), Toast.LENGTH_SHORT).show();
                    etForumDetailThreeComment.setText("");
                    initComment();
                    showHideSoftInput();
                    getPraiseCollect();
                }
            }
        });
    }
    private void showHideSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void commentBackComment(){
        String commentBackContent = etForumDetailThreeComment.getText().toString();
        if(commentBackContent.equals("")||commentBackContent.isEmpty()){
            return;
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("toaccountid",map.get("toaccountid"));
        paramMap.put("tonick",map.get("tonick"));
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        paramMap.put("account",account);
        paramMap.put("platform","android");
        paramMap.put("content",commentBackContent);
        paramMap.put("commentid",map.get("commentid"));
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        forumPostNetWork.addCommentBackToNet(paramMap, new Observer<AddCommentBackBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddCommentBackBean addCommentBackBean) {
                if(addCommentBackBean.getIssuccess().equals("1")){
                    Toast.makeText(getBaseContext(),addCommentBackBean.getMsg(),Toast.LENGTH_SHORT).show();
                    etForumDetailThreeComment.setText("");
                    dataList.clear();
                    rvAdapter.notifyDataSetChanged();
                    showHideSoftInput();
                    initComment();
                }
            }
        });
    }

    private void guanZhuSubmit(){
        Map<String,String> map = new HashMap<>();
        GuanZhuFansNetWork guanZhuFansNetWork= new GuanZhuFansNetWork();

        map.put("account",articleAccount);
        map.put("followid",account);
        /*if(isFriends.equals("0")){
            map.put("type","1");
        }
        if(isFriends.equals("1")){
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
                        if(isGuanZhu.equals("1")) {
//                            if (isFriends == 0) {
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvForumDetailThreeGuanZhu.setText("互相关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.GRAY);
//                            tvForumDetailThreeGuanZhu.setTextColor(Color.parseColor("#BEBEBE"));
//                                dataList.get(pos).setIsfans(1);
//                                dataList.get(pos).setIsfriends(1);
//                            }
//                            if (isFriends == 1) {
//                                rlyMineGuanZhuRVItemGuanZhu.setBackgroundResource(R.drawable.weiguanzhu_shape);
//                                tvMineGuanZhuRVItemGuanZhu.setText("关注");
//                                tvMineGuanZhuRVItemGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
//
//                                dataList.get(pos).setIsfans(0);
//                                dataList.get(pos).setIsfriends(0);
//                            }
                        }
                        if(isGuanZhu.equals("0")) {
//                            if (isFriends == 0) {
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvForumDetailThreeGuanZhu.setText("已关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.GRAY);
//                            tvForumDetailThreeGuanZhu.setTextColor(Color.parseColor("#BEBEBE"));
//                                dataList.get(pos).setIsfans(1);
//                                dataList.get(pos).setIsfriends(1);
//                            }
//                            if (isFriends == 1) {
//
//
//                            }
                        }
//                        notifyDataSetChanged();
                    }else {
                        rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                        tvForumDetailThreeGuanZhu.setText("关注");
                        tvForumDetailThreeGuanZhu.setTextColor(Color.WHITE);

//                            dataList.get(pos).setIsfans(0);
//                        dataList.get(pos).setIsfriends(0);
                    }
                    Toast.makeText(getBaseContext(), cancelGuanZhuBean.getMsg(), Toast.LENGTH_SHORT).show();
                    /*if (cancelGuanZhuBean.getIssuccess().equals("1")) {

                        if (isFriends.equals("0")) {
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.huxiangguanzhu_shape);
                            tvForumDetailThreeGuanZhu.setText("已关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.GRAY);
                            isFriends = "1";
                        } else {
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.weiguanzhu_shape);
                            tvForumDetailThreeGuanZhu.setText("关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                            isFriends = "0";
                        }

                        Toast.makeText(getBaseContext(), cancelGuanZhuBean.getMsg(), Toast.LENGTH_SHORT).show();
//                        notifyDataSetChanged();
                    }*/
                }
            });
       /* }else {
            guanZhuFansNetWork.addCancelFansToNet(map, new Observer<AddFansBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(AddFansBean addFansBean) {
                    if(addFansBean.getIssuccess().equals("1")){
                        if (isFriends.equals("0")) {
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.huxiangguanzhu_shape);
                            tvForumDetailThreeGuanZhu.setText("互相关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.GRAY);
                            isFriends = "1";
                        } else {
                            rlyForumDetailThreeGuanZhu.setBackgroundResource(R.drawable.weiguanzhu_shape);
                            tvForumDetailThreeGuanZhu.setText("关注");
                            tvForumDetailThreeGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                            isFriends = "0";
                        }
                        Toast.makeText(getBaseContext(), addFansBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }*/
    }
    private void collectArticle(){
        if(account == null || account.isEmpty()){
            return;
        }
//        if(account.equals(articleAccount)){
//            return;
//        }
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("account",account);
        forumPostNetWork.addCollectArticleToNet(map, new Observer<CollectArticleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CollectArticleBean collectArticleBean) {
                Toast.makeText(getBaseContext(),collectArticleBean.getMsg(),Toast.LENGTH_SHORT).show();
                if(collectArticleBean.getIssuccess().equals("1")){
//                    ibForumDetailThreeZan.setBackgroundResource(R.mipmap.redgood);
                    collectNums++;
                    ivForumDetailThreeCollect.setBackgroundResource(R.mipmap.collected);
                    tvForumDetailThreeCollect.setText(collectNums+"");

                }
                if(collectArticleBean.getIssuccess().equals("2")){
//                    ibForumDetailThreeZan.setBackgroundResource(R.mipmap.thumbs);
                    collectNums--;
                    tvForumDetailThreeCollect.setText(collectNums+"");
                    ivForumDetailThreeCollect.setBackgroundResource(R.mipmap.collect);
                }
            }
        });
    }

    private void praiseArticle(){
        if(account == null || account.isEmpty()){
            return;
        }
        PraiseArticleNetWork praiseArticleNetWork = new PraiseArticleNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("account",account);
        map.put("num",zanNums+"");
        map.put("type",type+"");
        praiseArticleNetWork.praiseAndSpreadArticleToNet(map, new Observer<BaseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getIssuccess().equals("1")){
                    ibForumDetailThreeZan.setBackgroundResource(R.mipmap.redgood);
                    zanNums++;
                    tvForumDetailThreeZan.setText(zanNums+"");
                }
                /*if(baseBean.getIssuccess().equals("2")){
                    ibForumDetailThreeZan.setBackgroundResource(R.mipmap.thumbs);
                    zanNums--;
                    tvForumDetailThreeZan.setText(zanNums+"");
                }*/
            }
        });
    }

    private void getPraiseCollect(){
        Map<String,Object> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("account",account);
        PraiseArticleNetWork praiseArticleNetWork = new PraiseArticleNetWork();
        Log.d("praisegood1",account + " "+articleid);
        praiseArticleNetWork.getArticlePraiseCollectFromNet(map, new Observer<GetPraiseCollectBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetPraiseCollectBean getPraiseCollectBean) {
                if(getPraiseCollectBean.getIssuccess().equals("1")){
                    zanNums = getPraiseCollectBean.getPraisecount();
                    Log.d("praisegood1",getPraiseCollectBean.getIspraise() + " ");
                    tvForumDetailThreeZan.setText(""+getPraiseCollectBean.getPraisecount());
                    if(getPraiseCollectBean.getIspraise() > 0){
                        ibForumDetailThreeZan.setBackgroundResource(R.mipmap.redgood);
                    }else {
                        ibForumDetailThreeZan.setBackgroundResource(R.mipmap.thumbs);
                    }
                    if(getPraiseCollectBean.getIscollect() >0){
                        ivForumDetailThreeCollect.setBackgroundResource(R.mipmap.collected);
                    }else {
                        ivForumDetailThreeCollect.setBackgroundResource(R.mipmap.collect);
                    }
                    collectNums = getPraiseCollectBean.getCollectcount();
                    tvForumDetailThreeCollect.setText(""+getPraiseCollectBean.getCollectcount());
                }
            }
        });
    }


    @Override
    protected void onResume(){
        super.onResume();
//        maxEightList.clear();
//        dataListTemp.clear();
        initContentData();
        initComment();
        getPraiseCollect();
    }
}
