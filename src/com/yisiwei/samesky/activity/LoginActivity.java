package com.yisiwei.samesky.activity;

import android.content.Intent;
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

/**
 * 
 * @ClassName: LoginActivity
 * @Description:登录界面
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	/**
	 * 用户名输入框
	 */
	private EditText mUsername;
	/**
	 * 密码输入框
	 */
	private EditText mPassword;
	/**
	 * 登录按钮
	 */
	private Button mLoginBtn;
	/**
	 * 注册按钮
	 */
	private Button mRegisterBtn;
	
	private static final int REGISTER_CODE = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_login);

		initView();
	}

	/**
	 * 
	 * @Title: initView
	 * @Description: 初始化View
	 * @param:
	 * @return: void
	 * @throws
	 */
	private void initView() {
		mUsername = (EditText) this.findViewById(R.id.et_username);
		mPassword = (EditText) this.findViewById(R.id.et_pwd);

		mLoginBtn = (Button) this.findViewById(R.id.login_btn);
		mLoginBtn.setOnClickListener(this);

		mRegisterBtn = (Button) this.findViewById(R.id.register_btn);
		mRegisterBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:// 登录
			
			String username = mUsername.getText().toString();
			String password = mPassword.getText().toString();
			
			if (StringUtil.isNullOrEmpty(username)) {
				Toast.show(this, "请输入用户名");
				return;
			}
			
			if (StringUtil.isNullOrEmpty(password)) {
				Toast.show(this, "请输入密码");
				return;
			}
			
			final User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.login(this, new SaveListener() {
				
				@Override
				public void onSuccess() {
					Toast.show(LoginActivity.this, "登录成功");
					// 登录成功跳转到主界面
					startActivity(new Intent(LoginActivity.this, MainActivity.class));
					finish();
				}
				
				@Override
				public void onFailure(int code, String msg) {
					Log.i("code:"+code+",msg:"+msg);
					if (code == 101) {
						Toast.show(getApplicationContext(), "用户名或密码错误");
					}
				}
			});
			
			break;
		case R.id.register_btn:// 注册
			startActivityForResult(new Intent(this, RegisterActivity.class),REGISTER_CODE);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REGISTER_CODE) {
				// 注册成功跳转到主界面
				startActivity(new Intent(LoginActivity.this, MainActivity.class));
				finish();
			}
		}
	}

}
