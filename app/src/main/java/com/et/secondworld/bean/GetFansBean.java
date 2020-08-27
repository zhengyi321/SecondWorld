package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/18
 **/
public class GetFansBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"nick":"33","head":"http://portrait2.sinaimg.cn/2419104481/blog/180","accountid":"725072250","followid":"C95D0205","personalnote":"你好么","isfriends":0}]
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

    public  class ListBean {
        /**
         * nick : 33
         * head : http://portrait2.sinaimg.cn/2419104481/blog/180
         * accountid : 725072250
         * followid : C95D0205
         * personalnote : 你好么
         * isfriends : 0
         */

        private String nick;
        private String head;
        private String accountid;
        private String followid;
        private String personalnote;
        private int isfriends;
        private int isguanzhu;
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

        public String getFollowid() {
            return followid;
        }

        public void setFollowid(String followid) {
            this.followid = followid;
        }

        public String getPersonalnote() {
            return personalnote;
        }

        public void setPersonalnote(String personalnote) {
            this.personalnote = personalnote;
        }

        public int getIsfriends() {
            return isfriends;
        }

        public void setIsfriends(int isfriends) {
            this.isfriends = isfriends;
        }
        public int getIsguanzhu() {
            return isguanzhu;
        }

        public void setIsguanzhu(int isguanzhu) {
            this.isguanzhu = isguanzhu;
        }
    }
}
