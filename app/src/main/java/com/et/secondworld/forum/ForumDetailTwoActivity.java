package com.et.secondworld.forum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.LoginActivity;
import com.et.secondworld.TecentMapViewActivity;
import com.et.secondworld.bean.GetArticleidLocBean;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.dialog.HaveSoluationedDialog;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.SocializeUtils;
import com.et.secondworld.R;
import com.et.secondworld.VisitOthersActivity;
import com.et.secondworld.bean.AddBrowsHistoryBean;
import com.et.secondworld.bean.AddCancelZanBean;
import com.et.secondworld.bean.AddCommentBackBean;
import com.et.secondworld.bean.AddCommentBean;
import com.et.secondworld.bean.CancelGuanZhuBean;
import com.et.secondworld.bean.CollectArticleBean;
import com.et.secondworld.bean.GetCommentBean;
import com.et.secondworld.bean.GetForumDetailBean;
import com.et.secondworld.bean.GetPraiseCollectBean;
import com.et.secondworld.dialog.ShareDialog;
import com.et.secondworld.forum.adapter.ForumDetailCommentRVAdapter;
import com.et.secondworld.network.ForumPostNetWork;
import com.et.secondworld.network.GuanZhuFansNetWork;
import com.et.secondworld.network.PraiseArticleNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.viewpage.ImageCycleView;

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
public class ForumDetailTwoActivity extends FragmentActivity {
    private List<GetCommentBean.CommentlistBean> dataList = new ArrayList<>();
    private List<GetCommentBean.CommentlistBean> dataListTemp = new ArrayList<>();
//    private List<GetCommentBean.CommentlistBean> maxEightList = new ArrayList<>();
    private int collectNums = 0;
    private int zanNums = 0;
    private boolean isSoluation = false;
    private boolean isResume = false;
    private ProgressDialog dialog;
    @BindView(R.id.rly_forum_detail_two)
    RelativeLayout rlyForumDetailTwo;
    @BindView(R.id.iv_forum_detail_two_loading)
    ImageView ivForumDetailTwoLoading;
    @BindView(R.id.rly_forum_detail_two_soluation)
    RelativeLayout rlyForumDetailTwoSoluation;
    @OnClick(R.id.rly_forum_detail_two_soluation)
    public void rlyForumDetailTwoSoluationOnclick(){
        if(!isSoluation) {
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName() ;
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            isResume = true;
            if(account != null){
                if (account.equals(articleAccount)){
                    HaveSoluationedDialog haveSoluationedDialog = new HaveSoluationedDialog(this).Build.setCallBackListener(new HaveSoluationedDialog.OnFinishClickListener() {
                        @Override
                        public void isUpdated(boolean isUpdated) {
                            initContentData();
                        }
                    }).build(this,articleid);
                    haveSoluationedDialog.show();
                }
            }

        }
    }

    @BindView(R.id.tv_forum_detail_two_soluation)
    TextView tvForumDetailTwoSoluation;
    @BindView(R.id.icv_forum_detail_two)
    ImageCycleView icvForumDetailTwo;
    @OnClick({R.id.srv_forum_detail_two,R.id.lly_forum_detail_two})
    public void srvForumDetailTwoOnclick(){
//        Toast.makeText(this,"zz",Toast.LENGTH_SHORT).show();

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        map.clear();
        etForumDetailTwoComment.setHint("火不火靠你了~");
        tvForumDetailTwoCommentPublish.setVisibility(View.GONE);
        llyForumDetailTwoCommentSubmit.setVisibility(View.VISIBLE);
    }

