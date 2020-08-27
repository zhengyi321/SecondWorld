package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/26
 **/
public class GetCollectVillageListBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"allname":"浙江省温州市乐清市北白象镇象东居委会","isselect":0,"guid":"4A219682-8692-4C01-AD30-DD3FF69FDB8A","lon":"28.00137883","village":"象东居委会","lat":"120.8301704"}]
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
         * allname : 浙江省温州市乐清市北白象镇象东居委会
         * isselect : 0
         * guid : 4A219682-8692-4C01-AD30-DD3FF69FDB8A
         * lon : 28.00137883
         * village : 象东居委会
         * lat : 120.8301704
         */

        private String allname;
        private int isselect;
        private String guid;
        private String lon;
        private String village;
        private String lat;

        public String getAllname() {
            return allname;
        }

        public void setAllname(String allname) {
            this.allname = allname;
        }

        public int getIsselect() {
            return isselect;
        }

        public void setIsselect(int isselect) {
            this.isselect = isselect;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
