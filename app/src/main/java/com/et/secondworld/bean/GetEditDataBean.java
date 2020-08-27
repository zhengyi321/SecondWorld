package com.et.secondworld.bean;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/16
 **/
public class GetEditDataBean {

    /**
     * head : http://portrait2.sinaimg.cn/2419104481/blog/180
     * nick : 关木子
     * msg : 获取信息成功
     * personnalnote : 一个不服输的人
     * trade : IT行业
     * locate : 山东潍坊
     * issuccess : 1
     * bg : null
     * sex : 女
     * birth : 1999-02-05
     */

    private String head;
    private String nick;

    private String trade;
    private String locate;
    private String msg;
    private String personnalnote;
    private String issuccess;
    private String bg;
    private String sex;
    private String birth;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPersonnalnote() {
        return personnalnote;
    }

    public void setPersonnalnote(String personnalnote) {
        this.personnalnote = personnalnote;
    }
    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }



    public Object getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
