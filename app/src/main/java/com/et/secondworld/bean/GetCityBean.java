package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/19
 **/
public class GetCityBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"area":"哈尔滨","lon":"45.77322463","lat":"126.6577169"},{"area":"大庆","lon":"46.59670902","lat":"125.0218397"},{"area":"大兴安岭","lon":"47.35659164","lat":"128.0474137"},{"area":"鹤岗","lon":"47.3386659","lat":"130.2924721"},{"area":"黑河","lon":"50.25069009","lat":"127.5008303"},{"area":"鸡西","lon":"45.32153989","lat":"130.9417673"},{"area":"佳木斯","lon":"46.8137796","lat":"130.2847346"},{"area":"牡丹江","lon":"44.58852115","lat":"129.6080354"},{"area":"七台河","lon":"45.77500537","lat":"131.019048"},{"area":"齐齐哈尔","lon":"47.34769981","lat":"123.9872889"},{"area":"双鸭山","lon":"46.65510206","lat":"131.1714017"},{"area":"绥化","lon":"46.64606393","lat":"126.9890946"},{"area":"伊春","lon":"47.73468508","lat":"128.910766"},{"area":"大兴安岭","lon":"51.99178897","lat":"124.1961042"}]
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
         * area : 哈尔滨
         * lon : 45.77322463
         * lat : 126.6577169
         */

        private String area;

        private String lon;
        private String lat;
        private String allname;
        public String getAllname() {
            return allname;
        }

        public void setAllname(String allname) {
            this.allname = allname;
        }
        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
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
