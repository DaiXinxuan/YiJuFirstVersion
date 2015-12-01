package load;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.HashMap;
import java.util.Map;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.example.haofangchan2.R;
import main.MainFragmentActivity;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import web.WebActivity;
import connect.Http_Connection;
import connect.NetHelper;
import different_jsonparse.AboutUsParser;
import different_jsonparse.Login_json_parse;
import different_jsonparse.State_parse_Json;
import differentjavabean.AboutUsModel;
import differentjavabean.LoginBean;
import add_salebuilding_demo.Add_salebuilding_activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static String base_url = "http://192.168.0.17:8080/eip/callServiceIOS_ios";
	public static String base_url_picture = "http://192.168.0.17:8080/eip";
	private String payid, hxpassword;
	private boolean state = false;
	private EditText user_name, pass_word;
	private String username;
	AboutUsModel amodel;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);

		SpannableString styledText = new SpannableString("请输入手机号");
		SpannableString styledText1 = new SpannableString("请输入密码");
		styledText.setSpan(
				new ForegroundColorSpan(Color.rgb(150, 150, 150)),
				0, styledText.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		styledText1.setSpan(
				new ForegroundColorSpan(Color.rgb(150, 150, 150)),
				0, styledText1.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		user_name = (EditText) findViewById(R.id.ed_username);
		pass_word = (EditText) findViewById(R.id.ed_password);

		user_name.setHint(styledText);
		pass_word.setHint(styledText1);
		
		if (MyApplication.getUserName() != null) {
			user_name.setText(MyApplication.getUserName());
		}
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.new_customer:
			Intent register = new Intent(getApplicationContext(),
					Register.class);
			startActivity(register);
			break;
		case R.id.forget_password:
			Intent forgetpassword = new Intent(getApplicationContext(),
					PasswordLost.class);
			forgetpassword.putExtra("change", "2");
			startActivity(forgetpassword);
			break;
		case R.id.login_button:
			username = user_name.getText().toString();
			String password = pass_word.getText().toString();
			String versionNameString=null;
			if (username != null && password != null) {
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);  
		        StringBuilder sb = new StringBuilder();  
		        sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());  
		        sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());  
		        sb.append("\nLine1Number = " + tm.getLine1Number());  
		        sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());  
		        sb.append("\nNetworkOperator = " + tm.getNetworkOperator());  
		        sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());  
		        sb.append("\nNetworkType = " + tm.getNetworkType());  
		        sb.append("\nPhoneType = " + tm.getPhoneType());  
		        sb.append("\nSimCountryIso = " + tm.getSimCountryIso());  
		        sb.append("\nSimOperator = " + tm.getSimOperator());  
		        sb.append("\nSimOperatorName = " + tm.getSimOperatorName());  
		        sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());  
		        sb.append("\nSimState = " + tm.getSimState());  
		        sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());  
		        sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());  
		        
		        try {
		        	versionNameString=getPackageManager().getPackageInfo(MyApplication.getContext().getPackageName(), 0).versionName;
					sb.append("\nversionName"+getPackageManager().getPackageInfo(MyApplication.getContext().getPackageName(), 0).versionName);
					sb.append("\nversionCode"+getPackageManager().getPackageInfo(MyApplication.getContext().getPackageName(), 0).versionCode);
				} catch (NameNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        Build bd = new Build();
		        Log.i("info", bd.MODEL+":"+bd.DEVICE+":"+bd.PRODUCT);
		        sb.append("\ninfo"+bd.MODEL+":"+bd.DEVICE+":"+bd.PRODUCT+"品牌："+bd.MODEL);
		        String version = android.os.Build.VERSION.RELEASE; 
				LogUtil.d("登录的设备号", ""+sb+"版本："+version);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", "10001");
				map.put("username", username);
				map.put("password", password);
                map.put("system", "android"+version);
                map.put("devicemodel",""+bd.MODEL);
                map.put("devicename", ""+bd.PRODUCT);
                map.put("devicesoftversions",""+versionNameString);
                map.put("deviceversions", ""+version);
				try {
					ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

						@Override
						public void updateUI(Object model) {
							LoginBean lb = (LoginBean)model;
							state = lb.isState();
							payid = lb.getPayid();
							hxpassword = lb.getHxpassword();
							SharedPreferences setting = getSharedPreferences(
									"setting", LoginActivity.this.MODE_PRIVATE);
							Editor editor = setting.edit();
							editor.putString("payid", payid);
							editor.putString("username", username);
							editor.putString("hxusername", lb.getHxusername());
							editor.putString("hxpassword", hxpassword);
							editor.commit();
							//LogUtil.d("ssssssssss", lb.getHxusername());
							if (state == true) {
								EMChatManager.getInstance().login(lb.getHxusername(),
										hxpassword, new EMCallBack() {// 回调

											public void onSuccess() {
												runOnUiThread(new Runnable() {
													public void run() {
														Toast.makeText(
															   LoginActivity.this	,
																"登录成功！",
																Toast.LENGTH_SHORT)
																.show();
														Intent login = new Intent(
																getApplicationContext(),
																Add_salebuilding_activity.class);
														startActivity(login);
													}
												});
											}

											@Override
											public void onProgress(
													int progress, String status) {

											}

											@Override
											public void onError(int code,
													String message) {
												Log.d("main", "登陆聊天服务器失败！");
											}
										});

							} else {
								Toast.makeText(getApplicationContext(),
										"错误的账号或密码，请重新输入", Toast.LENGTH_SHORT)
										.show();
							}

						}
					}, new Login_json_parse());

				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "网络异常",
							Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(getApplicationContext(), "账号密码不能为空",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.walk_button:
			HashMap<String, String> map = JSONCommand.JSON10020("", "", "", "");
			ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
				@Override
				public void updateUI(Object model) {
					// TODO Auto-generated method stub
					amodel = (AboutUsModel) model;
					Intent walk = new Intent(LoginActivity.this,WebActivity.class);
					walk.putExtra("url", amodel.getHtml());
					walk.putExtra("title", "关于我们");
					startActivity(walk);
				}
			}, new AboutUsParser());
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*
		 * if (id == R.id.action_settings) { return true; }
		 */
		return super.onOptionsItemSelected(item);
	}

}
