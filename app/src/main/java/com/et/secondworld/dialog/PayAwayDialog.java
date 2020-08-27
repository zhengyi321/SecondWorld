package com.et.secondworld.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alipay.sdk.app.PayTask;
import com.et.secondworld.R;
import com.et.secondworld.utils.WindowUtils;
import com.et.secondworld.widget.alipay.pay.AuthResult;
import com.et.secondworld.widget.alipay.pay.OrderInfoUtil2_0;
import com.et.secondworld.widget.alipay.pay.PayResult;
import com.et.secondworld.widget.alipay.pay.ZhiFuBaoUtil;
import com.et.secondworld.widget.wxpay.VXPayUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/28
 **/
public class PayAwayDialog extends Dialog {
    private View mPopView;
    Context context;
    private Activity activity;
    public OnPaySuccessfulListener onPaySuccessfulListener;
    public interface OnPaySuccessfulListener{
        public void isSuccessful(boolean isSuccessful);
    }

//    /**
//     * 用于支付宝支付业务的入参 app_id。
//     */
//    public static final String APPID = "2021001169624757";
//    /**
//     * 用于支付宝账户登录授权业务的入参 pid。
//     */
//    public static final String PID = "2088831763942432";
//    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJIgy2isKcBrf1MM7qiEPSmLqoepE0J4R5SCjxH/X3F9W9QKqQ0HalYsr8sPdCYEQDsC42tgkCYvuYBPmO3ig8Yx1Z37IJ6pzP1LoteOPZb1jn8PXcvDOSOlm8DLWUCRec03TMWkqsVal9Se1NX4W4rjrr6hH3sS5mBOn+fPCKzcSsXigOrB2iqiSHWP37LugDkxyFOeD/ogZx5oIFYNY1rWcYsbmTQ/DyvKwsF2G03RDNuKVjJtk4sNHeC3/kUyTAL6q/0YzQFZwXp3G9nRaQZB83WQ2IchseWRva3S1ixOULMGQh0P3rYEpVIpgmPrLqYbHK5mSFtp27OWPfKrLZAgMBAAECggEAaCh2o4fNvRjVTOTfpQAPE7xxulouRADn+/61KkBuYFm4POELLKdKFuBs8XWwuLfJsUt2LpQhT7DDDHpq2vsecI+P0Z246xNKPAmFx9gUqCiMzM8Wz6wbN6weUFkO6fvl9XYfNsA2C6Oc8s7d8XDmbpnXoyzMrOflKBP/bw6S3bnDeip1nUN/S//Z91p07v34dnBV1Lz5Z2i0kBV5OG9C7pGpm8gPnRpVi0pwN+l+X7yjhP2GpyB6e2wHaEzXXjUUTbMQpeVvbdygaRGBaue3K8WFSieHa2R0/6M28hU2ObQrDASpaBKb5hrx8VrxvX4BXlA1/WrsA3pT6gz+IYe1CQKBgQDKVgdbXZ9rzkUkBAefiFrMi5m+VgeZDtcsUJBhUEVXJhQMi/0bwJXcKlZhgZTJZdEGewKeXTb644tlWZnK2JZeoyhcJJdDj9rjFsC6r99nIi+ND75Rh2K22zsmE1mdkXok2XJo6Mkb/DrMDIAnXvoRB0zA53PvBQXWz4/1hQ5dGwKBgQCtgPFP4F5EdFdP3FaPzVE0U7aHvkQTEf50lgbzx7YyL8znkao4hhKW415ibzb5Jv1LZGMA29T5NaYah1KoD1hp+wxF83+1JBEi5WMzMxe5wotV2fwMnlQg9mprtUlyOJGAg8g38hHkAhk+KUzBWqR4RQOJOz2qY7luLyQ1zHezGwKBgDsPpZIb3/8d9LywmHEO4kI2tagDgLBCzrhWw+5D+hmYOd33tug07QmZKiHIK2AmYj9cBMWpO/U8GmJ5JxU455u0UIGlBKpqAWCsMZHM6I84QV0RrWIq/LuXfsp8d3oxDtBW0LiiyqFqnNcXBwl3BdOWp+bfKM4qTywG5mSqdCjlAoGBAJfECFDkY9DhK1aR163l3Lg22Z0XUxiTTs7QRLiM4tQ3d7a/dfQHYs9Vh0P3IFEER9/gqbEgmcR7pxXhdO3Irn/Vt0Cg4/6g09SAbitk78XvZE3uZUjsXY6PvTsF0n70+GE/5Kz7M6cMJuR238rp7J+/Tn6FsUUQAi1dUwE0HygtAoGANqaFRwxraU9GxZ9fIgi90oXGEQWwUBaS4k/g5/Cb8BHKAsTK75/QqhpAqUyveoZWsnYGwu/Q3HTwIGMZWZgB7qQBZsmSRxKH+Qpp8LStpVPXAsRTssZz/4Qv6Qyeg0gjtOM60hiFBX2tkrx/dup1BIe8HG8MbE1L3AshOC/IfAE=";
//    public static final String RSA_PRIVATE = "";

