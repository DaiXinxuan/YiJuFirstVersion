package setting;


import httpConnect.ConnectionHandleInteface;
import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.JSONCommand;
import testandmanage.MyApplication;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.haofangchan2.R;

import different_jsonparse.BooleanParser;
import differentjavabean.SalerModel;

public class ChangePasswordActivity extends Activity{
	private EditText prePassed,newPassed,assurePassed;
	private Button assureButton;
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changepassword);
		
		prePassed = (EditText) findViewById(R.id.prepaswword_ed);
		newPassed = (EditText) findViewById(R.id.newpaswword_ed);
		assurePassed = (EditText) findViewById(R.id.assurepaswword_ed);
		
		// �½�һ������������Ե��ı�����
    	SpannableString ss = new SpannableString("�����������");
    	SpannableString ss2 = new SpannableString("������������6-14�ַ���Ӣ�ļ��������"); 
    	SpannableString ss3 = new SpannableString("ȷ��������"); 
    	// �½�һ�����Զ���,�������ֵĴ�С
    	AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
    	// �������Ե��ı�
    	ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    	ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    	ss3.setSpan(ass, 0, ss3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    	// ����hint
    	prePassed.setHint(new SpannedString(ss)); // һ��Ҫ����ת��,�������Ի���ʧ
    	newPassed.setHint(new SpannedString(ss2)); 
    	assurePassed.setHint(new SpannedString(ss3)); 
		
		assureButton = (Button) findViewById(R.id.assure_button);
		//back = (ImageView) findViewById(R.id.back_arrow);
		final SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
		assureButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String password = newPassed.getText().toString();
				String oldPass = prePassed.getText().toString();
				String apass = assurePassed.getText().toString();
				if(!password.equals("")&&!oldPass.equals("")&&!apass.equals("")){
					if(password.equals(apass)){
						Map<String, String> map=new JSONCommand().JSON10003(MyApplication.getpayid(), MyApplication.getUserName(), password, oldPass);
						ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
							@Override
							public void updateUI(Object model) {
								// TODO Auto-generated method stub
								if((Boolean)model==true){
									Toast.makeText(getApplicationContext(), "�����޸ĳɹ���",
				                         Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(getApplicationContext(), "���緱æ�������޸ĳɹ���",
					                         Toast.LENGTH_SHORT).show();
								}
							}
						},new BooleanParser());
					}else{
						Toast.makeText(getApplicationContext(), "ȷ�������벻һ�£����֤��",
		                         Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "������Ϣ����Ϊ�գ�",
	                         Toast.LENGTH_SHORT).show();
				}
				
			}
		});
	}
	
}
