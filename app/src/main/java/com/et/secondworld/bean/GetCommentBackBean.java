package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/22
 **/
public class GetCommentBackBean {

    /**
     * backlist : [{"nick":"测试","head":"http://portrait2.sinaimg.cn/2419104481/blog/180","accountid":"725072250","time":"2020-04-13 22:03:50","good":0,"content":"232","tonick":"33","toaccountid":"C95D0205"}]
     * msg : 获取数据成功
     * issuccess : 1
     */

    private String msg;
    private String issuccess;
    private List<BacklistBean> backlist;

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

    public List<BacklistBean> getBacklist() {
        return backlist;
    }

    public void setBacklist(List<BacklistBean> backlist) {
        this.backlist = backlist;
    }

    public  class BacklistBean {
        /**
         * nick : 测试
         * head : http://portrait2.sinaimg.cn/2419104481/blog/180
         * accountid : 725072250
         * time : 2020-04-13 22:03:50
         * good : 0
         * content : 232
         * tonick : 33
         * toaccountid : C95D0205
         */

        private String nick;
        private String head;
        private String accountid;
        private String time;
        private int good;
        private String content;
        private String tonick;
        private String toaccountid;
        private String commentbackid;
        private String rule;

        private int praisecount;
        private int ispraise;

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }
        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getAccountid() {
            return accountid;
        }

        public void setAccountid(String accountid) {
            this.accountid = accountid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }
        public int getIspraise() {
            return ispraise;
        }

        public void setIspraise(int ispraise) {
            this.ispraise = ispraise;
        }

        public int getPraisecount() {
            return praisecount;
        }

        public void setPraisecount(int praisecount) {
            this.praisecount = praisecount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTonick() {
            return tonick;
        }

        public void setTonick(String tonick) {
            this.tonick = tonick;
        }

        public String getToaccountid() {
            return toaccountid;
        }

        public void setToaccountid(String toaccountid) {
            this.toaccountid = toaccountid;
        }
        public String getCommentbackid() {
            return commentbackid;
        }

        public void setCommentbackid(String commentbackid) {
            this.commentbackid = commentbackid;
        }
    }
}
