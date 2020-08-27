package com.et.secondworld.forum;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
public class ForumDetailOneActivity extends AppCompatActivity {

    private List<String> imgUrlList = new ArrayList<>();
    @BindView(R.id.rly_forum_detail_one_back)
    RelativeLayout rlyForumDetailOneBack;
    @OnClick(R.id.rly_forum_detail_one_back)
    public void rlyForumDetailOneBackOnclick(){
        finish();
    }
//    private List<GetCommentBean.CommentlistBean> maxEightList = new ArrayList<>();
    @BindView(R.id.tv_forum_detail_one_all)
    TextView tvForumDetailOneAll;
    @OnClick(R.id.et_forum_detail_one_comment)
    public void etForumDetailTwoCommentOnclick(){
//        Toast.makeText(this,"sss",Toast.LENGTH_SHORT).show();
        rvAdapter.isOpen = true;
    }
    @BindView(R.id.tv_forum_detail_one_time)
    TextView tvForumDetailOneTime;
    @OnClick(R.id.tv_forum_detail_one_all)
    public void tvForumDetailOneAllOnclick(){
        tvForumDetailOneLouZhu.setTextSize(14);
        tvForumDetailOneAll.setTextSize(18);

        tvForumDetailOneAll.setTextColor(Color.BLACK);
        tvForumDetailOneLouZhu.setTextColor(Color.parseColor("#8E8E8E"));
//        if(maxEightList.size() == 0){
//            rlyForumDetailOneNoComment.setVisibility(View.VISIBLE);
//            tvForumDetailOneNoComment.setText("快来发表你的评论吧！");
//        }else {
//            rlyForumDetailOneNoComment.setVisibility(View.GONE);
//        }
//        rvAdapter.replaceAll(maxEightList);
        initComment();
    }
    @BindView(R.id.tv_forum_detail_one_title)
    TextView tvForumDetailOneTitle;
    @BindView(R.id.rly_forum_detail_one_no_comment)
    RelativeLayout rlyForumDetailOneNoComment;
    @BindView(R.id.tv_forum_detail_one_no_comment)
    TextView tvForumDetailOneNoComment;
    @BindView(R.id.civ_forum_detail_one_head)
    ImageView civForumDetailOneHead;
    @OnClick(R.id.civ_forum_detail_one_head)
    public void civForumDetailOneHeadOnclick(){
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
    @BindView(R.id.rly_forum_detail_one_share)
    RelativeLayout rlyForumDetailOneShare;
    @BindView(R.id.rly_forum_detail_one)
    RelativeLayout rlyForumDetailOne;
    @OnClick(R.id.rly_forum_detail_one_share)
    public void rlyForumDetailOneShareOnclick(){
        ShareDialog shareDialog = new ShareDialog(this,articleAccount,articleid,1,1,"","","").Build.build(this,nick,title,img);
        shareDialog.show();
    }

    @BindView(R.id.tv_forum_detail_one_nick)
    TextView tvForumDetailOneNick;
    @BindView(R.id.tv_forum_detail_one_loc)
    TextView tvForumDetailOneLoc;
    private long clickTime = 0;
    @OnClick(R.id.tv_forum_detail_one_loc)
    public void tvForumDetailOneLocOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, TecentMapViewActivity.class);
            intent.putExtra("addr",addr);
            startActivity(intent);
        }

    }
    @BindView(R.id.tv_forum_detail_one_guanzhu)
    TextView tvForumDetailOneGuanZhu;
    @BindView(R.id.rly_forum_detail_one_guanzhu)
    RelativeLayout rlyForumDetailOneGuanZhu;
    @OnClick(R.id.rly_forum_detail_one_guanzhu)
    public void rlyForumDetailOneGuanZhuOnclick(){
        if(tvForumDetailOneGuanZhu.getText().toString().equals("已关注")||tvForumDetailOneGuanZhu.getText().toString().equals("互相关注")){
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
    @BindView(R.id.iv_forum_detail_one_img1)
    ImageView ivForumDetailOneImg1;
    @OnClick(R.id.iv_forum_detail_one_img1)
    public void ivForumDetailOneImg1Onclick(){
//        https://www.cnblogs.com/thare1307/p/4617558.html
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(etForumDetailOneComment)) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            rlyForumDetailOne.setFocusableInTouchMode(true);
            rlyForumDetailOne.requestFocus();

            map.clear();
            etForumDetailOneComment.setHint("火不火靠你了~");
            tvForumDetailOneCommentPublish.setVisibility(View.GONE);
            llyForumDetailOneCommentSubmit.setVisibility(View.VISIBLE);
        }else {
            startImagePre(0, ivForumDetailOneImg1);
        }
//        startImagePre(0,ivForumDetailOneImg1);
    }
    @BindView(R.id.iv_forum_detail_one_img2)
    ImageView ivForumDetailOneImg2;
    @OnClick(R.id.iv_forum_detail_one_img2)
    public void ivForumDetailOneImg2Onclick(){
        //        https://www.cnblogs.com/thare1307/p/4617558.html
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive(etForumDetailOneComment)) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            rlyForumDetailOne.setFocusableInTouchMode(true);
            rlyForumDetailOne.requestFocus();
            map.clear();
            etForumDetailOneComment.setHint("火不火靠你了~");
            tvForumDetailOneCommentPublish.setVisibility(View.GONE);
            llyForumDetailOneCommentSubmit.setVisibility(View.VISIBLE);
        }else {
            startImagePre(1, ivForumDetailOneImg2);
        }
