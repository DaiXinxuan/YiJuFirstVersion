package main;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import testandmanage.JSONCommand;
import web.WebActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.MediaStore.Video;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.mapcore.ad;
import com.example.haofangchan2.R;

import different_adapter.MyActivitiesAdapter;
import different_jsonparse.MyActivityParser;
import differentjavabean.AttentionRoomModel;
import differentjavabean.MyActivityModel;

public class MyActivitiesActivity extends Activity {
	private PullToRefreshListView lv;
	private ImageView noActivity;
	private TextView collect;
	private ImageButton back;
	private MyActivitiesAdapter adapter;
	private Map<String,String> map;
	private WebView web;
	private List<MyActivityModel> list;
	private int myActivitiesIndex;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.myactivitiesview);
		init();
		updateUI();
	}
	
	public void  updateUI() {
		map = JSONCommand.JSON10027("6", "1", ""+myActivitiesIndex, "20");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				list.addAll((List<MyActivityModel>) model);
				if(list.size()==0){
					noActivity.setVisibility(View.VISIBLE);
					lv.setVisibility(View.INVISIBLE);
				}else{
					adapter.notifyDataSetChanged();
					if(lv.isRefreshing()){
						lv.onRefreshComplete();
					}
				}
			}
		}, new MyActivityParser());
	}
	private void init() {
		noActivity = (ImageView) findViewById(R.id.noActivity);
		lv=(PullToRefreshListView) findViewById(R.id.myactivities_lv);
		lv.setMode(Mode.BOTH);
		list=new ArrayList<MyActivityModel>();
		back=(ImageButton) findViewById(R.id.myactivities_goback);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MyActivitiesActivity.this.finish();
			}
		});
		adapter=new MyActivitiesAdapter(list);
		lv.setAdapter(adapter);	
		lv.setOnRefreshListener(new OnRefreshListener2 <ListView> () {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
			     myActivitiesIndex=0;
			     list.clear();
			     updateUI();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				myActivitiesIndex++;
				updateUI();
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position != 0){
					position -= 1;
					MyActivityModel tempModel = list.get(position);
					Intent i = new Intent(MyActivitiesActivity.this,WebActivity.class);
					i.putExtra("title", "¾«²Ê»î¶¯");
					i.putExtra("url", tempModel.getUrl());
					startActivity(i);
				}
			}
			
		});
	}
	
	
	
}
