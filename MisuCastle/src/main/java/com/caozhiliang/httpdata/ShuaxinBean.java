package com.caozhiliang.httpdata;

import java.util.List;

public class ShuaxinBean {
private Boolean t;
private List<HomeDataBean> list;

public ShuaxinBean() {
	super();
}

	@Override
	public String toString() {
		return "ShuaxinBean{" +
				"t=" + t +
				", list=" + list +
				'}';
	}

	public ShuaxinBean(Boolean t, List<HomeDataBean> list) {
	super();
	this.t = t;
	this.list = list;
}
public Boolean getT() {
	return t;
}
public void setT(Boolean t) {
	this.t = t;
}
public List<HomeDataBean> getList() {
	return list;
}
public void setList(List<HomeDataBean> list) {
	this.list = list;
}

}
