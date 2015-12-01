package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.HomePageHouseListModel;
import differentjavabean.HomePageMapModel;
import differentjavabean.HomePageModel;
import differentjavabean.ImageModel;

public class HomePageModelParser implements ConnectionHandleInteface {
	private JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");

			if (status.equals("true")) {
				HomePageModel homePageModel = new HomePageModel();
				String projectName = responseObj.getString("projectname");
				homePageModel.setProjectName(projectName);
				String html=responseObj.getString("html");
				homePageModel.setBuildingIntrodeceHtml(html);
				String address = responseObj.getString("address");
				String saleAddress = responseObj.getString("saleaddress");
				homePageModel.setMagzineHtml(responseObj.getString("projectHtml"));
				HomePageMapModel homePageMapModel = new HomePageMapModel(
						address, saleAddress);
				homePageModel.setHomePageMapModel(homePageMapModel);
				homePageModel.setHouses(getHouses());
				homePageModel.setPhotos(getPhotos());
				return homePageModel;
			} else {
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			LogUtil.e("HomePageModelPaser", "JSON ×ª»»´íÎó");
		}
		return null;
	}

	private ArrayList<ImageModel> getPhotos() throws JSONException {
		ArrayList<ImageModel> photoArrayList = new ArrayList<ImageModel>();
		JSONArray photoArray = responseObj.getJSONArray("photo");

		for (int i = 0; i < photoArray.length(); i++) {
			JSONArray infoArray = (JSONArray) photoArray.get(i);
			JSONArray picArray = (JSONArray) infoArray.get(0);
			String imageUrl = (String) picArray.get(0);
			String redirectUrl = (String) infoArray.get(1);
			ImageModel imageModel = new ImageModel(imageUrl, redirectUrl);
			photoArrayList.add(imageModel);
		}
		return photoArrayList;
	}

	private ArrayList<HomePageHouseListModel> getHouses() throws JSONException {
		ArrayList<HomePageHouseListModel> houseArrayList = new ArrayList<HomePageHouseListModel>();
		JSONArray houseArray = responseObj.getJSONArray("house");

		for (int i = 0; i < houseArray.length(); i++) {
			JSONObject houseObj = (JSONObject) houseArray.get(i);
			HomePageHouseListModel homePageHouseListModel = new HomePageHouseListModel();
			homePageHouseListModel.setRoomId(houseObj.getString("roomid"));
			homePageHouseListModel.setDong(houseObj.getString("dong"));
			homePageHouseListModel.setUnit(houseObj.getString("unit"));
			homePageHouseListModel.setFloorString(houseObj.getString("floor"));
			homePageHouseListModel.setAllPrice(houseObj.getString("allPrice"));
			homePageHouseListModel.setHtml(houseObj.getString("url"));
			homePageHouseListModel.setStrutsArea(houseObj
					.getString("strutsArea"));
			homePageHouseListModel.setAttentioned(houseObj
					.getString("attentioned"));
			homePageHouseListModel.setCount(houseObj.getString("roomCount"));
			homePageHouseListModel.setHuxing(houseObj.getString("housetype"));
			homePageHouseListModel.setConfig(houseObj.getString("houseConfig"));
			homePageHouseListModel.setPersonCount(houseObj.getString("count"));
			JSONArray photos=houseObj.getJSONArray("photo");
			homePageHouseListModel.setPhoto(photos.getString(0));
			houseArrayList.add(homePageHouseListModel);
		}

		return houseArrayList;
	}

}
