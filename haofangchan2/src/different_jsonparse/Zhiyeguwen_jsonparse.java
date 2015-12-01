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
import differentjavabean.Zhiyeguwen_javabean;

public class Zhiyeguwen_jsonparse implements ConnectionHandleInteface{
	private int m=0;
	private String str=null;
	private List<Zhiyeguwen_javabean> list;
	private String SalerName="";
 public List<Zhiyeguwen_javabean> Picture_parse(String result){
	 JSONObject jsonObject;
	 
	 list=new ArrayList<Zhiyeguwen_javabean>() ;
	try {
		 jsonObject = new JSONObject(result);
		 boolean state=jsonObject.getBoolean("status");
		 if(state==true){
			
			 JSONArray salerInfos=jsonObject.getJSONArray("salerInfo");
			 JSONArray peoples=jsonObject.getJSONArray("people");
			 
			 for(int i=0;i<salerInfos.length();i++){
				 JSONObject jsonData=salerInfos.getJSONObject(i);
				 Zhiyeguwen_javabean data=new Zhiyeguwen_javabean();
                 data.setName(jsonData.getString("name"));
                 SalerName=jsonData.getString("name");
                 JSONArray photos=jsonData.getJSONArray("headphoto");
                 data.setPhotourl(photos.getString(0));
                 data.setSaleid(jsonData.getString("saleid"));
                 data.setHasSaler(true);
                 list.add(data);
			 }
			 
			 
			 for(int i=0;i<peoples.length();i++){
				 JSONObject jsonData=peoples.getJSONObject(i);
				 Zhiyeguwen_javabean data=new Zhiyeguwen_javabean();
                 data.setName(jsonData.getString("name"));
                 JSONArray photos=jsonData.getJSONArray("headphoto");
                 data.setPhotourl(photos.getString(0));
                 data.setSaleid(jsonData.getString("saleid"));
                 data.setHasSaler(false);
                 if(SalerName!=""&&SalerName.equals(jsonData.getString("name"))){
                	 
                 }else{
                 list.add(data);
                 }
			 }
		 }
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		LogUtil.d("bug", "zhenshibug4");
	}
    
	 return list;
	 
 }
@Override
public Object handResponse(String response) {
	
	return Picture_parse(response);
}
}
