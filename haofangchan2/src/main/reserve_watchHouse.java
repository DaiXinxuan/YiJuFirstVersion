package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import testandmanage.MyApplication;
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

import com.example.haofangchan2.R;

import connect.Http_Connection;
import different_adapter.reserveAdapter;
import differentjavabean.reserve_watchHousejavabean;

public class reserve_watchHouse extends Activity{
	
	private Map<String, String> map = null;//��װ����������͵�����
	private List<reserve_watchHousejavabean> list = null;//���շ���������
	private reserveAdapter madapter = null;//��ʼ��listview����adapter
	private ListView listview = null;
	private ImageView back = null;//����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reserve_watchhouse);
		initView();//��ʼ������
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {
			//listview����¼�
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(reserve_watchHouse.this ,reserve_details.class);
				intent.putExtra("roomid", list.get(position).getRoomid());//����roomid��Ϊ���ݱ�ʶ
				startActivity(intent);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		listview = (ListView)findViewById(R.id.reserve_listView);
		back = (ImageView)findViewById(R.id.reserve_watchhouse_back);
		list = new ArrayList<reserve_watchHousejavabean>();
		map = new HashMap<String, String>();
		map.put("type", "10057");
		map.put("payid", "1");
		try {
//			list = (List<reserve_watchHousejavabean>)Http_Connection.postRequest(
//					MyApplication.base_url, map, new reserve_watchHouseJsonParse());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		runOnUiThread(new Runnable() {//��ʼ��listview
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				madapter = new reserveAdapter(reserve_watchHouse.this, list);
				listview.setAdapter(madapter);
			}
		});
	}
}