//        startImagePre(1,ivForumDetailOneImg2);
    }
    @BindView(R.id.iv_forum_detail_one_img3)
    ImageView ivForumDetailOneImg3;
    int flag = 1;
    @OnClick(R.id.iv_forum_detail_one_img3)
    public void ivForumDetailOneImg3Onclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


        /*etForumDetailOneComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("detailone11",hasFocus+"");
                if(hasFocus){
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
//                    etForumDetailOneComment.clearFocus();
                }else {
                    startImagePre(0, ivForumDetailOneImg3);
                }
            }
        });*/
        Log.d("detailone11",imm.isActive(etForumDetailOneComment)+"");
        if (imm.isActive(etForumDetailOneComment)) {
//            rlyForumDetailOneGuanZhu.requestFocus();
//            ivForumDetailOneImg3.setFocusable(false);
//            ivForumDetailOneImg3.setFocusableInTouchMode(false);
//            ivForumDetailOneImg3.clearFocus();
            /*new Thread(){
                @Override
                public void run() {
                    Instrumentation instrumentation = new Instrumentation();
//                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                }
            }.start();*/
//            rlyForumDetailOne.setFocusableInTouchMode(false);
//            rlyForumDetailOne.setFocusableInTouchMode(true);
//            rlyForumDetailOne.requestFocus();
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            rlyForumDetailOne.setFocusableInTouchMode(true);
            rlyForumDetailOne.requestFocus();
            map.clear();
            etForumDetailOneComment.setHint("火不火靠你了~");
            tvForumDetailOneCommentPublish.setVisibility(View.GONE);
            llyForumDetailOneCommentSubmit.setVisibility(View.VISIBLE);
//            ivForumDetailOneImg3.setFocusable(true);

//            if(flag == 1) {
//                ivForumDetailOneImg3.setFocusableInTouchMode(false);
//                new Thread(){
//                    @Override
//                    public void run() {
//                        Instrumentation instrumentation = new Instrumentation();
////                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                    }
//                }.start();
//                ivForumDetailOneImg3.setFocusable(true);
//                ivForumDetailOneImg3.setFocusableInTouchMode(true);
//                ivForumDetailOneImg3.requestFocus();
//                flag = 0;
//
//            }else {
//                new Thread() {
//                    @Override
//                    public void run() {
//                        Instrumentation instrumentation = new Instrumentation();
//                        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                    }
//                }.start();
//            }
//            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            imm.showSoftInput(etForumDetailOneComment, 0);
//            etForumDetailOneComment.clearFocus();

//            imm.isActive()= false;
        }else {
            startImagePre(2, ivForumDetailOneImg3);
        }
