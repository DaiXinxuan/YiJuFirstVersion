package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.ServerAsyncTask;
import httpConnect.UpdateUIInterface;

import java.util.Map;

import testandmanage.JSONCommand;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.CalculateParser;
import different_jsonparse.State_parse_Json;
import differentjavabean.CalculateModel;

public class CalculateActivity extends Activity {
	private TextView tv1, tv2;
	private EditText houseprice_ed, carprice_ed, lilv;
	private Spinner s1, s2;
	private Button calculate_button;
	private RadioGroup rg1, rg2;
	private boolean bool;
	private ImageView goback;
	private RadioButton radiobtn1, radiobtn2, radiobtn3, radiobtn4;
	private ArrayAdapter<String> adapter;
	private static final String[] content = { "2成    ",
			"3成    ", "4成    ",
			"5成    ", "6成    ",
			"7成    ", "8成    ",
			"9成    " }, content2 = { "1年（12期）", "2年（24期）",
			"3年（36期）", "4年（48期）", "5年（60期）", "6年（72期）", "7年（84期）", "8年（96期）",
			"9年（108期）", "10年（120期）", "11年（132期）", "12年（144期）", "13年（156期）",
			"14年（168期）", "15年（180期）", "16年（192期）", "17年（204期）", "18年（216期）",
			"19年（228期）", "20年（240期）", "21年（252期）", "22年（264期）", "23年（276期）",
			"24年（288期）", "25年（300期）", "26年（312期）", "27年（324期）", "28年（336期）",
			"29年（348期）", "30年（360期）" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculateprice_layout);
		// 获取屏幕宽高
		Display display = getWindowManager().getDefaultDisplay();
		s1 = (Spinner) findViewById(R.id.firstpay);
		s2 = (Spinner) findViewById(R.id.lenddeadline);
		tv1 = (TextView) findViewById(R.id.houseprice_tv);
		tv2 = (TextView) findViewById(R.id.carprice_tv);
		houseprice_ed = (EditText) findViewById(R.id.houseprice);
		carprice_ed = (EditText) findViewById(R.id.carprice);
		LayoutParams lp = s1.getLayoutParams();
		lp.height = (int) (display.getHeight() * 0.1);
		lp = s2.getLayoutParams();
		lp.height = (int) (display.getHeight() * 0.1);
		lp = tv1.getLayoutParams();
		lp.width = (int) (display.getWidth() * 0.2);
		lp = tv2.getLayoutParams();
		lp.width = (int) (display.getWidth() * 0.2);
		lp = houseprice_ed.getLayoutParams();
		lp.width = (int) (display.getWidth() * 0.22);
		lp = carprice_ed.getLayoutParams();
		lp.width = (int) (display.getWidth() * 0.22);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, content);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1.setAdapter(adapter);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, content2);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s2.setAdapter(adapter);

		lilv = (EditText) findViewById(R.id.banklv_ed);

		rg1 = (RadioGroup) findViewById(R.id.loan_method);
		rg2 = (RadioGroup) findViewById(R.id.return_method);
		radiobtn1 = (RadioButton) findViewById(R.id.business_loan);
		radiobtn2 = (RadioButton) findViewById(R.id.gongjijin_loan);
		radiobtn3 = (RadioButton) findViewById(R.id.equal_benjin);
		radiobtn4 = (RadioButton) findViewById(R.id.equal_benxi);
		goback = (ImageView) findViewById(R.id.calculate_back);
		goback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		calculate_button = (Button) findViewById(R.id.calculate_button);
		calculate_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(houseprice_ed.getText().toString().equals("")||carprice_ed.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "请输入房价和车价，无请填0", Toast.LENGTH_SHORT).show();
				}else{
					postdata();
	
					Intent i = new Intent(CalculateActivity.this,
							CalculateResultActivity.class);
					Bundle b = new Bundle();
					b.putDouble("price", Double.parseDouble(houseprice_ed.getText().toString())+Double.parseDouble(carprice_ed.getText().toString()));
					b.putInt("peroid", s2.getSelectedItemPosition());
					b.putInt("firstpay", s1.getSelectedItemPosition());
					b.putString("interestrate", lilv.getText().toString());
					b.putInt("rg1", rg1.getCheckedRadioButtonId());
					b.putInt("rg2", rg2.getCheckedRadioButtonId());
					i.putExtras(b);
					startActivity(i);
				}
			}
		});
		initData();
	}
	private void initData(){
		Map<String,String> map = JSONCommand.JSON10013("6", "1");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				CalculateModel tempModel = (CalculateModel) model;
				lilv.setText(tempModel.getMrate());
			}
		}, new CalculateParser());
	}
	private void postdata() {
			Map<String, String> map = JSONCommand.JSON10012("6", "1", ""+getIntent()
					.getIntExtra("housid", -1), s1.getSelectedItem().toString(), s2
					.getSelectedItem().toString(),
					rg1.getCheckedRadioButtonId() == radiobtn1.getId() ? radiobtn1
							.getText().toString() : radiobtn2.getText().toString(),
					rg2.getCheckedRadioButtonId() == radiobtn2.getId() ? radiobtn3
							.getText().toString() : radiobtn4.getText().toString(),
					""+(Double.parseDouble(houseprice_ed.getText().toString())+Double.parseDouble(carprice_ed.getText().toString())));
			ServerAsyncTask asyncTask = new ServerAsyncTask();
	
			asyncTask.execute(map, new UpdateUIInterface() {
	
				public void updateUI(Object model) {
					bool = (boolean) model;
					if (bool == false) {
						Toast.makeText(getApplicationContext(), "上传数据失败",
								Toast.LENGTH_SHORT).show();
					} else {
	
						Toast.makeText(getApplicationContext(), "上传数据成功",
								Toast.LENGTH_SHORT).show();
					}
				}
			}, new State_parse_Json());
		}

}
