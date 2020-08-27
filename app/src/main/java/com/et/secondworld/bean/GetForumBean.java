package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/17
 **/
public class GetForumBean {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"imagethree":null,"types":"data","issoluation":"0","articleid":"B3FAF3DE-731D-410B-8129-900189BD5FBC","imageone":null,"Title":"啪啪啪啪啪","good":0,"content":"YY鱼鱼鱼鱼鱼鱼与呼呼呼呼呼呼哈哈哈哈哈哈哈","modules":"M4","nick":"九州","head":null,"accountid":"005073347","imagetwo":null,"comment":0},{"imagethree":null,"types":"data","issoluation":"0","articleid":"C0430F6A-C96E-4BCF-B96C-EC1FB09E8B08","imageone":"http://192.168.0.8/article/articleimg020200629133814005073347.jpg","Title":"我我我","good":0,"content":"","modules":"M4","nick":"九州","head":null,"accountid":"005073347","imagetwo":null,"comment":0},{"imagethree":null,"types":"data","issoluation":"1","articleid":"A5982D01-7069-40FE-B81A-E10B85B0A0DA","imageone":"http://192.168.0.8/article/articleimg020200629133448005073347.jpg","Title":"我","good":0,"content":"","modules":"M4","nick":"九州","head":null,"accountid":"005073347","imagetwo":null,"comment":0},{"imagethree":null,"types":"data","issoluation":"1","articleid":"5F8BE8DA-44A8-4C38-83E2-2F7CD2FB3F60","imageone":"http://192.168.0.8/article/articleimg020200624092727725072250.jpg","Title":"67676","good":0,"content":"","modules":"M4","nick":"Kizzy","head":"http://192.168.0.8/head/head20200528100611725072250.jpg","accountid":"725072250","imagetwo":null,"comment":0},{"imagethree":null,"types":"help","issoluation":"1","articleid":"A599F645-17BA-465B-9065-BF363968B50C","imageone":"http://192.168.0.8/article/articleimg020200623211133725072250.jpg","Title":"66777人73774","good":0,"content":"","modules":"M4","nick":"Kizzy","head":"http://192.168.0.8/head/head20200528100611725072250.jpg","accountid":"725072250","imagetwo":"http://192.168.0.8/article/articleimg120200623211133725072250.jpg","comment":0}]
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
         * types : data
         * issoluation : 0
         * articleid : B3FAF3DE-731D-410B-8129-900189BD5FBC
         * imageone : null
         * Title : 啪啪啪啪啪
         * good : 0
         * content : YY鱼鱼鱼鱼鱼鱼与呼呼呼呼呼呼哈哈哈哈哈哈哈
         * modules : M4
         * nick : 九州
         * head : null
         * accountid : 005073347
         * imagetwo : null
         * comment : 0
         */

        private String imagethree;
        private String types;
        private String issoluation;
        private String articleid;
        private String imageone;
        private String Title;
        private int good;
        private String content;
        private String modules;
        private String nick;
        private String head;
        private String accountid;
        private String imagetwo;
        private String thirdid;
        private String backtime;
        private int comment;
        private int repost;
        private int readers;
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
        public int getReaders() {
            return readers;
        }

        public void setReaders(int readers) {
            this.readers = readers;
        }
        public int getRepost() {
            return repost;
        }

        public void setRepost(int repost) {
            this.repost = repost;
        }
        public String getBacktime() {
            return backtime;
        }

        public void setBacktime(String backtime) {
            this.backtime = backtime;
        }
        public String getImagethree() {
            return imagethree;
        }

        public void setImagethree(String imagethree) {
            this.imagethree = imagethree;
        }

        public String getThirdid() {
            return thirdid;
        }

        public void setThirdid(String thirdid) {
            this.thirdid = thirdid;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getIssoluation() {
            return issoluation;
        }

        public void setIssoluation(String issoluation) {
            this.issoluation = issoluation;
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

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
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

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }
    }
}
