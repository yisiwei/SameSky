package com.yisiwei.samesky;

import cn.bmob.newim.BmobIM;
import android.app.Application;

public class MyApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// im初始化
		BmobIM.init(this);
	}

}
