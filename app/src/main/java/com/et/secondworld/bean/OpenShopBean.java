package com.et.secondworld.bean;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/24
 **/
public class OpenShopBean {
    /**
     * msg : 成功
     * issuccess : 1
     */

    private String msg;
    private String issuccess;
    private String shopid;

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
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
}