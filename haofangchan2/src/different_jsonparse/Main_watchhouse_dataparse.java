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
import differentjavabean.Main_seehouse_javabean;

public class Main_watchhouse_dataparse implements ConnectionHandleInteface{
	JSONArray jsonArray;
	Main_seehouse_javabean data=new Main_seehouse_javabean();
	List<Main_seehouse_javabean> listdata=new ArrayList<Main_seehouse_javabean>();
	List<String> list;
	
	public List<Main_seehouse_javabean> Maindata_parse(String result){
		 
		 listdata=new ArrayList<Main_seehouse_javabean>();
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
				 
				 int roomid=content_jObject.getInt("roomid");
				 LogUtil.d("Main_DataParse_roomid",""+roomid);
				 
				 String typephoto=content_jObject.getString("typephoto");
				 LogUtil.d("Main_DataParse_typephoto",""+typephoto);
				 
				 int photocount=content_jObject.getInt("photocount");
				 LogUtil.d("Main_DataParse_photocount",""+photocount);
				 
				 String activity=content_jObject.getString("activity");
				 LogUtil.d("Main_DataParse_activity",""+activity);
				 
				 JSONArray jsonArray2=content_jObject.getJSONArray("info");
				 LogUtil.d("Main_DataParse_info",""+jsonArray2);
				 list=new ArrayList<String>();
				 for(int i=0;i<jsonArray2.length();i++){
				
					String str1=jsonArray2.getString(i);
				    list.add(str1);
				 }
				 LogUtil.d("Main_DataParse_list",""+list);
				 
				 Main_seehouse_javabean data=new Main_seehouse_javabean();
				 data.setRoomid(roomid);
				 data.setTypephoto(typephoto);
				 data.setPhotocount(photocount);
				 data.setActivity(activity);
				 data.setList(list);
				 listdata.add(data);
				 LogUtil.d("main_watchhouse_dataparse", ""+listdata);
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