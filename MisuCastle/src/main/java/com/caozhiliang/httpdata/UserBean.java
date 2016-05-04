package com.caozhiliang.httpdata;

public class UserBean {
	String name;
	String phone;
	String key;
	String usernumber;
	String sellernumber;

	@Override
	public String toString() {
		return "UserBean{" +
				"name='" + name + '\'' +
				", phone='" + phone + '\'' +
				", key='" + key + '\'' +
				", usernumber='" + usernumber + '\'' +
				", sellernumber='" + sellernumber + '\'' +
				", storenumber='" + storenumber + '\'' +
				", shoucan='" + shoucan + '\'' +
				", address='" + address + '\'' +
				", chat='" + chat + '\'' +
				", comment='" + comment + '\'' +
				", image='" + image + '\'' +
				'}';
	}

	public String getSellernumber() {
		return sellernumber;
	}

	public void setSellernumber(String sellernumber) {
		this.sellernumber = sellernumber;
	}

	public String getStorenumber() {
		return storenumber;
	}

	public void setStorenumber(String storenumber) {
		this.storenumber = storenumber;
	}

	String storenumber;

	public String getUsernumber() {
		return usernumber;
	}

	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}

	String shoucan;
	String address;
	String chat;
	String comment;
	String image;

	public UserBean() {
		super();
	}
	public UserBean(String name, String phone, String key, String shoucan,
					String address, String chat, String comment, String image) {
		super();
		this.name = name;
		this.phone = phone;
		this.key = key;
		this.shoucan = shoucan;
		this.address = address;
		this.chat = chat;
		this.comment = comment;
		this.image = image;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getShoucan() {
		return shoucan;
	}
	public void setShoucan(String shoucan) {
		this.shoucan = shoucan;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getChat() {
		return chat;
	}
	public void setChat(String chat) {
		this.chat = chat;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
