package com.yisiwei.samesky.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.yisiwei.samesky.R;

public class MyProgressDialog {

	public Dialog mDialog;
	
	private AnimationDrawable animationDrawable = null;

	public MyProgressDialog(Context context) {

		View view = View.inflate(context, R.layout.progress_view, null);

		ImageView loadingImage = (ImageView) view
				.findViewById(R.id.progress_view);
		loadingImage.setImageResource(R.anim.loading_animation);
		animationDrawable = (AnimationDrawable) loadingImage.getDrawable();
		if (animationDrawable != null) {
			animationDrawable.setOneShot(false);
			animationDrawable.start();
		}

		mDialog = new Dialog(context, R.style.dialog);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(false);

	}

	/**
	 * 显示对话框
	 */
	public void show() {
		mDialog.show();
	}

	/**
	 * 点击对话框外面是否取消对话框
	 * 
	 * @param cancel
	 */
	public void setCanceledOnTouchOutside(boolean cancel) {
		mDialog.setCanceledOnTouchOutside(cancel);
	}

	/**
	 * 取消对话框
	 */
	public void dismiss() {
		if (mDialog.isShowing()) {
			mDialog.dismiss();
			animationDrawable.stop();
		}
	}

	/**
	 * 是否已经显示
	 * 
	 * @return
	 */
	public boolean isShowing() {
		if (mDialog.isShowing()) {
			return true;
		}
		return false;
	}
}
