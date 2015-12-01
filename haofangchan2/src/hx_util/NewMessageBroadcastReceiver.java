package hx_util;

import org.json.JSONObject;

import testandmanage.LogUtil;
import testandmanage.MyApplication;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.exceptions.EaseMobException;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;

//接受新消息广播
public class NewMessageBroadcastReceiver extends BroadcastReceiver {

//	private static NewMessageBroadcastReceiver instance = null;
//    private static IGetNewMessage iGetNewMessage=null;
//    
//	public static NewMessageBroadcastReceiver getInstance(IGetNewMessage iGetNewMessage) {
//		if (instance == null) {
//			instance = new NewMessageBroadcastReceiver(iGetNewMessage);
//		}
//		return instance;
//	}
//
//	private  NewMessageBroadcastReceiver(IGetNewMessage iGetNewMessage) {
//        this.iGetNewMessage=iGetNewMessage;
//	}

private EMMessage message;
	public void onReceive(Context context, Intent intent) {
		LogUtil.d("newmessageBroadcastReceiver", "gotNewMessage");
		
		// 注销广播
		abortBroadcast();
        
		// 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
		String msgId = intent.getStringExtra("msgid");
		// 发送方
		String username = intent.getStringExtra("from");
		// 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
	    message = EMChatManager.getInstance().getMessage(msgId);
		try {
			
			if(propellingMessageTurnTo.isAdv(message)){
				propellingMessageTurnTo turnTo=new propellingMessageTurnTo();
				turnTo.turnToActivity(message,null);
			
			}
			LogUtil.d("message", ""+message);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EMConversation conversation = EMChatManager.getInstance()
				.getConversation(username);
		// 如果是群聊消息，获取到group id
		if (message.getChatType() == ChatType.GroupChat) {
			username = message.getTo();
		}
		if (!username.equals(username)) {
			// 消息不是发给当前会话，return
			return;
		}
//        iGetNewMessage.RefreshMyfriendsList();
		LogUtil.d("newmessageBroadcastReceiver", "gotNewMessageSucceed");
	}
}
