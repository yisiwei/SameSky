package com.yisiwei.samesky.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.yisiwei.samesky.R;
import com.yisiwei.samesky.util.DensityUtil;
import com.yisiwei.samesky.util.StringUtil;

public class MyDialog {

	public interface OnConfirmListener {
		public void onConfirmClick();
	}

	public static void show(Activity activity, String content,
			final OnConfirmListener confirmListener) {
		// 加载布局文件
		View view = View.inflate(activity, R.layout.dialog, null);
		TextView text = (TextView) view.findViewById(R.id.text);
		TextView confirm = (TextView) view.findViewById(R.id.confirm);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);

		if (!StringUtil.isNullOrEmpty(content)) {
			text.setText(content);
		}

		// 创建Dialog
		final AlertDialog dialog = new AlertDialog.Builder(activity).create();
		dialog.setCancelable(false);// 设置点击dialog以外区域不取消Dialog
		dialog.show();
		dialog.setContentView(view);
		dialog.getWindow().setLayout(DensityUtil.getWidth(activity) / 3 * 2,
				LayoutParams.WRAP_CONTENT);

		// 确定
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				confirmListener.onConfirmClick();
			}
		});
		// 取消
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

}
