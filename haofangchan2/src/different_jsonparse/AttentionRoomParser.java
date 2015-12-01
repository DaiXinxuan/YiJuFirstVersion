package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.AttentionRoomModel;
import differentjavabean.MyActivityModel;

public class AttentionRoomParser implements ConnectionHandleInteface{
	JSONObject responseObj;
	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");
			if(status.equals("true")){
				JSONArray jar = responseObj.getJSONArray("info");
				ArrayList<AttentionRoomModel> model = new ArrayList<AttentionRoomModel>();
				LogUtil.e(String.valueOf(jar.length()), "false");
				for(int i = 0;i<jar.length();i++){
					JSONObject info = jar.getJSONObject(i);
					AttentionRoomModel AttentionModel = new AttentionRoomModel();
					AttentionModel.setRoomName(info.getString("housename"));
					AttentionModel.setRommId(info.getString("houseid"));
					model.add(AttentionModel);
				}
				return model;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
