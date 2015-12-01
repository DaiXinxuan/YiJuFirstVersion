package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.SalerModel;

public class SalerParser implements ConnectionHandleInteface {
	private JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");
			
			if (status.equals("true")) {
				SalerModel model = new SalerModel();
				model.setName(responseObj.getString("name"));
				model.setAge(responseObj.getString("age"));
				model.setPosition(responseObj.getString("position"));
				model.setTel(responseObj.getString("tel"));
				model.setCompany(responseObj.getString("company"));
				model.setIntroduction(responseObj.getString("introduction"));
				model.setHeadPhoto(responseObj.getJSONArray("headphoto").getString(0));
				model.setGoodLevel(responseObj.getInt("goodlevel"));
				model.setMidLevel(responseObj.getInt("midlevel"));
				model.setBadLevel(responseObj.getInt("badlevel"));
				model.setSex(responseObj.getString("sex"));
				model.setState(responseObj.getString("state"));
				return model;
			}else{
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtil.e(this.getClass().getName(), e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
	}

}
