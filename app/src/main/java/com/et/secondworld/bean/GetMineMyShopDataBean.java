package com.et.secondworld.bean;

/**
 * @Author zhyan
 * @Description //TODO
 * @Date 2020/4/24
 **/
public class GetMineMyShopDataBean {

    /**
     * msg : 获取店铺资料成功
     * locate : 浙江温州
     * articleid : EB036528-AB2F-46D4-9BA5-A88E3BE3DCF8
     * title : 测试
     * content : 各位大神，我想问一下在java中我们通常用substring来进行截取字符串，有时候也有用到指定的字符串进行截取，我现在遇到一个问题，比如这个字符串
     String  bb ="asdfadfasdfasfasdfsdaf12213;asdfjdksfjkl3434;34534534534;dsfdsf;234234234;sdfgfsdg"我怎么用substring截取字符串的方法截取，从第0位开始到第三个分号前的数据呢?我用substring中的indexOf来做的话那只能截取0到第一个分号这个部分的内容，而我现在想要截取从0到第三个分号的数据
     * modules : M1
     * praise : 5
     * fans : 0
     * shopbg : null
     * parise : 0
     * issuccess : 1
     * readers : 0
     * shopname : 小洋人
     * articleimg :
     * logo : http://192.168.0.4/shop/shoplogo20200424145356725072250.jpg
     * comment : 1
     * shopid : 59CCE34E-9F07-4E73-8FB7-8E835EBE80B3
     * tel : 187645545454
     * repost : 0
     * guanzhu : 0
     * status : 3
     */

    private String msg;
    private String locate;
    private String articleid;
    private String title;
    private String content;
    private String modules;
    private int good;
    private int fans;
    private String shopbg;
    private int praise;
    private String issuccess;
    private int readers;
    private String shopname;
    private String articleimg;
    private String logo;
    private int comment;
    private String shopid;
    private String tel;
    private String time;
    private String businesshour;
    private String street;
    private String isfirst;
    private int ispraised;
    private int repost;
    private int guanzhu;
    private int status;
    private int spreadprice;


    public int getSpreadprice() {
        return spreadprice;
    }

    public void setSpreadprice(int spreadprice) {
        this.spreadprice = spreadprice;
    }
    public Integer getIspraised() {
        return ispraised;
    }

    public void setIspraised(int ispraised) {
        this.ispraised = ispraised;
    }

    public String getIsfirst() {
        return isfirst;
    }

    public void setIsfirst(String isfirst) {
        this.street = street;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
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

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public String getShopbg() {
        return shopbg;
    }

    public void setShopbg(String shopbg) {
        this.shopbg = shopbg;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public String getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(String issuccess) {
        this.issuccess = issuccess;
    }

    public int getReaders() {
        return readers;
    }

    public void setReaders(int readers) {
        this.readers = readers;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getArticleimg() {
        return articleimg;
    }

    public void setArticleimg(String articleimg) {
        this.articleimg = articleimg;
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

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBusinesshour() {
        return businesshour;
    }

    public void setBusinesshour(String businesshour) {
        this.businesshour = businesshour;
    }

    public int getRepost() {
        return repost;
    }

    public void setRepost(int repost) {
        this.repost = repost;
    }

    public int getGuanzhu() {
        return guanzhu;
    }

    public void setGuanzhu(int guanzhu) {
        this.guanzhu = guanzhu;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
