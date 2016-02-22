package com.yisiwei.samesky.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.v3.listener.SaveListener;

import com.yisiwei.samesky.R;
import com.yisiwei.samesky.bean.User;
import com.yisiwei.samesky.util.Log;
import com.yisiwei.samesky.util.StringUtil;
import com.yisiwei.samesky.util.Toast;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	private EditText mUsername;
	private EditText mPassword;
	private EditText mConfirmPwd;

	private Button mRegisterBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ac_register);
		initView();
	}

	private void initView() {
		mUsername = (EditText) this.findViewById(R.id.et_username);
		mPassword = (EditText) this.findViewById(R.id.et_password);
		mConfirmPwd = (EditText) this.findViewById(R.id.et_confirm_pwd);

		mRegisterBtn = (Button) this.findViewById(R.id.btn_register);
		mRegisterBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:// 注册
			register();
			break;
		default:
			break;
		}
	}

	private void register() {
		String username = mUsername.getText().toString();
		String password = mPassword.getText().toString();
		String confirmPwd = mConfirmPwd.getText().toString();

		if (StringUtil.isNullOrEmpty(username)) {
			Toast.show(this, "请输入用户名");
			return;
		}
		if (StringUtil.isNullOrEmpty(password)) {
			Toast.show(this, "请输入密码");
			return;
		}
		if (StringUtil.isNullOrEmpty(confirmPwd)) {
			Toast.show(this, "请输入确认密码");
			return;
		}
		if (!password.equals(confirmPwd)) {
			Toast.show(this, "两次密码输入不一致");
			return;
		}

		final User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		user.signUp(this, new SaveListener() {

			@Override
			public void onSuccess() {
				Toast.show(getApplicationContext(), "注册成功");
				setResult(RESULT_OK);
				finish();
				Log.i(user.getObjectId());
			}

			@Override
			public void onFailure(int code, String msg) {
				Log.i("code:" + code + ",msg:" + msg);
			}
		});
	}
}
