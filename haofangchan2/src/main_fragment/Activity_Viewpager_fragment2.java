package main_fragment;

import handmark.pulltorefresh.library.PullToRefreshBase;
import handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import handmark.pulltorefresh.library.PullToRefreshListView;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;

import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.haofangchan2.R;

import different_adapter.DuobaoAdapter;
import different_jsonparse.ActivityParser;
import differentjavabean.ActivityModel;

public class Activity_Viewpager_fragment2 extends Fragment {
	private PullToRefreshListView lv;
	private int DuBaoIndex;
	private ArrayList<ArrayList<ActivityModel>> data;

	public void updateData() {

		HashMap<String, String> map;
		map = JSONCommand.JSON10050("6", "1", "" + DuBaoIndex, "20");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@SuppressWarnings("unchecked")
			public void updateUI(Object model) {
				if (model != null) {
					data.addAll((ArrayList<ArrayList<ActivityModel>>) model);
				}
				lv.setAdapter(new DuobaoAdapter(data));
				if (lv.isRefreshing()) {
					lv.onRefreshComplete();
				}
			}
		}, new ActivityParser());

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.activityfragment_viewpagerfragment2, container, false);
		data = new ArrayList<ArrayList<ActivityModel>>();
		lv = (PullToRefreshListView) view.findViewById(R.id.duobao_lv);
		lv.setMode(Mode.BOTH);
		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				DuBaoIndex = 0;
				data = new ArrayList<ArrayList<ActivityModel>>();
				updateData();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				DuBaoIndex++;
				updateData();
			}

		});

		updateData();

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				boolean isOver = false;
				LogUtil.d("点击项为：", "position:" + position + " id:" + id);
				ArrayList<ActivityModel> al1 = data.get(0), al2 = data.get(1), al3 = data
						.get(2);
				ArrayList<ActivityModel> al = new ArrayList<ActivityModel>();
				al.addAll(al1);
				al.addAll(al2);
				al.addAll(al3);
				LogUtil.d("获得的id为：", "activityid"
						+ al.get((int) id).getActivityId());
				if (position > al1.size() + al2.size())
					isOver = true;
				Intent i = new Intent(getActivity(), ActivityInfo.class);
				i.putExtra("activityid", al.get((int) id).getActivityId());
				if (isOver){
					i.putExtra("over", "over");
				}else{
					i.putExtra("over", "notOver");
				}
				getActivity().startActivity(i);
			}
		});

		return view;
	}

}