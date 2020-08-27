package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/3
 **/
public class GetCommentAndAt {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"head":"http://192.168.0.4/head/head2020042410013618767775244.jpg","nick":"12","accountid":"C95D02053","articlecontent":"内容够酷炫吧","articlemodules":"M4","commentcontent":"厕所","articleid":"7B2F8180-6A5C-4CA4-97F4-6C2957028E41","articleaccount":"725072250","type":"comment","commentandattime":"04-28   09:39","title":"怎么样","imgone":"http://192.168.0.4/article/articleimg020200428093407725072250.jpg"},{"head":"http://192.168.0.4/head/head2020042410013618767775244.jpg","nick":"66","accountid":"776B3AA3","articlecontent":"@12 @66","articlemodules":"M4","articleid":"0F423415-0B6C-437F-9456-848D4892600B","articleaccount":"725072250","type":"article","commentandattime":"04-28   09:39","title":"测试测试你好你好","imgone":"http://192.168.0.4/article/articleimg020200428093924725072250.jpg"},{"commentAccount":"725072250","articlecontent":"内容够酷炫吧","articleid":"5E6ACE91-7446-46CE-92B8-A51B935745E2","type":"commentback","title":"怎么样","good":8,"head":"http://192.168.0.4/head/head2020042410013618767775244.jpg","nick":"66","accountid":"776B3AA3","minecontent":"232","commentid":"5E6ACE91","time":"2020-04-21   02:30:19","commentandattime":"05-04   22:49","ispraised":1,"backcontent":"[害羞][汗]好不好","imgone":"http://192.168.0.4/article/articleimg020200428093407725072250.jpg"}]
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
         * head : http://192.168.0.4/head/head2020042410013618767775244.jpg
         * nick : 12
         * accountid : C95D02053
         * articlecontent : 内容够酷炫吧
         * articlemodules : M4
         * commentcontent : 厕所
         * articleid : 7B2F8180-6A5C-4CA4-97F4-6C2957028E41
         * articleaccount : 725072250
         * type : comment
         * commentandattime : 04-28   09:39
         * title : 怎么样
         * imgone : http://192.168.0.4/article/articleimg020200428093407725072250.jpg
         * commentAccount : 725072250
         * good : 8
         * minecontent : 232
         * commentid : 5E6ACE91
         * time : 2020-04-21   02:30:19
         * ispraised : 1
         * backcontent : [害羞][汗]好不好
         */

        private String head;
        private String nick;
        private String accountid;
        private String articlecontent;
        private String articlemodules;
        private String commentcontent;
        private String articleid;
        private String articleaccount;
        private String type;
        private String commentandattime;
        private String title;
        private String imgone;
        private String commentAccount;
        private int good;
        private String minecontent;
        private String commentid;
        private String time;
        private int ispraised;
        private int articlestatus;
        private String backcontent;
        private String commentnick;
        private String commentlogo;
        private int commentstatus;
        private int backstatus;

        public int getBackstatus() {
            return backstatus;
        }

        public void setBackstatus(int backstatus) {
            this.backstatus = backstatus;
        }

        public int getCommentstatus() {
            return commentstatus;
        }

        public void setCommentstatus(int commentstatus) {
            this.commentstatus = commentstatus;
        }
        public String getCommentnick() {
            return commentnick;
        }

        public void setCommentnick(String commentnick) {
            this.commentnick = commentnick;
        }
        public String getCommentlogo() {
            return commentlogo;
        }

        public void setCommentlogo(String commentlogo) {
            this.commentlogo = commentlogo;
        }
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

        public String getAccountid() {
            return accountid;
        }

        public void setAccountid(String accountid) {
            this.accountid = accountid;
        }

        public String getArticlecontent() {
            return articlecontent;
        }

        public void setArticlecontent(String articlecontent) {
            this.articlecontent = articlecontent;
        }

        public String getArticlemodules() {
            return articlemodules;
        }

        public void setArticlemodules(String articlemodules) {
            this.articlemodules = articlemodules;
        }

        public String getCommentcontent() {
            return commentcontent;
        }

        public void setCommentcontent(String commentcontent) {
            this.commentcontent = commentcontent;
        }

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
        }

        public String getArticleaccount() {
            return articleaccount;
        }

        public void setArticleaccount(String articleaccount) {
            this.articleaccount = articleaccount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCommentandattime() {
            return commentandattime;
        }

        public void setCommentandattime(String commentandattime) {
            this.commentandattime = commentandattime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgone() {
            return imgone;
        }

        public void setImgone(String imgone) {
            this.imgone = imgone;
        }

        public String getCommentAccount() {
            return commentAccount;
        }

        public void setCommentAccount(String commentAccount) {
            this.commentAccount = commentAccount;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public String getMinecontent() {
            return minecontent;
        }

        public void setMinecontent(String minecontent) {
            this.minecontent = minecontent;
        }

        public String getCommentid() {
            return commentid;
        }

        public void setCommentid(String commentid) {
            this.commentid = commentid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getIspraised() {
            return ispraised;
        }

        public void setIspraised(int ispraised) {
            this.ispraised = ispraised;
        }
        public int getArticlestatus() {
            return articlestatus;
        }

        public void setArticlestatus(int articlestatus) {
            this.articlestatus = articlestatus;
        }

        public String getBackcontent() {
            return backcontent;
        }

        public void setBackcontent(String backcontent) {
            this.backcontent = backcontent;
        }
    }
}
