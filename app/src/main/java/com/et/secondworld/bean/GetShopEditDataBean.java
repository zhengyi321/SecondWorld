package com.et.secondworld.bean;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/8
 **/
public class GetShopEditDataBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * shopname : 小洋人
     * logo : http://192.168.0.4/shop/shoplogo20200424145356725072250.jpg
     * tel : 187645545454
     * businesshour : null
     * addr : 浙江温州
     */

    private String msg;
    private String issuccess;
    private String shopname;
    private String logo;
    private String tel;
    private String businesshour;
    private String addr;
    private String trade;
    private String socialcode;
    private String dataverify;

    public String getDataverify() {
        return dataverify;
    }

    public void setDataverify(String dataverify) {
        this.dataverify = dataverify;
    }
    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }
    public String getSocialcode() {
        return socialcode;
    }

    public void setSocialcode(String socialcode) {
        this.socialcode = socialcode;
    }
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

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBusinesshour() {
        return businesshour;
    }

    public void setBusinesshour(String businesshour) {
        this.businesshour = businesshour;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
