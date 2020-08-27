package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/29
 **/
public class GetTownBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"town":"城东街道","lon":"120.967148","lat":"28.116083"},{"town":"乐成街道","lon":"120.967148","lat":"28.116083"},{"town":"城南街道","lon":"120.967148","lat":"28.116083"},{"town":"盐盆街道","lon":"120.967148","lat":"28.116083"},{"town":"翁垟街道","lon":"120.967148","lat":"28.116083"},{"town":"白石街道","lon":"120.967148","lat":"28.116083"},{"town":"石帆街道","lon":"120.967148","lat":"28.116083"},{"town":"天成街道","lon":"120.967148","lat":"28.116083"},{"town":"大荆镇","lon":"121.15802","lat":"28.408695"},{"town":"仙溪镇","lon":"121.072876","lat":"28.414606"},{"town":"雁荡镇","lon":"121.143875","lat":"28.357765"},{"town":"芙蓉镇","lon":"121.031578","lat":"28.296856"},{"town":"清江镇","lon":"121.106514","lat":"28.272156"},{"town":"虹桥镇","lon":"121.038132","lat":"28.220406"},{"town":"淡溪镇","lon":"120.99733","lat":"28.229271"},{"town":"柳市镇","lon":"120.892197","lat":"28.03825"},{"town":"北白象镇","lon":"120.860893","lat":"28.033998"}]
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

    public  class ListBean {
        /**
         * town : 城东街道
         * lon : 120.967148
         * lat : 28.116083
         */

        private String town;
        private String lon;
        private String lat;
        private String allname;

        public String getAllname() {
            return allname;
        }

        public void setAllname(String allname) {
            this.allname = allname;
        }
        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
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
