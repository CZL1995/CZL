package com.caozhiliang.httpdata;

public class HomeDataBean {
  String wenzi;
  String imageurl;
  String id;

	public HomeDataBean() {
	super();
}

	@Override
	public String toString() {
		return "HomeDataBean{" +
				"wenzi='" + wenzi + '\'' +
				", imageurl='" + imageurl + '\'' +
				", id='" + id + '\'' +
				'}';
	}

	public HomeDataBean(String wenzi, String imageurl, String id) {
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
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
  
}
