package hx_util;

import httpConnect.ServerAsyncHttpTask;

import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.HouseinfoActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import com.easemob.chat.EMMessage;

import different_jsonparse.State_parse_Json;
import differentjavabean.LatestNewsTable;
import differentjavabean.RequestFriendModel;

import testandmanage.ApplicationDialogSureClickInterface;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import testandmanage.MyApplicationAlertDialogActivity;
import web.WebActivity;

import android.R.integer;
import android.content.Intent;
import android.widget.Toast;

public class propellingMessageTurnTo implements ApplicationDialogSureClickInterface{

	public static final int MY_MAGAZINE = 1;
	public static final int HOUSE_INFO = 2;
	public static final int AUCTION_INFO = 3;
	public static final int GROUP_BUY_INFO = 4;
	public static final int ACTIVITY_INFO = 6;
	public static final int FriendRequest=7;
	public static final int FriendAgreeRequest=8;
    
	private RequestFriendModel requestFriendModel;
	// 判断是否广告
	public static Boolean isAdv(EMMessage message) {
		try {

			int type = message.getIntAttribute("type");
			if (type != 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	public static void SaveRemoteMessage(Boolean isRead, EMMessage message) {
		try {

			List<LatestNewsTable> latestNews = DataSupport
					.findAll(LatestNewsTable.class);
			DataSupport.deleteAll(LatestNewsTable.class);
			LatestNewsTable saveData = new LatestNewsTable();
			saveData.setActivityid(message.getStringAttribute("idNumber"));
			saveData.setContent(message.getStringAttribute("introduction"));
			saveData.setHtml(message.getStringAttribute("html"));
			saveData.setIsRead(isRead);
			saveData.setName(message.getStringAttribute("name"));
			saveData.setPhotopath(message.getStringAttribute("activityimage"));
			saveData.setTime(message.getStringAttribute("date"));
			LogUtil.d("日期数据：", "::" + message.getStringAttribute("date"));
			saveData.setType(message.getIntAttribute("type"));
			saveData.setProid(message.getStringAttribute("proid"));
			latestNews.add(0, saveData);
			
			if (latestNews.size() <= 20) {
				
			} else {
				
				LogUtil.d("到20以上了，是否删除", ""+latestNews.size());
				 latestNews.remove(20);
			}
			
			for (int j = 0; j < latestNews.size(); j++) {
				LatestNewsTable saveData2 = new LatestNewsTable();
				LatestNewsTable getMessageData = latestNews.get(j);
				saveData2.setActivityid(getMessageData.getActivityid());
				saveData2.setContent(getMessageData.getContent());
				saveData2.setHtml(getMessageData.getHtml());
				saveData2.setIsRead(getMessageData.getIsRead());
				saveData2.setName(getMessageData.getName());
				saveData2.setPhotopath(getMessageData.getPhotopath());
				saveData2.setTime(getMessageData.getTime());
				saveData2.setType(getMessageData.getType());
				saveData.setProid(getMessageData.getProid());
				saveData2.save();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private String html;
	private int type;
	private String roomid;

	// 如果是广告则执行跳转操作
	public void turnToActivity(EMMessage message,
			LatestNewsTable dataLatestNewsTable) {

		int type = 0;
		try {

			if (message != null) {
				
				type = message.getIntAttribute("type");
				if((type==propellingMessageTurnTo.FriendRequest)||(type==propellingMessageTurnTo.FriendAgreeRequest)){
					
				}else{
					html = message.getStringAttribute("html");
				}

			} else {
				html = dataLatestNewsTable.getHtml();
				type = dataLatestNewsTable.getType();
			}

			Intent intent = new Intent(MyApplication.getContext(),
					WebActivity.class);
			intent.putExtra("url", html);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			switch (type) {

			case MY_MAGAZINE:
				intent.putExtra("title", "精品杂志");
				MyApplication.getContext().startActivity(intent);
				break;
			case HOUSE_INFO:
				intent = new Intent(MyApplication.getContext(),
						HouseinfoActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				if (message != null) {
					roomid = message.getStringAttribute("idNumber");
				} else {
					roomid = dataLatestNewsTable.getActivityid();
				}
				intent.putExtra("houseid", roomid);
				MyApplication.getContext().startActivity(intent);
				break;
			case AUCTION_INFO:
				intent.putExtra("title", "火热拍卖");
				MyApplication.getContext().startActivity(intent);
				break;
			case GROUP_BUY_INFO:
				intent.putExtra("title", "特价团购");
				MyApplication.getContext().startActivity(intent);
				break;
			case ACTIVITY_INFO:
				intent.putExtra("title", "精彩夺宝");
				MyApplication.getContext().startActivity(intent);
				break;
			case FriendRequest:
				RequestFriendModel requestFriendModel=new RequestFriendModel();
				requestFriendModel.setFriendid(message.getIntAttribute("friendid")+"");
				requestFriendModel.setFriendPhone(message.getStringAttribute("friendphone"));
				requestFriendModel.setRemarkName(message.getStringAttribute("remarkname"));
				requestFriendModel.setNickName(message.getStringAttribute("nickname"));
				requestFriendModel.setProid(message.getStringAttribute("proid"));
				requestFriendModel.setRealPayid(message.getIntAttribute("realPayid")+"");
				this.requestFriendModel=requestFriendModel;
				String content=requestFriendModel.getNickName()+"请求添加您为好友，是否同意请求";
				LogUtil.d("执行带这里","好友请求");
				MyApplication.dialogInterface=propellingMessageTurnTo.this;
				intent = new Intent(MyApplication.getContext(),
						MyApplicationAlertDialogActivity.class);
				intent.putExtra("content", content);
				intent.putExtra("title", "好友添加请求");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				MyApplication.getContext().startActivity(intent);
				break;
			case FriendAgreeRequest:
				 Toast.makeText(MyApplication.getContext(), "您已添加"+message.getStringAttribute("nickname")+"为好友", Toast.LENGTH_SHORT).show();
				 break;
			default:
				break;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void dialogSureClick(){
		
		Map<String, String> map=JSONCommand.JSON10042(requestFriendModel.getProid(), MyApplication.getpayid(), requestFriendModel.getRemarkName(),
				requestFriendModel.getRealPayid(), requestFriendModel.getFriendPhone(),requestFriendModel.getFriendid() );
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
			
			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				Boolean state=(Boolean)model;
				if(state){
					     Intent intent = new Intent();  
				         intent.setAction("action.refreshFriend");  
				         MyApplication.getContext().sendBroadcast(intent);  
				      Toast.makeText(MyApplication.getContext(), "您已添加"+requestFriendModel.getNickName()+"为好友", Toast.LENGTH_SHORT).show();	
				}else{
					  Toast.makeText(MyApplication.getContext(),"服务器繁忙，添加失败" , Toast.LENGTH_SHORT).show();	
				}
			}
		}, new State_parse_Json());
		
	}

}
