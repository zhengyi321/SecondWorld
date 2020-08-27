package com.et.secondworld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.et.secondworld.R;
import com.et.secondworld.utils.WindowUtils;

public class MainDialog extends Dialog {
    Context context;

    public interface DialogCallBackListener{//通过该接口回调Dialog需要传递的值
        public void callBack(String tel);//具体方法
    }
    public MainDialog(Context context1) {
        super(context1);
        this.context = context1;

    }
    public MainDialog(Context context1, int themeResId) {
        super(context1, themeResId);
        this.context = context1;
    }

    protected MainDialog(Context context1, boolean cancelable, OnCancelListener cancelListener) {
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
        }

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
        }
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        public MainDialog build(Context context, Activity activity) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final MainDialog companyCustomTelDialog = new MainDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            companyCustomTelDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
            View view = mInflater.inflate(R.layout.dialog_main_index_bbs, null);
            TextView marquee1 = view.findViewById(R.id.tv_marquee1);
            TextView marquee2 = view.findViewById(R.id.tv_marquee2);
            //跑马灯效果必须加
            marquee1.setSelected(true);
            marquee2.setSelected(true);
            WindowUtils windowUtils = new WindowUtils(activity);
//            companyCustomTelDialog.addContentView(view, new ViewGroup.LayoutParams(600, ViewGroup.LayoutParams.MATCH_PARENT));

            companyCustomTelDialog.setContentView(view);
            WindowManager.LayoutParams layoutParams = companyCustomTelDialog.getWindow().getAttributes();
            layoutParams.width = (int)windowUtils.getWindowWidth();
//            layoutParams.height = (int)windowUtils.getWindowHeight();
            layoutParams.gravity = Gravity.CENTER;
            Log.d("width",""+windowUtils.getWindowWidth());
            companyCustomTelDialog.getWindow().setAttributes(layoutParams);

            return companyCustomTelDialog;
        }

    }
}

