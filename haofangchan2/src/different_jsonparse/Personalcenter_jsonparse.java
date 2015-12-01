package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Houseinfo_javabean;
import differentjavabean.Personalcenter_javabean;

public class Personalcenter_jsonparse  implements ConnectionHandleInteface{

	List<Personalcenter_javabean> listdata=
			new ArrayList<Personalcenter_javabean>();

	
	
	public List<Personalcenter_javabean> Maindata_parse(String result){


		try {
			
			 
			 JSONObject state_jObject=new JSONObject(result);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 
			 LogUtil.d("Main_DataParse","·µ»Ø×´Ì¬ÂëÕýÈ·");
			 if(state==true){
				String headphoto=state_jObject.getString("headphoto");
				String nickname=state_jObject.getString("nickname");
				String sex=state_jObject.getString("sex");
				String loginDate=state_jObject.getString("loginDate");
				String introduce=state_jObject.getString("introduce");
				Personalcenter_javabean data=new Personalcenter_javabean();
				data.setHeadphoto(headphoto);
				data.setNickname(nickname);
				data.setSex(sex);
				data.setLoginDate(loginDate);
				data.setIntroduce(introduce);
				listdata.add(data);
				
				 
			 }} catch (JSONException e) {
			
			LogUtil.d("bug", "zhenshibug4");
		}
	
	    
		 return listdata;
		 
	 }
	
	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}
