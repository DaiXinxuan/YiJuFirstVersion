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
					carListViewModel.setStatement(carInfo.getString("statement")+"��λ");
					carListViewModel.setArea(carInfo.getString("area")+"�O");
					carListViewModel.setBasePrice(carInfo.getString("baseprice"));
					carListViewModel.setReserved(carInfo.getString("reserver"));
					String reserver=carInfo.getString("reserver");
					String saleState=carInfo.getString("salestate");
					if(reserver.equals("1")){
						saleState="�Ѷ�";
					}else if(saleState.equals("1")){
						saleState="����";
					}else {
						saleState="δ��";
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
