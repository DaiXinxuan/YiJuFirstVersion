package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main_fragment.IMessageFragmentRefresh;

import testandmanage.JSONCommand;
import testandmanage.LogUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haofangchan2.R;

import different_adapter.FriendAdapter;
import different_adapter.contactAdapter;
import different_jsonparse.MyfriendActivity_jsonparse;
import differentjavabean.MyfriendActivity_javabean;

public class searchContact extends Activity {

	private ImageButton back = null;// 回退按钮
	private TextView searchText = null;// 用于隐藏或显示搜索框中的控件
	private ListView mlistview = null;// 存储搜索结果的listview
	private contactAdapter mAdapter = null;// 初始化listview所用的adapter
	private EditText search = null;// 搜索框
	private ImageView searchImg = null;// 用于隐藏或显示搜索框中的控件
	private ArrayList<MyfriendActivity_javabean> friendlist,
			searchResultFriendList;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.search_contact);

//		intent = getIntent();
//		Bundle bundle = intent.getBundleExtra("searchListBundle");
//		if (bundle != null) {
//			LogUtil.d("搜索界面得到数据bundle：", "" + bundle);
//			friendlist = bundle.getParcelableArrayList("searchFriendList");
//			LogUtil.d("搜索界面得到数据：", "" + friendlist);
//			MyfriendActivity_javabean bean = friendlist.get(0);
//
//			LogUtil.d("搜索界面得到数据单个：", "" + bean);
//
//			
//		}
		initViriable();// 初始化变量
		initContactsList();// 初始化好友列表
		onClickListeners();// 响应事件
	}

	private void initContactsList() {
		// TODO Auto-generated method stub
		Map<String, String> map;
		map = JSONCommand.JSON10030("6", "1");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				LogUtil.d("数据重新加载", "更新ui");
				@SuppressWarnings("unchecked")
				List<MyfriendActivity_javabean> myFriends = (List<MyfriendActivity_javabean>) model;
				friendlist = (ArrayList<MyfriendActivity_javabean>) myFriends;
				mAdapter = new contactAdapter(searchContact.this, myFriends);
				mlistview.setAdapter(mAdapter);

			}
		}, new MyfriendActivity_jsonparse());
		

	}

	private void onClickListeners() {
		// TODO Auto-generated method stub
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		search.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {// 点击时隐藏控件
					searchImg.setVisibility(View.GONE);
					searchText.setVisibility(View.GONE);
				} else {
					searchImg.setVisibility(View.VISIBLE);
					searchText.setVisibility(View.VISIBLE);
				}
			}
		});

		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String name = search.getText().toString();
				searchResultFriendList = new ArrayList<MyfriendActivity_javabean>();
				for (int i = 0; i < friendlist.size(); i++) {
					MyfriendActivity_javabean myfriendActivity_javabean = friendlist
							.get(i);
					if (matching(name, myfriendActivity_javabean.getNickname())) {
						searchResultFriendList.add(myfriendActivity_javabean);// 如果匹配成功将数据存入新的List中
					}
				}
				mAdapter = new contactAdapter(searchContact.this,
						searchResultFriendList);
				mlistview.setAdapter(mAdapter);
			}

			private boolean matching(String name, String matchString) {// 进行正则匹配
				// TODO Auto-generated method stub
				Pattern p = Pattern.compile("^.*?" + name + ".*?$");
				Matcher m = p.matcher(matchString);
				return m.matches();
			}
		});
	}

	private void initViriable() {
		// TODO Auto-generated method stub
		back = (ImageButton) findViewById(R.id.search_contact_back);
		searchText = (TextView) findViewById(R.id.search_contact_searchtext);
		mlistview = (ListView) findViewById(R.id.search_contact_contactsList);
		search = (EditText) findViewById(R.id.search_contact_search);
		searchImg = (ImageView) findViewById(R.id.search_contact_searchimg);
	}

}
