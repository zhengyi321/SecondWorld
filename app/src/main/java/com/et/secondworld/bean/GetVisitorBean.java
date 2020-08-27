package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/25
 **/
public class GetVisitorBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"nick":"12","head":"http://192.168.0.4/head/head2020042410013618767775244.jpg","personnalnote":"","time":1587817356000,"visitorid":"C95D02053"},{"nick":"66","head":"http://192.168.0.4/head/head2020042410013618767775244.jpg","personnalnote":"13868888887","time":1587816070000,"visitorid":"776B3AA3"}]
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
         * nick : 12
         * head : http://192.168.0.4/head/head2020042410013618767775244.jpg
         * personnalnote :
         * time : 1587817356000
         * visitorid : C95D02053
         */

        private String nick;
        private String head;
        private String personnalnote;
        private String time;
        private String visitorid;

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

        public String getPersonnalnote() {
            return personnalnote;
        }

        public void setPersonnalnote(String personnalnote) {
            this.personnalnote = personnalnote;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getVisitorid() {
            return visitorid;
        }

        public void setVisitorid(String visitorid) {
            this.visitorid = visitorid;
        }
    }
}
