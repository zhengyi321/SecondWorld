package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/7/11
 **/
public class GetStreetShopListBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"img":"http://192.168.0.8/article/articleimg020200710145443705870261.jpg","name":"88828282","articleid":"D8514C3A-6A1E-46FC-8923-57AAF581569D","logo":null,"shopid":"705870261","title":"好家伙","ispraised":0},{"img":"http://192.168.0.8/article/articleimg020200430161051F62C4C78-289D-4F00-9673-1CE9EFCE1C03.jpg","name":"飞翔","articleid":"0DCE1765-3E52-42CC-9D2F-C6D4B0D38EF1","logo":"http://192.168.0.8/shop/shoplogo20200703104229086277901.jpg","shopid":"086277901","title":"最新一篇","ispraised":0}]
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
         * img : http://192.168.0.8/article/articleimg020200710145443705870261.jpg
         * name : 88828282
         * articleid : D8514C3A-6A1E-46FC-8923-57AAF581569D
         * logo : null
         * shopid : 705870261
         * title : 好家伙
         * ispraised : 0
         */

        private String img;
        private String name;
        private String articleid;
        private String logo;
        private String shopid;
        private String title;
        private int ispraised;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIspraised() {
            return ispraised;
        }

        public void setIspraised(int ispraised) {
            this.ispraised = ispraised;
        }
    }
}
