package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Activity_Indiana_javabean;

public class Activity_Indiana_jsonparse implements ConnectionHandleInteface{
	JSONArray jsonArray;
	List<Activity_Indiana_javabean> list;
    private int code=0;
	public Activity_Indiana_jsonparse(){
		
	}
	public Activity_Indiana_jsonparse(int code){
		this.code=code;
	}
	public List<Activity_Indiana_javabean> Maindata_parse(String result){
		 
		try {
			 list=new ArrayList<Activity_Indiana_javabean>();
			 
			 LogUtil.d(" Main_DataParse frag2","开始解析");
			 
			 //JSONObject jsonArray=new JSONObject(result);
			 
			 //JSONObject state_jObject=jsonArray.getJSONObject(0);
			 JSONObject state_jObject=new JSONObject(result);
			 jsonArray=state_jObject.getJSONArray("pastInfo");
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 if(state==true){
				 LogUtil.d("Main_DataParse frag2","返回状态码正确");
                 if(code==1){
//				 String adsimage=state_jObject.getString("adsimage");
//				 String adsurl=state_jObject.getString("adsurl");
//				 
				 Activity_Indiana_javabean data=new Activity_Indiana_javabean();
//				 data.setAdsimage(adsimage);
//				 data.setAdsurl(adsurl);
                
				 //添加第一条数据，该数据为标题图片和链接地址
				 //list.add(data); 
				 }
				 
				 for(int i=1;i<jsonArray.length();i++){
					 JSONObject jsonObject2=jsonArray.getJSONObject(i);
					 Activity_Indiana_javabean data2=new Activity_Indiana_javabean();
					 String activityid=jsonObject2.getString("activityid");
					 String activityimage=jsonObject2.getString("activityimage");
					 String name=jsonObject2.getString("name");
					 String introduction=jsonObject2.getString("introduction");
					 String time=jsonObject2.getString("time");
			
					 
					 data2.setActivityid(activityid);
					 data2.setActivityimage(activityimage);
					 data2.setName(name);
					 data2.setIntroduction(introduction);
					 data2.setTime(time);

					 //添加第二条及之后数据，该数据为活动名称，ID，活动介绍，开始时间
					 //与结束时间
					 list.add(data2);
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