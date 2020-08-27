package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/7/9
 **/
public class GetApplyVillageListBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"accountid":"725072250","allname":"浙江温州乐清市磐石镇","lon":"120.847235","applyname":"榨汁","village":"东方村","lat":"28.016838"},{"accountid":"725072250","allname":"东方名剪镇溜溜山","lon":"120.847235","applyname":"试试","village":"熄灯村","lat":"28.016838"},{"accountid":"725072250","allname":"测试","lon":"120.847235","applyname":"测试","village":"测试","lat":"28.016838"},{"accountid":"725072250","allname":"66777","lon":"120.847235","applyname":"99","village":"66","lat":"28.016838"}]
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
         * allname : 浙江温州乐清市磐石镇
         * lon : 120.847235
         * applyname : 榨汁
         * village : 东方村
         * lat : 28.016838
         */

        private String accountid;
        private String allname;
        private String lon;
        private String applyname;
        private String village;
        private String lat;

        public String getAccountid() {
            return accountid;
        }

        public void setAccountid(String accountid) {
            this.accountid = accountid;
        }

        public String getAllname() {
            return allname;
        }

        public void setAllname(String allname) {
            this.allname = allname;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getApplyname() {
            return applyname;
        }

        public void setApplyname(String applyname) {
            this.applyname = applyname;
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
