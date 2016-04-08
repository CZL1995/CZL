package com.caozhiliang.httpdata;

public class StoreBean {
 
  String StoreNumber;
  String name;
  String address;
  String phone;
  String xiangq;
  String comment;
  String renjun;
  String images;
  String xfrenshu;
  String pjrenshu;
  
public StoreBean() {
	super();
}
public StoreBean(String storeNumber, String name, String address, String phone,
		String xiangq, String comment, String renjun, String images,
		String xfrenshu, String pjrenshu) {
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
}
public String getStoreNumber() {
	return StoreNumber;
}
public void setStoreNumber(String storeNumber) {
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
  
}
