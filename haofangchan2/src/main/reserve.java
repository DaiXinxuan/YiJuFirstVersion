package main;

import java.util.Calendar;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.MyApplication;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.haofangchan2.R;

import connect.Http_Connection;

public class reserve extends Activity {

	private Map<String, String> map = null;// 存放服务器所需数据
	private Button ensure = null;
	private ImageView back = null;
	private ImageButton selectDate = null;// 日期选择
	private TextView date = null,// 看房日期
			code = null;// 房屋编号
	private EditText name = null,// 看房人
			phoneNumber = null,// 手机号
			count = null,// 看房人数
			introduction = null;// 看房说明
	private int myear = 0,// 以下均为存储看房日期所需变量
			mmonth = 0, mday = 0, mhour = 0, mminute = 0;
	private Calendar calendar = null;// 获取系统时间所需组件

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reserve);
		initViriable();// 初始化变量
		back.setOnClickListener(new OnClickListener() {
			// 回退键
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		selectDate.setOnClickListener(new OnClickListener() {
			// 选择日期对话框
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				calendar = Calendar.getInstance();
				DatePickerDialog dateDialog = new DatePickerDialog(
						reserve.this, new DatePickerDialog.OnDateSetListener() {
							// 年月日选择对话框
							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								// TODO Auto-generated method stub
								myear = year;
								mmonth = monthOfYear;
								mday = dayOfMonth;
								// 时间选择对话框
								new TimePickerDialog(
										reserve.this,
										new TimePickerDialog.OnTimeSetListener() {

											@Override
											public void onTimeSet(
													TimePicker view,
													int hourOfDay, int minute) {
												// TODO Auto-generated method
												// stub
												mhour = hourOfDay;
												mminute = minute;
												date.setText("" + myear + "-"
														+ mmonth + "-" + mday
														+ " " + mhour + ":"
														+ mminute);
											}
										}, calendar.get(Calendar.HOUR_OF_DAY),
										calendar.get(Calendar.MINUTE), true)
										.show();
							}
						}, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH));
				dateDialog.show();
			}
		});
		ensure.setOnClickListener(new OnClickListener() {
			// 点击确认后向服务器发送数据
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (date.getText().length() != 0
						&& phoneNumber.getText().length() != 0
						&& count.getText().length() != 0
						&& introduction.getText().length() != 0
						&& name.getText().length() != 0) {
					map.put("type", "10058");
					map.put("payid", "1");
					map.put("proid", "1");
					map.put("name", name.getText().toString());
					map.put("date", date.getText().toString());
					map.put("phonenumber", phoneNumber.getText().toString());
					map.put("count", count.getText().toString());
					map.put("introduction", introduction.getText().toString());
					// 发送数据
					try {
						// boolean state =
						// (boolean)Http_Connection.postRequest(MyApplication.base_url,
						// map, new Connection_Handle_Inteface() {
						//
						// @Override
						// public Object hand(String response) {
						// // TODO Auto-generated method stub
						// JSONObject jsonObject;
						// boolean state = false;
						// try {
						// jsonObject = new JSONObject(response);
						// state = jsonObject.getBoolean("status");
						// } catch (JSONException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// Toast toast = null;
						// String text = "服务器异常";
						// if(toast==null){//防止Toast重复弹出
						// toast.makeText(reserve.this, text,
						// Toast.LENGTH_SHORT).show();
						// }
						// else{
						// toast.setText(text);
						// toast.setDuration(Toast.LENGTH_SHORT);
						// }
						// }
						// return state;
						// }
						// });
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast toast = null;
						String text = "网络错误";
						if (toast == null) {// 防止Toast重复弹出
							toast.makeText(reserve.this, text,
									Toast.LENGTH_SHORT).show();
						} else {
							toast.setText(text);
							toast.setDuration(Toast.LENGTH_SHORT);
						}
					}
				} else {// 如果信息没填全，提醒填写
					Toast toast = null;
					String text = "请填写完整信息";
					if (toast == null) {// 防止Toast重复弹出
						toast.makeText(reserve.this, text, Toast.LENGTH_SHORT)
								.show();
					} else {
						toast.setText(text);
						toast.setDuration(Toast.LENGTH_SHORT);
					}
				}
			}
		});
	}

	private void initViriable() {
		// TODO Auto-generated method stub
		code = (TextView) findViewById(R.id.reserve_houseCode);
		date = (TextView) findViewById(R.id.reserve_date);
		phoneNumber = (EditText) findViewById(R.id.reserve_phoneNumber);
		count = (EditText) findViewById(R.id.reserve_peopleNumber);
		introduction = (EditText) findViewById(R.id.reserve_introduction);
		name = (EditText) findViewById(R.id.reserve_person);
		ensure = (Button) findViewById(R.id.reserve_ensure);
		back = (ImageView) findViewById(R.id.reserve_back);
		selectDate = (ImageButton) findViewById(R.id.reserve_selectDate);
		code.setText(code.getText() + getIntent().getStringExtra("code"));
	}

}
