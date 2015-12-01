package main_fragment;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.Map;

import pictureconnectinit.InitPicture_setting;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import web.WebActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haofangchan2.R;

import different_jsonparse.Activity_detail_Indiana_jsonparse;
import differentjavabean.Activity_detail_Indiana_javabean;

public class ActivityInfo extends Activity {
	ImageView iv, back;
	Map<String, String> map;
	Activity_detail_Indiana_javabean data;
	TextView name, introduction, date, count;
	Button tel, join;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_info);

		init();
		initData();
	}

	private void initData() {
		map = JSONCommand.JSON10052("6", "1",
				getIntent().getStringExtra("activityid"));

		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				data = (Activity_detail_Indiana_javabean) model;
				LogUtil.d("活动数据：", "" + data.getName());
				// 加载网络数据到控件上
				LogUtil.d("图片地址", "url" + data.getActivityimage());
				InitPicture_setting.getImageLoader(data.getActivityimage(), iv);
				name.setText(data.getName());
				introduction.setText(data.getIntroduction());
				date.setText(data.getEndtime());
				count.setText("已有" + data.getCount() + "人参加");
				tel.setText(data.getTel());

			}
		}, new Activity_detail_Indiana_jsonparse());

	}

	private void init() {
		// 初始化控件
		name = (TextView) findViewById(R.id.activity_name);
		introduction = (TextView) findViewById(R.id.introduction_activity_info);
		date = (TextView) findViewById(R.id.date_activity_info);
		count = (TextView) findViewById(R.id.count_activity_info);
		tel = (Button) findViewById(R.id.tel_activity_info);
		join = (Button) findViewById(R.id.join_activity_info);
		iv = (ImageView) findViewById(R.id.top_image);
		iv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent(ActivityInfo.this,WebActivity.class);
				intent.putExtra("title", "精彩活动");
				intent.putExtra("url", data.getHtml());
				startActivity(intent);
			}
		});
		// 设置回退键
		back = (ImageView) findViewById(R.id.goback);
		if (getIntent().getStringExtra("over").equals("over")) {
			join.setBackgroundResource(R.drawable.graybutton);
			join.setClickable(false);

			join.setText("已结束");
		} else {
			join.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(ActivityInfo.this,
							WebActivity.class);
					intent.putExtra("title", "精彩活动");
					intent.putExtra("url", data.getUrl());
					startActivity(intent);
				}

			});
		}

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityInfo.this.finish();
			}
		});
		tel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ data.getTel()));
				startActivity(intent);
			}
		});

	}

}
