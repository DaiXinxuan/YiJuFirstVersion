package main;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haofangchan2.R;

import different_adapter.AttentionAdater;
import different_jsonparse.AttentionRoomParser;
import differentjavabean.AttentionRoomModel;

public class AttentionRoom_Activity extends Activity {
	private ImageView iv,noroom;
	private TextView edit, title;
	private PullToRefreshListView lv;
	private List<AttentionRoomModel> listdata = new ArrayList<AttentionRoomModel>();
	private int attentionRoomIndex;
    private AttentionAdater adapter;
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.attention_layout);
		init();
		
		updateUI();
	}

	public void updateUI() {
		HashMap<String, String> map = JSONCommand
				.JSON10031("6", "1", ""+attentionRoomIndex, "20");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				listdata.addAll((List<AttentionRoomModel>) model);
				if(listdata.size()==0){
					noroom.setVisibility(View.VISIBLE);
					lv.setVisibility(View.INVISIBLE);
				}else{
					adapter.notifyDataSetChanged();
					if(lv.isRefreshing()){
						lv.onRefreshComplete();
					}
				}
			}
		}, new AttentionRoomParser());
	}

	private void init() {
		noroom = (ImageView) findViewById(R.id.noRoom);
		iv = (ImageView) findViewById(R.id.goback);
		iv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AttentionRoom_Activity.this.finish();
			}
		});
		lv = (PullToRefreshListView) findViewById(R.id.roomnumber_lv);
		listdata = new ArrayList<AttentionRoomModel>();
		title = (TextView) findViewById(R.id.attention);
		title.setText("关注的户型");
		lv.setMode(Mode.BOTH);
		adapter=new AttentionAdater(listdata);
		lv.setAdapter(adapter);
		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				attentionRoomIndex = 0;
				listdata.clear();
				updateUI();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				attentionRoomIndex++;
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
					AttentionRoomModel tempModel = listdata.get(position);
					Intent i = new Intent(AttentionRoom_Activity.this,HouseinfoActivity.class);
					i.putExtra("houseid", tempModel.getRommId());
					startActivity(i);
				}
			}
		});
	}

}
