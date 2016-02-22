package com.yisiwei.samesky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yisiwei.samesky.R;
import com.yisiwei.samesky.bean.User;
import com.yisiwei.samesky.util.Log;
import com.yisiwei.samesky.view.MyDialog;
import com.yisiwei.samesky.view.MyDialog.OnConfirmListener;

public class SettingActivity extends BaseActivity implements OnClickListener {

	private TextView mUpdatePwd;
	private TextView mLogOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ac_setting);

		initView();
	}

	private void initView() {
		mLogOut = (TextView) this.findViewById(R.id.logout);
		mLogOut.setOnClickListener(this);
		
		mUpdatePwd = (TextView) this.findViewById(R.id.update_pwd);
		mUpdatePwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.update_pwd://修改密码
			startActivity(new Intent(this, UpdatePwdActivity.class));
			break;
		case R.id.logout:// 退出
			MyDialog.show(this, "确定退出登录吗?", new OnConfirmListener() {

				@Override
				public void onConfirmClick() {
					logout();
				}
			});
			break;

		default:
			break;
		}
	}

	private void logout() {
		Log.i("退出成功");
		User.logOut(this);// 退出登录
		setResult(RESULT_OK);
		finish();
	}
}
