package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.AttentionBean;
import differentjavabean.MyfriendActivity_javabean;

public class MyfriendActivity_jsonparse implements ConnectionHandleInteface {

	private List<MyfriendActivity_javabean> listdata;

	public List<MyfriendActivity_javabean> Maindata_parse(String result) {

		try {
			LogUtil.d(" Main_DataParse", "开始解析");

			JSONObject state_jObject = new JSONObject(result);
			boolean state = state_jObject.getBoolean("status");
			LogUtil.d("Commnet_DataParse", "" + state);

			LogUtil.d("Main_DataParse", "返回状态码正确");
			if (state == true) {

				listdata = new ArrayList<MyfriendActivity_javabean>();
				JSONArray salerlist = state_jObject.getJSONArray("saler");
				JSONArray friendList = state_jObject.getJSONArray("friends");
				JSONArray myPhotos = state_jObject.getJSONArray("headphoto");
				String myPhoto = null;
				if(myPhotos.length()!=0){
					myPhoto = myPhotos.getString(0);
				}
				
				for (int i = 0; i < salerlist.length(); i++) {
					JSONObject salerJsonObject = salerlist.getJSONObject(i);
					MyfriendActivity_javabean data = new MyfriendActivity_javabean();
					String payuserid = salerJsonObject.getString("salerid");
					String nickname = salerJsonObject.getString("realname");
					JSONArray headphotos=salerJsonObject.getJSONArray("headphoto");
					String headphotopath=null;
					if(headphotos.length()!=0){
					headphotopath=headphotos.getString(0);}
					String hxusername = salerJsonObject.getString("hxusername");
					int isVip = 1;
					data.setHeadphotopath(headphotopath);
					data.setHxusername(hxusername);
					data.setNickname(nickname);
					data.setIsVip(isVip);
					data.setPayuserid(payuserid);
					data.setMyHeadPhoto(myPhoto);
					listdata.add(data);
				}

				for (int i = 0; i < friendList.length(); i++) {
					JSONObject friendJsonObject = friendList.getJSONObject(i);
					MyfriendActivity_javabean data = new MyfriendActivity_javabean();
					String payuserid = friendJsonObject.getString("payuserid");
					String nickname = friendJsonObject.getString("nickname");
					JSONArray headphotos=friendJsonObject.getJSONArray("headphoto");
					String headphotopath=null;
					if(headphotos.length()!=0){
					headphotopath=headphotos.getString(0);}
					String hxusername = friendJsonObject
							.getString("hxusername");
					int isVip = 0;
					data.setHeadphotopath(headphotopath);
					data.setHxusername(hxusername);
					data.setNickname(nickname);
					data.setIsVip(isVip);
					data.setPayuserid(payuserid);
					data.setMyHeadPhoto(myPhoto);
					listdata.add(data);
				}

			}
		} catch (JSONException e) {
            e.printStackTrace();
			LogUtil.d("bug", "zhenshibug4");
		}

		return listdata;

	}

	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}
