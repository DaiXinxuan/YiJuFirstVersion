package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.ServerAsyncTask;
import httpConnect.UpdateUIInterface;

import java.util.Map;

import testandmanage.JSONCommand;
import testandmanage.MyApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.State_parse_Json;

public class SentCommentActivity extends Activity {
	ImageButton back;
	EditText et;
	TextView sent;
	Boolean bool = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sentcomment);
		back = (ImageButton) findViewById(R.id.scomment_title_btn);
		et = (EditText) findViewById(R.id.scomment_textfield);
		sent = (TextView) findViewById(R.id.scomment_title_save);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent load = new Intent(getApplicationContext(),
						CommnetActivity.class);
				load.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(load);
				SentCommentActivity.this.finish();
			}
		});
		sent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				postdata();
//				Intent load = new Intent(getApplicationContext(),
//						CommnetActivity.class);
//				load.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//				startActivity(load);
//				setResult(1);
				SentCommentActivity.this.finish();
			}

		});

	}

	private void postdata() {
		Map<String, String> map = JSONCommand.JSON10015(MyApplication.getproid(), MyApplication.getpayid(), et.getText()
				.toString(), getIntent().getStringExtra("housenum"),
				getIntent().getStringExtra("houseid"));
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@Override
			public void updateUI(Object model) {
				bool = (Boolean) model;
				if (bool == true) {
					Toast.makeText(getApplicationContext(), "发表成功",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "发表失败，每个房屋您只能评论一次",
							Toast.LENGTH_SHORT).show();
				}
			}
		}, new State_parse_Json());

	}
}
