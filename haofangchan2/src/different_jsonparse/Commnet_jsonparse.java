package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Commnet_javabean;
import differentjavabean.Houseinfo_javabean;

public class Commnet_jsonparse  implements ConnectionHandleInteface{
    
	List< Commnet_javabean> listdata=new ArrayList< Commnet_javabean>();

	
	
	public List< Commnet_javabean> Maindata_parse(String result){


		try {
			 
			 JSONObject state_jObject=new JSONObject(result);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Commnet_DataParse",""+state);
			 
			 LogUtil.d("Main_DataParse","·µ»Ø×´Ì¬ÂëÕýÈ·");
			 if(state==true){
				 JSONArray infos=state_jObject.getJSONArray("info");
				for(int i=0;i<infos.length();i++){
				JSONObject jsonObject2=infos.getJSONObject(i);
				JSONArray photos=jsonObject2.getJSONArray("headphoto");
				String headphoto=photos.getString(0);
				String username=jsonObject2.getString("name");
				String level=jsonObject2.getString("level");
				String content=jsonObject2.getString("content");
				String date=jsonObject2.getString("date");
				Commnet_javabean data1=new Commnet_javabean();
				data1.setHeadphoto(headphoto);
				data1.setUsername(username);
				data1.setLevel(level);
				data1.setContent(content);
				data1.setDate(date);
				listdata.add(data1);
				}
				
				
				 
				 
			 }} catch (JSONException e) {
			
			LogUtil.d("bug", "zhenshibug4");
		}
	    
		 return listdata;
		 
	 }
	
	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}
