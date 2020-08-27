package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/26
 **/
public class GetStreetBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"allname":"浙江省温州市乐清市北白象东大街","street":"东大街","lon":"1","lat":"1"}]
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
         * allname : 浙江省温州市乐清市北白象东大街
         * street : 东大街
         * lon : 1
         * lat : 1
         */

        private String allname;
        private String street;
        private String lon;
        private String lat;

        public String getAllname() {
            return allname;
        }

        public void setAllname(String allname) {
            this.allname = allname;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