    /*private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @RequiresApi(api = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    *//**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     *//*
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(getContext(), "支付成功:" + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(getContext(), "支付失败:" + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(getContext(), "授权成功:" + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(getContext(), "授权失败:" + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };*/
  /*  @RequiresApi(api = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }
    @RequiresApi(api = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }*/
    public PayAwayDialog(Context context1) {
        super(context1);
        context = context1;
//        init(context);
//        setPopupWindow();
    }
    public PayAwayDialog(Context context1, int themeResId) {
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

        @BindView(R.id.rly_dialog_pay_away_wx)
        RelativeLayout rlyDialogPayAwayWX;
        @OnClick(R.id.rly_dialog_pay_away_wx)
        public void rlyDialogPayAwayWXOnclick(){
            cbDialogPayAwayWX.setChecked(true);
        }

        @BindView(R.id.rly_dialog_pay_away_zfb)
        RelativeLayout rlyDialogPayAwayZFB;
        @OnClick(R.id.rly_dialog_pay_away_zfb)
        public void rlyDialogPayAwayZFBOnclick(){
            cbDialogPayAwayZFB.setChecked(true);
//            payV2(view);
        }
        @BindView(R.id.tv_dialog_pay_away_gold)
        TextView tvDialogPayAwayGold;
        @BindView(R.id.cb_dialog_pay_away_wx)
        CheckBox cbDialogPayAwayWX;
        @BindView(R.id.cb_dialog_pay_away_zfb)
        CheckBox cbDialogPayAwayZFB;
        @OnClick(R.id.rly_dialog_pay_away_submit)
        public void rlyDialogPayAwaySUbmitOnclick(){
            if(cbDialogPayAwayZFB.isChecked()){
                zhiFuBaoUtil.zhiFuBaoPay(cost,title,content,isShop);
                zhiFuBaoUtil.setOnPaySuccessfulListener(new ZhiFuBaoUtil.OnPaySuccessfulListener() {
                    @Override
                    public void isSuccessful(boolean isSuccessful) {
                        if(isSuccessful){
                            if(onPaySuccessfulListener != null){
                                onPaySuccessfulListener.isSuccessful(true);
                                payAwayDialog.dismiss();
                            }
                        }else {
                            if(onPaySuccessfulListener != null){
                                onPaySuccessfulListener.isSuccessful(false);
                                payAwayDialog.dismiss();
                            }
                        }
                    }
                });
            }
            if(cbDialogPayAwayWX.isChecked()){
                Double money = Double.parseDouble(cost);
                money = money*100;

                vxPayUtil.vxPay(money+"",title,content,isShop);
                vxPayUtil.setOnPaySuccessfulListener(new VXPayUtil.OnPaySuccessfulListener() {
                    @Override
                    public void isSuccessful(boolean isSuccessful) {
                        if(isSuccessful){
                            if(onPaySuccessfulListener != null){
                                onPaySuccessfulListener.isSuccessful(true);
                                payAwayDialog.dismiss();
                            }
                        }else {
                            if(onPaySuccessfulListener != null){
                                onPaySuccessfulListener.isSuccessful(false);
                                payAwayDialog.dismiss();
                            }
                        }
                    }
                });
            }
        }
         PayAwayDialog payAwayDialog;
        public Builder setOnPaySuccessfulListener(OnPaySuccessfulListener onPaySuccessfulListener1){
            onPaySuccessfulListener = onPaySuccessfulListener1;
            return this;
        }
       /* *//**
         * 支付宝支付业务示例
         *//*
        @RequiresApi(api = android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void payV2(View v) {
            if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
                showAlert(getContext(), "错误: 需要配置 PayDemoActivity 中的 APPID 和 RSA_PRIVATE");
                return;
            }

            *//*
             * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
             * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
             * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
             *
             * orderInfo 的获取必须来自服务端；
             *//*
            boolean rsa2 = (RSA2_PRIVATE.length() > 0);
            Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
            String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

            String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
            String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
            final String orderInfo = orderParam + "&" + sign;
            Log.d("sign11",sign);

            final Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(activity);
                    Map<String, String> result = alipay.payV2(orderInfo, true);
                    Log.i("msp", result.toString());

                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }*/
        /**
         * 1,加载要显示的布局
         * 2，通过dialog的addContentView将布局添加到window中
         * 3，基本逻辑处理
         * 4，显示dialog的布局
         */
        private ZhiFuBaoUtil zhiFuBaoUtil;
        private VXPayUtil vxPayUtil;
        View view = null;
        Activity activity = null;
        private String cost = "";
        private String title = "";
        private String content = "";
        private boolean isShop = false;
        public PayAwayDialog build(Context context,Activity activity,String cost,String title,String content,boolean isShop) {
//            activity = (Activity) context;
            this.activity = activity;
//            cost = "0.01";
            this.cost = cost;

            this.title = title;
            this.content = content;
            this.isShop = isShop;
            zhiFuBaoUtil = new ZhiFuBaoUtil(activity);
            vxPayUtil = new VXPayUtil(activity);
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            payAwayDialog = new PayAwayDialog(context, R.style.MyDialogStyle);//默认调用带style的构造
            payAwayDialog.setCanceledOnTouchOutside(true);//默认点击布局外不能取消dialog
             view = mInflater.inflate(R.layout.dialog_pay_away, null);
            ButterKnife.bind(this,view);
            payAwayDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager.LayoutParams layoutParams = payAwayDialog.getWindow().getAttributes();
//
            WindowUtils windowUtils = new WindowUtils(activity);
            tvDialogPayAwayGold.setText("¥ "+cost);
            layoutParams.width = (int)windowUtils.getWindowWidth()-40;
//            layoutParams.height = (int)windowUtils.getWindowHeight();
//            layoutParams.gravity = Gravity.BOTTOM;
//            Log.d("width",""+windowUtils.getWindowWidth());
            payAwayDialog.getWindow().setAttributes(layoutParams);


            payAwayDialog.setContentView(view);
            initCheckBox();

            return payAwayDialog;
        }


        private void initCheckBox(){
            cbDialogPayAwayWX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        cbDialogPayAwayZFB.setChecked(false);
                    }else {
                        cbDialogPayAwayZFB.setChecked(true);
                    }
                }
            });

            cbDialogPayAwayZFB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        cbDialogPayAwayWX.setChecked(false);
                    }else {
                        cbDialogPayAwayWX.setChecked(true);
                    }
                }
            });
        }
    }



/*

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_select_by_province, null);
        ButterKnife.bind(this,mPopView);

    }
    private void setPopupWindow() {

        this.setContentView(mPopView);// 设置View
    }
*/

}
