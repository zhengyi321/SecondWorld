package com.et.secondworld.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.et.secondworld.R;
import com.et.secondworld.VisitOthersActivity;
import com.et.secondworld.VisitOthersShopActivity;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.forum.EmergencyHelpPostActivity;
import com.et.secondworld.forum.ForumEditActivity;
import com.et.secondworld.mine.setting.MineSettingReportActivity;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.spread.SpreadActivity;
import com.et.secondworld.utils.WindowUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;
import com.et.secondworld.widget.dialog.QueryCancelDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

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
public class ShareDialog extends Dialog {
    private View mPopView;
    Context context;
    private Activity activity;
    private String articleAccount = "";
    private String articleid = "";
    private String account="";
    private String shopid="";
    private String managetype="";
    private String thirdid="";
    private String articletypes="";
    private int type = 0;//0为用户 1为店铺
    private int articletype = 0;//0为用户文章 1为店铺文章
    public interface OnFinishClickListener{
        public void getMaps(Map<Object, Object> map);
    }
    public interface OnDataListClickListener{
        public void getData(ArrayList<JsonBean> dataList);
    }
    private OnFinishClickListener onFinishClickListener;
    private OnDataListClickListener onDataListClickListener;
    public ShareDialog(Context context1,String articleAccount1 ,String articleid1 ,int type1,int articletype1,String managetype,String thirdid,String articletypes) {
        super(context1);
        context = context1;
        articletype = articletype1;
        articleAccount = articleAccount1;
        articleid = articleid1;
        type = type1;
        this.managetype = managetype;
        this.thirdid = thirdid;
        this.articletypes = articletypes;
//        init(context);
//        setPopupWindow();
    }
    public ShareDialog(Context context1, int themeResId) {
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

         ShareDialog shareDialog;



        @BindView(R.id.lly_dialog_share_weixinshare)
        LinearLayout llyDialogShareWeiXinShare;
        @OnClick(R.id.lly_dialog_share_weixinshare)
        public void llyDialogShareWeiXinShareOnclick(){
//            Toast.makeText(getContext(),"暂不开放,敬请期待！",Toast.LENGTH_SHORT).show();
            UMImage image = new UMImage(activity, img);//资源文件
            String url = "";
//            if(articletype == 0){
//                url = "http://et-stars.cn/detail.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account;
//            }else {
                url = "http://et-stars.cn/detailone.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account;
//            }
//        UMWeb  web = new UMWeb("http://et-stars.cn/detail.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account);
            UMWeb web = new UMWeb(url);
            web.setTitle(title);//标题
            web.setThumb(image);  //缩略图
            web.setDescription(title);//描述
////        new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN).withMedia(image)
////                .setCallback(shareListener).open();
            new ShareAction(activity)
                    .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                    .withText("hello")//分享内容
                    .withMedia(web)
                    .setCallback(shareListener)//回调监听器
                    .share();
        }
        @OnClick(R.id.lly_dialog_share_qqzone)
        public void llyDialogShareQQZoneOnclick(){
            UMImage image = new UMImage(activity, img);//资源文件
            String url = "";
//            if(articletype == 0){
//                url = "http://et-stars.cn/detail.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account;
//            }else {
                url = "http://et-stars.cn/detailone.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account;
//            }
//        UMWeb  web = new UMWeb("http://et-stars.cn/detail.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account);
            UMWeb web = new UMWeb(url);
            web.setTitle(title);//标题
            web.setThumb(image);  //缩略图
            web.setDescription(title);//描述
////        new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN).withMedia(image)
////                .setCallback(shareListener).open();
            new ShareAction(activity)
                    .setPlatform(SHARE_MEDIA.QZONE)//传入平台
                    .withText("hello")//分享内容
                    .withMedia(web)
                    .setCallback(shareListener)//回调监听器
                    .share();
        }
        @OnClick(R.id.lly_dialog_share_qq)
        public void llyDialogShareQQOnclick(){

            UMImage image = new UMImage(activity, img);//资源文件
            String url = "";
//            if(articletype == 0){
//                url = "http://et-stars.cn/detail.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account;
//            }else {
                url = "http://et-stars.cn/detailone.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account;
//            }
//        UMWeb  web = new UMWeb("http://et-stars.cn/detail.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account);
            UMWeb web = new UMWeb(url);
            web.setTitle(title);//标题
            web.setThumb(image);  //缩略图
            web.setDescription(title);//描述
////        new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN).withMedia(image)
////                .setCallback(shareListener).open();
            new ShareAction(activity)
                    .setPlatform(SHARE_MEDIA.QQ)//传入平台
                    .withText("hello")//分享内容
                    .withMedia(web)
                    .setCallback(shareListener)//回调监听器
                    .share();
        }
        @OnClick(R.id.lly_dialog_share_weixincycle)
        public void llyDialogShareWeiXInCycleOnclick(){
            UMImage image = new UMImage(activity, img);//资源文件
            String url = "";
//            if(articletype == 0){
//                url = "http://et-stars.cn/detail.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account;
//            }else {
                url = "http://et-stars.cn/detailone.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account;
//            }
//        UMWeb  web = new UMWeb("http://et-stars.cn/detail.html?articleid="+articleid+"&articleaccount="+articleAccount+"&account="+account);
            UMWeb web = new UMWeb(url);
            web.setTitle(title);//标题
            web.setThumb(image);  //缩略图
            web.setDescription(title);//描述
////        new ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN).withMedia(image)
////                .setCallback(shareListener).open();
            new ShareAction(activity)
                    .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                    .withText("hello")//分享内容
                    .withMedia(web)
                    .setCallback(shareListener)//回调监听器
                    .share();
        }
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
                Toast.makeText(activity,"分享成功",Toast.LENGTH_LONG).show();
            }
            /**
             * @descrption 分享失败的回调
             * @param platform 平台类型
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(activity,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
            /**
             * @descrption 分享取消的回调
             * @param platform 平台类型
             */
            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(activity,"取消了",Toast.LENGTH_LONG).show();
            }
        };
        @BindView(R.id.lly_dialog_share_report)
        LinearLayout llyDialogShareReport;
        @OnClick(R.id.lly_dialog_share_report)
        public void llyDialogShareReportOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                Intent intent = new Intent(context, MineSettingReportActivity.class);
                intent.putExtra("reportid", articleAccount);
                context.startActivity(intent);
            }
        }
