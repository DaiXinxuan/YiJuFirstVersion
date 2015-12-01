package main;

import hx_util.propellingMessageTurnTo;

import java.util.List;

import org.litepal.crud.DataSupport;

import com.example.haofangchan2.R;

import different_adapter.LatestNewsAdapter;
import differentjavabean.LatestNewsTable;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MessageCenterActivity extends Activity{
	private ListView lv;
	private ImageView nomessage;
	private ImageButton back;
	private LatestNewsAdapter adapter;
	private List<LatestNewsTable> list;
	private TextView titleTextView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.remote_notification_layout);
		init();
		list=DataSupport.findAll(LatestNewsTable.class);
	    if(list!=null&&list.size()>0){	
		adapter=new LatestNewsAdapter(list);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			  	LatestNewsTable data=list.get(arg2);
			  	propellingMessageTurnTo turnTo=new propellingMessageTurnTo();
			    turnTo.turnToActivity(null, data);
			}
		});
	    }else {
//	    	nomessage.setImageResource(R.drawable.nomessage);
	    	nomessage.setVisibility(View.VISIBLE);
	    	lv.setVisibility(View.INVISIBLE);
			Toast.makeText(MessageCenterActivity.this, "您当前没有收到任何项目资讯", Toast.LENGTH_SHORT).show();
		}
	    
	}
	private void init() {
		lv=(ListView) findViewById(R.id.remotemessage_lv);
		nomessage = (ImageView) findViewById(R.id.noNews);
		titleTextView=(TextView)findViewById(R.id.remotemessage_title_text);
		titleTextView.setText("消息中心");
		back=(ImageButton) findViewById(R.id.remotemessage_goback);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				MessageCenterActivity.this.finish();
			}
		});
		
	}
	
}