//        startImagePre(2,ivForumDetailOneImg3);
    }
    @OnClick(R.id.lly_forum_detail_one)
    public void llyForumDetailOneOnclick(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        map.clear();
        etForumDetailOneComment.setHint("火不火靠你了~");
        tvForumDetailOneCommentPublish.setVisibility(View.GONE);
        llyForumDetailOneCommentSubmit.setVisibility(View.VISIBLE);
    }
    @BindView(R.id.tv_forum_detail_one_content1)
    TextView tvForumDetailOneContent1;
    @BindView(R.id.tv_forum_detail_one_content2)
    TextView tvForumDetailOneContent2;
    @BindView(R.id.tv_forum_detail_one_content3)
    TextView tvForumDetailOneContent3;
    @BindView(R.id.tv_forum_detail_one_louzhu)
    TextView tvForumDetailOneLouZhu;
    @OnClick(R.id.tv_forum_detail_one_louzhu)
    public void tvForumDetailOneLouZhuOnclick(){
        tvForumDetailOneAll.setTextSize(14);
        tvForumDetailOneLouZhu.setTextSize(18);

        tvForumDetailOneLouZhu.setTextColor(Color.BLACK);
        tvForumDetailOneAll.setTextColor(Color.parseColor("#8E8E8E"));
        louzhuComment();
    }
    @BindView(R.id.tv_forum_detail_one_zan)
    TextView tvForumDetailOneZan;
    @BindView(R.id.ib_forum_detail_one_zan)
    ImageButton ibForumDetailOneZan;
    @OnClick(R.id.lly_forum_detail_one_zan)
    public void ibForumDetailOneZanOnclick(){
        praiseArticle();
    }
    @BindView(R.id.tv_forum_detail_one_collect)
    TextView tvForumDetailOneCollect;
    @BindView(R.id.ib_forum_detail_one_collect)
    ImageButton ibForumDetailOneCollect;
    @OnClick(R.id.lly_forum_detail_one_collect)
    public void ibForumDetailOneCollectOnclick(){
        collectArticle();
    }
    @BindView(R.id.tv_forum_detail_one_comment_submit)
    TextView tvForumDetailOneCommentSubmit;
    @BindView(R.id.lly_forum_detail_one_comment_submit)
    LinearLayout llyForumDetailOneCommentSubmit;

    @BindView(R.id.tv_forum_detail_one_comment_publish)
    TextView tvForumDetailOneCommentPublish;
    @OnClick(R.id.rly_forum_detail_one_comment_submit)
    public void llyForumDetailOneCommentSubmitOnclick(){
        rvForumDetailOneComment.requestFocus();
        rlyForumDetailOneNoComment.requestFocus();
        if(map.get("toaccountid") != null){
            commentBackComment();
        }else {
            submitComment();
        }
//        initComment();
    }
    @BindView(R.id.et_forum_detail_one_comment)
    EditText etForumDetailOneComment;

    @OnClick(R.id.iv_forum_detail_one_shop_times_pay)
    public void ivForumDetailOneShopTimesPayOnclick(){
//        MineShopTimesPayDialog mineShopTimesPayDialog = new MineShopTimesPayDialog(this).Build.build(this,articleid,type+"");
//        mineShopTimesPayDialog.show();
    }
    @BindView(R.id.iv_forum_detail_one_loading)
    ImageView ivForumDetailOneLoading;
    @BindView(R.id.rly_forum_detail_one_loading)
    RelativeLayout rlyForumDetailOneLoading;
    private List<GetCommentBean.CommentlistBean> dataList = new ArrayList<>();
//    private List<GetCommentBean.CommentlistBean> dataListTemp = new ArrayList<>();
    String articleAccount = "";
    String articleid = "";
    String title = "";
    String account = "";
    int type = 0;
    private String nick = "";
    private String img = "";
    private String head = "";
    private String addr = "";
    private int zanNums = 0;
    private int collectNums = 0;
    private String isFriends = "0";
    private String isGuanZhu = "0";
    private boolean isflag = true;
    private Map<String,Object> map = new HashMap<>();
    @BindView(R.id.rv_forum_detail_one_comment)
    RecyclerView rvForumDetailOneComment;
    private ForumDetailCommentRVAdapter rvAdapter;
