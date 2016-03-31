package com.caozhiliang.httpdata;

public class HomeDataBean {
  String wenzi;
  String imageurl;
  int id;
  
public HomeDataBean() {
	super();
}
public HomeDataBean(String wenzi, String imageurl, int id) {
	super();
	this.wenzi = wenzi;
	this.imageurl = imageurl;
	this.id = id;
}
public String getWenzi() {
	return wenzi;
}
public void setWenzi(String wenzi) {
	this.wenzi = wenzi;
}
public String getImageurl() {
	return imageurl;
}
public void setImageurl(String imageurl) {
	this.imageurl = imageurl;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
  
}
