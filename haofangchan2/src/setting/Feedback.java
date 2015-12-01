package setting;


import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.HashMap;
import java.util.Map;

import load.LoginActivity;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
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

import connect.Http_Connection;
import different_jsonparse.BooleanParser;
import different_jsonparse.State_parse_Json;

public class Feedback extends Activity {

    private EditText editText1,editText2;
    private Button btn;
    ImageView back;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_userfeedback);
		init();
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!editText1.getText().toString().equals("")&&!editText2.getText().toString().equals("")){
					postData();
				}else{
					Toast.makeText(getApplicationContext(), "反馈内容和联系方式不能为空", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	
	}
     private void postData() {
    	 Map<String, String> map=new HashMap<String, String>();
		 try {
			map = JSONCommand.JSON10032(editText1.getText().toString(),editText2.getText().toString()
					 ,getPackageManager().getPackageInfo(MyApplication.getContext().getPackageName(), 0).versionName);
		 } catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
		 ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				if((boolean)model==true){
					Toast.makeText(getApplicationContext(), "意见反馈成功", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "意见反馈失败，请稍后再试", Toast.LENGTH_SHORT).show();
				}
			}
		}, new BooleanParser());
		   
	
}
	public void init(){
        editText1 = (EditText)findViewById(R.id.feedback_et1);
    	editText2 = (EditText)findViewById(R.id.feedback_et2);
    	btn=(Button) findViewById(R.id.feedback_submit1);
    	back = (ImageView) findViewById(R.id.back);
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Feedback.this.finish();
			}
		});
    	// 新建一个可以添加属性的文本对象
    	SpannableString ss = new SpannableString("对我们有什么意见或者建议，我们将改进");
    	SpannableString ss2 = new SpannableString("请留下您的手机号码或邮箱"); 
    	// 新建一个属性对象,设置文字的大小
    	AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);
    	// 附加属性到文本
    	ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    	ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    	// 设置hint
    	editText1.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    	editText2.setHint(new SpannedString(ss2)); 
     };
}
