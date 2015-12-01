package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.NormalCarListViewModel;

public class NormalCarModelParser implements ConnectionHandleInteface {
	private JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");
			
			if (status.equals("true")) {
				JSONArray info = responseObj.getJSONArray("info");
				ArrayList<NormalCarListViewModel> normalCarModel = new ArrayList<NormalCarListViewModel>();
				
				for (int i = 0; i < info.length(); i++) {
					NormalCarListViewModel carListViewModel = new NormalCarListViewModel();
					JSONObject carInfo = info.getJSONObject(i);
					carListViewModel.setNum(carInfo.getString("num"));
					carListViewModel.setSize(carInfo.getString("size"));
					carListViewModel.setStatement(carInfo.getString("statement")+"车位");
					carListViewModel.setArea(carInfo.getString("area")+"㎡");
					carListViewModel.setBasePrice(carInfo.getString("baseprice"));
					carListViewModel.setReserved(carInfo.getString("reserver"));
					String reserver=carInfo.getString("reserver");
					String saleState=carInfo.getString("salestate");
					if(reserver.equals("1")){
						saleState="已订";
					}else if(saleState.equals("1")){
						saleState="已售";
					}else {
						saleState="未售";
					}
					carListViewModel.setSaleState(saleState);
					normalCarModel.add(carListViewModel);
				}
				
				return normalCarModel;
			}else{
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtil.e("NormalCarModelParser", e.getLocalizedMessage());
			e.printStackTrace();
		}
		return response;
	}

}
