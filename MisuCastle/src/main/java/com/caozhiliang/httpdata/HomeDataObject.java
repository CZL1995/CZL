package com.caozhiliang.httpdata;

import java.util.List;

public class HomeDataObject {
	 private List<HomeDataBean> vp;
	 private List<HomeDataBean> pic;
	 private List<HomeDataBean> list;
	 
	 
	public HomeDataObject() {
		super();
	}

	public HomeDataObject(List<HomeDataBean> vp, List<HomeDataBean> pic,
			List<HomeDataBean> list) {
		super();
		this.vp = vp;
		this.pic = pic;
		this.list = list;
	}

	public List<HomeDataBean> getVp() {
		return vp;
	}

	public void setVp(List<HomeDataBean> vp) {
		this.vp = vp;
	}

	public List<HomeDataBean> getPic() {
		return pic;
	}

	public void setPic(List<HomeDataBean> pic) {
		this.pic = pic;
	}

	public List<HomeDataBean> getList() {
		return list;
	}

	public void setList(List<HomeDataBean> list) {
		this.list = list;
	}
	
	 
}