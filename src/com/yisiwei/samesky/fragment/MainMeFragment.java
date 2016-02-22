package com.yisiwei.samesky.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DeleteFileListener;
import com.bmob.btp.callback.UploadListener;
import com.bumptech.glide.Glide;
import com.yisiwei.samesky.R;
import com.yisiwei.samesky.activity.LoginActivity;
import com.yisiwei.samesky.activity.SettingActivity;
import com.yisiwei.samesky.bean.User;
import com.yisiwei.samesky.util.DensityUtil;
import com.yisiwei.samesky.util.FileUtils;
import com.yisiwei.samesky.util.Log;
import com.yisiwei.samesky.util.PreferencesUtil;
import com.yisiwei.samesky.util.StringUtil;
import com.yisiwei.samesky.util.Toast;
import com.yisiwei.samesky.view.MyProgressDialog;

public class MainMeFragment extends Fragment implements OnClickListener {

	private ImageView mUserAvatar;
	private TextView mSetting;
	private static final int SETTING_REQEUST_CODE = 100;

	private Uri mOutputFileUri;// 拍照Uri
	private File mImageFile;
	private File mImagePath = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);// 头像存储目录

	private static final int CHOOSE_IMG = 1000;
	private static final int TAKE_PICTURE = 2000;
	private static final int CROP_PICTURE = 3000;
	
	private User mUser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fr_main_me, container, false);
		initView(view);
		
		mUser = User.getCurrentUser(getActivity(), User.class);
		
		String avatar = mUser.getAvatar();
		if (!StringUtil.isNullOrEmpty(avatar)) {
			// 显示头像
			Glide.with(this).load(avatar).into(mUserAvatar);
		}
		
		return view;
	}

	private void initView(View view) {
		mSetting = (TextView) view.findViewById(R.id.me_setting);
		mSetting.setOnClickListener(this);

		mUserAvatar = (ImageView) view.findViewById(R.id.user_avatar);
		mUserAvatar.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_avatar:// 头像
			showSetAvatarDialog();
			break;
		case R.id.me_setting:// 设置
			startActivityForResult(new Intent(getActivity(),
					SettingActivity.class), SETTING_REQEUST_CODE);
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @Title: showSetAvatarDialog
	 * @Description: 选择头像Dialog void
	 * @throws
	 */
	private void showSetAvatarDialog() {

		// 加载布局文件
		View view = View.inflate(getActivity(), R.layout.dialog_set_avatar,
				null);
		TextView chooseImg = (TextView) view.findViewById(R.id.choose_img);
		TextView takePicture = (TextView) view.findViewById(R.id.take_picture);
		TextView cancel = (TextView) view.findViewById(R.id.cancel);

		// 创建Dialog
		final AlertDialog dialog = new AlertDialog.Builder(getActivity())
				.create();
		dialog.setCancelable(false);// 设置点击dialog以外区域不取消Dialog
		dialog.show();
		dialog.setContentView(view);
		dialog.getWindow().setLayout(
				DensityUtil.getWidth(getActivity()) / 3 * 2,
				DensityUtil.getHeight(getActivity()) / 3 * 2);

		// 选择本地图片
		chooseImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				chooseImg();
			}
		});

		// 拍照
		takePicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				takePicture();
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

	/**
	 * 
	 * @Title: chooseImg
	 * @Description: 选择本地图片 void
	 * @throws
	 */
	private void chooseImg() {
		// Intent intent = new Intent(Intent.ACTION_PICK,
		// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
		// startActivityForResult(intent, CHOOSE_IMG);

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);// ACTION_OPEN_DOCUMENT
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/jpeg");
		startActivityForResult(intent, CHOOSE_IMG);
	}

	/**
	 * 
	 * @Title: takePicture
	 * @Description: 拍照 void
	 * @throws
	 */
	private void takePicture() {
		// 判断存储卡是否可以用，可用进行存储
		if (FileUtils.hasSdcard()) {
			mImageFile = new File(mImagePath, "IMG_"
					+ System.currentTimeMillis() + ".jpg");// 图片名称

			try {
				if (mImageFile.exists()) {// 如果存在则删除
					mImageFile.delete();
				}
				// 创建文件
				mImageFile.createNewFile();
				Log.i("拍照存储路径：" + mImageFile.getPath());
			} catch (IOException e) {
				e.printStackTrace();
				Toast.show(getActivity(), "照片存储失败！");
			}
			mOutputFileUri = Uri.fromFile(mImageFile);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri);// 指定图片输出地址
			startActivityForResult(intent, TAKE_PICTURE);
		} else {
			Toast.show(getActivity(), "SD卡不可用");
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case SETTING_REQEUST_CODE:// 退出成功
				// 跳转到登录界面
				startActivity(new Intent(getActivity(), LoginActivity.class));
				getActivity().finish();
				break;
			case CHOOSE_IMG: // 选择本地图片
				Uri uri = data.getData();
				Log.i("uri:" + uri + "，scheme:" + uri.getScheme());
				if ("file".equalsIgnoreCase(uri.getScheme())) {
					String path = uri.getPath();
					Log.i("path:" + path);

					startPhotoZoom(uri, 340, CROP_PICTURE);
					mImageFile = new File(mImagePath, "IMG_"
							+ System.currentTimeMillis() + ".jpg");// 图片名称
				} else {
					Log.i("path:" + uri.getPath());
					String[] column = { MediaStore.Images.Media.DATA };
					Cursor cursor = getActivity().getContentResolver().query(
							data.getData(), column, null, null, null);
					if (cursor.moveToFirst()) {
						int column_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						String imgPath = cursor.getString(column_index);
						Log.i("imgPath:" + imgPath);
						startPhotoZoom(imgPath);
						mImageFile = new File(mImagePath, "IMG_"
								+ System.currentTimeMillis() + ".jpg");// 图片名称
					}
					cursor.close();
				}
				break;
			case TAKE_PICTURE:// 拍照
				startPhotoZoom(mOutputFileUri, 340, CROP_PICTURE);
				Log.i("拍照imagePath:" + mImageFile.getPath());
				break;
			case CROP_PICTURE: // 裁剪
				Bundle extras = data.getExtras();
				if (extras != null) {
					// 获取Bitmap对象
					Bitmap bitmap = extras.getParcelable("data");
					// // 压缩图片
					try {
						Log.i("imgPath:" + mImageFile.getAbsolutePath());
						// 创建FileOutputStream对象
						FileOutputStream fos = new FileOutputStream(mImageFile);
						// 开始压缩图片
						if (bitmap
								.compress(Bitmap.CompressFormat.JPEG, 50, fos)) {
							fos.flush();
							// 关闭流对象
							fos.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					upload(mImageFile.getAbsolutePath(),bitmap);
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 
	 * @Title: startPhotoZoom
	 * @Description: 图片裁剪
	 * @param uri
	 *            void
	 * @throws
	 */
	public void startPhotoZoom(String uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		try {
			intent.setData(Uri.parse(android.provider.MediaStore.Images.Media
					.insertImage(getActivity().getContentResolver(), uri, null,
							null)));

			// intent.setDataAndType(uri, "image/*");
			// 设置裁剪
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", 340);
			intent.putExtra("outputY", 340);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CROP_PICTURE);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param activity
	 * @param uri
	 * @param size
	 * @param requestCode
	 */
	public void startPhotoZoom(Uri uri, int size, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, requestCode);
	}

	private void upload(String filePath,final Bitmap bitmap) {
		BmobProFile.getInstance(getActivity())
				.upload(filePath, new UploadListener() {

					@Override
					public void onSuccess(final String fileName, String url,
							BmobFile file) {
						Log.i("文件上传成功：" + fileName + ",可访问的文件地址："
								+ file.getUrl());
						// fileName
						// ：文件名（带后缀），这个文件名是唯一的，开发者需要记录下该文件名，方便后续下载或者进行缩略图的处理
						// url ：文件地址
						// file :BmobFile文件类型，`V3.4.1版本`开始提供，用于兼容新旧文件服务。
						// 注：若上传的是图片，url地址并不能直接在浏览器查看（会出现404错误），需要经过`URL签名`得到真正的可访问的URL地址,
						// 当然，`V3.4.1`的版本可直接从'file.getUrl()'中获得可访问的文件地址。
						
						Toast.show(getActivity(), "上传成功");
						// 将图片显示到ImageView中
						mUserAvatar.setImageBitmap(bitmap);
						
						String avatar = PreferencesUtil.getString(getActivity(), "avatar_filename");
						if (!StringUtil.isNullOrEmpty(avatar)) {
							BmobProFile.getInstance(getActivity()).deleteFile(avatar, new DeleteFileListener() {

					            @Override
					            public void onError(int errorcode, String errormsg) {
					                Log.i("删除文件失败："+errormsg+"("+errorcode+")");
					            }

					            @Override
					            public void onSuccess() {
					                Log.i("删除文件成功");
					              //保存fileName
									PreferencesUtil.putString(getActivity(), "avatar_filename", fileName);
					            }
					        });
						}else{
							//保存fileName
							PreferencesUtil.putString(getActivity(), "avatar_filename", fileName);
						}
						
						//更新用户信息
						mUser.setAvatar(file.getUrl());
						mUser.update(getActivity(),mUser.getObjectId(),new UpdateListener() {
							
							@Override
							public void onSuccess() {
								Log.i("更新头像成功");
							}
							
							@Override
							public void onFailure(int code, String msg) {
								Log.i("更新头像失败,code:"+code+",msg:"+msg);
							}
						});
					}

					@Override
					public void onProgress(int progress) {
						Log.i("onProgress :" + progress);
					}

					@Override
					public void onError(int statuscode, String errormsg) {
						Log.i("文件上传失败：" + errormsg);
					}
				});
	}
}
