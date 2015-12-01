package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.GroupBuyModel;

public class GroupBuyParser implements ConnectionHandleInteface {
	private JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");
			if (status.equals("true")) {
				GroupBuyModel model = new GroupBuyModel();
				model.setName(responseObj.getString("name"));
				model.setActivityid(responseObj.getString("activityid"));
				model.setContent(responseObj.getString("content"));
				model.setInstruction(responseObj.getString("instruction"));
				model.setPersonNum(responseObj.getString("personnum"));
				model.setTime(responseObj.getString("time"));
				model.setPhotoPath(responseObj.getJSONArray("photopath")
						.getString(0));
				model.setHtml(responseObj.getString("html"));
				return model;
			}else{
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtil.e(this.getClass().getName(), e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

}
