package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;

import testandmanage.LogUtil;
import differentjavabean.Houseinfo_javabean;
import differentjavabean.Main_seehouse_javabean;

public class Houseinfo_jsonparse  implements ConnectionHandleInteface{
	JSONArray jsonArray;
	Houseinfo_javabean data=new Houseinfo_javabean();
	List<Houseinfo_javabean> listdata=new ArrayList<Houseinfo_javabean>();
	List<String> list;
	List<String> list2;
	
	
	public List<Houseinfo_javabean> Maindata_parse(String result){
		 list=new ArrayList<String>();
		 list2=new ArrayList<String>();

		try {
			 LogUtil.d(" Main_DataParse","开始解析");
			 jsonArray = new JSONArray(result);
			 LogUtil.d("Main_DataParse",""+jsonArray);
			 
			 JSONObject state_jObject=jsonArray.getJSONObject(0);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 
			 LogUtil.d("Main_DataParse","返回状态码正确");
			 if(state==true){
				 JSONObject jsonObject2=jsonArray.getJSONObject(1);
				 String mainphoto=jsonObject2.getString("mainphoto");
				 list2.add(mainphoto);
				 
				 JSONObject jsonObject3=jsonArray.getJSONObject(2);
				 JSONArray jsonArray2=jsonObject3.getJSONArray("photo");
				 for(int i=0;i<jsonArray2.length();i++){
					 String photo2=jsonArray2.getString(i);
					 list2.add(photo2);
				 }
				 
				 JSONObject jsonObject4=jsonArray.getJSONObject(3);
				 String isactivity=jsonObject4.getString("isactivity");
				 String attentioned=jsonObject4.getString("attentioned");
				 JSONArray jsonArray3=jsonObject4.getJSONArray("data");
				 
				 
				 for(int i=0;i<jsonArray3.length();i++){
					 String str=jsonArray3.getString(i);
					 list.add(str);
				 }
				 
				 data.setAttentioned(attentioned);
				 data.setIsactivity(isactivity);
				 data.setPhotosurl(list2);
				 data.setData(list);
				
				 
			 }} catch (JSONException e) {
			
			LogUtil.d("bug", "zhenshibug4");
		}
		listdata.add(data);
	    
		 return listdata;
		 
	 }
	
	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}
