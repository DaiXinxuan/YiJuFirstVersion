package main_fragment;

import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshListView;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.ServerAsyncTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpRequest;

import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;

import load.LoginActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.haofangchan2.R;

import connect.Http_Connection;

import different_adapter.DuobaoAdapter;
import different_jsonparse.ActivityParser;
import different_jsonparse.Activity_Indiana_jsonparse;
import differentjavabean.ActivityModel;
import differentjavabean.Activity_Indiana_javabean;

public class Activity_Viewpager_fragment3 extends Fragment{
	private PullToRefreshListView lv;
	private int auctionIndex;
	private ArrayList<ArrayList<ActivityModel>> data;
	@SuppressWarnings("unchecked")
	private  void updateData(){
		
		HashMap<String,String> map;
		map=JSONCommand.JSON10051("6", "1", ""+auctionIndex, "20");
		ServerAsyncHttpTask.execute(map,new UpdateUIInterface() {
			
			public void updateUI(Object model) {
				if(model!=null){
				data.addAll((ArrayList<ArrayList<ActivityModel>>)model);}
				lv.setAdapter(new DuobaoAdapter(data)); 
				if(lv.isRefreshing()){
					lv.onRefreshComplete();
				}
			}
		},new ActivityParser());
		
	}
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view=LayoutInflater.from(getActivity()).inflate(R.layout.
				activityfragment_viewpagerfragment3, container,false);
		lv = (PullToRefreshListView) view.findViewById(R.id.auction_lv);
		lv.setMode(Mode.BOTH);
		data=new ArrayList<ArrayList<ActivityModel>>();
		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				auctionIndex = 0;
				data = new ArrayList<ArrayList<ActivityModel>>();
				updateData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				auctionIndex++;
				updateData();
			}

		});
		
		
		updateData();
		 
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LogUtil.d("点击项为：", "id"+id);
				
				ArrayList<ActivityModel> al1 = data.get(0),al2=data.get(1),al3=data.get(2);
				ArrayList<ActivityModel> al=new ArrayList<ActivityModel>();
				al.addAll(al1);
				al.addAll(al2);
				al.addAll(al3);
				LogUtil.d("获得的id为：", "s2s"+al.get((int) id).getActivityId());
				Intent i = new Intent(getActivity(),AuctionInfo.class);
				i.putExtra("auctionid", al.get((int) id).getActivityId());
				getActivity().startActivity(i);
			}
		});
		return view;
	}

}
