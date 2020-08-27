package com.et.secondworld.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/8/8
 **/
public class VXPayBean {

    /**
     * package : Sign=WXPay
     * appid : wxf0dc1844747169f6
     * extdata : {"user_id":"725072250"}
     * sign : 768E3A2F54B6D57432B9BC5B59040C62
     * partnerid : 1601624731
     * prepayid : wx08194715294652766c9408f82444c50000
     * noncestr : pUBbxFjyTiYhJgUO
     * timestamp : 1596887306
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String extdata;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    private String tradeno;

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno;
    }
    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getExtdata() {
        return extdata;
    }

    public void setExtdata(String extdata) {
        this.extdata = extdata;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
