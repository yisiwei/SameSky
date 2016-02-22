package com.yisiwei.samesky.fragment;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;

import com.yisiwei.samesky.R;
import com.yisiwei.samesky.util.Log;

public class MainSessionFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fr_main_session, container, false);
		
		List<BmobIMConversation> imConversations = BmobIM.getInstance().loadAllConversation();
		Log.i("会话列表数："+imConversations.size());
		
		BmobIMUserInfo userInfo = new BmobIMUserInfo();

		return view;
	}

}
