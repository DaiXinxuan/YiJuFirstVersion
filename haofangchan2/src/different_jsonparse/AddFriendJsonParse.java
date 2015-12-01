package different_jsonparse;

import org.json.JSONException;
import org.json.JSONObject;

import differentjavabean.AddFriendModel;

import android.R.bool;
import android.R.string;

import httpConnect.ConnectionHandleInteface;

public class AddFriendJsonParse implements ConnectionHandleInteface {

	@Override
	public Object handResponse(String response) {
	   	JSONObject jsonObject;
		try {
			
			jsonObject = new JSONObject(response);
			 String stateString=jsonObject.getString("status");
			   	if(stateString.equals("true")){
			        AddFriendModel addFriendModel=new AddFriendModel();
			        String friendidString=jsonObject.getString("friendid");
			        String nicknameString=jsonObject.getString("nickname");
			        String isFriendString=jsonObject.getString("state");
			        addFriendModel.setFriendidString(friendidString);
			        addFriendModel.setIsFriendString(isFriendString);
			        addFriendModel.setNicknameString(nicknameString);
			        return addFriendModel;
			   	}
			   	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		return null;
	}

}
