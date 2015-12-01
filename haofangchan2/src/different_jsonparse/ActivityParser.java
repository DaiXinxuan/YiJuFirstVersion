package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.ActivityModel;

public class ActivityParser implements ConnectionHandleInteface {
	private JSONObject responseObj;
	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status"); 
			
			if (status.equals("true")) {
				ArrayList<ActivityModel> nowInfo = new ArrayList<ActivityModel>(); 
				ArrayList<ActivityModel> pastInfo = new ArrayList<ActivityModel>(); 
				ArrayList<ActivityModel> willInfo = new ArrayList<ActivityModel>(); 
				JSONArray pastArray = responseObj.getJSONArray("pastInfo");
				JSONArray nowArray = responseObj.getJSONArray("nowInfo");
				JSONArray willArray = responseObj.getJSONArray("willInfo");
				handleInfo(nowInfo, nowArray);
				handleInfo(pastInfo, pastArray);
				handleInfo(willInfo, willArray);
				ArrayList<ArrayList<ActivityModel>> model = new ArrayList<ArrayList<ActivityModel>>();
				model.add(nowInfo);
				model.add(willInfo);
				model.add(pastInfo);
				return model;
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
	
	private void handleInfo(ArrayList<ActivityModel> info, JSONArray infoArray) throws JSONException{
		for (int i = 0; i < infoArray.length(); i++) {
			ActivityModel model = new ActivityModel();
			JSONObject modelInfo = infoArray.getJSONObject(i);
			model.setActivityId(modelInfo.getString("activityid"));
			model.setActivityImage(modelInfo.getJSONArray("activityimage").getString(0));
			model.setIntroduction(modelInfo.getString("introduction"));
			model.setName(modelInfo.getString("name"));
			model.setTime(modelInfo.getString("time"));
			info.add(model);
		}
	}

}
