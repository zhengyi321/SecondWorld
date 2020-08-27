package com.et.secondworld.widget.wxpay;

import android.app.Activity;
import android.util.Log;

import com.et.secondworld.bean.GetPayBean;
import com.et.secondworld.bean.VXPayBean;
import com.et.secondworld.network.PayNetWork;
import com.et.secondworld.widget.db.XCCacheManager.XCCacheManager;
import com.et.secondworld.widget.db.XCCacheSaveName.XCCacheSaveName;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/8/8
 **/
public class VXPayUtil {
    private Activity activity;
    public VXPayUtil(Activity activity){
        this.activity = activity;
    }
    private String TAG = "MainActivity";

    public OnPaySuccessfulListener onPaySuccessfulListener;

    public interface OnPaySuccessfulListener{
        public void isSuccessful(boolean isSuccessful);
    }
    public void setOnPaySuccessfulListener(OnPaySuccessfulListener onPaySuccessfulListener1){
        onPaySuccessfulListener = onPaySuccessfulListener1;
    }
    /*支付宝支付*/
    public void vxPay(String money,String title,String content,boolean isShop){
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
        money = rvZeroAndDot(money);
        map.put("money",money);
        Log.d("zzz2121",money+"");
        map.put("title",title);
        map.put("content",content);
        map.put("accountid",accountid);
        map.put("platform","android");
        payNetWork.getVXPayDataFromNet(map, new Observer<VXPayBean>() {
            @Override
            public void onCompleted() {
                Log.d("vxpay11","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("vxpay11","vxpay"+e);
        }

            @Override
            public void onNext(VXPayBean getPayBean) {
                WXPay.init(activity,Constants.APP_ID);
                xcCacheManager.writeCache(xcCacheSaveName.tradeorder,getPayBean.getTradeno());
                WXPay.getInstance().doPay(getPayBean, new WXPay.WXPayResultCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "支付成功");
                        Log.d("hhhh111","success");
                        if(onPaySuccessfulListener != null){
                            onPaySuccessfulListener.isSuccessful(true);
                        }
                    }

                    @Override
                    public void onError(int error_code) {
                        Log.d(TAG, "支付失败" + error_code);
                        if(onPaySuccessfulListener != null){
                            onPaySuccessfulListener.isSuccessful(false);
                        }
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "支付取消");
                        Log.d("hhhh111","cancel");
                        if(onPaySuccessfulListener != null){
                            onPaySuccessfulListener.isSuccessful(false);
                        }
                    }
                });
//                WXPay wxpay = new WXPay(activity, "wxf0dc1844747169f6");
//                Log.d("zzz2121",getPayBean.getAppid()+"");
//                WXPay.init(activity,Constants.APP_ID);
//                wxpay.doPay();
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

    public  String rvZeroAndDot(String s){
        if (s.isEmpty()) {
            return null;
        }
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
