package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import differentjavabean.OrderMoreInfoModel;

public class OrderMoreInfoParser implements ConnectionHandleInteface{
	OrderMoreInfoModel model = new OrderMoreInfoModel();
	JSONObject obj;
	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			obj = new JSONObject(response);
			if(obj.getString("status").equals("true")){
				JSONArray array = obj.getJSONArray("info");
//				model.setRoomNo(array.getJSONObject(0).getString("data"));
				model.setName(array.getJSONObject(0).getString("data"));
				model.setDate(array.getJSONObject(1).getString("data"));
				model.setPhoneNumber(array.getJSONObject(2).getString("data"));
				model.setCount(array.getJSONObject(3).getString("data"));
				model.setInstruction(array.getJSONObject(4).getString("data"));
				return model;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
