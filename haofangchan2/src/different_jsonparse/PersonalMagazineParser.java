package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.PersonalMagazineModel;

public class PersonalMagazineParser implements ConnectionHandleInteface {
	private JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");

			if (status.equals("true")) {
				ArrayList<PersonalMagazineModel> magazineModel = new ArrayList<PersonalMagazineModel>();
				JSONArray infos = responseObj.getJSONArray("info");

				for (int i = 0; i < infos.length(); i++) {
					JSONObject info = infos.getJSONObject(i);
					PersonalMagazineModel model = new PersonalMagazineModel();
					model.setName(info.getString("name"));
					model.setHeadPhoto(info.getJSONArray("headphoto")
							.getString(0));
					model.setKind(info.getString("kind"));
					model.setIntroduce(info.getString("introduce"));
					model.setUrl(info.getString("url"));
					magazineModel.add(model);
				}

				return magazineModel;
			} else {
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
