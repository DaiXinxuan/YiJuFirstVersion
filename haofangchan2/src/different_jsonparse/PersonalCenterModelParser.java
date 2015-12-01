package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.PersonalCenterModel;

public class PersonalCenterModelParser implements ConnectionHandleInteface {
	JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");

			if (status.equals("true")) {
				PersonalCenterModel personalCenterModel = new PersonalCenterModel();
				personalCenterModel.setHeadPhoto(getHeadPhoto(responseObj
						.getJSONArray("headphoto")));
				personalCenterModel.setNickName(responseObj
						.getString("nickname"));
				personalCenterModel.setSex(responseObj.getString("sex"));
				personalCenterModel.setLoginDate(responseObj
						.getString("logindate"));
				personalCenterModel.setIntroduce(responseObj
						.getString("introduce"));

				return personalCenterModel;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtil.e("PersonalCenterModelParser", e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}

		return null;
	}

	private String getHeadPhoto(JSONArray photoArray) throws JSONException {
		String headPhoto=null;
		if(photoArray.length()!=0){
			headPhoto = (String) photoArray.get(0);
		}
		
		return headPhoto;
	}


}
