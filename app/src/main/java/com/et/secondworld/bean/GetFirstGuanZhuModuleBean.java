package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/17
 **/
public class GetFirstGuanZhuModuleBean {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"imagethree":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/763/w1000h563/20200408/0d52-iryninw4454661.jpg","accountid":"12","imagetwo":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/150/w1000h750/20200408/28ae-iryninw4454960.jpg","readers":1,"articleid":"34","imageone":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/66/w1000h666/20200408/5e4c-iryninw4454917.jpg","Title":"测试"},{"imagethree":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/150/w1000h750/20200408/28ae-iryninw4454960.jpg","accountid":"234","imagetwo":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/763/w1000h563/20200408/0d52-iryninw4454661.jpg","readers":1,"articleid":"B34B9587-28D6-4452-AD68-EEDA7AC5705B","imageone":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/75/w1000h675/20200408/6134-iryninw4454774.jpg","Title":"234"},{"imagethree":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/763/w1000h563/20200408/0d52-iryninw4454661.jpg","accountid":"2411","imagetwo":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/66/w1000h666/20200408/5e4c-iryninw4454917.jpg","readers":1,"articleid":"ED21E764-CF43-430E-8F8B-606F8B02CB87","imageone":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/67/w1000h667/20200408/92c8-iryninw4454892.jpg","Title":"2"},{"imagethree":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/66/w1000h666/20200408/5e4c-iryninw4454917.jpg","accountid":"34","imagetwo":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/75/w1000h675/20200408/6134-iryninw4454774.jpg","readers":1,"articleid":"812872C2-F710-450C-86C3-E667DBE569B6","imageone":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/67/w1000h667/20200408/92c8-iryninw4454892.jpg","Title":"2323"}]
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
         * imagethree : http://n.sinaimg.cn/news/1_img/upload/cf3881ab/763/w1000h563/20200408/0d52-iryninw4454661.jpg
         * accountid : 12
         * imagetwo : http://n.sinaimg.cn/news/1_img/upload/cf3881ab/150/w1000h750/20200408/28ae-iryninw4454960.jpg
         * readers : 1
         * articleid : 34
         * imageone : http://n.sinaimg.cn/news/1_img/upload/cf3881ab/66/w1000h666/20200408/5e4c-iryninw4454917.jpg
         * Title : 测试
         */

        private String imagethree;
        private String accountid;
        private String imagetwo;
        private int readers;
        private int good;
        private int comments;
        private int repost;
        private int ispraised;
        private String articleid;
        private String imageone;
        private String Title;
        private String content;
        private String modules;
//        private String good;
//        private String comments;
        private String types;
        private int issoluation;
        private String nick;
        private String logo;



        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public int getIssoluation() {
            return issoluation;
        }

        public void setIssoluation(int issoluation) {
            this.issoluation = issoluation;
        }


        public int getIspraised() {
            return ispraised;
        }

        public void setIspraised(int ispraised) {
            this.ispraised = ispraised;
        }
        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }
        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }
        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }
        public int getRepost() {
            return repost;
        }

        public void setRepost(int repost) {
            this.repost = repost;
        }
        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
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

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
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
