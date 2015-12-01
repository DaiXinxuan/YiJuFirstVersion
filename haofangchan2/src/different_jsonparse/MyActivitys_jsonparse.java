package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

import testandmanage.LogUtil;
import testandmanage.MyApplication;
import differentjavabean.MyActivities_javabean;
import differentjavabean.Setting_Magazine_javabean;

public class MyActivitys_jsonparse  implements ConnectionHandleInteface{
	
	List<MyActivities_javabean> list;

	
	public List<MyActivities_javabean> Maindata_parse(String result){
		
		list=new ArrayList<MyActivities_javabean>();
		try {
			 LogUtil.d(" Main_DataParse","开始解析");
			 JSONObject state_jObject=new JSONObject(result);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 if(state==true){
				 LogUtil.d("Main_DataParse","返回状态码正确");
				JSONArray infos=state_jObject.getJSONArray("info");
				 //循环获取jsonobject对象
				 for(int i=0;i<infos.length();i++){

                 JSONObject jsonData=infos.getJSONObject(i);
				 
                 String name=jsonData.getString("name");
                 JSONArray photos=jsonData.getJSONArray("photopath");
                 String photopath=photos.getString(0);
                 String begindate=jsonData.getString("time");
                 String content=jsonData.getString("content");
                 LogUtil.d("Main_DataParse","返回状态码正确");
                 MyActivities_javabean data=new MyActivities_javabean();
		         data.setName(name);
		         data.setPhotopath(photopath);
		         data.setDate(begindate);
		         data.setContent(content);
				 list.add(data);
				 }

			 }} catch (JSONException e) {
			
			LogUtil.d("bug", "zhenshibug4");
		}
		
		 return list;
		 
	 }
	
	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}
