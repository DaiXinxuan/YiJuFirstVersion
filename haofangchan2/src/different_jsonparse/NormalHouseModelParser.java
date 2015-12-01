package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.NormalHouseInfoModel;
import differentjavabean.NormalHouseListViewModel;
import differentjavabean.NormalHouseViewModel;

public class NormalHouseModelParser implements ConnectionHandleInteface {
	private JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");

			if (status.equals("true")) {
				NormalHouseViewModel normalHouseViewModel = new NormalHouseViewModel();
				normalHouseViewModel.setHouseListViewModel(getHouseInfos());
				normalHouseViewModel.setSearchFormModel(getHouseForm());
				normalHouseViewModel.setSearchHouseTypeModel(getHouseTypes());
				normalHouseViewModel.setSearchPriceModel(getPrice());
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

	private ArrayList<String> getPrice() throws JSONException {
		ArrayList<String> priceList = new ArrayList<String>();
		priceList.add(responseObj.getString("minPrice"));
		priceList.add(responseObj.getString("maxPrice"));

		return priceList;
	}

	private HashMap<String, String> getHouseTypes() throws JSONException {
		HashMap<String, String> houseTypeHashMap = new HashMap<String, String>();
		JSONArray houseTypeArray = responseObj.getJSONArray("housetype");
		int houseTypeArrayLength = houseTypeArray.length();

		for (int i = 0; i < houseTypeArrayLength; i++) {
			JSONArray houseTypeArray2 = houseTypeArray.getJSONArray(i);
			String key = houseTypeArray2.getString(0);
			String value = houseTypeArray2.getString(1);
			houseTypeHashMap.put(key, value);
		}

		return houseTypeHashMap;
	}

	private ArrayList<String> getHouseForm() throws JSONException {
		ArrayList<String> houseFormArrayList = new ArrayList<String>();
		JSONArray formArray = responseObj.getJSONArray("houseForm");

		for (int i = 0; i < formArray.length(); i++) {
			String form = formArray.getString(i);
			houseFormArrayList.add(form);
		}

		return houseFormArrayList;
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

	private NormalHouseInfoModel getHouseInfoModel() throws JSONException {
		NormalHouseInfoModel houseinfo = new NormalHouseInfoModel();
		JSONArray imgArray = responseObj.getJSONArray("photo");
		String img = imgArray.getString(0);
		String img1[] = img.split(",");
		ArrayList<String> imgs = new ArrayList<String>();

		for (int j = 0; j < img1.length; j++) {

			String imgPath = img1[j];
			LogUtil.e("ffffffffffffffff", imgPath);
			LogUtil.e("ffffffffffffffff", "" + imgArray.length());
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
		houseinfo.setRoomGtArea(responseObj.getString("roomGtArea"));
		houseinfo.setRoomNumber(responseObj.getString("roomNumber"));
		houseinfo.setRoomPrice(responseObj.getString("roomPrice"));
		houseinfo.setRoomStruct(responseObj.getString("roomStruct"));
		houseinfo.setRoomUrl(responseObj.getString("roomUrl"));
		houseinfo.setStructArea(responseObj.getString("structArea"));
		houseinfo.setAttentioned(responseObj.getString("attentioned"));
		return houseinfo;

	}

}
