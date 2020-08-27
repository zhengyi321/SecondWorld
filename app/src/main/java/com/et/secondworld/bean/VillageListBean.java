package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/18
 **/
public class VillageListBean {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"locate":"北京市东城区东华门街道办事处多福巷社区居委会","lon":"39.92921104","village":"多福巷社区居委会","areacode":"110101001001","lat":"116.4195991"},{"locate":"北京市东城区东华门街道办事处银闸社区居委会","lon":"39.92581547","village":"银闸社区居委会","areacode":"110101001002","lat":"116.4125804"},{"locate":"北京市东城区东华门街道办事处东厂社区居委会","lon":"39.92939224","village":"东厂社区居委会","areacode":"110101001005","lat":"116.4147078"},{"locate":"北京市东城区东华门街道办事处智德社区居委会","lon":"39.91847079","village":"智德社区居委会","areacode":"110101001006","lat":"116.4113822"},{"locate":"北京市东城区东华门街道办事处南池子社区居委会","lon":"39.91815328","village":"南池子社区居委会","areacode":"110101001007","lat":"116.4107588"}]
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
         * locate : 北京市东城区东华门街道办事处多福巷社区居委会
         * lon : 39.92921104
         * village : 多福巷社区居委会
         * areacode : 110101001001
         * lat : 116.4195991
         */

        private String locate;
        private String lon;
        private String village;
        private String areacode;
        private String lat;
        private String shopid;

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }
        public String getLocate() {
            return locate;
        }

        public void setLocate(String locate) {
            this.locate = locate;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
