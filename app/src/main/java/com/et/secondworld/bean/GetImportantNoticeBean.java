package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/5
 **/
public class GetImportantNoticeBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"articleid":"242342","articleaccount":"C95D0205","time":"2020-05-20   18:50:32","title":"新年演唱会"}]
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

    public static class ListBean {
        /**
         * articleid : 242342
         * articleaccount : C95D0205
         * time : 2020-05-20   18:50:32
         * title : 新年演唱会
         */

        private String articleid;
        private String articleaccount;
        private String time;
        private String title;

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
    }
}
