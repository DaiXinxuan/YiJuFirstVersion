package main_fragment;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import pictureconnectinit.InitPicture_setting;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import web.WebActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haofangchan2.R;

import different_jsonparse.Activity_detail_Auction_jsonparse;
import differentjavabean.Activity_detail_auction_javabean;

public class AuctionInfo extends Activity {
	private ImageView iv, back;
	private Button join;
	private TextView name, introduction, nowprice, date ,duration;
	private Map<String, String> map;
	private Activity_detail_auction_javabean data;
	private Timer timer;	
	private long hour, minute, seconds, day;
	private String endtime, url;
	private boolean isInauction = false;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.auction_info);
		date = (TextView) findViewById(R.id.time_auction_info);
		join = (Button) findViewById(R.id.join_auction_info);
		init();
		initData();
	}

	private void initData() {
		// 获取网络数据
		map = JSONCommand.JSON10053("6", "1",
				getIntent().getStringExtra("auctionid"));
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				data = (Activity_detail_auction_javabean) model;
				InitPicture_setting.getImageLoader(data.getActivityimage(), iv);
				name.setText(data.getName());
				introduction.setText(data.getIntroduction());
				duration.setText(data.getBegintime().replace("+", " ")+"到"+data.getEndtime().replace("+", " "));
				double price = Double.parseDouble(data.getNowprice()) / 10000;
				DecimalFormat df = new DecimalFormat("######0.00");
				df.format(price);
				nowprice.setText("¥" + price + "万");
				endtime = data.getEndtime().replace("+", " ");
				handTimeString(data.getBegintime());
				url = data.getUrl();
			}
		}, new Activity_detail_Auction_jsonparse());

	}

	private void handTimeString(String time) {
		String dateString = time.replace("+", " ");
		Log.d("网络获取时间", "date:" + dateString);
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		try {
			Date date = format.parse(dateString);
			Date date1 = format.parse(endtime);
			c.setTime(date);
			c1.setTime(date1);
			Log.d("Auctioninfo", "begintime时间转化后的毫秒数1为：" + c.getTimeInMillis());
			Log.d("Auctioninfo", "endtime时间转化后的毫秒数1为：" + c1.getTimeInMillis());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		long timeInterval = c.getTimeInMillis() - System.currentTimeMillis();
		long timeInterval1 = c1.getTimeInMillis() - System.currentTimeMillis();
		// String intervalString = getStandardDate(timeInterval);
		// LogUtil.d("时间差为：", "timeinterval：" + intervalString);
		// date.setText(intervalString);
		if (timeInterval1 < 0) {
			// 现在时间大于endtime，活动已结束
			LogUtil.d("Auctioninfo", "现在时间大于endtime");
			date.setText("当前拍卖已经结束");
			// join.setClickable(false);
			join.setBackgroundResource(R.drawable.graybutton);
			join.setText("已结束");
			join.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(AuctionInfo.this, WebActivity.class);
					i.putExtra("title", "火热拍卖");
					i.putExtra("url", url);
					startActivity(i);
				}
			});
		} else if (timeInterval > 0) {
			// begintime大于现在时间,即活动还没开始
			isInauction = false;
			LogUtil.d("Auctioninfo", "begintime大于现在时间");
			String intervalString = getStandardDate(timeInterval);
			LogUtil.d("时间差为：", "timeinterval：" + intervalString);
			date.setText(intervalString);
			// 参加活动事件监听
			join.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(AuctionInfo.this, WebActivity.class);
					i.putExtra("title", "火热拍卖");
					i.putExtra("url", url);
					startActivity(i);
				}
			});
		} else if (timeInterval < 0 && timeInterval1 >= 0) {
			// 活动时间内
			isInauction = true;
			LogUtil.d("Auctioninfo", "活动时间内");
			String intervalString = getStandardDate(timeInterval1);
			LogUtil.d("时间差为：", "timeinterval：" + intervalString);
			date.setText("已经开始！还有" + intervalString);
			join.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(AuctionInfo.this, WebActivity.class);
					i.putExtra("title", "火热拍卖");
					i.putExtra("url", url);
					startActivity(i);
				}
			});
		}

	}

	public String getStandardDate(long time) {

		String sb = new String();
		if (time > 0) {
			seconds = (long) Math.ceil(time / 1000);// 秒前

			minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
			hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
			if (minute % 60 != 0)
				hour--;
			day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前
			if (day - 1 > 0) {
				sb = day + "天";
			} else if (hour > 6) {
				sb = hour + "小时";
			} else if (hour <= 6) {
				minute = (long) Math
						.ceil(((time - hour * 60 * 60 * 1000) / 1000.0f) / 60) - 1;
				if (minute < 0) {
					minute = 0;
					hour--;
				}
				if (minute == 60)
					minute = 0;
				seconds = (long) Math
						.ceil((time - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000.0f) + 60;
				if (seconds == 60)
					seconds = 0;
				if (seconds > 60)
					seconds -= 60;
				startTimer();
			}
		} else {
			sb = "当前拍卖已经结束";
		}

		return sb;
	}

	public void startTimer() {
		timer = new Timer(true);
		timer.schedule(task, 0, 1000);
	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				date.setText(hour + "时" + minute + "分" + seconds + "秒");
				if (seconds == 0) {
					seconds = 59;
					if (minute == 0) {
						if (hour != 0) {
							minute = 59;
							hour--;
						}
					} else
						minute--;
				} else {
					seconds--;
				}
				if (hour == 0 && seconds == 0 && minute == 0) {
					timer.cancel();
					task.cancel();
					if (isInauction) {
						date.setText("");
						// join.setClickable(false);
						join.setBackgroundResource(R.drawable.graybutton);
						join.setText("已结束");
						join.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent i = new Intent(AuctionInfo.this,
										WebActivity.class);
								i.putExtra("title", "火热拍卖");
								i.putExtra("url", url);
								startActivity(i);
							}
						});
					} else {
						date.setText("已经开始！");
						join.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent i = new Intent(AuctionInfo.this,
										WebActivity.class);
								i.putExtra("title", "火热拍卖");
								i.putExtra("url", url);
								startActivity(i);
							}
						});
					}
				}
				break;
			}
			super.handleMessage(msg);
		}
	};
	

	private void init() {
		// 初始化控件
		iv = (ImageView) findViewById(R.id.sample_hu);
		back = (ImageView) findViewById(R.id.goback);
		introduction = (TextView) findViewById(R.id.instruct_lv);
		name = (TextView) findViewById(R.id.name_auction_info);
		duration = (TextView) findViewById(R.id.instruct_lv2);
		nowprice = (TextView) findViewById(R.id.nowprice_auction_info);
		// 点击图片跳转WebActivity
		iv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AuctionInfo.this, WebActivity.class);
				intent.putExtra("title", "火热拍卖");
				intent.putExtra("url", data.getHtml());
				startActivity(intent);
			}
		});
		// 设置回退
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AuctionInfo.this.finish();
			}
		});

	}

}