    @BindView(R.id.rly_forum_detail_two_share)
    RelativeLayout rlyForumDetailTwoShare;
    @OnClick(R.id.rly_forum_detail_two_share)
    public void rlyForumDetailTwoShareOnclick(){

//        UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.WEIXIN, authListener);
//        UMImage image = new UMImage(this, img);//资源文件
////        UMWeb  web = new UMWeb("http://et-stars.cn/detail.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account);
//        UMWeb  web = new UMWeb("http://et-stars.cn/detailone.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account);
//        web.setTitle(title);//标题
//        web.setThumb(image);  //缩略图
//        web.setDescription(content);//描述
//////        new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN).withMedia(image)
//////                .setCallback(shareListener).open();
//        new ShareAction(this)
//                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
//                .withText("hello")//分享内容
//                .withMedia(web)
//                .setCallback(shareListener)//回调监听器
//                .share();
//        new ShareAction(this)
//                .setPlatform(SHARE_MEDIA.QQ)//传入平台
//                .withText("hello")//分享内容
//                .setCallback(shareListener)//回调监听器
//                .share();
//        Toast.makeText(this,"this is share",Toast.LENGTH_LONG).show();
        ShareDialog shareDialog = new ShareDialog(this,articleAccount,articleid,0,0,managetype,thirdid,articletypes).Build.build(this,nick,title,img);
        shareDialog.show();
        isResume = true;
    }