//    private List<GetCommentBean.CommentlistBean> dataList = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        setContentView(R.layout.activity_forum_detail_one);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        rlyForumDetailOneLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivForumDetailOneLoading);
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(account == null || account.isEmpty() || account == "") {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        String role = xcCacheManager.readCache(xcCacheSaveName.role);

        if(role != null && role.equals("01")) {
//            Toast.makeText(this,"禁言中",Toast.LENGTH_SHORT).show();
            etForumDetailOneComment.setEnabled(false);
            etForumDetailOneComment.setHint("禁言中");
//            return;

        }
        initContentData();
        initCommentRecycleView();
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
                tvForumDetailOneLoc.setText(addr);
            }
        });
    }
    private void initContentData(){

//        rlyForumDetailOneLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.mipmap.pageloading).into(ivForumDetailOneLoading);
        Intent intent = getIntent();
        articleAccount = intent.getStringExtra("articleAccount");
        articleid = intent.getStringExtra("articleid");
        title = intent.getStringExtra("title");
        type = intent.getIntExtra("type",-1);
//        Log.d("forumdetailAccount",articleAccount);
//        Log.d("forumdetailarticleid",articleid);

        etForumDetailOneComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                toast("您输入的数据为："+s.toString());
//                Log.d("etcomment",count+"");
                if(count > 0){
                    llyForumDetailOneCommentSubmit.setVisibility(View.GONE);
                    tvForumDetailOneCommentPublish.setVisibility(View.VISIBLE);
                }
                if(etForumDetailOneComment.getText().length() == 0){
                    llyForumDetailOneCommentSubmit.setVisibility(View.VISIBLE);
                    tvForumDetailOneCommentPublish.setVisibility(View.GONE);
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
            rlyForumDetailOneGuanZhu.setVisibility(View.GONE);
            etForumDetailOneComment.setFocusable(false);

            etForumDetailOneComment.setFocusableInTouchMode(false);
        }
//        account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(articleAccount != null &&articleAccount.equals(account)){
//             tvForumDetailTwoShare.setVisibility(View.GONE);
            rlyForumDetailOneGuanZhu.setVisibility(View.GONE);
        }
        if(account != null &&account.equals(articleAccount)){
//            llyForumDetailThreeCollect.setVisibility(View.GONE);
        }
        if(title != null) {
            tvForumDetailOneTitle.setText(title);
        }
//        account = xcCacheManager.readCache(xcCacheSaveName.account);
//        Log.d("forumdetailtitle",account);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("articleaccount",articleAccount);
        map.put("account",account);
