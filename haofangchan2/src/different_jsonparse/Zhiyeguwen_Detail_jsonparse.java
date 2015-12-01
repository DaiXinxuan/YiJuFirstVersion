package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Zhiyeguwen_Detail_javabean;

public class Zhiyeguwen_Detail_jsonparse implements ConnectionHandleInteface{
	JSONArray jsonArray;
	Zhiyeguwen_Detail_javabean data=new Zhiyeguwen_Detail_javabean();
	List<Map<String,String>> list;

	
	
	public Zhiyeguwen_Detail_javabean Maindata_parse(String result){
		 list=new ArrayList<Map<String,String>>();


		try {
			 LogUtil.d(" Main_DataParse","开始解析");
			 jsonArray = new JSONArray(result);
			 LogUtil.d("Main_DataParse",""+jsonArray);
			 
			 JSONObject state_jObject=jsonArray.getJSONObject(0);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 
			 LogUtil.d("Main_DataParse","返回状态码正确");
			 if(state==true){
				 
				 String name=state_jObject.getString("name");
				 String age=state_jObject.getString("age");
				 String position=state_jObject.getString("position");
				 String tel=state_jObject.getString("tel");
				 String company=state_jObject.getString("company");
				 String introduction=state_jObject.getString("introduction");
				 String headphoto=state_jObject.getString("headphoto");
				 
				 
				 
				
				
				 
					JSONArray jsonArray2=jsonArray.getJSONArray(1);
					for(int i=0;i<jsonArray2.length();i++){
						JSONObject jsonObject2=jsonArray2.getJSONObject(i);
						Map<String,String>map=new HashMap<String,String>();
						map.put("level", jsonObject2.getString("level"));
						map.put("content", jsonObject2.getString("content"));
						map.put("date", jsonObject2.getString("date"));
						map.put("id", jsonObject2.getString("id"));
						map.put("headphoto", jsonObject2.getString("headphoto"));
						map.put("nickname", jsonObject2.getString("nickname"));
						list.add(map);
					
					}
					
				data.setAge(age);
				data.setCompany(company);
				data.setHeadphoto(headphoto);
				data.setIntroduction(introduction);
				data.setList(list);
				data.setName(name);
				data.setPosition(position);
				data.setTel(tel);
				 
			
				 
				
				 
			 }} catch (JSONException e) {
			
			LogUtil.d("bug", "zhenshibug4");
		}
		
	    
		 return data;
		 
	 }
	
	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}

