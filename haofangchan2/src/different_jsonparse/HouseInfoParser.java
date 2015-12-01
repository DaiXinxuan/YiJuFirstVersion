package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.NormalHouseInfoModel;

public class HouseInfoParser implements ConnectionHandleInteface {
	private JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");

			if (status.equals("true")) {
				LogUtil.e("NormalHouseInfoParser", "JSON 开始转换");
				NormalHouseInfoModel normalHouseInfoModel = new NormalHouseInfoModel();
				normalHouseInfoModel = getHouseInfoModel();
				return normalHouseInfoModel;
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.e("NormalHouseInfoParser", "JSON 转换错误");
			return null;
		}
	}

	private NormalHouseInfoModel getHouseInfoModel() throws JSONException {
		NormalHouseInfoModel houseinfo = new NormalHouseInfoModel();
		JSONArray imgArray = responseObj.getJSONArray("photo");
		// String img = imgArray.getString(0);
		// String img1[] = img.split(",");
		String img1[] = new String[imgArray.length()];
		for (int i = 0; i < imgArray.length(); i++) {
			img1[i] = imgArray.getString(i);
		}
		ArrayList<String> imgs = new ArrayList<String>();

		for (int j = 0; j < img1.length; j++) {

			String imgPath = img1[j];
			LogUtil.e("ffffffffffffffff", imgPath);
			// LogUtil.e("ffffffffffffffff", "" + imgArray.length());
			imgs.add(imgPath);
		}
		houseinfo.setPhoto(imgs);
		houseinfo.setGiveArea(responseObj.getString("giveArea"));
		houseinfo.setHouseType(responseObj.getString("houseType"));
		houseinfo.setRoomArea(responseObj.getString("roomArea"));
		houseinfo.setRoomConfig(responseObj.getString("roomConfig"));
		houseinfo.setRoomCount(responseObj.getString("roomCount"));
		houseinfo.setRoomDecoration(responseObj.getString("roomDecoration"));
		houseinfo.setRoomForm(responseObj.getString("roomForm"));
		// houseinfo.setRoomGtArea(responseObj.getString("roomGtArea"));
		houseinfo.setRoomNumber(responseObj.getString("roomNumber"));
		houseinfo.setRoomPrice(responseObj.getString("roomPrice"));
		houseinfo.setRoomStruct(responseObj.getString("roomStruct"));
		houseinfo.setRoomUrl(responseObj.getString("roomUrl"));
		houseinfo.setStructArea(responseObj.getString("structArea"));
		houseinfo.setAttentioned(responseObj.getString("attentioned"));
		houseinfo.setRoomForward(responseObj.getString("roomForward"));
		return houseinfo;

	}

}
