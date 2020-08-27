package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/23
 **/
public class TecentMessageListBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"nick":"12","img":"http://192.168.0.8/booth/boothimg20200623170812725072250.jpg","lon":"120.911614","title":"233322","content":"测试你好能否","lat":"28.078493"}]
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
         * nick : 12
         * img : http://192.168.0.8/booth/boothimg20200623170812725072250.jpg
         * lon : 120.911614
         * title : 233322
         * content : 测试你好能否
         * lat : 28.078493
         */

        private String nick;
        private String img;
        private String lon;
        private String title;
        private String content;
        private String lat;
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
