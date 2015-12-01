package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.LoginBean;

public class Login_json_parse implements ConnectionHandleInteface{
	LoginBean lb=new LoginBean();
	public void parse_state_Jsondate(String result){
		try {
			LogUtil.d("parse", "解析JSON开始2");
			JSONObject jsonObject=new JSONObject(result);
	        lb.setState(jsonObject.getBoolean("status"));
	        lb.setPayid(jsonObject.getString("payid"));
	        lb.setHxpassword(jsonObject.getString("hxpassword"));
	        lb.setHxusername(jsonObject.getString("hxusername"));
		} catch (JSONException e) {
			LogUtil.d("parse", "解析JSON失败");
		}

 }
	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		parse_state_Jsondate(response);
		return lb;
	}

}
