package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import differentjavabean.ConsultantCommentModel;

public class ConsultantCommentParser implements ConnectionHandleInteface{
	JSONObject responseObj;
	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		ArrayList<ConsultantCommentModel> list = new ArrayList<ConsultantCommentModel>();
		try {
			responseObj = new JSONObject(response);
			if(responseObj.getString("status").equals("true")){
				JSONArray info = responseObj.getJSONArray("info");
				for(int i=0;i<info.length();i++){
					ConsultantCommentModel model = new ConsultantCommentModel();
					model.setName(info.getJSONObject(i).getString("name"));
					model.setContent(info.getJSONObject(i).getString("content"));
					model.setDate(info.getJSONObject(i).getString("date"));
					model.setLevel(info.getJSONObject(i).getString("level"));
					JSONArray headPhotos=info.getJSONObject(i).getJSONArray("headphoto");
					String headphoto=null;
					if(headPhotos.length()!=0){
						headphoto=headPhotos.getString(0);
					}
					model.setHeadphoto(headphoto);
					list.add(model);
				}
				return list;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
