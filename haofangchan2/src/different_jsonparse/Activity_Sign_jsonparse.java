package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Activity_Sign_javabean;

public class Activity_Sign_jsonparse implements ConnectionHandleInteface{
	JSONArray jsonArray;
	Activity_Sign_javabean data;

	
	public Activity_Sign_javabean Maindata_parse(String result){
		 
		try {
			 LogUtil.d(" Main_DataParse","开始解析");
			 data=new Activity_Sign_javabean();
			 JSONObject state_jObject=new JSONObject(result);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 if(state==true){
				 LogUtil.d("Main_DataParse","返回状态码正确");

				 String activatyid=state_jObject.getString("activityid");
				 String instruction=state_jObject.getString("instruction");
				 String date=state_jObject.getString("date");
				 String signState=state_jObject.getString("signState");
				 String nameLength=state_jObject.getString("nameLength");
				 String signday=state_jObject.getString("signday");
				 String content=state_jObject.getString("content");
				 String award = state_jObject.getString("signaward")+"元"+state_jObject.getString("awardkind");
				 String url = state_jObject.getString("html");
				 String topUrl = state_jObject.getString("url");
				 JSONArray pictureAddresses=state_jObject.getJSONArray("advertisementPic");
				 String pictureAddress=pictureAddresses.getString(0);
				 
		         data.setSignState(signState);
		         data.setDate(date);
		         data.setInstruction(instruction);
		         data.setActivatyid(activatyid);
		         data.setNameLength(nameLength);
		         data.setSignday(signday);
                 data.setContent(content);
                 data.setPictureAddress(pictureAddress);
                 data.setAward(award);
                 data.setUrl(url);
                 data.setTopUrl(topUrl);
                 
                 LogUtil.d("Main_DataParse","返回状态码正确");
                 LogUtil.d("Main_DataParse",data.toString());
			 
			 }} catch (JSONException e) {
			
			LogUtil.d("bug", "zhenshibug4");
		}
		
		 return data;
		 
	 }
	
	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}