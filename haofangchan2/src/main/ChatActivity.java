package main;

import friendchat.FriendProfileActivity;
import hx_util.NewMessageBroadcastReceiver;

import java.util.List;

import org.json.JSONObject;

import testandmanage.LogUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.TextMessageBody;
import com.easemob.util.NetUtils;
import com.example.haofangchan2.R;

import different_adapter.ChatAdapter;

public class ChatActivity extends Activity {
	
	private String contactName = null;//存放联系人名称
	private EMMessage sent_message = null;//存放发送的消息对象
	private List<EMMessage> messages = null;//存放联系人的聊天记录
	private ImageView back = null;
	private ListView lv = null;
	private Button sent = null;
	private TextView profile = null ,username = null,unRead;
	private EditText content = null;
	private ChatAdapter mAdapter;// 消息视图的Adapter
	private String friendidString;
	private String hxusernameString;
	private String nicknamrString ;
	private String myPictureString;
	private String friendPictureString;
	private int unReadCount;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.chatview);
		unReadCount = getIntent().getIntExtra("unReadCount", 0);
		
		initViriable();
		initChatView();
		onClickListeners();
		if(unReadCount != 0){
			unRead.setVisibility(View.VISIBLE);
			unRead.setText("聊天("+unReadCount+")");
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
        IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(5);
        registerReceiver(msgReceiver, intentFilter);
        EMChat.getInstance().setAppInited();
	}
	
	public NewMessageBroadcastReceiver msgReceiver = new hx_util.NewMessageBroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			LogUtil.d("聊天界面收到消息", "消息");
			
			int length = mAdapter.getCount();
			String msgid = intent.getStringExtra("msgid");
			String username = intent.getStringExtra("from");
			EMMessage message = EMChatManager.getInstance().getMessage(msgid);
			JSONObject jsonObject;
//			try {
//				if(propellingMessageTurnTo.isAdv(message)){
//					propellingMessageTurnTo.turnToActivity(message);
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if(message.getChatType()!=ChatType.GroupChat&&
				username.equals(hxusernameString)){
				abortBroadcast();
				Message message2 = new Message();
				message2.what = 1;
				handler.sendMessage(message2);
				mAdapter.notifyDataSetChanged();
				lv.setSelection(lv.getCount()-1);
				LogUtil.d("长度",length+"ddd"+mAdapter.getCount());
				
				//设置消息已读
				EMConversation conversation = EMChatManager.getInstance().getConversation(username);
				conversation.resetUnreadMsgCount();
			}else{
				unRead.setVisibility(View.VISIBLE);
				unReadCount++;
				unRead.setText("聊天("+unReadCount+")");
				return;
			}
		}
	};
	
	protected void onPause() {
		super.onPause();
		unregisterReceiver(msgReceiver);
	};
	
	private void onClickListeners() {
		// TODO Auto-generated method stub
		profile.setOnClickListener(new OnClickListener() {//进入联系人的资料界面
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		        Intent intent=new Intent();
		        intent.putExtra("userid", friendidString);
		        intent.putExtra("isVip", ""+getIntent().getIntExtra("isVip", 0));
		        intent.putExtra("phonenumber", hxusernameString);
		        intent.setClass(ChatActivity.this, FriendProfileActivity.class);
		        startActivityForResult(intent, 0);
			}
		});
		back.setOnClickListener(new OnClickListener() {//回退
			@Override
			public void onClick(View arg0) {
				
				finish();
			}
		});
		sent.setOnClickListener(new OnClickListener() {//发送消息	
			@Override
			public void onClick(View arg0) {
				String contString = content.getText().toString();
				if (contString.length() > 0) {
				    
					EMConversation conversation = EMChatManager.getInstance().getConversation(hxusernameString);
					sent_message = EMMessage.createSendMessage(Type.TXT);
					TextMessageBody txt = new TextMessageBody(content.getText().toString());
					content.setText("");// 清空编辑框数据w
					sent_message.addBody(txt);
					sent_message.setReceipt(hxusernameString);
					sent_message.setMsgTime(System.currentTimeMillis());
					conversation.addMessage(sent_message);
					LogUtil.d("asffaf","asfeertr");
					EMChatManager.getInstance().sendMessage(sent_message, new EMCallBack() {
						
						public void onSuccess() {
							LogUtil.d("success","chenggong");
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
						
						@Override
						public void onProgress(int arg0, String arg1) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onError(int arg0, String arg1) {
							//如果返回错误，检查网络或其他错误
							// TODO Auto-generated method stub
							LogUtil.d("error1","error");
							Message message = new Message();
							message.what = 0;
							handler.sendMessage(message);
							}
						});
					}
				}
		});
	}
	
	private Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			if(msg.what==1){
                if(lv.getCount() == 0){
                	lv.setSelection(0);
                }else{
                	lv.setSelection(lv.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
                }
                setResult(1);
			}
			else{
				if(!NetUtils.hasNetwork(ChatActivity.this)){//检查网络
					Toast mtoast = new Toast(ChatActivity.this);
					String text = "网络错误，请检查网络";
					if(mtoast == null){//防止Toast重复弹出
						Toast.makeText(ChatActivity.this, text, Toast.LENGTH_SHORT).show();
					}
					else{
						mtoast.setText(text);
						mtoast.setDuration(Toast.LENGTH_SHORT);
					}
				}
				else{
					LogUtil.d("fail","error");
					Toast mtoast = new Toast(ChatActivity.this);
					String text = "服务器繁忙";
					if(mtoast == null){//防止Toast重复弹出
						Toast.makeText(ChatActivity.this, text, Toast.LENGTH_SHORT).show();
					}
					else{
						mtoast.setText(text);
						mtoast.setDuration(Toast.LENGTH_SHORT);
					}
				}
			}
		}
	};

	
	private void initChatView() {
		// TODO Auto-generated method stub
		EMConversation conversation = EMChatManager.getInstance().getConversation(hxusernameString);
		Log.d("test1","对话初始化成功");
		username.setText(nicknamrString);//修改聊天对象名字
		messages = conversation.getAllMessages();//向环信拉取聊天记录，进行初始化
		conversation.resetUnreadMsgCount();
		mAdapter = new ChatAdapter(ChatActivity.this, messages,myPictureString,friendPictureString);
		lv.setAdapter(mAdapter);
		lv.setSelection(lv.getCount() - 1);
	}

	private void initViriable() {
		unRead = (TextView) findViewById(R.id.unReadText);
		lv = (ListView) findViewById(R.id.chat_lv);
		sent = (Button) findViewById(R.id.chat_sent);
		profile = (TextView) findViewById(R.id.chat_pro);
		back = (ImageView) findViewById(R.id.chat_title_btn);
		content = (EditText) findViewById(R.id.chat_edittext);
		content.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				Log.d("触发FOCUS","aaaaaaaaaaaaaaaaaaaaaaaaaaa");
				lv.setSelection(lv.getCount()-1);
			}
		});
		username = (TextView) findViewById(R.id.chat_name);
		friendidString = getIntent().getStringExtra("friendid");
		hxusernameString=getIntent().getStringExtra("hxusername");
		nicknamrString=getIntent().getStringExtra("nickname");
		myPictureString=getIntent().getStringExtra("myPicture");
		friendPictureString=getIntent().getStringExtra("friendPicture");
		lv.setDivider(null);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			setResult(2);
			ChatActivity.this.finish();
			break;
        case RESULT_CANCELED:
        	
        	break;
		default:
			break;
		}
	}
}
