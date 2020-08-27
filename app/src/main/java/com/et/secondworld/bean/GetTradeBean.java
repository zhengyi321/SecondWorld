package com.et.secondworld.bean;

import java.util.List;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/7/7
 **/
public class GetTradeBean {

    /**
     * msg : 获取数据成功
     * issuccess : 1
     * list : [{"trade":"全部"},{"trade":"计算机"}]
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
         * trade : 全部
         */

        private String trade;

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }
    }
}
