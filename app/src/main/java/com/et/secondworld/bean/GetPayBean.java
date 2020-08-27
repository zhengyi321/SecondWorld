package com.et.secondworld.bean;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/7/17
 **/
public class GetPayBean {


    /**
     * msg : 获取消息成功
     * issuccess : 1
     * data : alipay_sdk=alipay-sdk-java-3.1.0&biz_content=%7B%22body%22%3A%22JAVA%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A%220717151818-5352%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2230%22%7D&charset=UTF-8&method=alipay.trade.app.pay&notify_url=XXX%2FgetAlipayNotifyInfo&timestamp=2020-07-17+15%3A18%3A18&version=1.0
     */

    private String msg;
    private String issuccess;
    private String data;
    private String tradeorder;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTradeorder() {
        return tradeorder;
    }

    public void setTradeorder(String tradeorder) {
        this.tradeorder = tradeorder;
    }
}
