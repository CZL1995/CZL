package com.caozhiliang.httpdata;

public class TradeBean {
	int number;
	int StoreNumber;
	String Storename;
	String jianjie;
	int price1;

	@Override
	public String toString() {
		return "TradeBean{" +
				"number=" + number +
				", StoreNumber=" + StoreNumber +
				", Storename='" + Storename + '\'' +
				", jianjie='" + jianjie + '\'' +
				", price1=" + price1 +
				", price2=" + price2 +
				", address='" + address + '\'' +
				", phone='" + phone + '\'' +
				", images='" + images + '\'' +
				", xiangq='" + xiangq + '\'' +
				", pingl='" + pingl + '\'' +
				", juli=" + juli +
				", xingpj=" + xingpj +
				", xfrenshu=" + xfrenshu +
				'}';
	}

	int price2;
	String address;
	String phone;
	String images;
	String xiangq;
	String pingl;
	float juli;
	float xingpj;
	int xfrenshu;

	public TradeBean() {
		super();
	}

	public TradeBean(int number, int storeNumber, String storename,
					 String jianjie, int price1, int price2, String address,
					 String phone, String images, String xiangq, String pingl,
					 float juli, float xingpj, int xfrenshu) {
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
		this.juli = juli;
		this.xingpj = xingpj;
		this.xfrenshu = xfrenshu;
	}

	public String getPingl() {
		return pingl;
	}

	public void setPingl(String pingl) {
		this.pingl = pingl;
	}

	public float getXingpj() {
		return xingpj;
	}

	public void setXingpj(float xingpj) {
		this.xingpj = xingpj;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getStoreNumber() {
		return StoreNumber;
	}

	public void setStoreNumber(int storeNumber) {
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

	public int getPrice1() {
		return price1;
	}

	public void setPrice1(int price1) {
		this.price1 = price1;
	}

	public int getPrice2() {
		return price2;
	}

	public void setPrice2(int price2) {
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



	public float getJuli() {
		return juli;
	}

	public void setJuli(float juli) {
		this.juli = juli;
	}



	public int getXfrenshu() {
		return xfrenshu;
	}

	public void setXfrenshu(int xfrenshu) {
		this.xfrenshu = xfrenshu;
	}

	public void setTrade(TradeBean t1)
	{
		number=t1.getNumber();
		StoreNumber=t1.getStoreNumber();
		Storename=t1.getStorename();
		jianjie=t1.getJianjie();
		price1=t1.getPrice1();
		price2=t1.getPrice2();
		address=t1.getAddress();
		phone=t1.getPhone();
		images=t1.getImages();
		xiangq=t1.getXiangq();
		pingl=t1.getPingl();
		juli=t1.getJuli();
		xingpj=t1.getXingpj();
		xfrenshu=t1.getXfrenshu();
	}
}
