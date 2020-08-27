package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/19
 **/
public class GetAreaBean {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"area":"鹿城区","lon":"28.06786505","lat":"120.5657985"},{"area":"龙湾区","lon":"27.91334071","lat":"120.8110777"},{"area":"瓯海区","lon":"27.97217719","lat":"120.5584036"},{"area":"洞头区","lon":"27.88488371","lat":"121.1523182"},{"area":"永嘉县","lon":"28.33639047","lat":"120.6688087"},{"area":"平阳县","lon":"27.63770076","lat":"120.3893873"},{"area":"苍南县","lon":"27.43443638","lat":"120.4455428"},{"area":"文成县","lon":"27.81271344","lat":"120.0284221"},{"area":"泰顺县","lon":"27.53640684","lat":"119.8848676"},{"area":"温州经济技术开发区","lon":"27.87894879","lat":"120.8238493"},{"area":"瑞安市","lon":"27.82923053","lat":"120.4683404"},{"area":"乐清市","lon":"28.26183899","lat":"121.0161749"}]
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
         * area : 鹿城区
         * lon : 28.06786505
         * lat : 120.5657985
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
