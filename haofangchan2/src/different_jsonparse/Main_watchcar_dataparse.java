package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Main_seecar_javabean;
import differentjavabean.Main_seehouse_javabean;

public class Main_watchcar_dataparse implements ConnectionHandleInteface{
	JSONArray jsonArray;
	Main_seehouse_javabean data=new Main_seehouse_javabean();
	List<Main_seecar_javabean> listdata=new ArrayList<Main_seecar_javabean>();

	
	public List<Main_seecar_javabean> Maindata_parse(String result){
		

		try {
			 LogUtil.d(" Main_DataParse","开始解析");
			 jsonArray = new JSONArray(result);
			 
			 LogUtil.d("Main_DataParse",""+jsonArray);
			 JSONObject state_jObject=jsonArray.getJSONObject(0);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 if(state==true){
				 LogUtil.d("Main_DataParse","返回状态码正确");
				 
                 for(int j=1;j<jsonArray.length();j++){
                
				 
				 JSONObject content_jObject=jsonArray.getJSONObject(j);
				 
				 String photo=content_jObject.getString("photo");
				 LogUtil.d("Main_DataParse_projectname",""+photo);
				 
				 int id=content_jObject.getInt("id");
				 LogUtil.d("Main_DataParse_projectname",""+id);
				 
				 String area=content_jObject.getString("area");
				 LogUtil.d("Main_DataParse_projectname",""+area);
				 
				 String price=content_jObject.getString("price");
				 LogUtil.d("Main_DataParse_projectname",""+price);
				 
				 String size=content_jObject.getString("size");
				 LogUtil.d("Main_DataParse_projectname",""+size);
				   
		         String status=content_jObject.getString("status");
		         LogUtil.d("Main_DataParse_projectname",""+status);
		         
		         String statement=content_jObject.getString("statement");
		         LogUtil.d("Main_DataParse_projectname",""+statement);
		         
				 Main_seecar_javabean data=new Main_seecar_javabean();
                 data.setPhoto(photo);
                 data.setId(id);
                 data.setArea(area);
                 data.setPrice(price);
                 data.setSize(size);
                 data.setStatus(status);
                 data.setStatement(statement);
				 
				 
				 listdata.add(data);
				 
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
