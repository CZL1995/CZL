package com.caozhiliang.httpdata;

import java.util.List;

public class HomeDataObject {
	 private int pan;
	 private List<HomeDataBean> datas;
	 private int size;
	 
	public HomeDataObject() {
		super();
	}

	@Override
	public String toString() {
		return "HomeDataObject{" +
				"pan=" + pan +
				", datas=" + datas +
				", size=" + size +
				'}';
	}

	public HomeDataObject(int pan, List<HomeDataBean> datas) {
		super();
		this.pan = pan;
		this.datas = datas;
	}

	public HomeDataObject(int pan, List<HomeDataBean> datas, int size) {
		super();
		this.pan = pan;
		this.datas = datas;
		this.size = size;
	}
	public int getPan() {
		return pan;
	}
	public void setPan(int pan) {
		this.pan = pan;
	}
	public List<HomeDataBean> getDatas() {
		return datas;
	}
	public void setDatas(List<HomeDataBean> datas) {
		this.datas = datas;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
			 
}
