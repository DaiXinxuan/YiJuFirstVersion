package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.HashMap;
import java.util.Map;

import testandmanage.JSONCommand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.BooleanParser;
import different_jsonparse.OrderMoreInfoParser;
import differentjavabean.OrderMoreInfoModel;

public class OrderMoreInfoActivity extends Activity{
	private EditText roomNo,name,date,phone,peoCount,info;
	private Button b;
	String roomId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cancelorderwatching);
		
		init();
		roomId = getIntent().getStringExtra("roomid");
		updateUI();
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancel();
			}
		});
	}
	public void cancel(){
		Map<String, String> map = JSONCommand.JSON10062("1", roomId,"6");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				if((boolean) model){
					Toast.makeText(getApplicationContext(), "取消成功！",Toast.LENGTH_SHORT).show();
				}else Toast.makeText(getApplicationContext(), "取消失败！",Toast.LENGTH_SHORT).show();
			}
		}, new BooleanParser());
	}
	public void init(){
		roomNo = (EditText) findViewById(R.id.watchhouse_code_et);
		name = (EditText) findViewById(R.id.watch_person_et);
		date = (EditText) findViewById(R.id.watch_date_et);
		phone = (EditText) findViewById(R.id.phone_number_et);
		peoCount = (EditText) findViewById(R.id.watchhouse_number_et);
		info = (EditText) findViewById(R.id.watchhouse_explain_et);
		b = (Button) findViewById(R.id.sure_watching_bt);
		ImageView backImageView=(ImageView) findViewById(R.id.ordermore_back);
		backImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent load = new Intent(getApplicationContext(),
//						OrderWatchingItem_Activity.class);
//				load.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//				startActivity(load);
				setResult(1);
				OrderMoreInfoActivity.this.finish();
			}
		});
	}
	public void updateUI(){
		HashMap<String, String> map = JSONCommand.JSON10060(roomId, "1","6");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				OrderMoreInfoModel omm = (OrderMoreInfoModel) model;
				roomNo.setText(getIntent().getStringExtra("houseName"));
				name.setText(omm.getName());
				date.setText(omm.getDate());
				phone.setText(omm.getPhoneNumber());
				peoCount.setText(omm.getCount());
				info.setText(omm.getInstruction());
			}
		}, new OrderMoreInfoParser());
	}
}