    //https://www.jianshu.com/p/d93d5f95b141
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(getBaseContext(), "成功了", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(getBaseContext(), "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(getBaseContext(), "取消了", Toast.LENGTH_LONG).show();
        }
    };
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getBaseContext(),"分享成功",Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getBaseContext(),"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getBaseContext(),"取消了",Toast.LENGTH_LONG).show();
        }
    };
    @BindView(R.id.tv_forum_detail_two_title)
    TextView tvForumDetailTwoTitle;
    @BindView(R.id.civ_forum_detail_two_head)
    ImageView civForumDetailTwoHead;
    @OnClick(R.id.civ_forum_detail_two_head)
    public void civForumDetailTwoHeadOnclick(){
        if(account!= null){
            if(account.equals(articleAccount)){
                return;
            }
        }
        if(System.currentTimeMillis() - clickTime > 2000) {
            isResume = true;
            clickTime = System.currentTimeMillis();

            Intent intent = new Intent(this, VisitOthersActivity.class);
            intent.putExtra("articleaccount", articleAccount);
            startActivity(intent);
        }
    }
    @BindView(R.id.tv_forum_detail_two_nick)
    TextView tvForumDetailTwoNick;
    @BindView(R.id.tv_forum_detail_two_loc)
    TextView tvForumDetailTwoLoc;
    private long clickTime = 0;
    @OnClick(R.id.tv_forum_detail_two_loc)
    public void tvForumDetailTwoLocOnclick(){

        if(System.currentTimeMillis() - clickTime > 2000) {
            isResume = true;
            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, TecentMapViewActivity.class);
            intent.putExtra("lat",lat);
            intent.putExtra("lon",lon);
            intent.putExtra("accountid",articleAccount);
            intent.putExtra("articleid",articleid);
            intent.putExtra("title",title);
            startActivity(intent);
        }

    }

    @BindView(R.id.rly_forum_detail_two_no_comment)
    RelativeLayout rlyForumDetailTwoNoComment;
    @BindView(R.id.tv_forum_detail_two_no_comment)
    TextView tvForumDetailTwoNoComment;
    @BindView(R.id.tv_forum_detail_two_louzhu)
    TextView tvForumDetailTwoLouZhu;
    @OnClick(R.id.tv_forum_detail_two_louzhu)
    public void tvForumDetailTwoLouZhuOnclick(){
        tvForumDetailTwoLouZhu.setTextSize(18);
        tvForumDetailTwoAll.setTextSize(14);

        tvForumDetailTwoLouZhu.setTextColor(Color.BLACK);
        tvForumDetailTwoAll.setTextColor(Color.parseColor("#8E8E8E"));
        louzhuComment();
    }
    @BindView(R.id.tv_forum_detail_two_all)
    TextView tvForumDetailTwoAll;
    @OnClick(R.id.tv_forum_detail_two_all)
    public void tvForumDetailTwoAllOnclick(){
        tvForumDetailTwoLouZhu.setTextSize(14);
        tvForumDetailTwoAll.setTextSize(18);

        tvForumDetailTwoAll.setTextColor(Color.BLACK);
        tvForumDetailTwoLouZhu.setTextColor(Color.parseColor("#8E8E8E"));
//        if(maxEightList.size() == 0){
//        if(dataList.size() == 0){
//            rlyForumDetailTwoNoComment.setVisibility(View.VISIBLE);
//            tvForumDetailTwoNoComment.setText("快来发表你的评论吧！");
//        }else {
//            rlyForumDetailTwoNoComment.setVisibility(View.GONE);
//        }
        initComment();
//        rvAdapter.replaceAll(maxEightList);
    }
    @BindView(R.id.tv_forum_detail_two_guanzhu)
    TextView tvForumDetailTwoGuanZhu;

    @BindView(R.id.tv_forum_detail_two_content)
    TextView tvForumDetailTwoContent;
    @BindView(R.id.tv_forum_detail_two_repost)
    TextView tvForumDetailTwoRepost;
    @BindView(R.id.tv_forum_detail_two_comments)
    TextView tvForumDetailTwoComments;
    @BindView(R.id.tv_forum_detail_two_good)
    TextView tvForumDetailTwoGood;
    @BindView(R.id.tv_forum_detail_two_readers)
    TextView tvForumDetailTwoReaders;
    @BindView(R.id.et_forum_detail_two_comment)
    EditText etForumDetailTwoComment;
    @BindView(R.id.ib_forum_detail_two_zan)
    ImageButton ibForumDetailTwoZan;
    @OnClick(R.id.lly_forum_detail_two_zan)
    public void ibForumDetailTwoZanOnclick(){
        praiseArticle();
    }
    @BindView(R.id.tv_forum_detail_two_zan)
    TextView tvForumDetailTwoZan;
    @BindView(R.id.ib_forum_detail_two_collect)
    ImageButton ibForumDetailTwoCollect;
    @OnClick(R.id.lly_forum_detail_two_collect)
    public void ibForumDetailTwoCollectOnclick(){
        collectArticle();
    }
    @BindView(R.id.tv_forum_detail_two_collect)
    TextView tvForumDetailTwoCollect;
    @BindView(R.id.tv_forum_detail_two_comment_submit)
    TextView tvForumDetailTwoCommentSubmit;
    @BindView(R.id.lly_forum_detail_two_comment_submit)
    LinearLayout llyForumDetailTwoCommentSubmit;
    @BindView(R.id.tv_forum_detail_two_comment_publish)
    TextView tvForumDetailTwoCommentPublish;
    @OnClick(R.id.rly_forum_detail_two_comment_submit)
    public void rlyForumDetailTwoCommentSubmitOnclick(){
        rvForumDetailTwoComment.requestFocus();
        rlyForumDetailTwoNoComment.requestFocus();
        if(map.get("toaccountid") != null){

            commentBackComment();
        }else {
            submitComment();
        }
//        initComment();
    }

   /* @BindView(R.id.tv_forum_detail_two_share)
    TextView tvForumDetailTwoShare;
    @BindView(R.id.rly_forum_detail_two_collect)
    RelativeLayout rlyForumDetailTwoCollect;
    @OnClick(R.id.rly_forum_detail_two_collect)
    public void rlyForumDetailTwoCollectOnclick(){
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
            }
        });
    }*/
    /*@BindView(R.id.tv_forum_detail_two_comment_submit)
    TextView tvForumDetailTwoCommentSubmit;
    @OnClick(R.id.tv_forum_detail_two_comment_submit)
    public void tvForumDetailTwoCommentSubmitOnclick(){
        submitComment();
    }*/
    @OnClick(R.id.et_forum_detail_two_comment)
    public void etForumDetailTwoCommentOnclick(){
//        Toast.makeText(this,"sss",Toast.LENGTH_SHORT).show();
        rvAdapter.isOpen = true;
    }
    @BindView(R.id.rly_forum_detail_two_back)
    RelativeLayout rlyForumDetailTwoBack;
    @OnClick(R.id.rly_forum_detail_two_back)
    public void rlyForumDetailTwoBackOnclick(){
        finish();
    }
    @BindView(R.id.rly_forum_detail_two_guanzhu)
    RelativeLayout rlyForumDetailTwoGuanZhu;
    @OnClick(R.id.rly_forum_detail_two_guanzhu)
    public void rlyForumDetailTwoGuanZhuOnclick(){
        if(tvForumDetailTwoGuanZhu.getText().toString().equals("已关注")||tvForumDetailTwoGuanZhu.getText().toString().equals("互相关注")){
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
    @BindView(R.id.tv_forum_detail_two_time)
    TextView tvForumDetailTwoTime;
    @BindView(R.id.rly_forum_detail_two_loading)
    RelativeLayout rlyForumDetailTwoLoading;
    @BindView(R.id.rv_forum_detail_two_comment)
    RecyclerView rvForumDetailTwoComment;
    private ForumDetailCommentRVAdapter rvAdapter;
    String articleAccount = "";
    String articleid = "";
    String title = "";
    String account = "";
    String thirdid = "";
    private String nick = "";
    private String img = "";
    private String head = "";
    private String lat = "";
    private String lon = "";
    private String managetype = "";
    private String isFriends = "0";
    private String isGuanZhu = "0";
    private boolean isflag = true;
    private String articletypes = "";
    private String content = "";
    private Map<String,Object> map = new HashMap<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_forum_detail_two);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
        rlyForumDetailTwoLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivForumDetailTwoLoading);
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
            etForumDetailTwoComment.setEnabled(false);
            etForumDetailTwoComment.setHint("禁言中");
