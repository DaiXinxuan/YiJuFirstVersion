package main;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import testandmanage.JSONCommand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.haofangchan2.R;

import different_adapter.OrderWatchingAdapter;
import different_jsonparse.OrderItemParser;
import differentjavabean.OrderWatchingItemModel;

public class OrderWatchingItem_Activity extends Activity {

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1&&resultCode == 1){
			list.clear();
			updateUI();
		}
	}


	private ImageView back,noOrder;
	private PullToRefreshListView orderwatching_lv;
	private Map<String,String> map;
    private ArrayList<OrderWatchingItemModel> list;
    private OrderWatchingAdapter adapter;
    private int orderWatchIndex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_watching2);
		initView();
		updateUI();
		
	}
	
	private void initView(){
		orderwatching_lv = (PullToRefreshListView) findViewById(R.id.orderwatching_lv);
		noOrder = (ImageView) findViewById(R.id.noOrder);
		list = new ArrayList<OrderWatchingItemModel>();
		adapter=new OrderWatchingAdapter(list);
	    orderwatching_lv.setAdapter(adapter);
		
	    orderwatching_lv.setMode(Mode.BOTH);
	    orderwatching_lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				list.clear();
				orderWatchIndex=0;
				updateUI();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				orderWatchIndex++;
				updateUI();
			}
		});
	    
	    
		back = (ImageView) findViewById(R.id.order_goback);
	    back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OrderWatchingItem_Activity.this.finish();
			}
		});
	    
	    orderwatching_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String roomId = list.get((int) id).getRoomId();
				String houseName=list.get((int)id).getHouseNameData();
				Intent intent = new Intent(getApplicationContext(), OrderMoreInfoActivity.class);
				intent.putExtra("roomid",roomId);
				intent.putExtra("houseName", houseName);
				startActivityForResult(intent, 1);
				//startActivity(intent);
			}
		});
	}
	
	
	private  void updateUI(){
		HashMap<String,String> map = JSONCommand.JSON10059("6", "1", ""+orderWatchIndex, "20");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				list.addAll((ArrayList<OrderWatchingItemModel>) model);
				if(list.size()==0||list == null){
					noOrder.setVisibility(View.VISIBLE);
					orderwatching_lv.setVisibility(View.INVISIBLE);
				}else{
					adapter.notifyDataSetChanged();
					if(orderwatching_lv.isRefreshing()){
						orderwatching_lv.onRefreshComplete();
					}
				}
			}
		}, new OrderItemParser());
	}
	
}