//        @BindView(R.id.lly_dialog_share_edit)
//        LinearLayout llyDialogShareEdit;
        @OnClick(R.id.lly_dialog_share_edit)
        public void llyDialogShareEditOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                Intent intent = new Intent(activity, ForumEditActivity.class);
                intent.putExtra("articleid", articleid);
                activity.startActivity(intent);
            }
        }
        @BindView(R.id.lly_dialog_share_spread)
        LinearLayout llyDialogShareSpread;
        @OnClick(R.id.lly_dialog_share_spread)
        public void llyDialogShareSpreadOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                Intent intent = new Intent(activity, SpreadActivity.class);
                intent.putExtra("articleid", articleid);
                intent.putExtra("articletype", articletype);
                intent.putExtra("nick", nick);
                intent.putExtra("title", title);
                intent.putExtra("img", img);
                activity.startActivity(intent);
            }
        }
        @BindView(R.id.lly_dialog_share_home)
        LinearLayout llyDialogShareHome;
        @OnClick(R.id.lly_dialog_share_home)
        public void llyDialogShareHomeOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
                if (type == 0) {
                    Intent intent = new Intent(activity, VisitOthersActivity.class);
                    intent.putExtra("articleaccount", articleAccount);
                    activity.startActivity(intent);
                } else {
                    Intent intent = new Intent(activity, VisitOthersShopActivity.class);
                    intent.putExtra("shopid", shopid);
                    activity.startActivity(intent);
                }
            }
        }
        @OnClick(R.id.rly_dialog_share_cancel)
        public void rlyDialogShareCancelOnclick(){
            shareDialog.dismiss();
        }
        private long clickTime = 0;
        @OnClick(R.id.lly_dialog_share_emergencyhelp)
        public void llyDialogShareEmergencyHelpOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();

                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getContext());
                String auth = xcCacheManager.readCache(xcCacheSaveName.userAuth);
                if (auth != null && !auth.equals("1")) {
                    Toast.makeText(getContext(), "请先认证", Toast.LENGTH_LONG).show();
                    return;
                }
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(view.getContext());
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
                String role = xcCacheManager.readCache(xcCacheSaveName.role);

                if (role != null && role.equals("01")) {
                    Toast.makeText(getContext(), "禁言中", Toast.LENGTH_SHORT).show();
                    return;

                }
                String account = xcCacheManager.readCache(xcCacheSaveName.account);
                Map<String, Object> map = new HashMap<>();
                map.put("account", account);
                map.put("types", "help");
                ArticleNetWork articleNetWork = new ArticleNetWork();
                articleNetWork.getArticleStatusFromNet(map, new Observer<BaseBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.getIssuccess().equals("1")) {
                            Toast.makeText(getContext(), baseBean.getMsg(), Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Intent intent = new Intent(activity, EmergencyHelpPostActivity.class);
                            activity.startActivity(intent);
                        }
                    }
                });
            }
        }
        @BindView(R.id.lly_dialog_share_delete)
        LinearLayout llyDialogShareDelete;
        @OnClick(R.id.lly_dialog_share_delete)
        public void llyDialogShareDeleteOnclick(){
            if(System.currentTimeMillis() - clickTime > 2000) {

                clickTime = System.currentTimeMillis();
            if(articleAccount.equals(account) ||articleAccount.equals(shopid) || (managetype != null && managetype.equals("manage"))) {

                QueryCancelDialog queryCancelDialog = new QueryCancelDialog(activity).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
                    @Override
                    public void isQuery(boolean isQuery) {
                        if (isQuery) {
                            Log.d("delete11","thirdid:"+thirdid+" type:"+type+" articleid:"+articleid);
                            /*if (type == 0) {
                                ArticleNetWork articleNetWork = new ArticleNetWork();
                                Map<String, Object> paramMap = new HashMap<>();
                                paramMap.put("thirdid", thirdid);
//                                Toast.makeText(activity,"zz",Toast.LENGTH_SHORT).show();
                                activity.finish();
                                articleNetWork.deleteForumArticleToNet(paramMap, new Observer<BaseBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(BaseBean baseBean) {
                                        Toast.makeText(activity, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                                        activity.finish();
                                    }
                                });
                            }
                            if (type == 1) {*/
                                ArticleNetWork articleNetWork = new ArticleNetWork();
                                Map<String, Object> paramMap = new HashMap<>();
                                paramMap.put("articleid", articleid);
//                                Toast.makeText(activity,"zz",Toast.LENGTH_SHORT).show();
                                activity.finish();
                                articleNetWork.deleteArticleToNet(paramMap, new Observer<BaseBean>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(BaseBean baseBean) {
                                        Toast.makeText(activity, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
                                        activity.finish();
                                    }
                                });
//                            }
                        }
                    }
                }).build(activity, "确定删除帖子吗?");
                queryCancelDialog.show();
                }
            }
        }
        private String nick = "",title= "",img="";
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public ShareDialog build(Context context,String nick,String title,String img) {
            activity = (Activity)context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            shareDialog = new ShareDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            shareDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.dialog_share, null);
            ButterKnife.bind(this,view);
            this.nick = nick;
            this.title = title;
            this.img = img;
            if(this.img == null || this.img.isEmpty()){
                this.img = "http://et-stars.cn/logo.jpg";
            }
            shareDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = shareDialog.getWindow().getAttributes();
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(mContext);
            account = xcCacheManager.readCache(xcCacheSaveName.account);
            shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
            if(shopid == null){
                shopid = "";
            }
            if(account == null){
                account = "";
                llyDialogShareReport.setVisibility(View.GONE);
            }

            if(!account.equals(articleAccount)&&!articleAccount.equals(shopid)){
                llyDialogShareDelete.setVisibility(View.GONE);
                llyDialogShareSpread.setVisibility(View.GONE);
            }
            if(articletype == 0){
                llyDialogShareSpread.setVisibility(View.VISIBLE);
            }
            if(!account.equals(articleAccount)){
//                llyDialogShareEdit.setVisibility(View.GONE);
//                llyDialogShareSpread.setVisibility(View.GONE);
            }
            if(managetype != null && managetype.equals("manage")){
                llyDialogShareDelete.setVisibility(View.VISIBLE);
            }else {
//                llyDialogShareDelete.setVisibility(View.GONE);
            }
            if(articletypes != null) {
                if ( articletypes.equals("help") || articletypes.equals("data")){
                    llyDialogShareDelete.setVisibility(View.GONE);
                }
            }
            WindowUtils windowUtils = new WindowUtils(activity);
            layoutParams.width = (int)windowUtils.getWindowWidth();
//            layoutParams.height = (int)windowUtils.getWindowHeight();
            layoutParams.gravity = Gravity.BOTTOM;
            Log.d("width",""+windowUtils.getWindowWidth());
            shareDialog.getWindow().setAttributes(layoutParams);


            shareDialog.setContentView(view);


            return shareDialog;
        }

    }





}