//        Log.d("forumdetailNetWork",""+articleid);
//        Log.d("forumdetailNetWork",""+articleAccount);
//        Log.d("forumdetailNetWork",""+account);
        forumPostNetWork.getForumDetailOneFromNet(map, new Observer<GetForumDetailBean>() {
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
                        tvForumDetailOneNick.setText(getForumDetailBean.getNick());
//                        Log.d("forumdetailNetWork","step2");
                    }
                    if(getForumDetailBean.getHead() != null) {
                        head = getForumDetailBean.getHead();
                    }
                    Glide.with(getBaseContext()).load(getForumDetailBean.getHead())
                            .apply(new RequestOptions().fallback(R.mipmap.imghead)
                                    .error(R.mipmap.imghead).circleCrop())
                            .into(civForumDetailOneHead);
//                    ImageLoader.getInstance().displayImage(getForumDetailBean.getHead(),civForumDetailOneHead, ImageLoaderUtils.options);
//                    Log.d("forumdetailNetWork","step3");
                    isFriends = getForumDetailBean.getIsfriend();
                    isGuanZhu = getForumDetailBean.getIsguanzhu();
                    String time = getForumDetailBean.getTimes();
                    if(time != null) {
                        tvForumDetailOneTime.setText(time);
                    }
                    if (getForumDetailBean.getIsguanzhu().equals("0")) {
                        if (getForumDetailBean.getIsfriend().equals("0")) {
                            rlyForumDetailOneGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                            tvForumDetailOneGuanZhu.setText("关注");
//                            tvForumDetailOneGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                            tvForumDetailOneGuanZhu.setTextColor(Color.WHITE);
                        }else{
                            rlyForumDetailOneGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvForumDetailOneGuanZhu.setText("已关注");
                            tvForumDetailOneGuanZhu.setTextColor(Color.GRAY);
//                            tvForumDetailOneGuanZhu.setTextColor(Color.parseColor("#BEBEBE"));
                        }
                    } else {
                        if (getForumDetailBean.getIsfriend().equals("0")) {
                            rlyForumDetailOneGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                            tvForumDetailOneGuanZhu.setText("关注");
                            tvForumDetailOneGuanZhu.setTextColor(Color.WHITE);

                        } else {
                            rlyForumDetailOneGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvForumDetailOneGuanZhu.setText("互相关注");
                            tvForumDetailOneGuanZhu.setTextColor(Color.GRAY);
//                            tvForumDetailOneGuanZhu.setTextColor(Color.parseColor("#BEBEBE"));
                        }
                    }
                    if(getForumDetailBean.getImglist() == null){
                        return;
                    }
//                    int imgsize = getForumDetailBean.getImglist().size();
                    if(imgUrlList.size() == 0) {
                        for (GetForumDetailBean.ImglistBean item : getForumDetailBean.getImglist()) {
                            imgUrlList.add(item.getUrl());
                        }
                    }
                    int imgsize = getForumDetailBean.getImglist().size();
                    if(imgsize > 0){
                        img = imgUrlList.get(0);
                    }
                    String content = getForumDetailBean.getContent();

                    if(content == null){
                        return;
                    }
                    String temp = "";
//                    Log.d("forumdetailone111",content);
//                    Log.d("forumdetailone111size",imgsize+"");
                    if(imgsize == 0){
//                        rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                        tvForumDetailOneContent1.setVisibility(View.VISIBLE);
                        tvForumDetailOneContent1.setText(content);

                    }else {
                        switch (imgsize){
                            case 1:
//                                rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                                tvForumDetailOneContent1.setVisibility(View.VISIBLE);
                                ivForumDetailOneImg1.setVisibility(View.VISIBLE);
//                                ivForumDetailThreeImgOne.setVisibility(View.VISIBLE);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailOneImg1);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailOneImg1,ImageLoaderUtils.options);
                                tvForumDetailOneContent1.setText(content);
                                break;
                            case 2:
//                                rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                                tvForumDetailOneContent1.setVisibility(View.VISIBLE);
                                ivForumDetailOneImg1.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailOneImg1,ImageLoaderUtils.options);
                                ivForumDetailOneImg2.setVisibility(View.VISIBLE);
                                tvForumDetailOneContent2.setVisibility(View.VISIBLE);
//                                ivForumDetailThreeImgTwo.setVisibility(View.VISIBLE);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailOneImg1);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(1).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailOneImg2);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailOneImg2,ImageLoaderUtils.options);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailThreeImgTwo,ImageLoaderUtils.options);
                                temp = content.substring(0,content.length()/2);
                                tvForumDetailOneContent1.setText(temp);
                                temp = content.substring(content.length()/2,content.length());
                                tvForumDetailOneContent2.setText(temp);
                                break;
                            case 3:
//                                rlyForumDetailThreeTitleOne.setVisibility(View.VISIBLE);
                                tvForumDetailOneContent1.setVisibility(View.VISIBLE);
                                ivForumDetailOneImg1.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailOneImg1,ImageLoaderUtils.options);
                                ivForumDetailOneImg2.setVisibility(View.VISIBLE);
                                tvForumDetailOneContent2.setVisibility(View.VISIBLE);
//                                ivForumDetailThreeImgTwo.setVisibility(View.VISIBLE);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailOneImg2,ImageLoaderUtils.options);
                                ivForumDetailOneImg3.setVisibility(View.VISIBLE);
                                tvForumDetailOneContent3.setVisibility(View.VISIBLE);
//                                ivForumDetailThreeImgThree.setVisibility(View.VISIBLE);

                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailOneImg1);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(1).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailOneImg2);
                                Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(2).getUrl())
                                        .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                        .into(ivForumDetailOneImg3);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailOneImg3,ImageLoaderUtils.options);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailThreeImgThree,ImageLoaderUtils.options);
                                temp = content.substring(0,content.length()/3);
                                tvForumDetailOneContent1.setText(temp);
                                temp = content.substring((content.length()*1)/3,(content.length()*2)/3);
                                tvForumDetailOneContent2.setText(temp);
                                temp = content.substring((content.length()*2)/3,content.length());
                                tvForumDetailOneContent3.setText(temp);
                                break;
                        }

                        if(imgsize > 3){
                            tvForumDetailOneContent1.setVisibility(View.VISIBLE);
                            ivForumDetailOneImg1.setVisibility(View.VISIBLE);
//                            ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(0).getUrl(),ivForumDetailOneImg1,ImageLoaderUtils.options);
                            ivForumDetailOneImg2.setVisibility(View.VISIBLE);
                            tvForumDetailOneContent2.setVisibility(View.VISIBLE);
//                                ivForumDetailThreeImgTwo.setVisibility(View.VISIBLE);
//                            ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(1).getUrl(),ivForumDetailOneImg2,ImageLoaderUtils.options);
                            ivForumDetailOneImg3.setVisibility(View.VISIBLE);
                            tvForumDetailOneContent3.setVisibility(View.VISIBLE);
//                                ivForumDetailThreeImgThree.setVisibility(View.VISIBLE);
                            Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(0).getUrl())
                                    .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                    .into(ivForumDetailOneImg1);
                            Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(1).getUrl())
                                    .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                    .into(ivForumDetailOneImg2);
                            Glide.with(getBaseContext()).load(getForumDetailBean.getImglist().get(2).getUrl())
                                    .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                                    .into(ivForumDetailOneImg3);
