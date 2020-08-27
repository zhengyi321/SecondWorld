package com.et.secondworld.message;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.et.secondworld.bean.GetForumDetailBean;
import com.et.secondworld.dialog.ShareDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;
import com.et.secondworld.R;
import com.et.secondworld.VisitOthersActivity;
import com.et.secondworld.bean.CancelGuanZhuBean;
import com.et.secondworld.bean.GetCommentBean;
import com.et.secondworld.bean.GetImportantNoticeDetailBean;
import com.et.secondworld.network.GuanZhuFansNetWork;
import com.et.secondworld.network.NoticeNetWork;
import com.et.secondworld.utils.ImageLoaderUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.imageview.CircleImageView;
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
public class MessageImportantNoticeDetailActivity extends FragmentActivity {
    private List<GetCommentBean.CommentlistBean> dataList = new ArrayList<>();
    private List<GetCommentBean.CommentlistBean> dataListTemp = new ArrayList<>();
    private int collectNums = 0;
    private int zanNums = 0;
    private ProgressDialog dialog;
    @BindView(R.id.icv_message_important_notice_detail)
    ImageCycleView icvForumDetailTwo;
    @BindView(R.id.rly_message_important_notice_detail_share)
    RelativeLayout rlyForumDetailTwoShare;
    @OnClick(R.id.rly_message_important_notice_detail_share)
    public void rlyForumDetailTwoShareOnclick(){
//        UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.WEIXIN, authListener);
        /*new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).open();*/
//        new ShareAction(this)
//                .setPlatform(SHARE_MEDIA.QQ)//传入平台
//                .withText("hello")//分享内容
//                .setCallback(shareListener)//回调监听器
//                .share();
//        Toast.makeText(this,"this is share",Toast.LENGTH_LONG).show();
        ShareDialog shareDialog = new ShareDialog(this,articleAccount,articleid,1,1,"","","").Build.build(this,nick,title,img);
        shareDialog.show();
    }
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
            Toast.makeText(getBaseContext(),"成功                                        了",Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getBaseContext(),"失                                            败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getBaseContext(),"取消                                          了",Toast.LENGTH_LONG).show();
        }
    };
    @BindView(R.id.tv_message_important_notice_detail_title)
    TextView tvForumDetailTwoTitle;
    @BindView(R.id.civ_message_important_notice_detail_head)
    CircleImageView civForumDetailTwoHead;
    private long clickTime = 0;
    @OnClick(R.id.civ_message_important_notice_detail_head)
    public void civForumDetailTwoHeadOnclick(){
        if(System.currentTimeMillis() - clickTime > 2000) {

            clickTime = System.currentTimeMillis();
            Intent intent = new Intent(this, VisitOthersActivity.class);
            intent.putExtra("articleaccount", articleAccount);
            startActivity(intent);
        }
    }
    @BindView(R.id.tv_message_important_notice_detail_nick)
    TextView tvForumDetailTwoNick;


    @BindView(R.id.tv_message_important_notice_detail_guanzhu)
    TextView tvForumDetailTwoGuanZhu;

    @BindView(R.id.tv_message_important_notice_detail_content)
    TextView tvForumDetailTwoContent;
    @BindView(R.id.rly_message_important_notice_detail_loading)
    RelativeLayout rlyMessageImportantNoticeDetailLoading;
    @BindView(R.id.iv_message_important_notice_detail_loading)
    ImageView ivMessageImportantNoticeDetailLoading;

   /* @BindView(R.id.tv_message_important_notice_detail_share)
    TextView tvForumDetailTwoShare;
    @BindView(R.id.rly_message_important_notice_detail_collect)
    RelativeLayout rlyForumDetailTwoCollect;
    @OnClick(R.id.rly_message_important_notice_detail_collect)
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
    @BindView(R.id.rly_message_important_notice_detail_back)
    RelativeLayout rlyForumDetailTwoBack;
    @OnClick(R.id.rly_message_important_notice_detail_back)
    public void rlyForumDetailTwoBackOnclick(){
        finish();
    }
    @BindView(R.id.rly_message_important_notice_detail_guanzhu)
    RelativeLayout rlyForumDetailTwoGuanZhu;
    @OnClick(R.id.rly_message_important_notice_detail_guanzhu)
    public void rlyForumDetailTwoGuanZhuOnclick(){
        guanZhuSubmit();
    }


    String articleAccount = "";
    String articleid = "";
    String title = "";
    String account = "";
    private String nick = "";
    private String head = "";
    private String isFriends = "0";
    private String isGuanZhu = "0";
    private Map<String,Object> map = new HashMap<>();
    private List<String> imgUrlList = new ArrayList<>();
    private String img = "";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_message_important_notice_detail);
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        ButterKnife.bind(this);
//        initLunBo();

        initContentData();
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
            ImageLoader.getInstance().displayImage(imageURL,imageView,ImageLoaderUtils.options);
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
        rlyMessageImportantNoticeDetailLoading.setVisibility(View.VISIBLE);
        Glide.with(this).load(R.mipmap.pageloading).into(ivMessageImportantNoticeDetailLoading);
        Intent intent = getIntent();
         articleAccount = intent.getStringExtra("articleAccount");
         articleid = intent.getStringExtra("articleid");
         title = intent.getStringExtra("title");
//         Log.d("forumdetailAccount",articleAccount);
//         Log.d("forumdetailarticleid",articleid);
        if(articleAccount == null){
            articleAccount = "";
        }

        XCCacheManager xcCacheManager = XCCacheManager.getInstance(this);
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
         account = xcCacheManager.readCache(xcCacheSaveName.account);

         if(articleAccount!= null && articleAccount.equals(account)){
//             tvForumDetailTwoShare.setVisibility(View.GONE);
             rlyForumDetailTwoGuanZhu.setVisibility(View.GONE);
         }
         if(account.equals(articleAccount)){
//             rlyForumDetailTwoCollect.setVisibility(View.GONE);
         }
         tvForumDetailTwoTitle.setText(title);
//        Log.d("forumdetailtitle",account);
        NoticeNetWork noticeNetWork = new NoticeNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("articleid",articleid);
        map.put("articleaccount",articleAccount);
        map.put("account",account);
//        Log.d("forumdetailNetWork",""+articleid);
//        Log.d("forumdetailNetWork",""+articleAccount);
//        Log.d("forumdetailNetWork",""+account);
        noticeNetWork.getImportantNoticeDetailFromNet(map, new Observer<GetImportantNoticeDetailBean>() {
            @Override
            public void onCompleted() {
//                Log.d("forumdetailNetWork","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
//                Log.d("forumdetailNetWork",""+e);
            }

            @Override
            public void onNext(GetImportantNoticeDetailBean getImportantNoticeDetailBean) {
//                Log.d("forumdetailNetWork","onCompleted"+getForumDetailBean.getMsg());
                if(getImportantNoticeDetailBean.getIssuccess().equals("1")){
//                    Log.d("forumdetailNetWork","step1");
                    rlyMessageImportantNoticeDetailLoading.setVisibility(View.GONE);
                    if(getImportantNoticeDetailBean.getNick() != null) {
                        nick = getImportantNoticeDetailBean.getNick();
                        tvForumDetailTwoNick.setText(getImportantNoticeDetailBean.getNick());
//                        Log.d("forumdetailNetWork","step2");
                    }
                    if(getImportantNoticeDetailBean.getHead() != null) {
                        head = getImportantNoticeDetailBean.getHead();
                    }
                    Glide.with(getBaseContext()).load(getImportantNoticeDetailBean.getHead())
                            .apply(new RequestOptions().fallback(R.mipmap.imghead).error(R.mipmap.imghead).circleCrop())
                            .into(civForumDetailTwoHead);
//                    ImageLoader.getInstance().displayImage(getImportantNoticeDetailBean.getHead(),civForumDetailTwoHead, ImageLoaderUtils.options);
//                    Log.d("forumdetailNetWork","step3");
                    isFriends = getImportantNoticeDetailBean.getIsfriend();
                    isGuanZhu = getImportantNoticeDetailBean.getIsguanzhu();
                        if (getImportantNoticeDetailBean.getIsguanzhu().equals("0")) {
                            if (getImportantNoticeDetailBean.getIsfriend().equals("0")) {
                                rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                                tvForumDetailTwoGuanZhu.setText("关注");
//                                tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                                tvForumDetailTwoGuanZhu.setTextColor(Color.WHITE);
                            }else{
                                rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                                tvForumDetailTwoGuanZhu.setText("已关注");
                                tvForumDetailTwoGuanZhu.setTextColor(Color.GRAY);
                            }
                        } else {
                            if (getImportantNoticeDetailBean.getIsfriend().equals("0")) {
                                rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.green_half_round_shape);
                                tvForumDetailTwoGuanZhu.setText("关注");
//                                tvForumDetailTwoGuanZhu.setTextColor(Color.parseColor("#6d9eff"));
                                tvForumDetailTwoGuanZhu.setTextColor(Color.WHITE);

                            } else {
                                rlyForumDetailTwoGuanZhu.setBackgroundResource(R.drawable.gray_half_round_shape);
                                tvForumDetailTwoGuanZhu.setText("互相关注");
                                tvForumDetailTwoGuanZhu.setTextColor(Color.GRAY);
                            }
                        }

                    if(getImportantNoticeDetailBean.getContent() != null) {
                        tvForumDetailTwoContent.setText(getImportantNoticeDetailBean.getContent());
                    }
                    for(GetImportantNoticeDetailBean.ImglistBean item : getImportantNoticeDetailBean.getImglist()){
                        imgUrlList.add(item.getUrl());
                    }
                    int imgsize = getImportantNoticeDetailBean.getImglist().size();
                    if(imgsize > 0){
                        img = imgUrlList.get(0);
                    }
//                    tvForumDetailTwoRepost.setText(getForumDetailBean.getRepost()+"");
//                    tvForumDetailTwoComments.setText(getForumDetailBean.getComment()+"");
//                    tvForumDetailTwoGood.setText(getForumDetailBean.getGood()+"");
//                    tvForumDetailTwoReaders.setText(getForumDetailBean.getReaders()+"");
//                    Log.d("forumdetailNetWork",""+getForumDetailBean.getImglist());
                    if(getImportantNoticeDetailBean.getImglist().size() > 0) {
                        icvForumDetailTwo.setVisibility(View.VISIBLE);
                        List<String> urlList = new ArrayList<>();
                        for(GetImportantNoticeDetailBean.ImglistBean item:getImportantNoticeDetailBean.getImglist()){
                            urlList.add(item.getUrl());
                        }
                        icvForumDetailTwo.setImageResources(urlList, null, null, imageCycleViewListener,null,null);
                    }

                }

//                Log.d("forumdetailNetWork","step4");

            }
        });


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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onResume(){
        super.onResume();
//        initContentData();
//        initComment();

    }

}
