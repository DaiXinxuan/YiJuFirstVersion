package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.ServerAsyncTask;
import httpConnect.UpdateUIInterface;

import java.util.List;
import java.util.Map;

import testandmanage.JSONCommand;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.State_parse_Json;
import differentjavabean.OrderWatching1_javabean;

public class OrderWatchingActivity extends Activity {

	private EditText housecode, watchhouse_person, watchhouse_date,
			phone_number, watchhouse_number, watchhouse_ex;
	private Button sure_bt;
	private ImageView back_bt, datapicker;
	private RelativeLayout watchingdate;
	List<OrderWatching1_javabean> data;
	private Map<String, String> map;
	boolean state = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_watching);
		housecode = (EditText) findViewById(R.id.watchhouse_code_et);
		housecode.setText(getIntent().getStringExtra("housenum"));
		watchhouse_person = (EditText) findViewById(R.id.watch_person_et);
		watchhouse_date = (EditText) findViewById(R.id.watch_date_et);
		phone_number = (EditText) findViewById(R.id.phone_number_et);
		watchhouse_number = (EditText) findViewById(R.id.watchhouse_number_et);
		watchhouse_ex = (EditText) findViewById(R.id.watchhouse_explain_et);
		back_bt = (ImageView) findViewById(R.id.bt_back);
		sure_bt = (Button) findViewById(R.id.sure_watching_bt);
		datapicker = (ImageView) findViewById(R.id.watch_datapicker);
		watchingdate = (RelativeLayout) findViewById(R.id.watching_date);

		back_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		sure_bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postData();
			}
		});
		watchingdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DatePickerFragment datePicker = new DatePickerFragment(
						watchhouse_date);
				datePicker.show(getFragmentManager(), "datePicker");
			}
		});

	}

	private void postData() {

		String housecode1 = housecode.getText().toString();
		String watchhouse_person1 = watchhouse_person.getText().toString();
		String watchhouse_date1 = watchhouse_date.getText().toString();
		String phone_number1 = phone_number.getText().toString();
		String watchhouse_number1 = watchhouse_number.getText().toString();
		String watchhouse_ex1 = watchhouse_ex.getText().toString();
		if (watchhouse_ex1.equals("")) watchhouse_ex1="暂无说明";
		if (watchhouse_person1.equals("")) watchhouse_person1="1";
		Log.d("order","person:"+watchhouse_person1+"date:"+watchhouse_date1+"phone"+phone_number1+".");
		if (!watchhouse_date1.equals("") && !phone_number1.equals("")) {
			Map<String, String> map = JSONCommand.JSON10058("6", "1",
					getIntent().getStringExtra("houseid"), watchhouse_person1, watchhouse_date.getText()
							.toString(), phone_number.getText().toString(),
					watchhouse_number.getText().toString(), watchhouse_ex1);

			ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

				public void updateUI(Object model) {
					state = (boolean) model;
					if (state == false) {
						Toast.makeText(getApplicationContext(), "预定看房失败",
								Toast.LENGTH_SHORT).show();
					} else {

						Toast.makeText(getApplicationContext(), "预定看房成功",
								Toast.LENGTH_SHORT).show();
					}
				}
			}, new State_parse_Json());
		} else {
			Toast.makeText(getApplicationContext(), "信息不能为空，请您完善预定看房信息",
					Toast.LENGTH_SHORT).show();
		}
	}

}
