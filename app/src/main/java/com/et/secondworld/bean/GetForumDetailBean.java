package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/20
 **/
public class GetForumDetailBean {


    /**
     * nick : 测试
     * head : http://portrait2.sinaimg.cn/2419104481/blog/180
     * msg : 获取数据成功
     * issuccess : 1
     * readers : 4
     * isfriend : 0
     * comment : 1
     * good : 2
     * imglist : [{"url":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/75/w1000h675/20200408/6134-iryninw4454774.jpg"}]
     * isguanzhu : 0
     * content : 各位大神，我想问一下在java中我们通常用substring来进行截取字符串，有时候也有用到指定的字符串进行截取，我现在遇到一个问题，比如这个字符串
     String  bb ="asdfadfasdfasfasdfsdaf12213;asdfjdksfjkl3434;34534534534;dsfdsf;234234234;sdfgfsdg"我怎么用substring截取字符串的方法截取，从第0位开始到第三个分号前的数据呢?我用substring中的indexOf来做的话那只能截取0到第一个分号这个部分的内容，而我现在想要截取从0到第三个分号的数据
     * repost : 0
     */

    private String nick;
    private String head;
    private String msg;
    private String issuccess;
    private int readers;
    private String isfriend;
    private int comment;
    private int good;
    private String isguanzhu;
    private String content;
    private String sections;
    private String title;
    private String types;
    private String times;
    private int repost;
    private int issoluation;
    private List<ImglistBean> imglist;

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
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

    public int getReaders() {
        return readers;
    }

    public void setReaders(int readers) {
        this.readers = readers;
    }

    public int getIssoluation() {
        return issoluation;
    }

    public void setIssoluation(int issoluation) {
        this.issoluation = issoluation;
    }

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
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
    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public int getRepost() {
        return repost;
    }

    public void setRepost(int repost) {
        this.repost = repost;
    }

    public List<ImglistBean> getImglist() {
        return imglist;
    }

    public void setImglist(List<ImglistBean> imglist) {
        this.imglist = imglist;
    }

    public   class ImglistBean {
        /**
         * url : http://n.sinaimg.cn/news/1_img/upload/cf3881ab/75/w1000h675/20200408/6134-iryninw4454774.jpg
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
