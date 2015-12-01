package main_fragment;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.Map;

import main.MainFragmentActivity;
import pictureconnectinit.InitPicture_setting;
import testandmanage.JSONCommand;
import testandmanage.MyApplication;
import web.WebActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.Activity_detail_Groupbuying_jsonparse;
import different_jsonparse.State_parse_Json;
import differentjavabean.Activity_detail_Groupbuying_javabean;

public class GroupInfo extends Activity {
	private Button b1, b2, b3, b4, b5, b6, b7;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
	private ImageView iv, goback;
	private Map<String, String> map;
	private Boolean state;
	private Activity_detail_Groupbuying_javabean data;
	private String groupidString = null;
	private String url = null;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.group_layout);
		groupidString = getIntent().getStringExtra("groupid");
		init();
		initData();

	}

	private void initData() {

		map = JSONCommand.JSON10048("6", "1", groupidString);

		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			public void updateUI(Object model) {
				data = (Activity_detail_Groupbuying_javabean) model;
				InitPicture_setting.getImageLoader(data.getAdsimage(), iv);
				tv7.setText(data.getCount().get(6) + "人已报名");
				tv6.setText(data.getCount().get(5) + "人已报名");
				tv5.setText(data.getCount().get(4) + "人已报名");
				tv4.setText(data.getCount().get(3) + "人已报名");
				tv3.setText(data.getCount().get(2) + "人已报名");
				tv2.setText(data.getCount().get(1) + "人已报名");
				tv1.setText(data.getCount().get(0) + "人已报名");
				url = data.getAdsurl();
				for (int i = 0; i < data.getStates().size(); i++) {
					if (data.getStates().get(i).equals("1")) {

						switch (i) {
						case 0:
							b1.setClickable(false);
							b1.setBackgroundResource(R.drawable.graybutton);
							break;
						case 1:
							b2.setClickable(false);
							b2.setBackgroundResource(R.drawable.graybutton);
							break;
						case 2:
							b3.setClickable(false);
							b3.setBackgroundResource(R.drawable.graybutton);
							break;
						case 3:
							b4.setBackgroundResource(R.drawable.graybutton);
							b4.setClickable(false);
							break;
						case 4:
							b5.setBackgroundResource(R.drawable.graybutton);
							b5.setClickable(false);
							break;
						case 5:
							b6.setBackgroundResource(R.drawable.graybutton);
							b6.setClickable(false);
							break;
						case 6:
							b7.setBackgroundResource(R.drawable.graybutton);
							b7.setClickable(false);
							break;
						default:
							break;
						}

					}
				}
			}
		}, new Activity_detail_Groupbuying_jsonparse());

	}

	private void init() {
		// 初始化按钮
		b1 = (Button) findViewById(R.id.one_logi);
		b2 = (Button) findViewById(R.id.two_logi);
		b3 = (Button) findViewById(R.id.three_logi);
		b4 = (Button) findViewById(R.id.four_logi);
		b5 = (Button) findViewById(R.id.five_logi);
		b6 = (Button) findViewById(R.id.six_logi);
		b7 = (Button) findViewById(R.id.seven_logi);
		// 初始化文本框
		tv1 = (TextView) findViewById(R.id.oneroom_person);
		tv2 = (TextView) findViewById(R.id.tworoom_person);
		tv3 = (TextView) findViewById(R.id.threeroom_person);
		tv4 = (TextView) findViewById(R.id.fourroom_person);
		tv5 = (TextView) findViewById(R.id.fiveroom_person);
		tv6 = (TextView) findViewById(R.id.sixroom_person);
		tv7 = (TextView) findViewById(R.id.sevenroom_person);

		iv = (ImageView) findViewById(R.id.top_image);
		goback = (ImageView) findViewById(R.id.goback);

		// 参加活动事件监听
		b1.setOnClickListener(listener);
		b2.setOnClickListener(listener);
		b3.setOnClickListener(listener);
		b4.setOnClickListener(listener);
		b5.setOnClickListener(listener);
		b6.setOnClickListener(listener);
		b7.setOnClickListener(listener);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(GroupInfo.this, WebActivity.class);
				i.putExtra("url", url);
				i.putExtra("title", "特价团购");
				startActivity(i);
			}
		});
		// 获取平米大小
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenH = dm.heightPixels;
		// 设置顶部图大小
		// RelativeLayout.LayoutParams lp = (LayoutParams) b1.getLayoutParams();
		// lp.height = (int) (0.05 * screenH);
		// LinearLayout.LayoutParams lp1 =
		// (android.widget.LinearLayout.LayoutParams) iv
		// .getLayoutParams();
		// lp1.height = (int) (0.15 * screenH);
		// iv.setLayoutParams(lp1);
		// 回退设置
		goback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(GroupInfo.this,
						MainFragmentActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
				GroupInfo.this.finish();
			}
		});
	}

	OnClickListener listener = new OnClickListener() {

		private Button btn;

		public void onClick(View v) {
			btn = (Button) v;
			// 初始化发送数据
			switch (v.getId()) {
			case R.id.one_logi:
				map = JSONCommand.JSON10049("6", "1", groupidString, "1");
				break;
			case R.id.two_logi:
				map = JSONCommand.JSON10049("6", "1", groupidString, "2");
				break;
			case R.id.three_logi:
				map = JSONCommand.JSON10049("6", "1", groupidString, "3");
				break;
			case R.id.four_logi:
				map = JSONCommand.JSON10049("6", "1", groupidString, "4");
				break;
			case R.id.five_logi:
				map = JSONCommand.JSON10049("6", "1", groupidString, "5");
				break;
			case R.id.six_logi:
				map = JSONCommand.JSON10049("6", "1", groupidString, "6");
				break;
			case R.id.seven_logi:
				map = JSONCommand.JSON10049("6", "1", groupidString, "7");
				break;

			}

			ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

				public void updateUI(Object model) {
					state = (boolean) model;
					if (state == true) {
						initData();
						btn.setClickable(false);
						btn.setBackgroundResource(R.drawable.graybutton);
						Toast.makeText(MyApplication.getContext(), "参加成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "参加失败",
								Toast.LENGTH_SHORT).show();
					}

				}
			}, new State_parse_Json());

		}
	};
}
