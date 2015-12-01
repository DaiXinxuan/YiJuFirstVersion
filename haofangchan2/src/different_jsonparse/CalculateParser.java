package different_jsonparse;

import org.json.JSONException;
import org.json.JSONObject;

import differentjavabean.CalculateModel;

import httpConnect.ConnectionHandleInteface;

public class CalculateParser implements ConnectionHandleInteface{
	JSONObject resposneObject;
	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		CalculateModel model;
		try {
			resposneObject = new JSONObject(response);
			String status = resposneObject.getString("status");
			if(status.equals("true")){
				model = new CalculateModel();
				model.setGmrate(resposneObject.getString("gmrate"));
				model.setMrate(resposneObject.getString("mrate"));
				return model;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
