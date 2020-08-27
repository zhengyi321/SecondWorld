package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/18
 **/
public class GetGuanZhuBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"nick":null,"head":null,"accountid":"9BB1AA9F","followid":"725072250","personalnote":null,"isfriends":0},{"nick":null,"head":null,"accountid":"776B3AA3","followid":"725072250","personalnote":null,"isfriends":0},{"nick":null,"head":null,"accountid":"2725B9F4","followid":"725072250","personalnote":null,"isfriends":0},{"nick":"33","head":"http://portrait2.sinaimg.cn/2419104481/blog/180","accountid":"C95D0205","followid":"725072250","personalnote":"你好么","isfriends":1}]
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
         * nick : null
         * head : null
         * accountid : 9BB1AA9F
         * followid : 725072250
         * personalnote : null
         * isfriends : 0
         */

        private String nick;
        private String head;
        private String accountid;
        private String followid;
        private String personalnote;
        private int isfans;
        private int isfriends;
        private int status;

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
        public int getIsfans() {
            return isfans;
        }

        public void setIsfans(int isfans) {
            this.isfans = isfans;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
