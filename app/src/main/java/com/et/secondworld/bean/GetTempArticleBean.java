package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/25
 **/
public class GetTempArticleBean {

    /**
     * msg : 获取信息成功
     * atid : 690404486,C95D02053,C95D0205,
     * issuccess : 1
     * city : 温州市
     * lon : 120.862742
     * atnick : 九州,12,33,
     * title : 我是草稿箱子
     * addr : 中国浙江省温州市乐清市北白象镇前港路
     * imglist : [{"imgurl":"http://192.168.0.8/article/articleimg020200525193145725072250.jpg"},{"imgurl":"http://192.168.0.8/article/articleimg020200525193145725072250.jpg"},{"imgurl":"http://192.168.0.8/article/articleimg020200525193145725072250.jpg"},{"imgurl":"http://192.168.0.8/article/articleimg020200525193145725072250.jpg"}]
     * content : 我是草稿箱子草稿箱子啊啊啊啊啊啊啊啊啊啊啊啊@九州 @12 @33
     * lat : 28.030837
     * sections : -1
     */

    private String msg;
    private String atid;
    private String issuccess;
    private String city;
    private String lon;
    private String atnick;
    private String title;
    private String addr;
    private String content;
    private String lat;
    private String sections;
    private List<ImglistBean> imglist;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAtid() {
        return atid;
    }

    public void setAtid(String atid) {
        this.atid = atid;
    }

    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAtnick() {
        return atnick;
    }

    public void setAtnick(String atnick) {
        this.atnick = atnick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
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

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public List<ImglistBean> getImglist() {
        return imglist;
    }

    public void setImglist(List<ImglistBean> imglist) {
        this.imglist = imglist;
    }

    public static class ImglistBean {
        /**
         * imgurl : http://192.168.0.8/article/articleimg020200525193145725072250.jpg
         */

        private String imgurl;

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
