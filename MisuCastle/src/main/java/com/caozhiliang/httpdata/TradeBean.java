package com.caozhiliang.httpdata;

public class TradeBean {
	String number;
	String StoreNumber;
	String Storename;

	String jianjie;
	String price1;

	String price2;
	String address;
	String phone;
	String images;
	String xiangq;
	String pingl;
	Float  xingji;
	String diqu;

	public TradeBean(String number, String storeNumber, String storename,
					 String jianjie, String price1, String price2, String address,
					 String phone, String images, String xiangq, String pingl,
					 Float xingji, String diqu, int juii) {
		super();
		this.number = number;
		StoreNumber = storeNumber;
		Storename = storename;
		this.jianjie = jianjie;
		this.price1 = price1;
		this.price2 = price2;
		this.address = address;
		this.phone = phone;
		this.images = images;
		this.xiangq = xiangq;
		this.pingl = pingl;
		this.xingji = xingji;
		this.diqu = diqu;
		this.juii = juii;
	}

	public TradeBean(String number, String storeNumber, String storename,
					 String jianjie, String price1, String price2, String address,
					 String phone, String images, String xiangq, String pingl) {
		super();
		this.number = number;
		StoreNumber = storeNumber;
		Storename = storename;
		this.jianjie = jianjie;
		this.price1 = price1;
		this.price2 = price2;
		this.address = address;
		this.phone = phone;
		this.images = images;
		this.xiangq = xiangq;
		this.pingl = pingl;
	}
	public TradeBean() {
		super();
	}

	@Override
	public String toString() {
		return "TradeBean{" +
				"number='" + number + '\'' +
				", StoreNumber='" + StoreNumber + '\'' +
				", Storename='" + Storename + '\'' +
				", jianjie='" + jianjie + '\'' +
				", price1='" + price1 + '\'' +
				", price2='" + price2 + '\'' +
				", address='" + address + '\'' +
				", phone='" + phone + '\'' +
				", images='" + images + '\'' +
				", xiangq='" + xiangq + '\'' +
				", pingl='" + pingl + '\'' +
				", xingji=" + xingji +
				", diqu='" + diqu + '\'' +
				", juii=" + juii +
				'}';
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStoreNumber() {
		return StoreNumber;
	}
	public void setStoreNumber(String storeNumber) {
		StoreNumber = storeNumber;
	}
	public String getStorename() {
		return Storename;
	}
	public void setStorename(String storename) {
		Storename = storename;
	}
	public String getJianjie() {
		return jianjie;
	}
	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
	public String getPrice1() {
		return price1;
	}
	public void setPrice1(String price1) {
		this.price1 = price1;
	}
	public String getPrice2() {
		return price2;
	}
	public void setPrice2(String price2) {
		this.price2 = price2;
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
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getXiangq() {
		return xiangq;
	}
	public void setXiangq(String xiangq) {
		this.xiangq = xiangq;
	}

	public String getPingl() {
		return pingl;
	}
	public void setPingl(String pingl) {
		this.pingl = pingl;
	}
	public Float getXingji() {
		return xingji;
	}
	public void setXingji(Float xingji) {
		this.xingji = xingji;
	}
	public String getDiqu() {
		return diqu;
	}
	public void setDiqu(String diqu) {
		this.diqu = diqu;
	}
	public int getJuii() {
		return juii;
	}
	public void setJuii(int juii) {
		this.juii = juii;
	}
	int juii;

}
