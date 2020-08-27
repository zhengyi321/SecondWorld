package com.et.secondworld.bean;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/15
 **/
public class SaveEditDataBean {
    /**
     * msg : 注册成功
     * issuccess : 1
     */

    private String msg;
    private String issuccess;
    private String id;
    private String head;
    private String bg;
    private String nick;
    private String personnalnote;
    private String birth;
    private String locate;
    private String trade;
    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }
    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }
    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getPersonnalnote() {
        return personnalnote;
    }

    public void setPersonnalnote(String personnalnote) {
        this.personnalnote = personnalnote;
    }
    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        /*
        {"msg":"注册成功","issuccess":"1"}
         */


}
