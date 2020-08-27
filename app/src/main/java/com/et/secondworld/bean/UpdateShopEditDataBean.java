package com.et.secondworld.bean;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/8
 **/
public class UpdateShopEditDataBean {

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

    private String logo;
    private String bg;

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



    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }


}