//            return;

        }
        Intent intent = getIntent();
        articleAccount = intent.getStringExtra("articleAccount");
        articleid = intent.getStringExtra("articleid");
        managetype = intent.getStringExtra("managetype");
        thirdid = intent.getStringExtra("thirdid");
//        initLunBo();
        initContentData();
        initCommentRecycleView();
        getArticleloc();
        initComment();
        getPraiseCollect();
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
        map.put("articleid",articleid);
        ArticleNetWork articleNetWork = new ArticleNetWork();
        articleNetWork.getArticleLocFromNet(map, new Observer<GetArticleidLocBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetArticleidLocBean getArticleidLocBean) {
                String addr = getArticleidLocBean.getAddr();
                 lat = getArticleidLocBean.getLat();
                 lon = getArticleidLocBean.getLon();
                if(addr != null) {
                    tvForumDetailTwoLoc.setText(addr);
                }
            }
        });
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
//                Toast.makeText(getBaseContext(),"this is two onCompleted",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

//                Toast.makeText(getBaseContext(),"this is two"+e,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(AddBrowsHistoryBean addBrowsHistoryBean) {
//                Toast.makeText(getBaseContext(),"this is two"+addBrowsHistoryBean.getMsg(),Toast.LENGTH_SHORT).show();
            }
        });
    }
