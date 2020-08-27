package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/25
 **/
public class GetBrowsHistoryBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"nick":"12","head":"","accountid":null,"comments":6,"articleid":"A51B935745E2","time":1587808972000,"title":"zxcv","good":0,"modules":"M3"},{"nick":"","head":"","accountid":null,"comments":1,"articleid":"B34B9587-28D6-4452-AD68-EEDA7AC5705B","time":1587808962000,"title":"大文章小智慧","good":1,"modules":"M2"},{"nick":"","head":"","accountid":null,"comments":1,"articleid":"B34B9587-28D6-4452-AD68-EEDA7AC5705B","time":1587809903000,"title":"大文章小智慧","good":1,"modules":"M2"},{"nick":"","head":"","accountid":null,"comments":1,"articleid":"ED21E764-CF43-430E-8F8B-606F8B02CB87","time":1587809570000,"title":"测试","good":1,"modules":"M1"},{"nick":"","head":"","accountid":null,"comments":1,"articleid":"ED21E764-CF43-430E-8F8B-606F8B02CB87","time":1587809889000,"title":"测试","good":1,"modules":"M1"},{"nick":"4","head":"","accountid":null,"comments":1,"articleid":"812872C2-F710-450C-86C3-E667DBE569B6","time":1587809912000,"title":"小文章大智慧","good":1,"modules":"M2"},{"nick":null,"head":null,"accountid":null,"comments":1,"articleid":"EB036528-AB2F-46D4-9BA5-A88E3BE3DCF8","time":1587802107000,"title":"测试","good":0,"modules":"M1"},{"nick":null,"head":null,"accountid":null,"comments":1,"articleid":"EB036528-AB2F-46D4-9BA5-A88E3BE3DCF8","time":1587802507000,"title":"测试","good":0,"modules":"M1"},{"nick":null,"head":null,"accountid":null,"comments":1,"articleid":"EB036528-AB2F-46D4-9BA5-A88E3BE3DCF8","time":1587802516000,"title":"测试","good":0,"modules":"M1"},{"nick":null,"head":null,"accountid":null,"comments":1,"articleid":"EB036528-AB2F-46D4-9BA5-A88E3BE3DCF8","time":1587802523000,"title":"测试","good":0,"modules":"M1"}]
     */

    private String msg;
    private String issuccess;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public  class ListBean {
        /**
         * nick : 12
         * head :
         * accountid : null
         * comments : 6
         * articleid : A51B935745E2
         * time : 1587808972000
         * title : zxcv
         * good : 0
         * modules : M3
         */

        private String nick;
        private String head;
        private String accountid;
        private int comments;
        private String articleid;
        private String time;
        private String title;
        private int good;
        private int repost;
        private int readers;
        private String modules;
        public int getRepost() {
            return repost;
        }

        public void setRepost(int repost) {
            this.repost = repost;
        }

        public int getReaders() {
            return readers;
        }

        public void setReaders(int readers) {
            this.readers = readers;
        }
        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
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

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public String getModules() {
            return modules;
        }

        public void setModules(String modules) {
            this.modules = modules;
        }
    }
}
