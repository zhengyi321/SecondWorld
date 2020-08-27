package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/8/1
 **/
public class TradeOrderListBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"ordertitle":"测试","time":"2020-07-24   14:50:54","mount":"0.01"},{"ordertitle":"摆摊","time":"2020-07-24   18:51:29","mount":"0.01"},{"ordertitle":"紧急事件与求助","time":"2020-07-27   11:29:56","mount":"0.01"},{"ordertitle":"摆摊","time":"2020-07-27   11:32:07","mount":"0.01"},{"ordertitle":"紧急事件与求助","time":"2020-07-27   12:26:32","mount":"0.01"},{"ordertitle":"紧急事件与求助","time":"2020-07-27   13:56:08","mount":"0.01"},{"ordertitle":"紧急事件与求助","time":"2020-07-28   12:46:53","mount":"0.01"},{"ordertitle":"紧急事件与求助","time":"2020-07-28   12:48:25","mount":"0.01"},{"ordertitle":"紧急事件与求助","time":"2020-07-28   13:20:20","mount":"0.01"},{"ordertitle":"摆摊","time":"2020-07-28   13:24:00","mount":"0.01"},{"ordertitle":"推广","img":"http://47.114.155.83/15669858212/article/articleimg020200726103112005073347.jpg","time":"2020-07-31   09:03:35","title":"what  are you doing","mount":"0.05"},{"ordertitle":"推广","img":"http://47.114.155.83/18767775244/article/articleimg020200727122641725072250.jpg","time":"2020-07-31   17:18:18","title":"紧急寻人","mount":"0.01"}]
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
         * ordertitle : 测试
         * time : 2020-07-24   14:50:54
         * mount : 0.01
         * img : http://47.114.155.83/15669858212/article/articleimg020200726103112005073347.jpg
         * title : what  are you doing
         */

        private String ordertitle;
        private String time;
        private String mount;
        private String img;
        private String title;

        public String getOrdertitle() {
            return ordertitle;
        }

        public void setOrdertitle(String ordertitle) {
            this.ordertitle = ordertitle;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMount() {
            return mount;
        }

        public void setMount(String mount) {
            this.mount = mount;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
