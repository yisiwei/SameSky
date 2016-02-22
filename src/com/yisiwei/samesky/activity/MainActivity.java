package com.yisiwei.samesky.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yisiwei.samesky.R;
import com.yisiwei.samesky.fragment.MainContactsFragment;
import com.yisiwei.samesky.fragment.MainSessionFragment;
import com.yisiwei.samesky.fragment.MainMeFragment;
import com.yisiwei.samesky.util.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private TextView mTabSession;
	private TextView mTabContacts;
	private TextView mTabMe;

	private FragmentManager mFragmentManager;
	private FragmentTransaction mTransaction;

	private int mCurrentTab = 1;// 当前Tab

	private long mTouchTime = 0;
	private long mWaitTime = 2000;// 再按一次退出等待时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_main);
		initView();

		mFragmentManager = getFragmentManager();

		setTabSelection(1);
	}

	private void initView() {
		mTabSession = (TextView) this.findViewById(R.id.tab_session);
		mTabContacts = (TextView) this.findViewById(R.id.tab_contacts);
		mTabMe = (TextView) this.findViewById(R.id.tab_me);

		mTabSession.setOnClickListener(this);
		mTabContacts.setOnClickListener(this);
		mTabMe.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	private Drawable getTopDrawable(int resId) {
		Drawable drawable = getResources().getDrawable(resId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		return drawable;
	}

	public void setTabSelection(int index) {
		Fragment fragment = null;
		mTransaction = mFragmentManager.beginTransaction();

		Drawable sessionDrawable = null;
		Drawable contactsDrawable = null;
		Drawable meDrawable = null;

		switch (index) {
		case 1:
			fragment = new MainSessionFragment();

			mTabSession.setTextColor(getResources()
					.getColor(R.color.color_main));
			mTabContacts
					.setTextColor(getResources().getColor(R.color.color_64));
			mTabMe.setTextColor(getResources().getColor(R.color.color_64));

			sessionDrawable = getTopDrawable(R.drawable.tab_message_on);
			contactsDrawable = getTopDrawable(R.drawable.tab_user_center);
			meDrawable = getTopDrawable(R.drawable.tab_user_center);

			mCurrentTab = 1;

			break;
		case 2:

			fragment = new MainContactsFragment();

			mTabSession.setTextColor(getResources().getColor(R.color.color_64));
			mTabContacts.setTextColor(getResources().getColor(
					R.color.color_main));
			mTabMe.setTextColor(getResources().getColor(R.color.color_64));

			sessionDrawable = getTopDrawable(R.drawable.tab_message);
			contactsDrawable = getTopDrawable(R.drawable.tab_user_center_on);
			meDrawable = getTopDrawable(R.drawable.tab_user_center);

			mCurrentTab = 2;

			break;
		case 3:
			fragment = new MainMeFragment();

			mTabSession.setTextColor(getResources().getColor(R.color.color_64));
			mTabContacts
					.setTextColor(getResources().getColor(R.color.color_64));
			mTabMe.setTextColor(getResources()
					.getColor(R.color.color_main));

			sessionDrawable = getTopDrawable(R.drawable.tab_message);
			contactsDrawable = getTopDrawable(R.drawable.tab_user_center);
			meDrawable = getTopDrawable(R.drawable.tab_user_center_on);

			mCurrentTab = 3;

			break;
		}

		mTabSession.setCompoundDrawables(null, sessionDrawable, null, null);
		mTabContacts.setCompoundDrawables(null, contactsDrawable, null, null);
		mTabMe.setCompoundDrawables(null, meDrawable, null, null);
		mTransaction.replace(R.id.index_content, fragment);
		mTransaction.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_session:
			if (mCurrentTab != 1) {
				setTabSelection(1);
			}
			break;
		case R.id.tab_contacts:
			if (mCurrentTab != 2) {
				setTabSelection(2);
			}
			break;
		case R.id.tab_me:
			if (mCurrentTab != 3) {
				setTabSelection(3);
			}
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回键监听
			long currentTime = System.currentTimeMillis();
			if ((currentTime - mTouchTime) > mWaitTime) {
				Toast.show(this, "再按一次退出程序");
				mTouchTime = currentTime;
			} else {
				this.finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
