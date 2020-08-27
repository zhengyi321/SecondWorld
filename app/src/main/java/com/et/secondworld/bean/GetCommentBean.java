package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/20
 **/
public class GetCommentBean {

    /**
     * msg : 获取数据成功
     * commentlist : [{"nick":"测试","head":"http://portrait2.sinaimg.cn/2419104481/blog/180","backsize":1,"bad":0,"time":"2020-04-21 02:30:19","good":0,"content":"测试"}]
     * issuccess : 1
     */

    private String msg;
    private String issuccess;
    private List<CommentlistBean> commentlist;

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

    public List<CommentlistBean> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<CommentlistBean> commentlist) {
        this.commentlist = commentlist;
    }

    public static class CommentlistBean {
        /**
         * nick : 测试
         * head : http://portrait2.sinaimg.cn/2419104481/blog/180
         * backsize : 1
         * bad : 0
         * time : 2020-04-21 02:30:19
         * good : 0
         * content : 测试
         */

        private String nick;
        private String head;
        private int backsize;
        private int bad;
        private String time;
        private int good;
        private String content;
        private String commentid;
        private String account;
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

        public int getBacksize() {
            return backsize;
        }

        public void setBacksize(int backsize) {
            this.backsize = backsize;
        }

        public int getBad() {
            return bad;
        }

        public void setBad(int bad) {
            this.bad = bad;
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

        public int getPraisecount() {
            return praisecount;
        }

        public void setPraisecount(int praisecount) {
            this.praisecount = praisecount;
        }
        public int getIspraise() {
            return ispraise;
        }

        public void setIspraise(int ispraise) {
            this.ispraise = ispraise;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCommentid() {
            return commentid;
        }

        public void setCommentid(String commentid) {
            this.commentid = commentid;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }
}
