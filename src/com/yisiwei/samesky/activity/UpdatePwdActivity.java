package com.yisiwei.samesky.activity;

import cn.bmob.v3.listener.UpdateListener;

import com.yisiwei.samesky.R;
import com.yisiwei.samesky.bean.User;
import com.yisiwei.samesky.util.Log;
import com.yisiwei.samesky.util.StringUtil;
import com.yisiwei.samesky.util.Toast;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class UpdatePwdActivity extends BaseActivity implements OnClickListener {

	private EditText mOldPwd;
	private EditText mNewPwd;
	private EditText mConfirmPwd;

	private Button mSubmitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ac_update_pwd);
		initView();
	}

	private void initView() {
		mOldPwd = (EditText) this.findViewById(R.id.et_old_pwd);
		mNewPwd = (EditText) this.findViewById(R.id.et_new_pwd);
		mConfirmPwd = (EditText) this.findViewById(R.id.et_confirm_pwd);

		mSubmitBtn = (Button) this.findViewById(R.id.btn_submit);
		mSubmitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_submit:
			submit();
			break;

		default:
			break;
		}
	}

	private void submit() {
		String oldPwd = mOldPwd.getText().toString();
		String newPwd = mNewPwd.getText().toString();
		String confirmPwd = mConfirmPwd.getText().toString();

		if (StringUtil.isNullOrEmpty(oldPwd)) {
			Toast.show(this, "请输入旧密码");
			return;
		}
		if (StringUtil.isNullOrEmpty(newPwd)) {
			Toast.show(this, "请输入新密码");
			return;
		}
		if (StringUtil.isNullOrEmpty(confirmPwd)) {
			Toast.show(this, "请输入确认密码");
			return;
		}
		if (!newPwd.equals(confirmPwd)) {
			Toast.show(this, "两次密码不一致");
			return;
		}

		User.updateCurrentUserPassword(this, oldPwd, newPwd,
				new UpdateListener() {

					@Override
					public void onSuccess() {
						Toast.show(getApplicationContext(), "修改成功");
					}

					@Override
					public void onFailure(int code, String msg) {
						Log.i("code:" + code + ",msg:" + msg);
						if (code == 210) {
							Toast.show(getApplicationContext(), "旧密码错误");
						}
					}
				});
	}

}
