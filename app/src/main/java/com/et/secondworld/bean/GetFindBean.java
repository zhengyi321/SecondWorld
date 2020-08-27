package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/17
 **/
public class GetFindBean {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"nick":"小洋人","imagethree":"","accountid":"59CCE34E-9F07-4E73-8FB7-8E835EBE80B3","imagetwo":"","readers":0,"articleid":"EB036528-AB2F-46D4-9BA5-A88E3BE3DCF8","imageone":"","logo":"http://192.168.0.4/shop/shoplogo20200424145356725072250.jpg","title":"测试","content":"各位大神，我想问一下在java中我们通常用substring来进行截取字符串，有时候也有用到指定的字符串进行截取，我现在遇到一个问题，比如这个字符串\r\nString  bb =\"asdfadfasdfasfasdfsdaf12213;asdfjdksfjkl3434;34534534534;dsfdsf;234234234;sdfgfsdg\"我怎么用substring截取字符串的方法截取，从第0位开始到第三个分号前的数据呢?我用substring中的indexOf来做的话那只能截取0到第一个分号这个部分的内容，而我现在想要截取从0到第三个分号的数据","modules":"M1"},{"nick":"小洋人","imagethree":"","accountid":"59CCE34E-9F07-4E73-8FB7-8E835EBE80B3","imagetwo":"","readers":0,"articleid":"9A2DE4E0-E84F-4449-81F5-8E14626BA6F1","imageone":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/67/w1000h667/20200408/92c8-iryninw4454892.jpg","logo":"http://192.168.0.4/shop/shoplogo20200424145356725072250.jpg","title":"无线测试","content":"各位大神，我想问一下在java中我们通常用substring来进行截取字符串，\n有时候也有用到指定的字符串进行截取，我现在遇到一个问题，比如这个字符串\nString  bb =\"asdfadfasdfasfasdfsdaf12213;asdfjdksfjkl3434;34534534534;\ndsfdsf;234234234;sdfgfsdg\"我怎么用substring截取字符串的方法截取，从第0位开始到第三个分号前的数据呢?我用substring中的indexOf来做的话那只能截取0到第一个分号这个部分的内容，而我现在想要截取从0到第三个分号的数据","modules":"M3"},{"nick":"小洋人","imagethree":null,"accountid":"59CCE34E-9F07-4E73-8FB7-8E835EBE80B3","imagetwo":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/75/w1000h675/20200408/6134-iryninw4454774.jpg","readers":3,"articleid":"F15C131C-D610-4F9A-9275-4BA4861EEB61","imageone":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/75/w1000h675/20200408/6134-iryninw4454774.jpg","logo":"http://192.168.0.4/shop/shoplogo20200424145356725072250.jpg","title":"成都市","content":"成都市","modules":"M3"}]
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
         * nick : 小洋人
         * imagethree :
         * accountid : 59CCE34E-9F07-4E73-8FB7-8E835EBE80B3
         * imagetwo :
         * readers : 0
         * articleid : EB036528-AB2F-46D4-9BA5-A88E3BE3DCF8
         * imageone :
         * logo : http://192.168.0.4/shop/shoplogo20200424145356725072250.jpg
         * title : 测试
         * content : 各位大神，我想问一下在java中我们通常用substring来进行截取字符串，有时候也有用到指定的字符串进行截取，我现在遇到一个问题，比如这个字符串
         String  bb ="asdfadfasdfasfasdfsdaf12213;asdfjdksfjkl3434;34534534534;dsfdsf;234234234;sdfgfsdg"我怎么用substring截取字符串的方法截取，从第0位开始到第三个分号前的数据呢?我用substring中的indexOf来做的话那只能截取0到第一个分号这个部分的内容，而我现在想要截取从0到第三个分号的数据
         * modules : M1
         */

        private String nick;
        private String imagethree;
        private String accountid;
        private String imagetwo;
        private int readers;
        private int repost;
        private int comments;
        private int good;
        private int isfire;
        private String articleid;
        private String imageone;
        private String logo;
        private String title;
        private String content;
        private String modules;
        private String shoptime;
        private int ispraised;
        private int istop;


        public int getIstop() {
            return istop;
        }

        public void setIstop(int istop) {
            this.istop = istop;
        }
        public int getIspraised() {
            return ispraised;
        }

        public void setIspraised(int ispraised) {
            this.ispraised = ispraised;
        }
        public int getRepost() {
            return repost;
        }

        public void setRepost(int repost) {
            this.repost = repost;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public String getShoptime() {
            return shoptime;
        }

        public void setShoptime(String shoptime) {
            this.shoptime = shoptime;
        }
        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getImagethree() {
            return imagethree;
        }

        public void setImagethree(String imagethree) {
            this.imagethree = imagethree;
        }

        public String getAccountid() {
            return accountid;
        }

        public void setAccountid(String accountid) {
            this.accountid = accountid;
        }

        public String getImagetwo() {
            return imagetwo;
        }

        public void setImagetwo(String imagetwo) {
            this.imagetwo = imagetwo;
        }

        public int getReaders() {
            return readers;
        }

        public void setReaders(int readers) {
            this.readers = readers;
        }
        public int getIsfire() {
            return isfire;
        }

        public void setIsfire(int isfire) {
            this.isfire = isfire;
        }

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
        }

        public String getImageone() {
            return imageone;
        }

        public void setImageone(String imageone) {
            this.imageone = imageone;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getModules() {
            return modules;
        }

        public void setModules(String modules) {
            this.modules = modules;
        }
    }
}
