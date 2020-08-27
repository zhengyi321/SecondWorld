package com.et.secondworld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.et.secondworld.R;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.utils.WindowUtils;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/28
 **/
public class QueryCancelDialog extends Dialog {

    Context context;
    private Activity activity;
    private String shopid="";
    private int type = 0;//0为用户 1为店铺
    public interface OnFinishClickListener{
        public void isQuery(boolean isQuery);
    }
    public interface OnDataListClickListener{
        public void getData(ArrayList<JsonBean> dataList);
    }
    private OnFinishClickListener onFinishClickListener;
    private OnDataListClickListener onDataListClickListener;
    public QueryCancelDialog(Context context1) {
        super(context1);
        context = context1;
//        init(context);
//        setPopupWindow();
    }
    public QueryCancelDialog(Context context1, int themeResId) {
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

         QueryCancelDialog praiseDialog;
        public Builder setCallBackListener(OnFinishClickListener onFinishClickListener1){//设置回调
            onFinishClickListener = onFinishClickListener1;
            return this;
        }

        @BindView(R.id.tv_query_cancel_title)
        TextView tvQueryCancelTitle;
        @OnClick(R.id.rly_query_cancel_query)
        public void rlyQueryCancelQueryOnclick(){
            if(onFinishClickListener != null){
                onFinishClickListener.isQuery(true);
            }
            praiseDialog.dismiss();
        }
        @OnClick(R.id.rly_query_cancel_cancel)
        public void rlyQueryCancelCancelOnclick(){
            if(onFinishClickListener != null){
                onFinishClickListener.isQuery(false);
            }
            praiseDialog.dismiss();
        }

        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public QueryCancelDialog build(Context context,String title) {
            activity = (Activity)context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            praiseDialog = new QueryCancelDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
//            praiseDialog.setCanceledOnTouchOutside(false);//默认点击布局外不能取消dialog


            praiseDialog.setCancelable(true);
            View view = mInflater.inflate(R.layout.dialog_query_cancel, null);
            ButterKnife.bind(this,view);
            tvQueryCancelTitle.setText(title);
            praiseDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = praiseDialog.getWindow().getAttributes();
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(mContext);
//
//
            WindowUtils windowUtils = new WindowUtils(activity);
            layoutParams.width = (int)windowUtils.getWindowWidth();
//            layoutParams.height = (int)windowUtils.getWindowHeight()-82;
//            layoutParams.height = (int)windowUtils.getWindowHeight();
            layoutParams.gravity = Gravity.CENTER;
//            Log.d("width",""+windowUtils.getWindowWidth());
            praiseDialog.getWindow().setAttributes(layoutParams);


            praiseDialog.setContentView(view);


            return praiseDialog;
        }

    }





}
