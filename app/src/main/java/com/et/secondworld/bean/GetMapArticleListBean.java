package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/22
 **/
public class GetMapArticleListBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"accountid":"725072250","articleid":"B682F250-30B4-4534-BE6B-A5E68A019547","lon":"120.880169","title":"呜呜呜呜","lat":"28.050846"}]
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
         * accountid : 725072250
         * articleid : B682F250-30B4-4534-BE6B-A5E68A019547
         * lon : 120.880169
         * title : 呜呜呜呜
         * lat : 28.050846
         */

        private String accountid;
        private String articleid;
        private String lon;
        private String title;
        private String lat;

        public String getAccountid() {
            return accountid;
        }

        public void setAccountid(String accountid) {
            this.accountid = accountid;
        }

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
