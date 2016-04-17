package com.caozhiliang.httpdata;

/**
 * @author CZL
 * @time 2016-04-16 13:36
 */
public class AddressData {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShengf() {
        return shengf;
    }

    public void setShengf(String shengf) {
        this.shengf = shengf;
    }

    public String getChengs() {
        return chengs;
    }

    public void setChengs(String chengs) {
        this.chengs = chengs;
    }

    public String getXiangq() {
        return xiangq;
    }

    public void setXiangq(String xiangq) {
        this.xiangq = xiangq;
    }

    public String getXianc() {
        return xianc;
    }

    public void setXianc(String xianc) {
        this.xianc = xianc;
    }

    public String getYoubian() {
        return youbian;
    }

    public void setYoubian(String youbian) {
        this.youbian = youbian;
    }

    public String getAddnumber() {
        return addnumber;
    }

    public void setAddnumber(String addnumber) {
        this.addnumber = addnumber;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }

    @Override
    public String toString() {
        return "AddressData{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", shengf='" + shengf + '\'' +
                ", chengs='" + chengs + '\'' +
                ", xiangq='" + xiangq + '\'' +
                ", xianc='" + xianc + '\'' +
                ", youbian='" + youbian + '\'' +
                ", addnumber='" + addnumber + '\'' +
                ", usernumber='" + usernumber + '\'' +
                '}';
    }

    String name;
    String phone;
    String shengf;
    String chengs;
    String xiangq;
    String xianc;
    String youbian;
    String addnumber;
    String usernumber;

}
