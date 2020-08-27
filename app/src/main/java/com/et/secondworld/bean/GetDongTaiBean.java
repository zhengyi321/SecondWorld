package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/18
 **/
public class GetDongTaiBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"readers":0,"articleid":"EB036528-AB2F-46D4-9BA5-A88E3BE3DCF8","comment":3,"title":"测试","good":0,"content":"各位大神，我想问一下在java中我们通常用substring来进行截取字符串，有时候也有用到指定的字符串进行截取，我现在遇到一个问题，比如这个字符串\r\nString bb =\"asdfadfasdfasfasdfsdaf12213;asdfjdksfjkl3434;34534534534;dsfdsf;234234234;sdfgfsdg\"我怎么用substring截取字符串的方法截取，从第0位开始到第三个分号前的数据呢?我用substring中的indexOf来做的话那只能截取0到第一个分号这个部分的内容，而我现在想要截取从0到第三个分号的数据","repost":0},{"readers":0,"articleid":"9A2DE4E0-E84F-4449-81F5-8E14626BA6F1","comment":7,"title":"无线测试","good":0,"content":"各位大神，我想问一下在java中我们通常用substring来进行截取字符串，有时候也有用到指定的字符串进行截取，我现在遇到一个问题，比如这个字符串\r\nString bb =\"asdfadfasdfasfasdfsdaf12213;asdfjdksfjkl3434;34534534534;dsfdsf;234234234;sdfgfsdg\"我怎么用substring截取字符串的方法截取，从第0位开始到第三个分号前的数据呢?我用substring中的indexOf来做的话那只能截取0到第一个分号这个部分的内容，而我现在想要截取从0到第三个分号的数据","repost":0},{"readers":3,"articleid":"F15C131C-D610-4F9A-9275-4BA4861EEB61","comment":1,"title":"成都市","good":2,"content":"成都市","repost":0},{"readers":4,"articleid":"5E6ACE91-7446-46CE-92B8-A51B935745E2","comment":1,"title":"长沙市","good":2,"content":"各位大神，我想问一下在java中我们通常用substring来进行截取字符串，有时候也有用到指定的字符串进行截取，我现在遇到一个问题，比如这个字符串\r\nString bb =\"asdfadfasdfasfasdfsdaf12213;asdfjdksfjkl3434;34534534534;dsfdsf;234234234;sdfgfsdg\"我怎么用substring截取字符串的方法截取，从第0位开始到第三个分号前的数据呢?我用substring中的indexOf来做的话那只能截取0到第一个分号这个部分的内容，而我现在想要截取从0到第三个分号的数据","repost":0}]
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
         * readers : 0
         * articleid : EB036528-AB2F-46D4-9BA5-A88E3BE3DCF8
         * comment : 3
         * title : 测试
         * good : 0
         * content : 各位大神，我想问一下在java中我们通常用substring来进行截取字符串，有时候也有用到指定的字符串进行截取，我现在遇到一个问题，比如这个字符串
         String bb ="asdfadfasdfasfasdfsdaf12213;asdfjdksfjkl3434;34534534534;dsfdsf;234234234;sdfgfsdg"我怎么用substring截取字符串的方法截取，从第0位开始到第三个分号前的数据呢?我用substring中的indexOf来做的话那只能截取0到第一个分号这个部分的内容，而我现在想要截取从0到第三个分号的数据
         * repost : 0
         */

        private int readers;
        private String articleid;
        private int comment;
        private String title;
        private int good;
        private String content;
        private int repost;
        private String modules;


        private String nick;
        private String head;


        private String imgone;
        private String imgtwo;
        private String imgthree;
        private String types;

        private int ispraised;
        private int issoluation;




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

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }
        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
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

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
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

        public int getRepost() {
            return repost;
        }

        public void setRepost(int repost) {
            this.repost = repost;
        }

        public String getModules() {
            return modules;
        }

        public void setModules(String modules) {
            this.modules = modules;
        }


        public String getImgone() {
            return imgone;
        }

        public void setImgone(String imgone) {
            this.imgone = imgone == null ? null : imgone.trim();
        }
        public String getImgtwo() {
            return imgtwo;
        }

        public void setImgtwo(String imgtwo) {
            this.imgtwo = imgtwo == null ? null : imgtwo.trim();
        }
        public String getImgthree() {
            return imgthree;
        }

        public void setImgthree(String imgthree) {
            this.imgthree = imgthree == null ? null : imgthree.trim();
        }
    }
}
