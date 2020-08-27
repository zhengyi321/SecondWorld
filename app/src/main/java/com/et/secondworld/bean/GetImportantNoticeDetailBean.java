package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/5/5
 **/
public class GetImportantNoticeDetailBean {

    /**
     * nick :
     * head :
     * msg : 获取数据成功
     * issuccess : 1
     * isfriend : 1
     * title : 新年演唱会
     * imglist : [{"url":"http://192.168.0.4/article/articleimg020200430161051F62C4C78-289D-4F00-9673-1CE9EFCE1C03.jpg"}]
     * isguanzhu : 0
     * content : 新年演唱会
     */

    private String nick;
    private String head;
    private String msg;
    private String issuccess;
    private String isfriend;
    private String title;
    private String isguanzhu;
    private String content;
    private List<ImglistBean> imglist;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

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

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsguanzhu() {
        return isguanzhu;
    }

    public void setIsguanzhu(String isguanzhu) {
        this.isguanzhu = isguanzhu;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ImglistBean> getImglist() {
        return imglist;
    }

    public void setImglist(List<ImglistBean> imglist) {
        this.imglist = imglist;
    }

    public  class ImglistBean {
        /**
         * url : http://192.168.0.4/article/articleimg020200430161051F62C4C78-289D-4F00-9673-1CE9EFCE1C03.jpg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
