package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Personalcenter_javabean;
import differentjavabean.Personalcenter_profile_javabean;

public class Personalcenter_profile_jsonparse  implements ConnectionHandleInteface{

	List<Personalcenter_profile_javabean> listdata=
			new ArrayList<Personalcenter_profile_javabean>();

	
	
	public List<Personalcenter_profile_javabean> Maindata_parse(String result){


		try {
			
			 JSONArray jsonArray=new JSONArray(result);
			 JSONObject state_jObject=jsonArray.getJSONObject(0);
			 boolean state=state_jObject.getBoolean("status");
			 LogUtil.d("Main_DataParse",""+state);
			 
			 LogUtil.d("Main_DataParse","·µ»Ø×´Ì¬ÂëÕýÈ·");
			 if(state==true){
				String headphoto=state_jObject.getString("headphoto");
				String nickname=state_jObject.getString("nickname");
				String sex=state_jObject.getString("sex");
				String introduce=state_jObject.getString("introduce");
				String name=state_jObject.getString("name");
				String age=state_jObject.getString("age");
				String tel=state_jObject.getString("tel");
				String addr=state_jObject.getString("addr");
				String member=state_jObject.getString("member");
				String job=state_jObject.getString("job");
				String income=state_jObject.getString("income");
				String housetype=state_jObject.getString("housetype");
				String willprice=state_jObject.getString("willprice");
				String logintime=state_jObject.getString("logintime");
				Personalcenter_profile_javabean data=new Personalcenter_profile_javabean();
				data.setHeadphoto(headphoto);
				data.setNickname(nickname);
				data.setSex(sex);
				data.setIntroduce(introduce);
                data.setName(name);
                data.setAge(age);
                data.setTel(tel);
                data.setAddr(addr);
                data.setMember(member);
                data.setJob(job);
                data.setIncome(income);
                data.setHousetype(housetype);
                data.setWillprice(willprice);
                data.setLogintime(logintime);
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

