package com.yisiwei.samesky.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.yisiwei.samesky.R;
import com.yisiwei.samesky.bean.User;
import com.yisiwei.samesky.util.Log;

public class WelcomeActivity extends Activity {

	private User mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_welcome);

		// 获取本地用户信息
		mUser = User.getCurrentUser(this, User.class);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				startApp();
			}
		}, 2000);
	}

	private void startApp() {
		Intent intent = null;
		// 如果已经登录，跳转到主界面
		if (mUser != null) {
			Log.i(mUser.getUsername());
			//Log.i(mUser.getAvatar());
			intent = new Intent(WelcomeActivity.this, MainActivity.class);
		} else {// 否则，跳转到登录界面
			intent = new Intent(WelcomeActivity.this, LoginActivity.class);
		}
		startActivity(intent);
		this.finish();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
