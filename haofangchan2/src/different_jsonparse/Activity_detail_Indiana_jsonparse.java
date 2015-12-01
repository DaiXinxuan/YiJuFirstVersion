package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Activity_Sign_javabean;
import differentjavabean.Activity_detail_Indiana_javabean;

public class Activity_detail_Indiana_jsonparse implements ConnectionHandleInteface{
	JSONArray jsonArray;
	Activity_detail_Indiana_javabean data;

	
	public Activity_detail_Indiana_javabean Maindata_parse(String result){
		data=new Activity_detail_Indiana_javabean();
		try {
			 LogUtil.d(" Main_DataParse","开始解析");
			 JSONObject state_jObject=new JSONObject(result);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 if(state==true){
				 LogUtil.d("Main_DataParse","返回状态码正确");
                 JSONArray pictures=state_jObject.getJSONArray("activityimage");
				 String activityimage=pictures.getString(0);
				 String name=state_jObject.getString("name");
				 String introduction=state_jObject.getString("introduction");
				 String date=state_jObject.getString("date");
				 String count=state_jObject.getString("count");
				 String tel=state_jObject.getString("tel");
				 String url=state_jObject.getString("url");
				 String html = state_jObject.getString("html");
		         data.setActivityimage(activityimage);
		         data.setName(name);
		         data.setIntroduction(introduction);
		         data.setEndtime(date);
		         data.setCount(count);
		         data.setTel(tel);
                 data.setUrl(url);
                 data.setHtml(html);

               

			 
			 }} catch (JSONException e) {
			
			LogUtil.d("bug", "zhenshibug4");
		}
		
		 return data;
		 
	 }
	
	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}
