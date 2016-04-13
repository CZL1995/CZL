package com.caozhiliang.httpdata;

/**
 * @author CZL
 * @time 2016-04-13 10:45
 */
public class ImageData {
    int Storenumber;
    String imagename;
    String imageaddress;

    @Override
    public String toString() {
        return "ImageData{" +
                "Storenumber=" + Storenumber +
                ", imagename='" + imagename + '\'' +
                ", imageaddress='" + imageaddress + '\'' +
                '}';
    }

    public ImageData() {
        super();
    }
    public ImageData(int storenumber, String imagename, String imageaddress) {
        super();
        Storenumber = storenumber;
        this.imagename = imagename;
        this.imageaddress = imageaddress;
    }
    public int getStorenumber() {
        return Storenumber;
    }
    public void setStorenumber(int storenumber) {
        Storenumber = storenumber;
    }
    public String getImagename() {
        return imagename;
    }
    public void setImagename(String imagename) {
        this.imagename = imagename;
    }
    public String getImageaddress() {
        return imageaddress;
    }
    public void setImageaddress(String imageaddress) {
        this.imageaddress = imageaddress;
    }
}
