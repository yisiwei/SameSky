package com.yisiwei.samesky.bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

	private static final long serialVersionUID = 2530528172509193703L;

	private String avatar;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
