package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/6/27
 **/
public class GetMyShopArticleHistoryListBean {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"imagethree":null,"loc":"浙江省温州市","articleid":"BA5894F8-E1E6-41BC-B401-0A112D3FE17D","imageone":null,"title":"2222222","good":0,"content":"厄尔让人22","modules":"M1","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"DE72CC41-BFE6-492C-A22A-DBAF8722BB52","imageone":"http://192.168.0.8/article/articleimg020200620100713086277901.jpg","title":".1111","good":0,"content":"测试页面用","modules":"M1","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"54F69B65-7E51-4104-9561-34BCD855CD7F","imageone":"http://192.168.0.8/article/articleimg020200620100713086277901.jpg","title":".1111","good":0,"content":"测试页面用","modules":"M1","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"8A8A723F-0C8D-4F47-8E0F-07F1197247E3","imageone":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg","title":"古古怪怪","good":0,"content":"","modules":"M3","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":1,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"61E7D2AB-61B2-4686-A368-E2D78031F4D0","imageone":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg","title":"急急急","good":0,"content":"","modules":"M3","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"12E55C16-2D54-4408-A4DD-D02DF25590F4","imageone":"http://192.168.0.8/article/articleimg020200616164154086277901.jpg","title":"模板工程","good":0,"content":"1112222","modules":"M1","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"B6A0F357-6DCE-4F2C-A434-C1832C23BF22","imageone":"http://192.168.0.8/article/articleimg020200616134040086277901.jpg","title":"三生三世","good":0,"content":"呃呃呃额额的的恶魔嗯嗯的魔门得得","modules":"M2","nick":"飞翔","accountid":"086277901","imagetwo":"http://192.168.0.8/article/articleimg120200616134041086277901.jpg","readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"articleid":"E18EEB64-4033-4A49-8B44-BF52C566C313","imageone":"http://192.168.0.8/article/articleimg020200616133745086277901.jpg","title":"三生三世","good":0,"content":"呃呃呃额额的的恶魔嗯嗯的魔门得得","modules":"M2","nick":"飞翔","accountid":"086277901","imagetwo":"http://192.168.0.8/article/articleimg120200616133745086277901.jpg","readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"8906F501-DD86-4E7A-8634-C944A2F65F62","imageone":"http://192.168.0.8/article/articleimg020200616133411086277901.jpg","title":"呃呃呃额额的的恶魔嗯嗯的魔门得得","good":0,"content":"测的时候回来啊！","modules":"M2","nick":"飞翔","accountid":"086277901","imagetwo":"http://192.168.0.8/article/articleimg120200616133412086277901.jpg","readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"ED530C46-A690-49D6-8A9E-65D1E76668F0","imageone":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg","title":"急急急","good":0,"content":"","modules":"M3","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"DDA5A0FD-8A2A-4F19-8841-024C5A720EE8","imageone":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg","title":"急急急","good":0,"content":"","modules":"M3","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"A605B1A0-2FFD-4AF1-AA9F-704702C13057","imageone":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg","title":"急急急","good":0,"content":"","modules":"M3","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"558CA730-C398-40EA-905B-7CAFF620595B","imageone":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg","title":"急急急","good":0,"content":"","modules":"M3","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"articleid":"B3DE6762-8297-4B91-86F8-8B4B4E1711ED","imageone":"http://192.168.0.8/article/articleimg320200611183719725072250.jpg","title":"急急急","good":0,"content":"","modules":"M3","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0},{"imagethree":null,"loc":"浙江省温州市","articleid":"B3520F54-2B8F-485E-8B90-FEF81C681D19","imageone":"http://192.168.0.8/article/articleimg020200611172245086277901.jpg","title":"。。。","good":0,"content":"。。。","modules":"M3","nick":"飞翔","accountid":"086277901","imagetwo":null,"readers":0,"logo":"http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg","comment":0,"repost":0}]
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
         * loc : 浙江省温州市
         * articleid : BA5894F8-E1E6-41BC-B401-0A112D3FE17D
         * imageone : null
         * title : 2222222
         * good : 0
         * content : 厄尔让人22
         * modules : M1
         * nick : 飞翔
         * accountid : 086277901
         * imagetwo : null
         * readers : 0
         * logo : http://192.168.0.8/shop/shoplogo20200526154232086277901.jpg
         * comment : 0
         * repost : 0
         */

        private Object imagethree;
        private String loc;
        private String articleid;
        private Object imageone;
        private String title;
        private int good;
        private String content;
        private String modules;
        private String nick;
        private String accountid;
        private Object imagetwo;
        private int readers;
        private String logo;
        private int comment;
        private int repost;

        public Object getImagethree() {
            return imagethree;
        }

        public void setImagethree(Object imagethree) {
            this.imagethree = imagethree;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
        }

        public Object getImageone() {
            return imageone;
        }

        public void setImageone(Object imageone) {
            this.imageone = imageone;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
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

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public int getRepost() {
            return repost;
        }

        public void setRepost(int repost) {
            this.repost = repost;
        }
    }
}
