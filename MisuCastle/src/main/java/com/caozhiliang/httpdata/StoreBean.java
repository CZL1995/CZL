package com.caozhiliang.httpdata;

public class StoreBean {

    int StoreNumber;
    String name;
    String address;
    String phone;
    String xiangq;

    public String getTime() {
        return time;
    }

    String comment;

    @Override
    public String toString() {
        return "StoreBean{" +
                "StoreNumber=" + StoreNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", xiangq='" + xiangq + '\'' +
                ", comment='" + comment + '\'' +
                ", renjun='" + renjun + '\'' +
                ", images='" + images + '\'' +
                ", xfrenshu='" + xfrenshu + '\'' +
                ", pjrenshu='" + pjrenshu + '\'' +
                ", juli='" + juli + '\'' +
                ", xingpj='" + xingpj + '\'' +
                ", diqu='" + diqu + '\'' +
                ", time='" + time + '\'' +
                ", fuwu='" + fuwu + '\'' +
                '}';
    }

    String renjun;

    public void setTime(String time) {
        this.time = time;
    }

    String images;
    String xfrenshu;
    String pjrenshu;
    String juli;
    String xingpj;
    String diqu;
    String time;
    String fuwu;

    public StoreBean() {
        super();
    }

    public String getFuwu() {
        return fuwu;
    }

    public void setFuwu(String fuwu) {
        this.fuwu = fuwu;
    }

    public StoreBean(int storeNumber, String name, String address, String phone,
                     String xiangq, String comment, String renjun, String images, String xfrenshu,
                     String pjrenshu, String juli, String xingpj, String diqu) {
        super();
        StoreNumber = storeNumber;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.xiangq = xiangq;
        this.comment = comment;
        this.renjun = renjun;
        this.images = images;
        this.xfrenshu = xfrenshu;
        this.pjrenshu = pjrenshu;
        this.juli = juli;
        this.xingpj = xingpj;
        this.diqu = diqu;
    }

    public int getStoreNumber() {
        return StoreNumber;
    }

    public void setStoreNumber(int storeNumber) {
        StoreNumber = storeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getXiangq() {
        return xiangq;
    }

    public void setXiangq(String xiangq) {
        this.xiangq = xiangq;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRenjun() {
        return renjun;
    }

    public void setRenjun(String renjun) {
        this.renjun = renjun;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getXfrenshu() {
        return xfrenshu;
    }

    public void setXfrenshu(String xfrenshu) {
        this.xfrenshu = xfrenshu;
    }

    public String getPjrenshu() {
        return pjrenshu;
    }

    public void setPjrenshu(String pjrenshu) {
        this.pjrenshu = pjrenshu;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public String getXingpj() {
        return xingpj;
    }

    public void setXingpj(String xingpj) {
        this.xingpj = xingpj;
    }

    public String getDiqu() {
        return diqu;
    }

    public void setDiqu(String diqu) {
        this.diqu = diqu;
    }

    public void setStore(StoreBean s1) {
        name = s1.getName();
        StoreNumber = s1.getStoreNumber();
        address = s1.getAddress();
        phone = s1.getPhone();
        xiangq = s1.getXiangq();
        comment = s1.getComment();
        renjun = s1.getRenjun();
        images = s1.getImages();
        xfrenshu = s1.getXfrenshu();
        pjrenshu = s1.getPjrenshu();
        juli = s1.getJuli();
        xingpj = s1.getXingpj();
        diqu = s1.getDiqu();

    }

}
