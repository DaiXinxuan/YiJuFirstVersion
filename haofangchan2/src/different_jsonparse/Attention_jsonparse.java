package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.AttentionBean;

public class Attention_jsonparse implements ConnectionHandleInteface {

	private List<AttentionBean> listdata;

	public List<AttentionBean> Maindata_parse(String result) {

		listdata = new ArrayList<AttentionBean>();
		try {
			LogUtil.d(" Main_DataParse", "¿ªÊ¼½âÎö");

			JSONObject state_jObject = new JSONObject(result);
			boolean state = state_jObject.getBoolean("status");
			if (state == true) {
				JSONArray infos = state_jObject.getJSONArray("info");
				for (int i = 0; i < infos.length(); i++) {

					JSONObject jsonObject2 = infos.getJSONObject(i);

					String projectname = jsonObject2.getString("housename");
					AttentionBean data1 = new AttentionBean();
					data1.setT1(projectname);
					listdata.add(data1);
				}

			}
		} catch (JSONException e) {

			LogUtil.d("bug", "zhenshibug4");
		}

		return listdata;

	}

	public Object handResponse(String response) {
		return Maindata_parse(response);
	}

}
