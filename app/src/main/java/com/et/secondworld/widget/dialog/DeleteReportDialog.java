package com.et.secondworld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.mine.setting.MineSettingReportActivity;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.utils.WindowUtils;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

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
public class DeleteReportDialog extends Dialog {

    Context context;
    private Activity activity;
    private String shopid="";
    private int type = 0;//0为用户 1为店铺
    public interface OnFinishClickListener{
        public void isBack(Boolean isBack);
    }
    public interface OnDataListClickListener{
        public void getData(ArrayList<JsonBean> dataList);
    }
    private OnFinishClickListener onFinishClickListener;
    private OnDataListClickListener onDataListClickListener;
    public DeleteReportDialog(Context context1) {
        super(context1);
        context = context1;
//        init(context);
//        setPopupWindow();
    }
    public DeleteReportDialog(Context context1, int themeResId) {
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

         DeleteReportDialog praiseDialog;
/*
        @BindView(R.id.tv_dialog_mine_get_good)
        TextView tvDialogMineGetGood;
        @OnClick(R.id.rly_dialog_mine_get_good_ok)
        public void rlyDialogMineGetGoodOKOnclick(){
            praiseDialog.dismiss();
        }*/
        @OnClick(R.id.rly_delete_report_cancel)
        public void rlyDeleteReportCancelOnclick(){
            praiseDialog.dismiss();
        }
        @OnClick(R.id.rly_delete_report_back)
        public void rlyDeleteReportBackOnclick(){
            if(onFinishClickListener != null){
                onFinishClickListener.isBack(true);
            }
            praiseDialog.dismiss();
        }
        @OnClick(R.id.rly_delete_report_copy)
        public void rlyDeleteReportCopyOnclick(){

        }
        @OnClick(R.id.rly_delete_report_delete)
        public void rlyDeleteReportDeleteOnclick(){
            if(isreport) {
                Intent intent = new Intent(context, MineSettingReportActivity.class);
                intent.putExtra("reportid", commentaccount);
                context.startActivity(intent);
            }else {
                QueryCancelDialog queryCancelDialog = new QueryCancelDialog(context).Build.setCallBackListener(new QueryCancelDialog.OnFinishClickListener() {
                    @Override
                    public void isQuery(boolean isQuery) {
                        if(isQuery) {
//                            guanZhuSubmit();

                        if(type == 0) {
                            ArticleNetWork articleNetWork = new ArticleNetWork();
                            Map<String, Object> parammap = new HashMap<>();
                            parammap.put("commentid", commentid);
                            articleNetWork.deleteCommentToNet(parammap, new Observer<BaseBean>() {
                                @Override
                                public void onCompleted() {
        //                                        Log.d("comment111","this is onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
        //                                        Log.d("comment111","this is onCompleted"+e);
                                }

                                @Override
                                public void onNext(BaseBean baseBean) {
        //                                        Log.d("comment111","this is onCompleted"+baseBean.getIssuccess());
                                    if (baseBean.getIssuccess().equals("1")) {
                                        Toast.makeText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();

        //                            notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                        if(type == 1){
                            ArticleNetWork articleNetWork = new ArticleNetWork();
                            Map<String,Object> parammap = new HashMap<>();
                            parammap.put("backid",commentid);
                            articleNetWork.deleteCommentBackToNet(parammap, new Observer<BaseBean>() {
                                @Override
                                public void onCompleted() {
        //                                        Log.d("comment111","this is onCompleted");
                                }

                                @Override
                                public void onError(Throwable e) {
        //                                        Log.d("comment111","this is onCompleted"+e);
                                }

                                @Override
                                public void onNext(BaseBean baseBean) {
        //                                        Log.d("comment111","this is onCompleted"+baseBean.getIssuccess());
                                    if(baseBean.getIssuccess().equals("1")) {
                                        Toast.makeText(context, baseBean.getMsg(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        }

                            if(onFinishClickListener != null){
                                onFinishClickListener.isBack(false);
                            }
                        }
                    }
                }).build(context,"确认删除吗?");
                queryCancelDialog.show();


            }
            praiseDialog.dismiss();
        }
        @BindView(R.id.tv_delete_report_comment)
        TextView tvDeleteReportComment;
        @BindView(R.id.tv_delete_report_delete)
        TextView tvDeleteReportDelete;
        public Builder setCallBackListener(OnFinishClickListener onFinishClickListener1){//设置回调
            onFinishClickListener = onFinishClickListener1;
            return this;
        }
        private String commentaccount = "";
        private boolean isreport = false;
        private String commentid ="";
        private int type = 0;
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public DeleteReportDialog build(Context context,String content,String commentaccount,String nick,String commentid,int type) {
            activity = (Activity)context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            praiseDialog = new DeleteReportDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            praiseDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.dialog_delete_report, null);
            ButterKnife.bind(this,view);
            praiseDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = praiseDialog.getWindow().getAttributes();
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(mContext);
//
//
            this.type = type;
            this.commentaccount = commentaccount;
            this.commentid = commentid;
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(context);
            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
            String account = xcCacheManager.readCache(xcCacheSaveName.account);
            String shopid = xcCacheManager.readCache(xcCacheSaveName.shopId);
            if(account == null){
                account = "";
            }
            if(shopid == null){
                shopid = "";
            }
            if(account.equals(commentaccount)||shopid.equals(commentaccount)) {
                tvDeleteReportComment.setText("你的评论:"+content);
                tvDeleteReportDelete.setText("删除");
                isreport = false;
            }else {
                tvDeleteReportComment.setText(nick+":"+content);
                tvDeleteReportDelete.setText("举报");
                isreport = true;
            }
            WindowUtils windowUtils = new WindowUtils(activity);
            layoutParams.width = (int)windowUtils.getWindowWidth();
//            layoutParams.height = (int)windowUtils.getWindowHeight()-82;
//            layoutParams.height = (int)windowUtils.getWindowHeight();
            layoutParams.gravity = Gravity.BOTTOM;
//            Log.d("width",""+windowUtils.getWindowWidth());
            praiseDialog.getWindow().setAttributes(layoutParams);


            praiseDialog.setContentView(view);


            return praiseDialog;
        }

    }





}
