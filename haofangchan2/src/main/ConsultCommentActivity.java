package main;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;

import testandmanage.JSONCommand;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.haofangchan2.R;

import different_adapter.ConsultantCommentAdapter;
import different_jsonparse.ConsultantCommentParser;
import differentjavabean.ConsultantCommentModel;

public class ConsultCommentActivity extends Activity {

	private PullToRefreshListView lv;
	private ImageButton back;
	private ConsultantCommentAdapter adapter;
	private ArrayList<ConsultantCommentModel> list;
	private String salerid, salername;
	private int commentIndex;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.consultcommnetview);

		salerid = getIntent().getStringExtra("salerid");
		salername = getIntent().getStringExtra("salername");
		list = new ArrayList<ConsultantCommentModel>();
		lv = (PullToRefreshListView) findViewById(R.id.consultcommnet_lv);
		adapter = new ConsultantCommentAdapter(list);
		lv.setAdapter(adapter);
		back = (ImageButton) findViewById(R.id.consultcommnet_goback);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				ConsultCommentActivity.this.finish();
			}
		});
		lv.setMode(Mode.BOTH);
		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub

				commentIndex = 0;
				list.clear();
				updateUI();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				commentIndex++;
				updateUI();
			}
		});
		updateUI();
	}

	public void updateUI() {
		HashMap<String, String> map = JSONCommand.JSON10029("6", "1", ""
				+ commentIndex, "15", salerid, salername);
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				list.addAll((ArrayList<ConsultantCommentModel>) model);
				adapter.notifyDataSetChanged();
				if(lv.isRefreshing()){
					lv.onRefreshComplete();
				}
			}
		}, new ConsultantCommentParser());
	}
}