//    轮播初始化
    private void initLunBo(){
//        ArrayList<String> imgURL = new ArrayList<>();
//        ArrayList<Integer> imgLocal = new ArrayList<>();
//        imgLocal.add(R.mipmap.forumdetailone1);
//        imgLocal.add(R.mipmap.forumdetailone2);
//        imgLocal.add(R.mipmap.forumdetailone3);
//        icvForumDetailTwo.setImageResources(imgURL,null,imgLocal,imageCycleViewListener);

    }
    private ImageCycleView.ImageCycleViewListener imageCycleViewListener=new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void displayImageURL(String imageURL, ImageView imageView) {
            Glide.with(getBaseContext()).load(imageURL)
                    .apply(new RequestOptions().fallback(R.mipmap.forumdetailthree1))
                    .into(imageView);
//            ImageLoader.getInstance().displayImage(imageURL,imageView, ImageLoaderUtils.options);
        }

        @Override
        public void displayImageLocal(int mipmap, ImageView imageView) {
            imageView.setImageResource(mipmap);
        }

        @Override
        public void onImageClick(ArrayList<String> urlList, ArrayList<Integer> localList, int postion, View imageView) {

        }


    };


    private void initContentData(){
//        rlyForumDetailTwoLoading.setVisibility(View.VISIBLE);
//        Glide.with(this).load(R.mipmap.pageloading).into(ivForumDetailTwoLoading);
//         title = intent.getStringExtra("title");
//         Log.d("forumdetailAccount",articleAccount);
//         Log.d("forumdetailarticleid",articleid);
        etForumDetailTwoComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                toast("您输入的数据为："+s.toString());
                Log.d("etcomment",count+"");
                if(count > 0){
                    llyForumDetailTwoCommentSubmit.setVisibility(View.GONE);
                    tvForumDetailTwoCommentPublish.setVisibility(View.VISIBLE);
                }
                if(etForumDetailTwoComment.getText().length() == 0){
                    llyForumDetailTwoCommentSubmit.setVisibility(View.VISIBLE);
                    tvForumDetailTwoCommentPublish.setVisibility(View.GONE);
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
//         account = xcCacheManager.readCache(xcCacheSaveName.account);
         if(articleAccount.equals(account)){
//             tvForumDetailTwoShare.setVisibility(View.GONE);
             rlyForumDetailTwoGuanZhu.setVisibility(View.GONE);
         }
         if(account == null){
             account = "";
             rlyForumDetailTwoGuanZhu.setVisibility(View.GONE);
             etForumDetailTwoComment.setFocusable(false);

             etForumDetailTwoComment.setFocusableInTouchMode(false);
         }
         if(account.equals(articleAccount)){
//             rlyForumDetailTwoCollect.setVisibility(View.GONE);
         }
//         tvForumDetailTwoTitle.setText(title);
//        Log.d("forumdetailtitle",account);
        ForumPostNetWork forumPostNetWork = new ForumPostNetWork();
        Map<String,String> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("articleaccount",articleAccount);
        map.put("account",account);
//        Log.d("forumdetailNetWork",""+articleid);
//        Log.d("forumdetailNetWork",""+articleAccount);
//        Log.d("forumdetailNetWork",""+account);
        forumPostNetWork.getForumDetailFromNet(map, new Observer<GetForumDetailBean>() {
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
                        tvForumDetailTwoNick.setText(getForumDetailBean.getNick());
//                        Log.d("forumdetailNetWork","step2");
                    }
                    if(getForumDetailBean.getHead() != null) {
                        head = getForumDetailBean.getHead();
                    }
                     articletypes = getForumDetailBean.getTypes();
                    String time = getForumDetailBean.getTimes();
                    if(time != null) {
                        tvForumDetailTwoTime.setText(time);
                    }
                    int isSoluations = getForumDetailBean.getIssoluation();
                    if(articletypes != null) {
                        if (articletypes.equals("help") || articletypes.equals("data")) {
                            rlyForumDetailTwoSoluation.setVisibility(View.VISIBLE);
                            if (isSoluations == 1) {
//                                ivForumRVItemTitleLogo.setBackgroundResource(R.mipmap.soluationed);
                                rlyForumDetailTwoSoluation.setBackgroundResource(R.drawable.green_acute_shape);
                                tvForumDetailTwoSoluation.setText("已解决");
                                tvForumDetailTwoSoluation.setTextColor(Color.WHITE);
                                isSoluation = true;
                            } else {
                                rlyForumDetailTwoSoluation.setBackgroundResource(R.drawable.gray_acute_shape);
                                tvForumDetailTwoSoluation.setTextColor(Color.BLACK);
                                tvForumDetailTwoSoluation.setText("未解决");
                                isSoluation = false;
                            }
                        } else {
                            rlyForumDetailTwoSoluation.setVisibility(View.GONE);
                        }
                    }else {
                        rlyForumDetailTwoSoluation.setVisibility(View.GONE);
                    }
                    title = getForumDetailBean.getTitle();
                    content = getForumDetailBean.getContent();
                    tvForumDetailTwoTitle.setText(getForumDetailBean.getTitle());
                    Glide.with(getBaseContext()).load(getForumDetailBean.getHead()).apply(new RequestOptions().circleCrop().fallback(R.mipmap.imghead)
                            .error(R.mipmap.imghead)).into(civForumDetailTwoHead);
//                    ImageLoader.getInstance().displayImage(getForumDetailBean.getHead(),civForumDetailTwoHead, ImageLoaderUtils.options);
//                    Log.d("forumdetailNetWork","step3");
                    isFriends = getForumDetailBean.getIsfriend();
                    isGuanZhu = getForumDetailBean.getIsguanzhu();
                        if (getForumDetailBean.getIsguanzhu().equals("0")) {
                            if (getForumDetailBean.getIsfriend().equals("0")) {
                                rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                                tvForumDetailTwoGuanZhu.setText("关注");
//                                tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                                tvForumDetailTwoGuanZhu.setTextColor(Color.WHITE);
                            }else{
                                rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                                tvForumDetailTwoGuanZhu.setText("已关注");
//                                tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                                tvForumDetailTwoGuanZhu.setTextColor(Color.GRAY);
                            }
                        } else {
                            if (getForumDetailBean.getIsfriend().equals("0")) {
                                rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                                tvForumDetailTwoGuanZhu.setText("关注");
                                tvForumDetailTwoGuanZhu.setTextColor(Color.WHITE);

                            } else {
                                rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                                tvForumDetailTwoGuanZhu.setText("互相关注");
                                tvForumDetailTwoGuanZhu.setTextColor(Color.GRAY);
//                                tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                            }
                        }

                    if(getForumDetailBean.getContent() != null) {
                        tvForumDetailTwoContent.setText(getForumDetailBean.getContent());
                    }
//                    tvForumDetailTwoRepost.setText(getForumDetailBean.getRepost()+"");
//                    tvForumDetailTwoComments.setText(getForumDetailBean.getComment()+"");
//                    tvForumDetailTwoGood.setText(getForumDetailBean.getGood()+"");
//                    tvForumDetailTwoReaders.setText(getForumDetailBean.getReaders()+"");
//                    Log.d("forumdetailNetWork",""+getForumDetailBean.getImglist());
                    if(getForumDetailBean.getImglist().size() > 0) {
                        icvForumDetailTwo.setVisibility(View.VISIBLE);
                        List<String> urlList = new ArrayList<>();
                        for(GetForumDetailBean.ImglistBean item:getForumDetailBean.getImglist()){

                            urlList.add(item.getUrl());
                            if(img == null || img.isEmpty()){
                                img = item.getUrl();
                            }
                        }
//                        if(urlList.size()>0){
//                            img = urlList.get(0);
//                        }
                        icvForumDetailTwo.setImageResources(urlList, null, null, imageCycleViewListener,etForumDetailTwoComment,rlyForumDetailTwo);
                    }
                }

                rlyForumDetailTwoLoading.setVisibility(View.GONE);
                if(isflag) {
                    initBrowsHistory();
                    isflag = false;
                }
//                Log.d("forumdetailNetWork","step4");

            }
        });


    }


    private void initCommentRecycleView(){
//        rvAdapter = new ForumDetailCommentRVAdapter(dataList);
        rvAdapter = new ForumDetailCommentRVAdapter(dataList,articleid,etForumDetailTwoComment,this,map,account);
        rvForumDetailTwoComment.setLayoutManager(new LinearLayoutManager(this));
        rvForumDetailTwoComment.setAdapter(rvAdapter);
        rvForumDetailTwoComment.setItemViewCacheSize(500);
        rvAdapter.setThirdid(thirdid);
        rvAdapter.setArticleAccount(articleAccount);
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
                    dataList = getCommentBean.getCommentlist();
                    List<GetCommentBean.CommentlistBean> commentList = new ArrayList<>();
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
                        rlyForumDetailTwoNoComment.setVisibility(View.VISIBLE);
                    }else {
                        rlyForumDetailTwoNoComment.setVisibility(View.GONE);
                    }
