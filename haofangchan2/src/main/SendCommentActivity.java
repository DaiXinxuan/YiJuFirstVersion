package main;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.HashMap;

import testandmanage.JSONCommand;
import testandmanage.Window_metrics;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.State_parse_Json;

public class SendCommentActivity extends Activity {
	private RadioGroup rg;
	private RadioButton rb1, rb2, rb3;
	private Button b;
	private EditText et;
	private ImageView back;
	String level = null;
	HashMap<String, String> map = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.servicecomment);
		init();
		LayoutParams lp = (LayoutParams) rb1.getLayoutParams();
		lp.width = Window_metrics.getScreenWidth() / 4;
		rb1.setLayoutParams(lp);
		rb2.setLayoutParams(lp);
		rb3.setLayoutParams(lp);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (rg.getCheckedRadioButtonId()) {
				case R.id.goodcomment:
					level = "1";
					break;
				case R.id.mediumcomment:
					level = "2";
					break;
				case R.id.badcomment:
					level = "3";
					break;
				}
				if (level != null) {
					if (et.getText().toString() != null) {
						map = JSONCommand.JSON10057("6", "1", ""
								+ getIntent().getStringExtra("salerid"), ""
								+ getIntent().getStringExtra("salername"), et
								.getText().toString(),
								(Integer.parseInt(level)) + "");
						ServerAsyncHttpTask.execute(map,
								new UpdateUIInterface() {
									@Override
									public void updateUI(Object model) {
										// TODO Auto-generated method stub
										if ((boolean) model) {
											Toast.makeText(
													SendCommentActivity.this,
													"评论成功，感谢您的评论！",
													Toast.LENGTH_SHORT).show();
											finish();
										} else
											Toast.makeText(
													SendCommentActivity.this,
													"评论失败，服务器繁忙，请稍后再试！",
													Toast.LENGTH_SHORT).show();
									}
								}, new State_parse_Json());
					} else {

						Toast.makeText(SendCommentActivity.this, "评论不能为空",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(SendCommentActivity.this, "请选择评论等级！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void init() {
		rg = (RadioGroup) findViewById(R.id.consultant_rg);
		rb1 = (RadioButton) findViewById(R.id.goodcomment);
		rb1.setChecked(true);
		rb2 = (RadioButton) findViewById(R.id.mediumcomment);
		rb3 = (RadioButton) findViewById(R.id.badcomment);
		b = (Button) findViewById(R.id.sendbutton);
		et = (EditText) findViewById(R.id.scomment_textfield);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent load = new Intent(getApplicationContext(),
						ConsultantActivity.class);
				load.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(load);
				finish();
			}
		});
	}
}
