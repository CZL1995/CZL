package com.caozhiliang.httpdata;

/**
 * @author CZL
 * @time 2016-04-20 16:06
 */
public class OrderData {


    /**
     * Ordernumber : 190223
     * Usernumber : 571628
     * Usernumber2 : 0
     * number : 21
     * Addressnumber : 755491
     * count : 1
     * zhuangtai : 等待处理
     * zongjia : 25
     * jieshao : haha
     * xingji : 0.0
     * liuyan : 无
     * image : http://119.29.148.150:8080/Fuwu1/image/Trade/shang_014.jpg
     * jianjie : 仅售25.5元！价值30元的代金券1张，全场通用，可叠加使用，提供免费WiFi。
     * price1 : 25
     * Tradename : 贝小七
     */

    private int Ordernumber;
    private int Usernumber;
    private int Usernumber2;
    private int number;
    private int Addressnumber;
    private int count;
    private String zhuangtai;
    private int zongjia;
    private String jieshao;
    private double xingji;
    private String liuyan;
    private String image;
    private String jianjie;
    private int price1;
    private String Tradename;
    //private String time;

//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }

    @Override
    public String toString() {
        return "OrderData{" +
                "Ordernumber=" + Ordernumber +
                ", Usernumber=" + Usernumber +
                ", Usernumber2=" + Usernumber2 +
                ", number=" + number +
                ", Addressnumber=" + Addressnumber +
                ", count=" + count +
                ", zhuangtai='" + zhuangtai + '\'' +
                ", zongjia=" + zongjia +
                ", jieshao='" + jieshao + '\'' +
                ", xingji=" + xingji +
                ", liuyan='" + liuyan + '\'' +
                ", image='" + image + '\'' +
                ", jianjie='" + jianjie + '\'' +
                ", price1=" + price1 +
                ", Tradename='" + Tradename + '\'' +
                '}';
    }

    public void setOrdernumber(int Ordernumber) {
        this.Ordernumber = Ordernumber;
    }

    public void setUsernumber(int Usernumber) {
        this.Usernumber = Usernumber;
    }

    public void setUsernumber2(int Usernumber2) {
        this.Usernumber2 = Usernumber2;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setAddressnumber(int Addressnumber) {
        this.Addressnumber = Addressnumber;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai;
    }

    public void setZongjia(int zongjia) {
        this.zongjia = zongjia;
    }

    public void setJieshao(String jieshao) {
        this.jieshao = jieshao;
    }

    public void setXingji(double xingji) {
        this.xingji = xingji;
    }

    public void setLiuyan(String liuyan) {
        this.liuyan = liuyan;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    public void setPrice1(int price1) {
        this.price1 = price1;
    }

    public void setTradename(String Tradename) {
        this.Tradename = Tradename;
    }

    public int getOrdernumber() {
        return Ordernumber;
    }

    public int getUsernumber() {
        return Usernumber;
    }

    public int getUsernumber2() {
        return Usernumber2;
    }

    public int getNumber() {
        return number;
    }

    public int getAddressnumber() {
        return Addressnumber;
    }

    public int getCount() {
        return count;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public int getZongjia() {
        return zongjia;
    }

    public String getJieshao() {
        return jieshao;
    }

    public double getXingji() {
        return xingji;
    }

    public String getLiuyan() {
        return liuyan;
    }

    public String getImage() {
        return image;
    }

    public String getJianjie() {
        return jianjie;
    }

    public int getPrice1() {
        return price1;
    }

    public String getTradename() {
        return Tradename;
    }
}
