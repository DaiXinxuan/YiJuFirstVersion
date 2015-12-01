package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Setting_Magazine_javabean;

public class Setting_Magazine_jsonparse  implements ConnectionHandleInteface{
	Setting_Magazine_javabean data;
	List<Setting_Magazine_javabean> list;

	
	public List<Setting_Magazine_javabean> Maindata_parse(String result){
		
		list=new ArrayList<Setting_Magazine_javabean>();
		try {
			 LogUtil.d(" Main_DataParse","开始解析");
			 JSONObject state_jObject=new JSONObject(result);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 if(state==true){
				 LogUtil.d("Main_DataParse","返回状态码正确");
				 //循环获取jsonobject对象
				 JSONArray infos=state_jObject.getJSONArray("info");
				 
				 for(int i=0;i<infos.length();i++){
					 
                 JSONObject jsonData=infos.getJSONObject(i);
				 
                 String name=jsonData.getString("name");
                 JSONArray photos=jsonData.getJSONArray("headphoto");
                 String photopath=photos.getString(0);
                 String url=jsonData.getString("url");
                 String type=jsonData.getString("kind");
                 String content=jsonData.getString("introduce");
                 
                 data=new Setting_Magazine_javabean();
		         data.setName(name);
		         data.setPhotopath(photopath);
		         data.setUrl(url);
		         data.setType(type);
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