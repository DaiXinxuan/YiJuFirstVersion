package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import differentjavabean.OrderWatchingItemModel;

public class OrderItemParser implements ConnectionHandleInteface{
	JSONObject json;
	ArrayList<OrderWatchingItemModel> list = new ArrayList<OrderWatchingItemModel>();
	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			json = new JSONObject(response);
			if(json.getString("status").equals("true")){
				JSONArray array = json.getJSONArray("dataInfo");
				for(int i=0;i<array.length();i++){
					JSONObject obj = array.getJSONObject(i);
					OrderWatchingItemModel model = new OrderWatchingItemModel();
					model.setHouseName(obj.getString("houseName"));
					model.setHouseNameData(obj.getString("houseNameData"));
					model.setVisitDate(obj.getString("visitDate"));
					model.setRoomId(obj.getString("roomId"));
					list.add(model);
				}
				return list;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
