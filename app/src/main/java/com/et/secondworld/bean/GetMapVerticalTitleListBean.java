package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/22
 **/
public class GetMapVerticalTitleListBean {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"shopid":"086277901","place":"全国","title":"2222222"},{"shopid":"086277901","place":"全国","title":".1111"},{"shopid":"086277901","place":"全国","title":".1111"},{"shopid":"086277901","place":"全国","title":"古古怪怪"},{"shopid":"086277901","place":"全国","title":"模板工程"},{"shopid":"086277901","place":"全国","title":"急急急"},{"shopid":"086277901","place":"瑞安市","title":"急急急"},{"shopid":"086277901","place":"全国","title":"三生三世"}]
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
         * shopid : 086277901
         * place : 全国
         * title : 2222222
         */

        private String shopid;
        private String place;
        private String title;

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
