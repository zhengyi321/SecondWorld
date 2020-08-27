package com.et.secondworld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.et.secondworld.R;
import com.et.secondworld.utils.WindowUtils;

import butterknife.ButterKnife;

public class MineGetGoodDialog extends Dialog {
    Context context;

    public interface DialogCallBackListener{//通过该接口回调Dialog需要传递的值
        public void callBack(String tel);//具体方法
    }
    public MineGetGoodDialog(Context context1) {
        super(context1);
        this.context = context1;

    }
    public MineGetGoodDialog(Context context1, int themeResId) {
        super(context1, themeResId);
        this.context = context1;
    }

    protected MineGetGoodDialog(Context context1, boolean cancelable, OnCancelListener cancelListener) {
        super(context1, cancelable, cancelListener);
        this.context = context1;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public Builder Build = new Builder(context);
    //用Builder模式来构造Dialog
    public  class Builder {
        private Context mContext;
        public Builder(Context mContext1) {
            this.mContext = mContext1;
        }
      /*  private Context mContext;
        private View contentView;
        private String title;
        private String message;
        private String positiveText;
        private String negativeText;
        private OnClickListener positiviOnclickListener;
        private OnClickListener negativeOnclickListener;
        private DialogCallBackListener mDialogCallBackListener;
        public Builder(Context mContext) {
            this.mContext = mContext;
        }*/
/*
        public Builder setContentView(View contentView) {//设置dialog的主view
            this.contentView = contentView;
            return this;
        }

        public Builder setTitle(String title) {//设置dialog的标题
            this.title = title;
            return this;
        }

        public Builder setMessage(String msg) {//设置dialig的内容
            this.message = msg;
            return this;
        }

        public Builder setPositiveButton(String text, OnClickListener positiviOnclickListener) {//dialog的确认按钮
            this.positiveText = text;
            this.positiviOnclickListener = positiviOnclickListener;
            return this;
        }

        public Builder setNegativeButton(String text, OnClickListener negativeOnclickListener) {//dialog的取消按钮
            this.negativeText = text;
            this.negativeOnclickListener = negativeOnclickListener;
            return this;
        }

        public Builder setCallBackListener(DialogCallBackListener mDialogCallBackListener){//设置回调
            this.mDialogCallBackListener = mDialogCallBackListener;
            return this;
        }*/
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        View view;
         MineGetGoodDialog praiseDialog;
        public MineGetGoodDialog build(Activity activity) {



            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            praiseDialog = new MineGetGoodDialog(activity, R.style.MyDialogStyle);//默认调用带style的构造
            praiseDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.dialog_mine_get_good, null);
            ButterKnife.bind(this,view);
            praiseDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = praiseDialog.getWindow().getAttributes();

            WindowUtils windowUtils = new WindowUtils(activity);
            layoutParams.width = (int)windowUtils.getWindowWidth();
            layoutParams.height = (int)windowUtils.getWindowHeight()-82;
//            layoutParams.height = (int)windowUtils.getWindowHeight();
//            layoutParams.gravity = Gravity.BOTTOM;
            Log.d("width",""+windowUtils.getWindowWidth());
            praiseDialog.getWindow().setAttributes(layoutParams);


            praiseDialog.setContentView(view);




















            /*
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             companyCustomTelDialog = new MineGetGoodDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
//            companyCustomTelDialog.setCanceledOnTouchOutside(false);//默认点击布局外不能取消dialog
            view = mInflater.inflate(R.layout.dialog_mine_get_good, null);
            ButterKnife.bind(this,view);
      *//*      TextView marquee1 = view.findViewById(R.id.tv_marquee1);
            TextView marquee2 = view.findViewById(R.id.tv_marquee2);
            //跑马灯效果必须加
            marquee1.setSelected(true);
            marquee2.setSelected(true);*//*
            WindowUtils windowUtils = new WindowUtils(activity);
//            companyCustomTelDialog.addContentView(view, new ViewGroup.LayoutParams(600, ViewGroup.LayoutParams.MATCH_PARENT));

            companyCustomTelDialog.setContentView(view);
            companyCustomTelDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            WindowManager.LayoutParams layoutParams = companyCustomTelDialog.getWindow().getAttributes();
            layoutParams.width = (int)windowUtils.getWindowWidth();
            layoutParams.height = (int)windowUtils.getWindowHeight()-82;
//            layoutParams.gravity = Gravity.CENTER;
            Log.d("width",""+windowUtils.getWindowWidth());
            companyCustomTelDialog.getWindow().setAttributes(layoutParams);*/

            return praiseDialog;
        }
//        @BindView(R.id.rly_dialog_getgood)
//        public RelativeLayout rlyDialogGetGood;
//        @OnClick(R.id.rly_dialog_getgood)
//        public void rlyDialogGetGoodOnclick(){
////            negativeOnclickListener.onClick(companyCustomTelDialog, BUTTON_NEGATIVE);
//            companyCustomTelDialog.dismiss();
//        }

    }
}

