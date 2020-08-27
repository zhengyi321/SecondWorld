package com.et.secondworld.bean;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/22
 **/
public class GetArticleidLocBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * lon : 120.856383
     * addr : 浙江省温州市乐清市中港路与兴港路交叉口西100米
     * lat : 28.024568
     */

    private String msg;
    private String issuccess;
    private String lon;
    private String addr;
    private String lat;

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

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
