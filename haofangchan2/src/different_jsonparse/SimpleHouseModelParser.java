package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.NormalHouseListViewModel;
import differentjavabean.NormalHouseViewModel;

public class SimpleHouseModelParser implements ConnectionHandleInteface {
	private JSONObject responseObj;
	ArrayList<NormalHouseListViewModel> houseListViewModel;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");

			if (status.equals("true")) {
				NormalHouseViewModel normalHouseViewModel = new NormalHouseViewModel();
				normalHouseViewModel.setHouseListViewModel(getHouseInfos());
				return normalHouseViewModel;
			} else {
				return null;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.e("NormalHouseModelParser", "JSON ×ª»»´íÎó");
			return null;
		}
	}

	private ArrayList<NormalHouseListViewModel> getHouseInfos()
			throws JSONException {
		ArrayList<NormalHouseListViewModel> houseInfos = new ArrayList<NormalHouseListViewModel>();
		JSONArray infoArray = responseObj.getJSONArray("info");

		for (int i = 0; i < infoArray.length(); i++) {
			JSONObject info = infoArray.getJSONObject(i);
			NormalHouseListViewModel model = new NormalHouseListViewModel();
			model.setRoomId(info.getString("roomid"));
			JSONArray imgArray = info.getJSONArray("typephoto");
			String img = imgArray.getString(0);
			String img1[] = img.split(",");
			ArrayList<String> imgs = new ArrayList<String>();

			for (int j = 0; j < img1.length; j++) {

				String imgPath = img1[j];
				LogUtil.e("ffffffffffffffff", imgPath);
				LogUtil.e("ffffffffffffffff", "" + imgArray.length());
				imgs.add(imgPath);
			}

			model.setTypePhotos(imgs);
			LogUtil.e("abcdefg", imgs.toString());
			model.setRoomPrice(info.getString("roomPrice"));
			model.setConfig(info.getString("config"));
			model.setAllArea(info.getString("allarea"));
			model.setDecoration(info.getString("decoration"));
			model.setGiveArea(info.getString("giveArea"));
			model.setFloor(info.getString("floornum"));
			model.setForward(info.getString("forward"));
			model.setRoomNum(info.getString("roomNum"));
			houseInfos.add(model);
		}

		return houseInfos;
	}
}