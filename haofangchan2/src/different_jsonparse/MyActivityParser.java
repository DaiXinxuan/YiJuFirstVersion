package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.MyActivityModel;

public class MyActivityParser implements ConnectionHandleInteface {
	private JSONObject responseObject ;
	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObject = new JSONObject(response);
			if(responseObject.getString("status").equals("true")){
				JSONArray infos = responseObject.getJSONArray("info");
				ArrayList<MyActivityModel> model = new ArrayList<MyActivityModel>();
				LogUtil.e(String.valueOf(infos.length()), "false");
				for(int i = 0;i<infos.length();i++){
					JSONObject info = infos.getJSONObject(i);
					MyActivityModel myActivityModel = new MyActivityModel();
					myActivityModel.setContent(info.getString("content"));
					myActivityModel.setName(info.getString("name"));
					myActivityModel.setPhotopath(info.getJSONArray("photopath").get(0).toString());
					myActivityModel.setTime(info.getString("time"));
					myActivityModel.setUrl(info.getString("html"));
					model.add(myActivityModel);
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
