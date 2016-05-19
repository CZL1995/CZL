package com.caozhiliang.httpdata;

/**
 * @author CZL
 * @time 2016-05-18 10:31
 */
public class XianshiData {

    @Override
    public String toString() {
        return "XianshiData{" +
                "Tradenumber=" + Tradenumber +
                ", image='" + image + '\'' +
                ", price1=" + price1 +
                ", price2=" + price2 +
                '}';
    }

    /**
     * Tradenumber : 6
     * image : http://119.29.148.150:8080/Fuwu1/image/Trade/shang_038.jpg
     * price1 : 158
     * price2 : 218
     */

    private int Tradenumber;
    private String image;
    private int price1;
    private int price2;

    public void setTradenumber(int Tradenumber) {
        this.Tradenumber = Tradenumber;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice1(int price1) {
        this.price1 = price1;
    }

    public void setPrice2(int price2) {
        this.price2 = price2;
    }

    public int getTradenumber() {
        return Tradenumber;
    }

    public String getImage() {
        return image;
    }

    public int getPrice1() {
        return price1;
    }

    public int getPrice2() {
        return price2;
    }
}
