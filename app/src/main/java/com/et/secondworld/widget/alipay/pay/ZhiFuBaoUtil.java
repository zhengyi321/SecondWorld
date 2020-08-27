package com.et.secondworld.widget.alipay.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.et.secondworld.bean.GetPayBean;
import com.et.secondworld.network.PayNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;


/**
 * Created by zhyan on 2017/2/25.
 */

public class ZhiFuBaoUtil {
    private Activity activity;
    public ZhiFuBaoUtil(Activity activity){
        this.activity = activity;
    }
      /*支付*/

/*    private void zhiFuBaoInit(){
    }*/


    /** 支付宝支付业务：入参app_id */
//    public  final String APPID = "2017022205818471";
    public  final String APPID = "2021001169624757";

    /** 支付宝账户登录授权业务：入参pid值  签约合作者身份ID */
    public  final String PID = "2088831763942432";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public  final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    /*public  final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9t3skcCmB2xL0b3ZeC9p+/yvZC2RVlc+jbHayyh7BNX2kDN5PVd7CQo7sCMqUt4AUt6AzJO8OCEI7z1Qzzqq1Hld5oAW25j31aXth1S+AcqriquJ1iqXlEa8i+amkd/Ygh21JOEhK6y8+RZ/P2NqGPu5Lj94MeQovAA8ajzpC0l9nTXF/1aOEHI8QuQy0FRnNDi2G4F48pW+esrlbifJe1Qg9+Ot5Y+RbiAkNOJmFmevPZqFiPq/3V35J+23kskY7h+veTgVzSXE2Ef2o2XL3nfg6ODCo+FqKVC65j3UIqxqHD+zm+1kEbKCbjMTDeODIgEH2B/2Sa+jhe99dSQpfAgMBAAECggEAfmPXcS4cKPvXQ+tiE0OYXBUJlD6HqJD21Ow0o+YN2TAWtQ0xqe8OSaLn7o3o0GzzslmnK4uGi3WrEoeMlDsx9Rnysv+N7wyy6GVOop3RWKSL/hhUaHRnExXV9NoZT43v9jni7+3EE81dGwJtdp4E73zLaqG9D9gvlaJXbZ1P9iVjNMdLaNEhM6DJtD4uZhSYouPxEqcGh7IyDe4zFtyh/houAB+rIzjQyJFivNi6UYVensnUPRoB33OzAY2s8K9aTscRFqu2e5hPC1Nk15bgmULda08BjCuXHINDMCH8N/PNVq3mb+GKPn0FhGXHXmQkmFyV7bjLgMUd1pBMQaFG2QKBgQD5RDYm6vho5YtVVKYNNS71FD3qSFzYKk4aPqqLm9OfU6rEzoTtQ4oHxv3QliZswa/N1F5Le0xefB5A2Q/XM11iknwBC1pZsxLjOIqer76H7p36CEpifxgjKo1ldrWAmNRQbD/Ve+Nh+jaG+9XgVxzWKTF3NJqxz9q1SRAhDQ/tmwKBgQDC13TkkfbqjoNzV4qx1wH3e7hGoTYIU+akKQm8/YT8L3bQlojIbqrO5JXhiJyGkSbCz3KV1KnFaK+o1rXCmGwetZ2KVpLZMKZpebrNqV76lX64NACHIkOvvgucrIIPoajrMde8kqFfPMf9+cearcklm73mok/NS7ydutmGKpJEjQKBgApeQfpgb7C9S4xjFv0ZpzhefSzPkb0UFWeWD7X651SLQGErTfAojQdBybDxbeu8KrnfCYUOPeD4PLHp1XTYGBxPh4Trmfh9MF3vLiTnnn8896k8ywtJyhE8ZeFAjNAqizpFJmqU2qJqq/X1Z7ILLZHi6MHSmFSpFqnPvjIBvxXdAoGAJDC68J3AHE8+UVVIWMIUBnD/iHb3P1K+O2239lestN7oFdRwMbKEnYtN36dtU31wkqzPns7yfc11G0pOVofTRVz8ly5/dkIYFu1aakr0bl0IPZTDKFaP9fu50m8kdVzUsGMArHEb8kGq8BzOG4f+HbMw8b7iTcKslSuYrdgIdR0CgYEA8kvu0N7h+mDxlWsFXlxyhLnYfXQ8uQkEgMWTcyYj/BaHB5Oq/GrhSR2liSOVp/x9Ad6Bpxv9vyjcxsqxUPIrr8RVFKgwyMpFlDQDQqmx5vESSP0Jm+uJhGU95sfiYrdftsZrzdIMBkjUN5tim7eTWY+7xWs5RxFYjLwon79Gmx0=";*/
//    public  final String RSA2_PRIVATE = "MIIEpQIBAAKCAQEAxxYoZXFw1VDFkq7f69wbx+fXG8Hj8eLuLNFv4feAXQE2CMHY/DBf1wUSGMaAVKKvbo6zaUwNrDbJFPUZjlB/2o2ikhz3PfTiKOeOEconk/Jdf5XHODr2FSCSdUQhuw8Kr23KY6vuYSPxG26Pu1OUDaYF5KCHBX8v4SDvyqL/MoIi5xGTDf4jdLd/LBJs/nLr9hSKM22x1+8CTgvvKd2z/rvIHEHv1omQdZ/CoU8kZZ91PKpH7nbSiFkKh+BoRpNVCNjxVn8N2rqkR/cv0Fm11iycc9jGaKSmYjcR28R+9zzD4dpTVstQlyR6bf5XGhMMOY+tVqClvyncowY6Jd1i3wIDAQABAoIBAQCGOiA+VP+4Mt8iiJkckqiAXYWgh1dCTLg8q3B9r7tx+VEPEnNThOH5PMf4wEzhw1X4emnoQoitZbqpUJ9MXmX1Wq0TSkhAE40v+v7Ea8HHdA8p6DgLZ2Vm1TEpsyfy0K9pm+Jq1Q7Bm9QBpMdvi2g0040BmmGuiRQk+2nw+3iH+hjTtY7PQYUMxt2o7M/qGAKk1Vmu/UnkKulvZHVZTnXSUI9Mm9Z13zYy22siUWWKilsEwkC8yH2S6+2M4EY7mioTZ8vKLn9/eUnNrDVSXNZ5ADkdwJx1OrOH9zJi9a5+yk4YfmvMkc3PCjhbmON5yuycHzJd10GcRonfgkzrzlhRAoGBAO80FJGP4iZl9ZXVJDz7t9AllpgxRxpMoXyLALjWN/of7HOOEkFg0pbQxa1UYFA6w14ypBxSYdRKQhc4LUAHU9ijbu6zQwGGYeAU8HzSmF8vt69v42qmKV/+VcXcsvl71GTLuzYMc3Un+mfLfwOnDUMt29DoIR3XmoR7C3MWJvhTAoGBANUQ78IpsX+rO+OpuDJzSohw9DzoQkfcYvwROXb5XwZgmRGetAm2TgEy0pwBXli3QMZ6K8Y5x3W8KcntmiI1895YxeQsrrIQXuCMTo7wiHlvvLKIumx+J9dnASMOpy/rCTADi0H7Xav9mKSgDoovJDMrIPEb7b3bq6DX8c50GSnFAoGBAJ/IGSk2hGkD2G0yh9dYiWs3gIW+/PDbWU95Bo1xQskIhAiYUSdsxp7JbBIg94kAB7qndyoX+ZM12znpY4iOnHpsiVBXXB3rpvto4U9v2SpUFI3S3oVB4wdlO+RETSULi0+dKzuqS4FpqAGnU/ubnedO/bNpK53khCmUi4xfsr5TAoGBAL0BQS5j/eja5U2XiKZ4T22phX9iKSnF/qjMPsWjgQAPQLm4pszWw2ucwedR73eTWAq45Yv6RU3TlN5jv48JfUOITEOUv4OFsiRPUoDKLsZKsIFPeNA1OPKDlSacIQMI5CWLf0UQzo97bqTatwsttlcNszMvKz54V09fQEl8A7ZhAoGAcqPNUBDPTHGBU67KSLSfgF6PIX5Td7qLlVcFHm0dHcVok1KuPhXlMJPDBEuIBjWXqjx1qyj6KS+V3lpQEa5pdJumKvqA5jMgH+sU26gaMcDRgN5CCK2nDKiamGt/Adt1qFDn2+QcVRG7eVB12Ra3lvX4EOR7AMqsEno4pmoZ9HA=";
    public  final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJIgy2isKcBrf1MM7qiEPSmLqoepE0J4R5SCjxH/X3F9W9QKqQ0HalYsr8sPdCYEQDsC42tgkCYvuYBPmO3ig8Yx1Z37IJ6pzP1LoteOPZb1jn8PXcvDOSOlm8DLWUCRec03TMWkqsVal9Se1NX4W4rjrr6hH3sS5mBOn+fPCKzcSsXigOrB2iqiSHWP37LugDkxyFOeD/ogZx5oIFYNY1rWcYsbmTQ/DyvKwsF2G03RDNuKVjJtk4sNHeC3/kUyTAL6q/0YzQFZwXp3G9nRaQZB83WQ2IchseWRva3S1ixOULMGQh0P3rYEpVIpgmPrLqYbHK5mSFtp27OWPfKrLZAgMBAAECggEAaCh2o4fNvRjVTOTfpQAPE7xxulouRADn+/61KkBuYFm4POELLKdKFuBs8XWwuLfJsUt2LpQhT7DDDHpq2vsecI+P0Z246xNKPAmFx9gUqCiMzM8Wz6wbN6weUFkO6fvl9XYfNsA2C6Oc8s7d8XDmbpnXoyzMrOflKBP/bw6S3bnDeip1nUN/S//Z91p07v34dnBV1Lz5Z2i0kBV5OG9C7pGpm8gPnRpVi0pwN+l+X7yjhP2GpyB6e2wHaEzXXjUUTbMQpeVvbdygaRGBaue3K8WFSieHa2R0/6M28hU2ObQrDASpaBKb5hrx8VrxvX4BXlA1/WrsA3pT6gz+IYe1CQKBgQDKVgdbXZ9rzkUkBAefiFrMi5m+VgeZDtcsUJBhUEVXJhQMi/0bwJXcKlZhgZTJZdEGewKeXTb644tlWZnK2JZeoyhcJJdDj9rjFsC6r99nIi+ND75Rh2K22zsmE1mdkXok2XJo6Mkb/DrMDIAnXvoRB0zA53PvBQXWz4/1hQ5dGwKBgQCtgPFP4F5EdFdP3FaPzVE0U7aHvkQTEf50lgbzx7YyL8znkao4hhKW415ibzb5Jv1LZGMA29T5NaYah1KoD1hp+wxF83+1JBEi5WMzMxe5wotV2fwMnlQg9mprtUlyOJGAg8g38hHkAhk+KUzBWqR4RQOJOz2qY7luLyQ1zHezGwKBgDsPpZIb3/8d9LywmHEO4kI2tagDgLBCzrhWw+5D+hmYOd33tug07QmZKiHIK2AmYj9cBMWpO/U8GmJ5JxU455u0UIGlBKpqAWCsMZHM6I84QV0RrWIq/LuXfsp8d3oxDtBW0LiiyqFqnNcXBwl3BdOWp+bfKM4qTywG5mSqdCjlAoGBAJfECFDkY9DhK1aR163l3Lg22Z0XUxiTTs7QRLiM4tQ3d7a/dfQHYs9Vh0P3IFEER9/gqbEgmcR7pxXhdO3Irn/Vt0Cg4/6g09SAbitk78XvZE3uZUjsXY6PvTsF0n70+GE/5Kz7M6cMJuR238rp7J+/Tn6FsUUQAi1dUwE0HygtAoGANqaFRwxraU9GxZ9fIgi90oXGEQWwUBaS4k/g5/Cb8BHKAsTK75/QqhpAqUyveoZWsnYGwu/Q3HTwIGMZWZgB7qQBZsmSRxKH+Qpp8LStpVPXAsRTssZz/4Qv6Qyeg0gjtOM60hiFBX2tkrx/dup1BIe8HG8MbE1L3AshOC/IfAE=";
    public  final String RSA_PRIVATE = "";