//                    rvAdapter.replaceAll(maxEightList);
                    rvAdapter.replaceAll(dataList);
//                    for(GetCommentBean.CommentlistBean item :getCommentBean.getCommentlist()) {
//                        Log.d("zzzzzzzzz", item.getBacksize() + "");
//                    }
                    int commentsize = dataList.size();
                    tvForumDetailTwoCommentSubmit.setText(commentsize+"");

                }
            }
        });
    }
    private void louzhuComment(){

        List<GetCommentBean.CommentlistBean> louzhouList = new ArrayList<>();
//        List<GetCommentBean.CommentlistBean> tempLouZhuList = new ArrayList<>();
//        louzhouList.addAll(dataList);
//        louzhouList.addAll(dataListTemp);
//        if(louzhouList.size() == 0){
        if(dataList.size() == 0){
            rlyForumDetailTwoNoComment.setVisibility(View.VISIBLE);
            tvForumDetailTwoNoComment.setText("楼主还没有评论，快来戳他吧！");
        }else {
            rlyForumDetailTwoNoComment.setVisibility(View.GONE);
        }
//        if(louzhouList.size()>0) {
        if(dataList.size()>0) {
//            String louzhuAccount = louzhouList.get(0).getAccount();
//            Log.d("louzhucommentAccount",louzhouList.size()+"");
//            String louzhuAccount = articleAccount;
            for (int i = 0; i < dataList.size(); ) {
                String temp = dataList.get(i).getAccount();

                if(articleAccount.equals(temp)){
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
            if(dataList.size() == 0){
                rlyForumDetailTwoNoComment.setVisibility(View.VISIBLE);
                tvForumDetailTwoNoComment.setText("楼主还没有评论，快来戳他吧！");
            }else {
                rlyForumDetailTwoNoComment.setVisibility(View.GONE);
            }
            rvAdapter.replaceAll(dataList);
        }
    }
    private void submitComment(){
        String content = etForumDetailTwoComment.getText().toString();
//        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
//        String account = xcCacheManager.readCache(xcCacheSaveName.account);
        if(content.equals("")||content.isEmpty()){
            return;
        }
        if(thirdid == null){
            thirdid = "";
        }
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
//                Log.d("ddddd11",e+"");
            }

            @Override
            public void onNext(AddCommentBean addCommentBean) {
                if(addCommentBean.getIssuccess().equals("1")) {
                    Toast.makeText(getBaseContext(), addCommentBean.getMsg(), Toast.LENGTH_SHORT).show();
                    etForumDetailTwoComment.setText("");
                    showHideSoftInput();
                    initComment();
                }
            }
        });
    }
    private void commentBackComment(){
        String commentBackContent = etForumDetailTwoComment.getText().toString();
        if(commentBackContent.equals("")||commentBackContent.isEmpty()){
            return;
        }
        if(thirdid == null){
            thirdid = "";
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
        paramMap.put("thirdid",thirdid);
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
                    etForumDetailTwoComment.setText("");
                    dataList.clear();
                    showHideSoftInput();
                    rvAdapter.notifyDataSetChanged();
                    initComment();
                }
            }
        });
    }
    private void showHideSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void guanZhuSubmit(){

        Map<String,String> map = new HashMap<>();
        GuanZhuFansNetWork guanZhuFansNetWork= new GuanZhuFansNetWork();

        map.put("account",articleAccount);
        map.put("followid",account);
       /* if(isFriends.equals("0")){
            map.put("type","1");
        }
        if(isFriends.equals("1")){
            map.put("type","0");
        }*/
//        if(isGuanZhu.equals("0")) {
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
                            rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvForumDetailTwoGuanZhu.setText("互相关注");
                            tvForumDetailTwoGuanZhu.setTextColor(Color.GRAY);
//                            tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
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
                            rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                            tvForumDetailTwoGuanZhu.setText("已关注");
                            tvForumDetailTwoGuanZhu.setTextColor(Color.GRAY);
//                            tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
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
                        rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                        tvForumDetailTwoGuanZhu.setText("关注");
                        tvForumDetailTwoGuanZhu.setTextColor(Color.WHITE);
//                        tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));

//                            dataList.get(pos).setIsfans(0);
//                        dataList.get(pos).setIsfriends(0);
                    }
                    Toast.makeText(getBaseContext(), cancelGuanZhuBean.getMsg(), Toast.LENGTH_SHORT).show();/*
                    if (cancelGuanZhuBean.getIssuccess().equals("1")) {

                        if (isFriends.equals("0")) {
                            rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.huxiangguanzhu_shape);
                            tvForumDetailTwoGuanZhu.setText("已关注");
                            tvForumDetailTwoGuanZhu.setTextColor(Color.GRAY);
                            isFriends = "1";
                        } else {
                            rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.weiguanzhu_shape);
                            tvForumDetailTwoGuanZhu.setText("关注");
                            tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                            isFriends = "0";
                        }

                        Toast.makeText(getBaseContext(), cancelGuanZhuBean.getMsg(), Toast.LENGTH_SHORT).show();*/
