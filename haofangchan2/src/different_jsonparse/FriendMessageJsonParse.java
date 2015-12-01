package different_jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import differentjavabean.FriendMessageModel;

import httpConnect.ConnectionHandleInteface;

public class FriendMessageJsonParse  implements ConnectionHandleInteface{

	@Override
	public Object handResponse(String response) {
		
		try {
			JSONObject jsonObject=new JSONObject(response);
			String state=jsonObject.getString("status");
			if(state.equals("true")){
				
				JSONArray headphotos=jsonObject.getJSONArray("headphotopath");
				String headphotoString=headphotos.getString(0);
				String sex=jsonObject.getString("sex");
				if(sex.equals("1")){
					sex="ÄÐ";
				}else if(sex.equals("2")){
					sex="Å®";
				}
				String nicknameString=jsonObject.getString("nickname");
                String contentString=jsonObject.getString("content");
                String remarkString=jsonObject.getString("remarkname");
                FriendMessageModel data=new FriendMessageModel();
                data.setNicknameString(nicknameString);
                data.setContentString(contentString);
                data.setHeadphotopathString(headphotoString); 
                data.setSexString(sex);
                data.setRemarknameString(remarkString);
                return data;
                
			}else{
			
			return null;
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}

}