    private  final int SDK_PAY_FLAG = 1;
    private  final int SDK_AUTH_FLAG = 2;
    private String orderInfo = "";
    // 签约卖家支付宝账号
    String SELLER = "2247613647@qq.com";
    /*支付宝*/
    public OnPaySuccessfulListener onPaySuccessfulListener;

    public interface OnPaySuccessfulListener{
        public void isSuccessful(boolean isSuccessful);
    }
    public void setOnPaySuccessfulListener(OnPaySuccessfulListener onPaySuccessfulListener1){
        onPaySuccessfulListener = onPaySuccessfulListener1;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                        if(onPaySuccessfulListener != null){
                            onPaySuccessfulListener.isSuccessful(true);
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                        if(onPaySuccessfulListener != null){
                            onPaySuccessfulListener.isSuccessful(false);
                        }
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
                        Toast.makeText(activity,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(activity,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };


    /*支付宝支付*/
    public void zhiFuBaoPay(String money,String title,String content,boolean isShop){
//        String outTradeNo = getOutTradeNo();
        /*Toast.makeText(activity, " onCompleted mPopView:"+goodsName+price, Toast.LENGTH_LONG).show();*/


        /*String passback_params = "{ \"dingdanid\" = \""+price"+\",\n \"lx\" = \"1\"\n}";*/
        XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
        XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
        String accountid = xcCacheManager.readCache(xcCacheSaveName.account);
        if(isShop){
            accountid = xcCacheManager.readCache(xcCacheSaveName.shopId);
        }
        PayNetWork payNetWork = new PayNetWork();
        Map<String,Object> map = new HashMap<>();
        map.put("money",money);
        map.put("title",title);
        map.put("content",content);
        map.put("accountid",accountid);
        map.put("platform","android");
        payNetWork.getZhiFuBaoPayDataFromNet(map, new Observer<GetPayBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GetPayBean getPayBean) {
                orderInfo = getPayBean.getData();
                if((orderInfo != null)&&(!orderInfo.isEmpty())) {
                    xcCacheManager.writeCache(xcCacheSaveName.tradeorder,getPayBean.getTradeorder());
                    payV3(null, orderInfo);

                }
            }
        });

//        String passback_params = "{\"dingdanid\":\""+baojiaId+"\",\"lx\":\"1\"}";
//        try {
//            passback_params = URLEncoder.encode(passback_params, "utf8");
        /*Toast.makeText(activity,passback_params,Toast.LENGTH_LONG).show();*/

        /*zhiFuBaoUtil.payV2(null, "定金支付", "0.1",outTradeNo,passback_params);*/
//            zhiFuBaoUtil.payV3(null);

//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        zhiFuBaoUtil.setOnPaySuccessfulListener(new ZhiFuBaoUtil.OnPaySuccessfulListener() {
//            @Override
//            public void isSuccessful(boolean isSuccessful) {
//
//                XCCacheManager xcCacheManager = XCCacheManager.getInstance(getBaseContext());
//                XCCacheSaveName xcCacheSaveName = new XCCacheSaveName();
//                xcCacheManager.writeCache(xcCacheSaveName.payStatus,"1");
//
////                activity.finish();
//            }
//        });
        /*去支付金钱*/
        /*Toast.makeText(activity," 我成功啦111 isSuccessful:"+helpMeBuyBean.getOrderNo(),Toast.LENGTH_LONG).show();*/


    }



    /**
     * 支付宝支付业务
     *  不走服务器
     * @param v
     */
    public void payV2(View v,String goodsName,String price,String outTradeNo,String passback_params) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            activity.finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        /*price = "0.01";*/
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,goodsName,price,outTradeNo,passback_params);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        Log.d("sign11",orderParam);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        System.out.print("\norderInfo:"+orderInfo);
        /*final String orderInfo = getOrderInfo("走兔商品名称","走兔商品详情","0.01");*/
        Runnable payRunnable = new Runnable() {

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

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付业务
     *  走服务器
     * @param v
     */
//    public void payV3(View v) {
    public void payV3(View v,final String orderInfo) {
       /* if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            activity.finish();
                        }
                    }).show();
            return;
        }

        *//**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
       /* boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;*/
       /* boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        *//*String price = "0.01";*//*
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        System.out.print("\norderInfo:"+orderInfo);*/
        /*final String orderInfo = getOrderInfo("走兔商品名称","走兔商品详情","0.01");*/
        Runnable payRunnable = new Runnable() {

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

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }









/*
    *//**
     * 支付宝账户授权业务
     *
     * @param v
     *//*
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        *//**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         *//*
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(activity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }*/

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(activity);
        String version = payTask.getVersion();
        Toast.makeText(activity, version, Toast.LENGTH_SHORT).show();
    }
/*
*//**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v*//*

    public void h5Pay(View v) {
        Intent intent = new Intent(activity, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
   *//*     *
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现*//*

        String url = "http://www.gotogoto.com";
        // url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        activity.startActivity(intent);
    }*/
}
