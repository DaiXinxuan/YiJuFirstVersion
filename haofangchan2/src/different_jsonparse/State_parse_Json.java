package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONException;
import org.json.JSONObject;



import testandmanage.LogUtil;

public class State_parse_Json implements ConnectionHandleInteface{
	 Boolean state=false;
/* public State_parse_Json(String result) {
	this.result=result;
}*/
 public void parse_state_Jsondate(String result){
		try {
			LogUtil.d("parse", "解析JSON开始2");
			JSONObject jsonObject=new JSONObject(result);
	        state=jsonObject.getBoolean("status");
		} catch (JSONException e) {
			LogUtil.d("parse", "解析JSON失败");
		}

 }
 

public Object handResponse(String response) {
	parse_state_Jsondate(response);
	return state;
}
 

}
