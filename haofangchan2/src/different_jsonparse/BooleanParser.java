package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

public class BooleanParser implements ConnectionHandleInteface {
	private JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");
			return status.equals("true");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtil.e("SimpleParser", e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

}

