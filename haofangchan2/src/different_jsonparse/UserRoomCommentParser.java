package different_jsonparse;

import httpConnect.ConnectionHandleInteface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import testandmanage.LogUtil;
import differentjavabean.UserRoomCommentModel;

public class UserRoomCommentParser implements ConnectionHandleInteface {
	private JSONObject responseObj;
	private boolean isMycomment;

	public UserRoomCommentParser(Boolean isMy) {
		// TODO Auto-generated constructor stub
		isMycomment = isMy;
	}

	@Override
	public Object handResponse(String response) {
		// TODO Auto-generated method stub
		try {
			responseObj = new JSONObject(response);
			String status = responseObj.getString("status");
			LogUtil.d("返回的评论数据", "大小" + status);
			if (status.equals("true")) {
				List<UserRoomCommentModel> userRoomCommentViewModel = new ArrayList<UserRoomCommentModel>();
				JSONArray infos = responseObj.getJSONArray("info");

				for (int i = 0; i < infos.length(); i++) {
					JSONObject info = infos.getJSONObject(i);
					UserRoomCommentModel model = new UserRoomCommentModel();
					model.setComment(info.getString("content"));
					model.setDate(info.getString("time"));
					model.setUserName(info.getString("nickname"));
					model.setHeadPhoto(info.getJSONArray("headphoto")
							.getString(0));
					if (isMycomment)
						model.setId(info.getString("id"));
					userRoomCommentViewModel.add(model);
				}
				LogUtil.d("返回的评论数据", "大小" + userRoomCommentViewModel.size()
						+ "值为" + userRoomCommentViewModel);
				return userRoomCommentViewModel;
			} else {
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtil.e(this.getClass().getName(), e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
	}
}
