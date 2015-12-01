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

import org.litepal.util.LogUtil;

import testandmanage.JSONCommand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_adapter.CommentAdapter;
import different_jsonparse.UserRoomCommentParser;
import differentjavabean.UserRoomCommentModel;

public class CommnetActivity extends Activity {
	private boolean open = false;
	private ImageView more, back,nocomment;
	private PullToRefreshListView lv;
	private LinearLayout commentlist;
	private FrameLayout frame;
	private RelativeLayout collect, share, write;
	private CommentAdapter adapter;
	private List<UserRoomCommentModel> list;
	private int commentId;
	private HashMap<String, String> map;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.housecomment);
		commentId = 0;
		init();
		updateUI();
	}

	public void updateUI() {
		if (getIntent().getBooleanExtra("isMyComment", false)) {
			map = JSONCommand.JSON10063("6", "1", "10", "" + commentId, "-1");
		} else {
			map = JSONCommand.JSON10014("6", "1", "" + commentId, "10", ""
					+ getIntent().getStringExtra("houseid"));
		}
		ServerAsyncHttpTask.execute(
				map,
				new UpdateUIInterface() {

					@SuppressWarnings("unchecked")
					@Override
					public void updateUI(Object model) {
						// TODO Auto-generated method stub
						List<UserRoomCommentModel> list2 = ((List<UserRoomCommentModel>) model);
						LogUtil.d("WWWWW", list2.size()+"");
						list.addAll((List<UserRoomCommentModel>) model);
						//Toast.makeText(getApplicationContext(), list.size()+"", Toast.LENGTH_SHORT).show();
						if (list.size() != 0) {
//							LogUtil.d("返回的评论数据3", "大小2" + list2.size() + "值为"
//									+ list);
							adapter.notifyDataSetChanged();

							if (lv.isRefreshing()) {
								lv.onRefreshComplete();
							}
						} else {
							nocomment.setVisibility(View.VISIBLE);
							lv.setVisibility(View.INVISIBLE);
							Toast.makeText(CommnetActivity.this, "没有评论",
									Toast.LENGTH_SHORT).show();
						}
					}
				},
				new UserRoomCommentParser(getIntent().getBooleanExtra(
						"isMyComment", false)));
	}

	private void init() {
		nocomment = (ImageView) findViewById(R.id.nocomment);
		more = (ImageView) findViewById(R.id.comment_more);
		back = (ImageView) findViewById(R.id.comment_back);
		lv = (PullToRefreshListView) findViewById(R.id.comment_lv);
		// commentlist = (LinearLayout) findViewById(R.id.comment_list);
		frame = (FrameLayout) findViewById(R.id.comment_frames);
		// collect = (RelativeLayout) findViewById(R.id.comment_collect);
		// share = (RelativeLayout) findViewById(R.id.comment_share);
		// write = (RelativeLayout) findViewById(R.id.comment_write);
		list = new ArrayList<UserRoomCommentModel>();
		lv.setMode(Mode.BOTH);
		adapter = new CommentAdapter(list);
		lv.setAdapter(adapter);
		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				commentId = 0;
				list.clear();
				updateUI();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				commentId++;
				updateUI();
			}
		});
		if (getIntent().getBooleanExtra("isMyComment", false)) {
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(getApplicationContext(),
							HouseinfoActivity.class);
					Log.d("houseid", arg2 + "");
					Log.d("houseid", list.get(arg2 - 1).getId());
					intent.putExtra("houseid", list.get(arg2 - 1).getId());
					startActivity(intent);
				}
			});
		}
		if (getIntent().getBooleanExtra("isMyComment", false)) {
			// commentlist.setVisibility(View.GONE);
			more.setVisibility(View.GONE);
			open = false;
		} else
			more.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(CommnetActivity.this,
							SentCommentActivity.class);
					intent.putExtra("houseid",
							getIntent().getStringExtra("houseid"));
					intent.putExtra("housenum",
							getIntent().getStringExtra("housenum"));
					startActivityForResult(intent, 1);
				}
			});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		frame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (open) {
					commentlist.setVisibility(View.GONE);
					open = false;
				}
			}
		});
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			list.clear();
			updateUI();
			lv.setVisibility(View.VISIBLE);
			nocomment.setVisibility(View.INVISIBLE);
			break;
		}
		
	}
}
