package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/17
 **/
public class GetFourBean {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"imagethree":null,"articleid":"8A8A723F-0C8D-4F47-8E0F-07F1197247E3","imageone":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg","Title":"古古怪怪","imglist":[{"img":"http://192.168.0.8/article/articleimg020200611183719725072250.jpg"},{"img":"http://192.168.0.8/article/articleimg120200611183719725072250.jpg"},{"img":"http://192.168.0.8/article/articleimg220200611183719725072250.jpg"},{"img":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg"}],"local":"浙江省温州市","content":"","modules":"M3","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg"},{"imagethree":null,"articleid":"12E55C16-2D54-4408-A4DD-D02DF25590F4","imageone":"http://192.168.0.8/article/articleimg020200616164154086277901.jpg","Title":"模板工程","imglist":[{"img":"http://192.168.0.8/article/articleimg020200616164154086277901.jpg"}],"local":"浙江省温州市","content":"1112222","modules":"M1","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg"},{"imagethree":null,"articleid":"B6A0F357-6DCE-4F2C-A434-C1832C23BF22","imageone":"http://192.168.0.8/article/articleimg020200616134040086277901.jpg","Title":"三生三世","imglist":[{"img":"http://192.168.0.8/article/articleimg020200616134040086277901.jpg"},{"img":"http://192.168.0.8/article/articleimg120200616134041086277901.jpg"}],"local":"浙江省温州市","content":"呃呃呃额额的的恶魔嗯嗯的魔门得得","modules":"M2","nick":"飞翔","accountid":"086277901","imagetwo":"http://192.168.0.8/article/articleimg120200616134041086277901.jpg","readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg"}]
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
         * imagethree : null
         * articleid : 8A8A723F-0C8D-4F47-8E0F-07F1197247E3
         * imageone : http://192.168.0.8/article/articleimg320200611183719725072250.jpg
         * Title : 古古怪怪
         * imglist : [{"img":"http://192.168.0.8/article/articleimg020200611183719725072250.jpg"},{"img":"http://192.168.0.8/article/articleimg120200611183719725072250.jpg"},{"img":"http://192.168.0.8/article/articleimg220200611183719725072250.jpg"},{"img":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg"}]
         * local : 浙江省温州市
         * content :
         * modules : M3
         * nick : 飞翔
         * accountid : 086277901
         * imagetwo : null
         * readers : 0
         * logo : http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg
         */

        private Object imagethree;
        private String articleid;
        private String imageone;
        private String Title;
        private String local;
        private String content;
        private String modules;
        private String nick;
        private String accountid;
        private Object imagetwo;
        private int readers;
        private int repost;
        private int comments;
        private int good;
        private int isfire;
        private int ispraised;
        private int istop;
        private String logo;
        private List<ImglistBean> imglist;



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

        public int getIsfire() {
            return isfire;
        }

        public void setIsfire(int isfire) {
            this.isfire = isfire;
        }
        public Object getImagethree() {
            return imagethree;
        }

        public void setImagethree(Object imagethree) {
            this.imagethree = imagethree;
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

        public String getLocal() {
            return local;
        }

        public void setLocal(String local) {
            this.local = local;
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

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getAccountid() {
            return accountid;
        }

        public void setAccountid(String accountid) {
            this.accountid = accountid;
        }

        public Object getImagetwo() {
            return imagetwo;
        }

        public void setImagetwo(Object imagetwo) {
            this.imagetwo = imagetwo;
        }

        public int getReaders() {
            return readers;
        }

        public void setReaders(int readers) {
            this.readers = readers;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public List<ImglistBean> getImglist() {
            return imglist;
        }

        public void setImglist(List<ImglistBean> imglist) {
            this.imglist = imglist;
        }

        public static class ImglistBean {
            /**
             * img : http://192.168.0.8/article/articleimg020200611183719725072250.jpg
             */

            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
