package com.caozhiliang.httpdata;

/**
 * @author CZL
 * @time 2016-04-24 10:33
 */
public class EvaluateData {

    /**
     * ordernumber : 0
     * Pingjianumber : 164798
     * number : 16
     * Usernumber : 0
     * Username : 小二郎的实验数据
     * xingji : 3.2
     * time : Apr 23, 2016 3:40:04 PM
     * xiangqin : 骨灰盒
     */

    private int ordernumber;
    private int Pingjianumber;
    private int number;
    private int Usernumber;
    private String Username;
    private double xingji;
    private String time;
    private String xiangqin;

    @Override
    public String toString() {
        return "EvaluateData{" +
                "ordernumber=" + ordernumber +
                ", Pingjianumber=" + Pingjianumber +
                ", number=" + number +
                ", Usernumber=" + Usernumber +
                ", Username='" + Username + '\'' +
                ", xingji=" + xingji +
                ", time='" + time + '\'' +
                ", xiangqin='" + xiangqin + '\'' +
                '}';
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public void setPingjianumber(int Pingjianumber) {
        this.Pingjianumber = Pingjianumber;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setUsernumber(int Usernumber) {
        this.Usernumber = Usernumber;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setXingji(double xingji) {
        this.xingji = xingji;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setXiangqin(String xiangqin) {
        this.xiangqin = xiangqin;
    }

    public int getOrdernumber() {
        return ordernumber;
    }

    public int getPingjianumber() {
        return Pingjianumber;
    }

    public int getNumber() {
        return number;
    }

    public int getUsernumber() {
        return Usernumber;
    }

    public String getUsername() {
        return Username;
    }

    public double getXingji() {
        return xingji;
    }

    public String getTime() {
        return time;
    }

    public String getXiangqin() {
        return xiangqin;
    }
}
