package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import testandmanage.JSONCommand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.haofangchan2.R;

import different_adapter.Setting_ZhiyeguwenAdapter;
import different_jsonparse.Zhiyeguwen_jsonparse;
import differentjavabean.Zhiyeguwen_javabean;

public class ZhiyeguwenActivity extends Activity{
	Map<String,String> map;
	List<Zhiyeguwen_javabean> list;
	Setting_ZhiyeguwenAdapter adapter;
	GridView gv;
	ImageView back;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_zhiyeguwen);
		back = (ImageView) findViewById(R.id.friendpro_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		list=new ArrayList<Zhiyeguwen_javabean>();
		gv=(GridView) findViewById(R.id.gridview_zhiyeguwen);
		updateUI();
//		initDate(); 
		gv.setOnItemClickListener(new OnItemClickListener() {
	
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent=new Intent(getApplicationContext(),ConsultantActivity.class);
				Zhiyeguwen_javabean salerdata=list.get(0);
				if(salerdata.getHasSaler()){
					intent.putExtra("hasSaler", true);
				}else {
					intent.putExtra("hasSaler", false);
				}
				Zhiyeguwen_javabean data=list.get(position);
				intent.putExtra("salerid", data.getSaleid());
				startActivityForResult(intent, 0);
			}
		}) ;
	}
	public void updateUI(){
		map=new HashMap<String,String>();
		map=JSONCommand.JSON10056("6", "1");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				list = (List<Zhiyeguwen_javabean>) model;
				adapter=new Setting_ZhiyeguwenAdapter(list);
				gv.setAdapter(adapter);
			}
		}, new Zhiyeguwen_jsonparse());
	}
  @Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	if(requestCode==0&&resultCode==1){
		updateUI();
	}
}
}
