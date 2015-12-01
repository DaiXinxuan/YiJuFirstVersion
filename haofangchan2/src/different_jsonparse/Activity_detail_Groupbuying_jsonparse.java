package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import testandmanage.LogUtil;
import differentjavabean.Activity_detail_Groupbuying_javabean;

public class Activity_detail_Groupbuying_jsonparse implements ConnectionHandleInteface{
	JSONArray jsonArray;
	Activity_detail_Groupbuying_javabean data;
    List<String> counts,states;
	
	public Activity_detail_Groupbuying_javabean Maindata_parse(String result){
		data=new Activity_detail_Groupbuying_javabean();
		counts=new ArrayList<String>();
		states=new ArrayList<String>();
		try {
			 LogUtil.d(" Main_DataParse","开始解析");

			 JSONObject state_jObject=new JSONObject(result);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 if(state==true){
				 LogUtil.d("Main_DataParse","返回状态码正确");
				 JSONArray pictures=state_jObject.getJSONArray("adsimage");
				 JSONObject imageInfo=pictures.getJSONObject(0);
				 String image=imageInfo.getJSONArray("img").getString(0);
				 String adsimage=image;
                 String adsurl=imageInfo.getString("url");
                 JSONArray infos=state_jObject.getJSONArray("info");
                 JSONArray statesInfos=state_jObject.getJSONArray("state");
                 for(int i=0;i<infos.length();i++){

                	 String count=infos.getString(i);
                	 counts.add(count);
                 }
                 for(int i=0;i<statesInfos.length();i++){
                	 String state2=statesInfos.getString(i);
                	 states.add(state2);
                 }
                 
				 data.setAdsimage(adsimage);
				 data.setAdsurl(adsurl);
				 data.setCount(counts);
				 data.setStates(states);
			 
			 }} catch (JSONException e) {
			
			LogUtil.d("bug", "zhenshibug4");
		}
		
		 return data;
		 
	 }
	
	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}