//                        notifyDataSetChanged();

                }
            });
     /*   }else {
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
                            rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.huxiangguanzhu_shape);
                            tvForumDetailTwoGuanZhu.setText("互相关注");
                            tvForumDetailTwoGuanZhu.setTextColor(Color.GRAY);
                            isFriends = "1";
                        } else {
                            rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.weiguanzhu_shape);
                            tvForumDetailTwoGuanZhu.setText("关注");
                            tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
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
                    tvForumDetailTwoCollect.setText(collectNums+"");
                    ibForumDetailTwoCollect.setBackgroundResource(R.mipmap.collected);

                }
                if(collectArticleBean.getIssuccess().equals("2")){
//                    ibForumDetailThreeZan.setBackgroundResource(R.mipmap.thumbs);
                    collectNums--;
                    tvForumDetailTwoCollect.setText(collectNums+"");
                    ibForumDetailTwoCollect.setBackgroundResource(R.mipmap.collect);
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
                    tvForumDetailTwoZan.setText(""+getPraiseCollectBean.getPraisecount());
                    if(getPraiseCollectBean.getIspraise() > 0){
                        ibForumDetailTwoZan.setBackgroundResource(R.mipmap.redgood);
                    }else {
                        ibForumDetailTwoZan.setBackgroundResource(R.mipmap.thumbs);
                    }
                    if(getPraiseCollectBean.getIscollect() >0){
                        ibForumDetailTwoCollect.setBackgroundResource(R.mipmap.collected);
                    }else {
                        ibForumDetailTwoCollect.setBackgroundResource(R.mipmap.collect);
                    }
                    collectNums = getPraiseCollectBean.getCollectcount();
                    tvForumDetailTwoCollect.setText(""+getPraiseCollectBean.getCollectcount());
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
        praiseArticleNetWork.addCancelZanArticleToNet(map, new Observer<AddCancelZanBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AddCancelZanBean addCancelZanBean) {
                if(addCancelZanBean.getIssuccess().equals("1")){
                    ibForumDetailTwoZan.setBackgroundResource(R.mipmap.redgood);
                    zanNums++;
                    tvForumDetailTwoZan.setText(zanNums+"");
                }
                if(addCancelZanBean.getIssuccess().equals("2")){
                    ibForumDetailTwoZan.setBackgroundResource(R.mipmap.thumbs);
                    zanNums--;
                    tvForumDetailTwoZan.setText(zanNums+"");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onResume(){
        super.onResume();
//        initContentData();
//        Toast.makeText(this,"this is onresume",Toast.LENGTH_SHORT).show();
//        maxEightList.clear();
//        dataListTemp.clear();
        if(isResume) {
            initContentData();
            initComment();
            getPraiseCollect();
            isResume = false;
        }
    }

}
