package com.et.secondworld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.et.secondworld.R;
import com.et.secondworld.bean.BaseBean;
import com.et.secondworld.bean.JsonBean;
import com.et.secondworld.dialog.SelectTownDialog;
import com.et.secondworld.network.ArticleNetWork;
import com.et.secondworld.utils.WindowUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/28
 **/
public class HaveSoluationedDialog extends Dialog {

    Context context;
    private Activity activity;
//    private String shopid="";
    private int type = 0;//0为用户 1为店铺

    private OnFinishClickListener onFinishClickListener;
    private OnDataListClickListener onDataListClickListener;
    public interface OnFinishClickListener{
        public void isUpdated(boolean isUpdated);
    }
    public interface OnDataListClickListener{
        public void getData(ArrayList<JsonBean> dataList);
    }
    public HaveSoluationedDialog(Context context1) {
        super(context1);
        context = context1;
//        init(context);
//        setPopupWindow();
    }
    public HaveSoluationedDialog(Context context1, int themeResId) {
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

         HaveSoluationedDialog praiseDialog;
        private String articleid = "";


        @OnClick(R.id.rly_have_soluation_cancel)
        public void rlyHaveSoluationCancelOnclick(){
            if(onFinishClickListener != null){
                onFinishClickListener.isUpdated(false);
            }
            praiseDialog.dismiss();

        }
        @OnClick(R.id.rly_have_soluation_ok)
        public void rlyHaveSoluationOKOnclick(){

            Map<String,Object> map = new HashMap<>();
            map.put("articleid",articleid);
            ArticleNetWork articleNetWork = new ArticleNetWork();
            articleNetWork.updateSoluationsToNet(map, new Observer<BaseBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(BaseBean baseBean) {
                    if(baseBean.getIssuccess().equals("1")){
                        Toast.makeText(getContext(),baseBean.getMsg(),Toast.LENGTH_SHORT).show();
                        if(onFinishClickListener != null){
                            onFinishClickListener.isUpdated(true);
                        }
                        praiseDialog.dismiss();
                    }
                }
            });


        }
        public Builder setCallBackListener(OnFinishClickListener onFinishClickListener1){//设置回调
            onFinishClickListener = onFinishClickListener1;
            return this;
        }
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public HaveSoluationedDialog build(Context context,String articleid1) {
            activity = (Activity)context;
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            praiseDialog = new HaveSoluationedDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            praiseDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.dialog_have_soluation, null);
            ButterKnife.bind(this,view);
            praiseDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = praiseDialog.getWindow().getAttributes();
//            XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//            XCCacheManager xcCacheManager = XCCacheManager.getInstance(mContext);
//
//
            articleid = articleid1;
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
