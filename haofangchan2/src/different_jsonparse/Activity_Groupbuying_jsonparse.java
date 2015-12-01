package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Activity_Groupbuying_javabean;


public class Activity_Groupbuying_jsonparse  implements ConnectionHandleInteface{
	JSONArray jsonArray;
	Activity_Groupbuying_javabean data=new Activity_Groupbuying_javabean();

	
	public Activity_Groupbuying_javabean Maindata_parse(String result){
		 
		try {
			 LogUtil.d(" Main_DataParse","开始解析");
			
			 JSONObject state_jObject=new JSONObject(result);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 if(state==true){
				 LogUtil.d("Main_DataParse","返回状态码正确");

				 String name=state_jObject.getString("name");
				 String personnum=state_jObject.getString("personnum");
				 String begindate=state_jObject.getString("begindate");
				 String enddate=state_jObject.getString("enddate");
				 String instruction=state_jObject.getString("instruction");
				
				 String content=state_jObject.getString("content");
				 
		         data.setBegindate(begindate);
		         data.setEnddate(enddate);
		         data.setInstruction(instruction);
                 data.setName(name);
                 data.setPersonnum(personnum);
                 data.setContent(content);

               

			 
			 }} catch (JSONException e) {
			
			LogUtil.d("bug", "zhenshibug4");
		}
		
		 return data;
		 
	 }
	
	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}