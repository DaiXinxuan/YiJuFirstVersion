package connect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import load.LoginActivity;

import testandmanage.LogUtil;

import com.example.haofangchan2.R;

import different_jsonparse.Parsenew;
import different_jsonparse.PictureList_parse_Json;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

public class Connect_test_view extends Activity{
	  private ImageView imageView = null;  
	    Bitmap bitmap=null;
	    private static final int MSG_CODE = 1001;
	    ImageView imgv=null;

protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.justfortestshowview);
	TextView tvvv=(TextView) findViewById(R.id.id);
	imgv=(ImageView) findViewById(R.id.show_justshow);
	 final Handler mHandler = new Handler()
     {
     public void handleMessage(Message msg)
     {
     //接收并处理消息
     if(msg.what == MSG_CODE)
     {
     	imgv.setImageBitmap(bitmap); 
     }
     }
     };
	String username="13880709107";
	Map<String, String> map=new HashMap<String, String>();
	map.put("type", "10005");
	map.put("username",username+"");
	List<Map<String, String>> listofpicture=new ArrayList<Map<String, String>>() ;
	try {
		listofpicture=(List<Map<String, String>>) Http_Connection.postRequest(
				LoginActivity.base_url,map,
				new PictureList_parse_Json());
		LogUtil.d("ceshijiemian", "fuwuqifanhui"+listofpicture);
	} catch (Exception e) {
		LogUtil.d("ssss2", "big wrong");
	} 

	  int id=Integer.parseInt(listofpicture.get(0).get("id"));
	  final String pictureurl=listofpicture.get(0).get("picture_url");
      tvvv.setText(id+"");
      
  	new Thread(new Runnable(){
	  public void run(){
	  Looper.prepare();
      bitmap=new Parsenew(Parsenew.base_url_picture+pictureurl).parse_picture();
  	  Message msg = mHandler.obtainMessage(MSG_CODE); 
	 mHandler.sendMessage(msg);  
	 }}).start();
      
}  

}
