package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;

import differentjavabean.PersonalInfoModel;

public class PersonalInfoModelParser implements ConnectionHandleInteface {
	JSONObject responseObj;

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");

			if (status.equals("true")) {
				PersonalInfoModel personalInfoModel = new PersonalInfoModel();
				personalInfoModel.setNickName(responseObj.getString("nickname"));
				String sex=responseObj.getString("sex");
				if(sex.equals("1")){
					sex="ƒ–";
				}else if (sex.equals("2")){
					sex="≈Æ";
				}else sex="Œ¥…Ë÷√";
				personalInfoModel.setSex(sex);
				personalInfoModel.setIntroduce(responseObj.getString("introduce"));
				personalInfoModel.setName(responseObj.getString("name"));
				personalInfoModel.setAge(responseObj.getString("age"));
				personalInfoModel.setTel(responseObj.getString("tel"));
				personalInfoModel.setAddr(responseObj.getString("addr"));
				personalInfoModel.setMember(responseObj.getString("member"));
				personalInfoModel.setJob(responseObj.getString("job"));
				personalInfoModel.setIncome(responseObj.getString("income"));
				personalInfoModel.setHouseType(responseObj.getString("housetype"));
				personalInfoModel.setWillPrice(responseObj.getString("willprice"));
				
				return personalInfoModel;
			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtil.e("PersonalInfoModelParser", e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
	}

}
