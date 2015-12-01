package load;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import testandmanage.MyApplication;

import com.example.haofangchan2.R;

import connect.Http_Connection;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import different_jsonparse.State_parse_Json;
import add_salebuilding_demo.Add_salebuilding_activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Register extends Activity{
	private Button b1,b2;
	private EditText et1,et2,et3,et4;
	public String phString,username,password;
	int i=30;//������֤���ȡ��֤ʱ��
	Timer timer;
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 1){
				if(i==0){
					b1.setClickable(true);
					b1.setText("��ȡ��֤��");
					timer.cancel();
					i=30;
				}else{
					b1.setText(i+"������»�ȡ");
					i--;
				}
			}else{
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event="+event);
				if (result == SMSSDK.RESULT_COMPLETE) {
					//����ע��ɹ��󣬷���MainActivity,Ȼ����ʾ�º���
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//�ύ��֤��ɹ�
						Toast.makeText(getApplicationContext(), "�ύ��֤��ɹ�", Toast.LENGTH_SHORT).show();
						Map<String, String> map=new HashMap<String, String>();
						   map.put("type", "10002");
						   map.put("username",username);
						   map.put("password", password);
						   map.put("nickname", "�˾�_"+username);
						   boolean state=false;
						   try{
							   state=(Boolean)Http_Connection.postRequest(MyApplication.base_url, map, 
										 new  State_parse_Json());
						   }catch (Exception e) {
								Toast.makeText(getApplicationContext(), "�����쳣",
				                         Toast.LENGTH_SHORT).show();
						   }
						   if(state==true){
							   Toast.makeText(getApplicationContext(), "ע��ɹ�",
				                         Toast.LENGTH_SHORT).show();
							   Intent login=new Intent(getApplicationContext(),LoginActivity.class);
							   startActivity(login);
						   }else{
							   Toast.makeText(getApplicationContext(), "ע��ʧ��,�û����Ѵ��ڣ�",
				                         Toast.LENGTH_SHORT).show();
						   }
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
						Toast.makeText(getApplicationContext(), "��֤���Ѿ�����", Toast.LENGTH_SHORT).show();
					}else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//����֧�ַ�����֤��Ĺ����б�
						Toast.makeText(getApplicationContext(), "��ȡ�����б��ɹ�", Toast.LENGTH_SHORT).show();
						
					}
				} else {
					((Throwable) data).printStackTrace();
					Toast.makeText(Register.this, "��֤�����", Toast.LENGTH_SHORT).show();
				}
			}	
		}
	};
	TimerTask task = new TimerTask() {
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newuser_register);
		ImageButton back=(ImageButton) findViewById(R.id.title_btn_register);
		back.setOnClickListener(listener);
		b1=(Button) findViewById(R.id.nr_get_identifying_code);
		b2=(Button) findViewById(R.id.nr_register);
		et1=(EditText) findViewById(R.id.nr_Phone_number);
		et2=(EditText) findViewById(R.id.nr_identifying_code);
		et3=(EditText) findViewById(R.id.nr_password);
		et4=(EditText) findViewById(R.id.nr_password2);
		
		SpannableString ss = new SpannableString("�������ֻ�����");
		SpannableString ss1 = new SpannableString("��������֤��");
		SpannableString ss2 = new SpannableString("4-16λ,Ӣ�ļ��������");
		SpannableString ss3 = new SpannableString("����һ��������������");
		AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12,true);
		ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss3.setSpan(ass, 0, ss3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		et1.setHint(new SpannableString(ss));
		et2.setHint(new SpannableString(ss1));
		et3.setHint(new SpannableString(ss2));
		et4.setHint(new SpannableString(ss3));
		
		SMSSDK.initSDK(this,"5887b8134af8","9cd46d6cf9908a297fbafeb75b7b19d3");
		EventHandler eh=new EventHandler(){
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eh);
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!TextUtils.isEmpty(et1.getText().toString())){
					SMSSDK.getVerificationCode("86",et1.getText().toString());
					phString=et1.getText().toString();
					timer = new Timer();
					task = new TimerTask() {
						public void run() {
							// TODO Auto-generated method stub
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					};
					timer.schedule(task, 0, 1000);
					b1.setClickable(false);
				}else {
					Toast.makeText(Register.this,"�绰����Ϊ��",1).show();
				}
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!TextUtils.isEmpty(et3.getText().toString())){
					if(et4.getText().toString().equals(et3.getText().toString())){
						username=et1.getText().toString();
						password=et3.getText().toString();
						if(!TextUtils.isEmpty(et2.getText().toString())){
							SMSSDK.submitVerificationCode("86", phString, et2.getText().toString());
						}else {
							Toast.makeText(Register.this, "��֤�벻��Ϊ��", 1).show();
						}
					}else{
						Toast.makeText(Register.this, "�����������벻һ�£����֤��", 1).show();
					}
				}else {
					Toast.makeText(Register.this, "���벻��Ϊ��", 1).show();
				}
				
			}
		});
	}
	OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.title_btn_register:
				Intent load=new Intent(getApplicationContext(),LoginActivity.class);
				load.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(load);
				Register.this.finish();
			}
			
		}
	};
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}
}