//                            ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailOneImg3,ImageLoaderUtils.options);
//                                ImageLoader.getInstance().displayImage(getForumDetailBean.getImglist().get(2).getUrl(),ivForumDetailThreeImgThree,ImageLoaderUtils.options);
                            temp = content.substring(0,content.length()/3);
                            tvForumDetailOneContent1.setText(temp);
                            temp = content.substring((content.length()*1)/3,(content.length()*2)/3);
                            tvForumDetailOneContent2.setText(temp);
                            temp = content.substring((content.length()*2)/3,content.length());
                            tvForumDetailOneContent3.setText(temp);
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
                rlyForumDetailOneLoading.setVisibility(View.GONE);
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
//                    dataListTemp.clear();
//                    dataListTemp.addAll(getCommentBean.getCommentlist());
//                    List<GetCommentBean.CommentlistBean> commentList = new ArrayList<>();
//                    maxEightList.clear();
//                    commentList.addAll(dataListTemp);
                    dataList = getCommentBean.getCommentlist();
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
                        rlyForumDetailOneNoComment.setVisibility(View.VISIBLE);
                    }else {
                        rlyForumDetailOneNoComment.setVisibility(View.GONE);
                    }
//                    rvAdapter.replaceAll(maxEightList);
                    rvAdapter.replaceAll(dataList);

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
//                        rlyForumDetailOneNoComment.setVisibility(View.VISIBLE);
//                    }else {
//                        rlyForumDetailOneNoComment.setVisibility(View.GONE);
//                    }
//                    rvAdapter.replaceAll(maxEightList);
//                    rvAdapter.replaceAll(getCommentBean.getCommentlist());
                    int commentsize = dataList.size();
                    tvForumDetailOneCommentSubmit.setText(commentsize+"");
                }
            }
        });
    }

    private void getPraiseCollect(){
        Map<String,Object> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("account",account);
        PraiseArticleNetWork praiseArticleNetWork = new PraiseArticleNetWork();
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
                    tvForumDetailOneZan.setText(""+getPraiseCollectBean.getPraisecount());
                    if(getPraiseCollectBean.getIspraise() > 0){
                        ibForumDetailOneZan.setBackgroundResource(R.mipmap.redgood);
                    }else {
                        ibForumDetailOneZan.setBackgroundResource(R.mipmap.thumbs);
                    }
                    if(getPraiseCollectBean.getIscollect() >0){
                        ibForumDetailOneCollect.setBackgroundResource(R.mipmap.collected);
                    }else {
                        ibForumDetailOneCollect.setBackgroundResource(R.mipmap.collect);
                    }
                    collectNums = getPraiseCollectBean.getCollectcount();
                    tvForumDetailOneCollect.setText(""+getPraiseCollectBean.getCollectcount());
                }
            }
        });
    }
    private void submitComment(){
        String content = etForumDetailOneComment.getText().toString();
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(content.equals("")||content.isEmpty()){
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("account",account);
        map.put("content",content);
        map.put("platform","android");
        map.put("articleaccount",articleAccount);
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
                    etForumDetailOneComment.setText("");
                    initComment();
                    showHideSoftInput();
                    getPraiseCollect();
                }
            }
        });
    }
    private void commentBackComment(){
        String commentBackContent = etForumDetailOneComment.getText().toString();
        if(commentBackContent.equals("")||commentBackContent.isEmpty()){
            return;
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("toaccountid",map.get("toaccountid"));
        paramMap.put("tonick",map.get("tonick"));
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        paramMap.put("account",account);
        paramMap.put("content",commentBackContent);
        paramMap.put("commentid",map.get("commentid"));
        paramMap.put("platform","android");
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
                    etForumDetailOneComment.setText("");
                    dataList.clear();
                    showHideSoftInput();
                    rvAdapter.notifyDataSetChanged();
                    initComment();
                }
            }
        });
    }
    private void guanZhuSubmit() {
        Map<String, String> map = new HashMap<>();
        GuanZhuFansNetWork guanZhuFansNetWork = new GuanZhuFansNetWork();

        map.put("account", articleAccount);
        map.put("followid", account);
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
                if (cancelGuanZhuBean.getIssuccess().equals("1")) {
                    if (isGuanZhu.equals("1")) {
//                            if (isFriends == 0) {
                        rlyForumDetailOneGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                        tvForumDetailOneGuanZhu.setText("互相关注");
                        tvForumDetailOneGuanZhu.setTextColor(Color.GRAY);
//                        tvForumDetailOneGuanZhu.setTextColor(Color.parseColor("#BEBEBE"));
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
                    if (isGuanZhu.equals("0")) {
//                            if (isFriends == 0) {
                        rlyForumDetailOneGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                        tvForumDetailOneGuanZhu.setText("已关注");
                        tvForumDetailOneGuanZhu.setTextColor(Color.GRAY);
//                        tvForumDetailOneGuanZhu.setTextColor(Color.parseColor("#BEBEBE"));
//                                dataList.get(pos).setIsfans(1);
//                                dataList.get(pos).setIsfriends(1);
//                            }
//                            if (isFriends == 1) {
//
//
//                            }
                    }
//                        notifyDataSetChanged();
                } else {
                    rlyForumDetailOneGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                    tvForumDetailOneGuanZhu.setText("关注");
                    tvForumDetailOneGuanZhu.setTextColor(Color.WHITE);

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
                    tvForumDetailOneCollect.setText(collectNums+"");
                    ibForumDetailOneCollect.setBackgroundResource(R.mipmap.collected);
                }
                if(collectArticleBean.getIssuccess().equals("2")){
//                    ibForumDetailThreeZan.setBackgroundResource(R.mipmap.thumbs);
                    collectNums--;
                    tvForumDetailOneCollect.setText(collectNums+"");
                    ibForumDetailOneCollect.setBackgroundResource(R.mipmap.collect);
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
                    ibForumDetailOneZan.setBackgroundResource(R.mipmap.redgood);
                    zanNums++;
                    tvForumDetailOneZan.setText(zanNums+"");
                }
//                if(baseBean.getIssuccess().equals("2")){
//                    ibForumDetailOneZan.setBackgroundResource(R.mipmap.thumbs);
//                    zanNums--;
//                    tvForumDetailOneZan.setText(zanNums+"");
//                }
            }
        });
    }

    private void louzhuComment(){
        List<GetCommentBean.CommentlistBean> louzhouList = new ArrayList<>();
//        List<GetCommentBean.CommentlistBean> tempLouZhuList = new ArrayList<>();

        if(dataList.size() == 0){
            rlyForumDetailOneNoComment.setVisibility(View.VISIBLE);
            tvForumDetailOneNoComment.setText("楼主还没有评论，快来戳他吧！");
        }else {
            rlyForumDetailOneNoComment.setVisibility(View.GONE);
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
//        if(louzhouList.size() == 0){
//            rlyForumDetailOneNoComment.setVisibility(View.VISIBLE);
//            tvForumDetailOneNoComment.setText("楼主还没有评论，快来戳他吧！");
//        }else {
//            rlyForumDetailOneNoComment.setVisibility(View.GONE);
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
//                rlyForumDetailOneNoComment.setVisibility(View.VISIBLE);
//                tvForumDetailOneNoComment.setText("楼主还没有评论，快来戳他吧！");
//            }else {
//                rlyForumDetailOneNoComment.setVisibility(View.GONE);
//            }
//            rvAdapter.replaceAll(tempLouZhuList);
//        }
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
    private void initCommentRecycleView(){
        rvAdapter = new ForumDetailCommentRVAdapter(dataList,articleid,etForumDetailOneComment,this,map,account);
//        rvAdapter = new ForumDetailCommentRVAdapter(dataList);
      /*  ArrayList<String> dataList = new ArrayList<>();
        for(int i = 0;i < 4;i++){
            dataList.add("");
        }*/
        rvForumDetailOneComment.setLayoutManager(new LinearLayoutManager(this));
        rvForumDetailOneComment.setAdapter(rvAdapter);
        rvAdapter.setArticleAccount(articleAccount);
//        rvAdapter.replaceAll(dataList);
    }

    @Override
    protected void onResume(){
        super.onResume();
//        maxEightList.clear();
//        dataListTemp.clear();
        initContentData();
        initComment();
    }
    private void showHideSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
