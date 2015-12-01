package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.LogoImageModel;

public class LogoViewParser implements ConnectionHandleInteface {
	private JSONObject responseObject;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObject = new JSONObject(response);
			String status = responseObject.getString("status");

			if (status.equals("true")) {
				ArrayList<LogoImageModel> imageModels = new ArrayList<LogoImageModel>();
				JSONArray modelArray = responseObject.getJSONArray("info");
				
				for (int i = 0; i < modelArray.length(); i++) {
					JSONObject imageModel = modelArray.getJSONObject(i);
					LogoImageModel logoImageModel = new LogoImageModel();
					logoImageModel.setImg(imageModel.getJSONArray("img").getString(0));
					logoImageModel.setProId(imageModel.getString("proid"));
					imageModels.add(logoImageModel);
				}

				return imageModels;
			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtil.e("LogoViewParser", e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
	}
}
