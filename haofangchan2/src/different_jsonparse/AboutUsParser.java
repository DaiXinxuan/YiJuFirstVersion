package different_jsonparse;

import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.AboutUsModel;

import httpConnect.ConnectionHandleInteface;

public class AboutUsParser implements ConnectionHandleInteface{
	private JSONObject responseObject;
	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		LogUtil.d("ssssssss", response.toString());
		AboutUsModel model = new AboutUsModel();
		try {
			responseObject = new JSONObject(response);
			String status = responseObject.getString("status");
			if(status.equals("true")){
				model.setHtml(responseObject.getString("html"));
				return model;
			}else{
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
