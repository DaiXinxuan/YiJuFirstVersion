package main;

import handmark.pulltorefresh.library.PullToRefreshListView;
import hx_util.propellingMessageTurnTo;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

import testandmanage.LogUtil;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_adapter.LatestNewsAdapter;
import differentjavabean.LatestNewsTable;

public class LatestNewsActivity extends Activity {
	private ListView lv;
	private ImageView nozixun;
	private ImageButton back;
	private LatestNewsAdapter adapter;
	private List<LatestNewsTable> list,unreadList;
	private List<Integer> unreadDataIndex;
	private TextView titleTextView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.remote_notification_layout);
		init();
		initData();
		if(list!=null){
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
				  	LatestNewsTable data=unreadList.get((int) arg3);
				  	Integer i=unreadDataIndex.get((int) arg3);
				  	list.get(i).setIsRead(true);
				    propellingMessageTurnTo turnTo=new propellingMessageTurnTo();
				    turnTo.turnToActivity(null, data);
				    
				    DataSupport.deleteAll(LatestNewsTable.class);
				    
				    for (int j = 0; j < list.size(); j++) {
						LatestNewsTable saveData=new LatestNewsTable();
						LatestNewsTable getMessageData=list.get(j);
						saveData.setActivityid(getMessageData.getActivityid());
						saveData.setContent(getMessageData.getContent());
						saveData.setHtml(getMessageData.getHtml());
						saveData.setIsRead(getMessageData.getIsRead());
						saveData.setName(getMessageData.getName());
						saveData.setPhotopath(getMessageData.getPhotopath());
						saveData.setTime(getMessageData.getTime());
						saveData.setType(getMessageData.getType());
                        saveData.setProid(getMessageData.getProid());
                        saveData.save();
						
					}
				    initData();
				    
				}
				
			});
		}
	}
	
	private void initData(){
		
		list=DataSupport.findAll(LatestNewsTable.class);
		LogUtil.d("推送数组", "数据："+list);
	    if(list!=null&&list.size()>0){	
	    	LogUtil.d("推送数组", "数据："+list+list.size());
	    	unreadList=new ArrayList<LatestNewsTable>();
			unreadDataIndex=new ArrayList<Integer>();
	    for (int i = 0; i < list.size(); i++) {
	    	LogUtil.d("推送数组", "未阅读数量："+unreadList.size());
			LatestNewsTable newsTable=list.get(i);
			if(newsTable.getIsRead()==false){
				LogUtil.d("推送数组", "未阅读数量："+unreadList.size());
				unreadList.add(newsTable);
				unreadDataIndex.add(i);
			}
		}
	    
	    if(unreadList.size()==0){
	    	nozixun.setVisibility(View.VISIBLE);
	    	lv.setVisibility(View.GONE);
	    	Toast.makeText(LatestNewsActivity.this, "您当前没有未读的最新资讯,可以前往消息中心查看历史咨询", Toast.LENGTH_SHORT).show();
	    }
	    
		adapter=new LatestNewsAdapter(unreadList);
		lv.setAdapter(adapter);
		
	    }else {
	    	nozixun.setVisibility(View.VISIBLE);
	    	lv.setVisibility(View.GONE);
			Toast.makeText(LatestNewsActivity.this, "您还未收到最新资讯", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private void init() {
		nozixun = (ImageView) findViewById(R.id.noNews);
		lv=(ListView) findViewById(R.id.remotemessage_lv);
		titleTextView=(TextView)findViewById(R.id.remotemessage_title_text);
		titleTextView.setText("最新资讯");
		back=(ImageButton) findViewById(R.id.remotemessage_goback);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				LatestNewsActivity.this.finish();
			}
		});
		
	}
	
}

