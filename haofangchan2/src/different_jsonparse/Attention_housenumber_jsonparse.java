package different_jsonparse;



import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.AttentionBean;
import differentjavabean.Attention_housenumber_javabean;

public class Attention_housenumber_jsonparse implements ConnectionHandleInteface{
	JSONArray jsonArray;
    
	List<AttentionBean> listdata=new ArrayList<AttentionBean>();

	
	
	public List< AttentionBean> Maindata_parse(String result){


		try {
			 LogUtil.d(" Main_DataParse","开始解析");
			 jsonArray = new JSONArray(result);
			 LogUtil.d("Main_DataParse",""+jsonArray);
			 
			 JSONObject state_jObject=jsonArray.getJSONObject(0);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Commnet_DataParse",""+state);
			 
			 LogUtil.d("Main_DataParse","返回状态码正确");
			 if(state==true){
				for(int i=1;i<jsonArray.length();i++){
				JSONObject jsonObject2=jsonArray.getJSONObject(i);
				String unit=jsonObject2.getString("unit");
				String projectname=jsonObject2.getString("projectname");
				String buildingnum=jsonObject2.getString("bulidingnum");
				String floor=jsonObject2.getString("floor");
				String roomnum=jsonObject2.getString("roomnum");
				
				AttentionBean data1=new AttentionBean();
		        data1.setT1(projectname);
		        data1.setT2(buildingnum+"幢"+unit+"单元"+floor+"层"+roomnum+"号");
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
