package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONException;
import org.json.JSONObject;

import differentjavabean.MainZhiYeGuWenModel;

import testandmanage.LogUtil;

public class MainZhiYeGuWenJsonparse implements ConnectionHandleInteface {
	private JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");
			String salerid = responseObj.getString("salerid");
			MainZhiYeGuWenModel data = new MainZhiYeGuWenModel();
			data.setStatus(status);
			data.setSalerid(salerid);
			return data;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtil.e("SimpleParser", e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

}
