package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.Activity_detail_auction_javabean;

public class Activity_detail_Auction_jsonparse implements
		ConnectionHandleInteface {
	Activity_detail_auction_javabean data;

	public Activity_detail_auction_javabean Maindata_parse(String result) {
		data = new Activity_detail_auction_javabean();
		try {
			LogUtil.d(" Main_DataParse", "开始解析");
			JSONObject state_jObject = new JSONObject(result);
			boolean state = state_jObject.getBoolean("status");
			LogUtil.d("Main_DataParse", "" + state);
			if (state == true) {
				LogUtil.d("Main_DataParse", "返回状态码正确");

				JSONArray pictures = state_jObject
						.getJSONArray("activityimage");
				String activityimage = pictures.getString(0);
				String name = state_jObject.getString("name");
				String introduction = state_jObject.getString("introduction");
				String endtime = state_jObject.getString("endtime");
				String nowprice = state_jObject.getString("baseprice");
				String html = state_jObject.getString("html");
				String begintime = state_jObject.getString("begintime");
				String url = state_jObject.getString("url");
				data.setActivityimage(activityimage);
				data.setName(name);
				data.setIntroduction(introduction);
				data.setEndtime(endtime);
				data.setNowprice(nowprice);
				data.setHtml(html);
				data.setBegintime(begintime);
				data.setUrl(url);
			}
		} catch (JSONException e) {

			LogUtil.d("bug", "zhenshibug4");
		}

		return data;

	}

	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}
