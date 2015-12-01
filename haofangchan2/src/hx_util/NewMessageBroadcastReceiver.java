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

//��������Ϣ�㲥
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
		
		// ע���㲥
		abortBroadcast();
        
		// ��Ϣid��ÿ����Ϣ��������Ψһ��һ��id��Ŀǰ��SDK���ɣ�
		String msgId = intent.getStringExtra("msgid");
		// ���ͷ�
		String username = intent.getStringExtra("from");
		// �յ�����㲥��ʱ��message�Ѿ���db���ڴ����ˣ�����ͨ��id��ȡmesage����
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
		// �����Ⱥ����Ϣ����ȡ��group id
		if (message.getChatType() == ChatType.GroupChat) {
			username = message.getTo();
		}
		if (!username.equals(username)) {
			// ��Ϣ���Ƿ�����ǰ�Ự��return
			return;
		}
//        iGetNewMessage.RefreshMyfriendsList();
		LogUtil.d("newmessageBroadcastReceiver", "gotNewMessageSucceed");
	}
}
