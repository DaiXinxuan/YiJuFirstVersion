package main;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.Map;

import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import web.WebActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.haofangchan2.R;

import different_adapter.MagazineAdapter;
import different_jsonparse.PersonalMagazineParser;
import differentjavabean.PersonalMagazineModel;

public class ReceiveMagazineActivity extends Activity {
	private PullToRefreshListView lv;
	private ImageButton back;
	private MagazineAdapter adapter;
	private Map<String, String> map;
	private ArrayList<PersonalMagazineModel> list;
	private WebView web;
	private int magezineIndex;
	private ImageView noMag;
    private Boolean isProjectMagazine;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.recievemagazineview);
		isProjectMagazine=getIntent().getBooleanExtra("isProjectMagazine", false);
		init();
		initData();
	}

	private void init() {
		noMag = (ImageView) findViewById(R.id.noMagzine);
		web = new WebView(getApplicationContext());
		list = new ArrayList<PersonalMagazineModel>();
		lv = (PullToRefreshListView) findViewById(R.id.magazine_list);
		lv.setMode(Mode.BOTH);
		adapter = new MagazineAdapter(list);
		lv.setAdapter(adapter);
		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				magezineIndex = 0;
				list.clear();
				initData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				magezineIndex++;
				initData();

			}

		});
		back = (ImageButton) findViewById(R.id.magazine_goback);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
		// 设置列表监听事件，添加关联的网址
	}

	private void initData() {
		if(isProjectMagazine){
			map = JSONCommand.JSON10020("6", "1", ""+magezineIndex,"10" ); 
		}else{
			map = JSONCommand.JSON10028("6", "1", ""+magezineIndex, "10");
		}
		
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				list.addAll((ArrayList<PersonalMagazineModel>) model);
				if(list.size()==0){
					noMag.setVisibility(View.VISIBLE);
					lv.setVisibility(View.INVISIBLE);
				}else{
					adapter.notifyDataSetChanged();
					lv.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							Intent intent = new Intent(
									ReceiveMagazineActivity.this, WebActivity.class);
							intent.putExtra("url", list.get((int) id).getUrl());
							intent.putExtra("title", "精品杂志");
							startActivity(intent);
						}
					});
					if (lv.isRefreshing()) {
						lv.onRefreshComplete();
					}
				}
			}
		}, new PersonalMagazineParser());
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack(); // goBack()表示返回WebView的上一页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
