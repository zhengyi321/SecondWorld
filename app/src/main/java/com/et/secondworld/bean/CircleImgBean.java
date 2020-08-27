package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/18
 **/
public class CircleImgBean {


    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"img":"http://n.sinaimg.cn/news/1_img/upload/cf3881ab/75/w1000h675/20200408/6134-iryninw4454774.jpg","articleid":"9A2DE4E0-E84F-4449-81F5-8E14626BA6F1","title":"无线测试"}]
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

    public    class ListBean {
        /**
         * img : http://n.sinaimg.cn/news/1_img/upload/cf3881ab/75/w1000h675/20200408/6134-iryninw4454774.jpg
         * articleid : 9A2DE4E0-E84F-4449-81F5-8E14626BA6F1
         * title : 无线测试
         */

        private String img;
        private String articleid;
        private String title;
        private String accountid;
        private String modules;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getArticleid() {
            return articleid;
        }

        public void setArticleid(String articleid) {
            this.articleid = articleid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getAccountid() {
            return accountid;
        }

        public void setAccountid(String accountid) {
            this.accountid = accountid;
        }
        public String getModules() {
            return modules;
        }

        public void setModules(String modules) {
            this.modules = modules;
        }
    }
}
