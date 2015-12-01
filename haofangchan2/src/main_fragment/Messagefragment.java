package main_fragment;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;
import hx_util.NewMessageBroadcastReceiver;
import hx_util.propellingMessageTurnTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.ChatActivity;
import main.searchContact;
import main.searchUser;

import org.litepal.crud.DataSupport;

import rewriteView.RewriteExpandListViewWithDelete;
import rewriteView.RewriteExpandListViewWithDelete.ChildItemClickListener;
import rewriteView.RewriteExpandListViewWithDelete.ExpandableListViewListener;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.example.haofangchan2.R;
import com.readystatesoftware.viewbadger.BadgeView;

import different_adapter.FriendAdapter;
import different_jsonparse.MyfriendActivity_jsonparse;
import differentjavabean.FriendBean;
import differentjavabean.MyfriendActivity_javabean;

public class Messagefragment extends Fragment implements OnChildClickListener ,MessageDialogInterface{
	private MyfriendActivity_javabean recentJavabean;
	private RewriteExpandListViewWithDelete mListView = null;
	private FriendAdapter adapter;
	private List<List<FriendBean>> mData = new ArrayList<List<FriendBean>>();
	private TextView add;
	private List<List<MyfriendActivity_javabean>> data = new ArrayList<List<MyfriendActivity_javabean>>();
	private Map<String, String> map;
	private List<MyfriendActivity_javabean> recentCont = new ArrayList<MyfriendActivity_javabean>();
	private Intent searchIntent;
	private View view;
	private String messageFromUsernameString;
	public static MessageDialogInterface dialogInterface;
	public BadgeView childPhotoBadgeView;
	public RadioButton messageTintRadioButton;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		view = inflater.inflate(R.layout.myfriend, container, false);
		init();
		initData();
		IntentFilter intentFilter = new IntentFilter();  
	    intentFilter.addAction("action.refreshFriend");  
	    getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);  
		return view;
	}

	public void onStart() {// ע�����δ����Ϣ�Ĺ㲥
		super.onStart();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		// ��ߵȼ����ԣ���ʱ���myapplication�еĹ㲥
		intentFilter.setPriority(4);
		getActivity().registerReceiver(msgReceiver, intentFilter);
		EMChat.getInstance().setAppInited();
	}

	 private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {  
		  
	      @Override  
	      public void onReceive(Context context, Intent intent) {  
	          String action = intent.getAction();  
	          if (action.equals("action.refreshFriend"))  
	          {  
	            initData(); 
	          }  
	      }  
	  };  
	
	
	private EMMessage message;
	public NewMessageBroadcastReceiver msgReceiver = new NewMessageBroadcastReceiver() {
		

		public void onReceive(Context context, Intent intent) {
			LogUtil.d("myfriendActivity", "�ص�");
			 String action = intent.getAction();  
			String msgId = intent.getStringExtra("msgid");
			// ���ͷ�
			// �յ�����㲥��ʱ��message�Ѿ���db���ڴ����ˣ�����ͨ��id��ȡmesage����
			message = EMChatManager.getInstance().getMessage(msgId);
			LogUtil.d("��Ϣ���ͷ�����Ϣ��", "11" + message);
			messageFromUsernameString = intent.getStringExtra("from");

			try {
				if (propellingMessageTurnTo.isAdv(message)) {
					
					int type = message.getIntAttribute("type");
					LogUtil.d("������������type��", "type" + type);
					if((type==propellingMessageTurnTo.FriendRequest)||(type==propellingMessageTurnTo.FriendAgreeRequest)){
						
						propellingMessageTurnTo turnTo = new propellingMessageTurnTo();
						abortBroadcast();
						turnTo.turnToActivity(message, null);
						return;
					}	
					dialogInterface = Messagefragment.this;
					Intent i = new Intent(MyApplication.getContext(),
							MessageAlertDialogActivity.class);
					i.putExtra("title", "�˾�");
					i.putExtra("content", "���ʻ���ڽ��У�����鿴����");
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_SINGLE_TOP
							);
					getActivity().startActivity(i);
					
//					
//					AlertDialog alertDialog = new AlertDialog.Builder(
//							getActivity())
//							.setTitle("�˾�")
//							.setMessage("���ʻ���ڽ��У�����鿴����")
//							.setPositiveButton("ȷ��",
//									new AlertDialog.OnClickListener() {
//
//										public void onClick(
//												DialogInterface arg0, int arg1) {
//											propellingMessageTurnTo
//													.SaveRemoteMessage(true,
//															message);
//											propellingMessageTurnTo turnTo = new propellingMessageTurnTo();
//											turnTo.turnToActivity(message, null);
//										}
//									})
//							.setNegativeButton("ȡ��",
//									new AlertDialog.OnClickListener() {
//
//										public void onClick(
//												DialogInterface arg0, int arg1) {
//											propellingMessageTurnTo
//													.SaveRemoteMessage(false,
//															message);
//											Main_Fragment main_Fragment = (Main_Fragment) getFragmentManager()
//													.getFragments().get(0);
//											main_Fragment
//													.initLatestNewsTintNumber();
//
//										}
//									}).create();
//					alertDialog.show();
				}
				abortBroadcast();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Message message2 = new Message();
			message2.what = 1;
			handler.sendMessage(message2);
		};
	};

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				LogUtil.d("myfriendactivity handler", "start to refresh from"
						+ messageFromUsernameString);
				MyfriendActivity_javabean data = findFriendByHxId(messageFromUsernameString);
				if (data != null) {
					LogUtil.d("�ҵ���һ��������", "start to refresh from"
							+ messageFromUsernameString);
					deleteRepeatFriendIdInGroup(data.getHxusername());
					recentCont.add(0, data);
				}
				RadioButton messageRadioButton = (RadioButton) getActivity()
						.findViewById(R.id.rbThree);
				LogUtil.d("��Ϣ��ť�Ƿ���", "s" + messageRadioButton.isChecked());
				if (!messageRadioButton.isChecked()) {
					LogUtil.d("��ʾ�����Ƿ�Ϊ��", "" + childPhotoBadgeView);
					showTabBadgeView();
				}

				initData();
				LogUtil.d("myfriendactivity handler", "stop to refresh");
			}
		};
	};

	public void showTabBadgeView() {
		if (childPhotoBadgeView != null) {
			int unreadCount = 0;
			EMConversation conversation;
			for (int i = 0; i < recentCont.size(); i++) {
				MyfriendActivity_javabean recentFriend = recentCont.get(i);
				conversation = EMChatManager.getInstance().// δ����ϢС�����ʾ
						getConversation(recentFriend.getHxusername());
				unreadCount += conversation.getUnreadMsgCount();
			}
			if (unreadCount > 0) {
				LogUtil.d("�Ƿ��ȥչʾ", "22" + childPhotoBadgeView);
				messageTintRadioButton.setVisibility(View.VISIBLE);
				childPhotoBadgeView.setText("" + unreadCount);
				childPhotoBadgeView.setTextSize(9);
				childPhotoBadgeView.setTextColor(Color.WHITE);
				childPhotoBadgeView
						.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
				childPhotoBadgeView.setBadgeMargin(45, 10);
				childPhotoBadgeView.show();
			}
		}
	}

	public void hideTabBadgeView() {
		if (childPhotoBadgeView != null) {
			messageTintRadioButton.setVisibility(View.INVISIBLE);
			childPhotoBadgeView.setVisibility(View.GONE);
		}
	}

	private void init() {

		mListView = (RewriteExpandListViewWithDelete) view
				.findViewById(R.id.myfriend_list);
		mListView.setDivider(new ColorDrawable(0xffdedede));
		mListView.setChildDivider(new ColorDrawable(0xffdedede));
		mListView.setDividerHeight(1);

		recentCont = DataSupport.findAll(MyfriendActivity_javabean.class);

		
		LogUtil.d("�����ϵ�˱��ػ�ȡ", "sf" + getActivity());
		EditText search = (EditText) view.findViewById(R.id.myfriend_search);
		add = (TextView) view.findViewById(R.id.myfriend_add);

		search.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View arg0, boolean arg1) {

				if (arg1) {
					LogUtil.d("�ٴε������", "arg1::," + "data:" + data.get(1));
					searchIntent = new Intent(getActivity(),
							searchContact.class);
					Bundle bundle = new Bundle();
					bundle.putParcelableArrayList("searchFriendList",

					(ArrayList<? extends Parcelable>) data.get(1));
					searchIntent.putExtra("searchListBundle", bundle);
					startActivity(searchIntent);
				}
			}
		});

		mListView.setChildItemClickListener(new ChildItemClickListener() {

			@Override
			public void itemclick(RewriteExpandListViewWithDelete parent,
					View v, int groupPosition, int childPosition) {
				MyfriendActivity_javabean item = adapter.getChild(
						groupPosition, childPosition);
				Intent intent = new Intent();
				intent.putExtra("friendid", item.getPayuserid());
				intent.putExtra("hxusername", item.getHxusername());
				intent.putExtra("nickname", item.getNickname());
				intent.putExtra("friendPicture", item.getHeadphotopath());
				intent.putExtra("myPicture", item.getMyHeadPhoto());
				intent.putExtra("isVip", item.getIsVip());
				int unreadCount=0;
				for (int i = 0; i < data.get(0).size(); i++) {
					MyfriendActivity_javabean recentFriend = data.get(0).get(i);
					EMConversation conversation = EMChatManager.getInstance().// δ����ϢС�����ʾ
							getConversation(recentFriend.getHxusername());
					unreadCount += conversation.getUnreadMsgCount();
				}
				LogUtil.d("δ������","Ϊ"+unreadCount);
				intent.putExtra("unReadCount", unreadCount);
				intent.setClass(getActivity(), ChatActivity.class);
				recentJavabean = item;
				startActivityForResult(intent, 0);

				EMConversation conversation = EMChatManager.getInstance().// δ����ϢС�����ʾ
						getConversation(item.getHxusername());
				conversation.resetUnreadMsgCount();
				initData();

			}
		});
		mListView
				.setExpandableListViewDeleteListener(new ExpandableListViewListener() {

					@Override
					public boolean onChildClick(
							RewriteExpandListViewWithDelete parent, View v,
							int groupPosition, int childPosition, long id,
							float beginX, float beginY, float endX, float endY,
							float slideX, float slideY) {

						return false;
					}
				});
		search.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), searchContact.class);
				startActivity(intent);
			}
		});

		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), searchUser.class);
				startActivityForResult(intent, 1);
			}
		});

	}

	private void initData() {
		LogUtil.d("�������¼���", "�����С�����������");
		data = new ArrayList<List<MyfriendActivity_javabean>>();
		data.add(recentCont);

		DataSupport.deleteAll(MyfriendActivity_javabean.class);
		MyfriendActivity_javabean recentFriend;
		// ֱ�Ӵ�list����bug����Ҫÿ��newһ���¶���ȥ�洢
		for (int i = 0; i < recentCont.size(); i++) {
			recentFriend = new MyfriendActivity_javabean();
			MyfriendActivity_javabean data = recentCont.get(i);
			recentFriend.setHeadphotopath(data.getHeadphotopath());
			recentFriend.setHxusername(data.getHxusername());
			recentFriend.setIsVip(data.getIsVip());
			recentFriend.setMyHeadPhoto(data.getMyHeadPhoto());
			recentFriend.setNickname(data.getNickname());
			recentFriend.setPayuserid(data.getPayuserid());
			recentFriend.save();
		}

		map = JSONCommand.JSON10030("6", "1");
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {

				@SuppressWarnings("unchecked")
				List<MyfriendActivity_javabean> myFriends = (List<MyfriendActivity_javabean>) model;
				MyApplication.setFriendData(myFriends);
				LogUtil.d("�ҵĺ��ѽ������ݣ�", "����" + myFriends);
				data.add(myFriends);
				adapter = new FriendAdapter(getActivity(), data,
						new IMessageFragmentRefresh() {

							@Override
							public void deleteRefresh(String hxUserName) {

								if (deleteRepeatFriendIdInGroup(hxUserName)) {
									LogUtil.d("�ҵĺ����б�ɾ�������ϵ�˺��ѳɹ�", "�ɹ�");
								} else {
									LogUtil.d("�ҵĺ����б�ɾ�������ϵ�˺��ѳɹ�", "�ɹ�");
								}
								initData();

							}
						});
				// mListView.setGroupIndicator(getResources().getDrawable(
				// R.drawable.expandablelistselector));
				mListView.setGroupIndicator(null);
				mListView.setAdapter(adapter);
				mListView.expandGroup(1);
				mListView.expandGroup(0);

			}
		}, new MyfriendActivity_jsonparse());

	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
//		MyfriendActivity_javabean item = adapter.getChild(groupPosition,
//				childPosition);
//		Intent intent;
//		intent = new Intent(getActivity(), ChatActivity.class);
//		intent.putExtra("friendid", item.getPayuserid());
//		intent.putExtra("hxusername", item.getHxusername());
//		intent.putExtra("nickname", item.getNickname());
//		intent.putExtra("friendPicture", item.getHeadphotopath());
//		intent.putExtra("myPicture", item.getMyHeadPhoto());
//		intent.putExtra("isVip", item.getIsVip());
//		// intent.setClass(getActivity(), ChatActivity.class);
//		recentJavabean = item;
//		
//
//		EMConversation conversation = EMChatManager.getInstance().// δ����ϢС�����ʾ
//				getConversation(item.getHxusername());
//		conversation.resetUnreadMsgCount();
//		
//		initData();
//		int unreadCount=0;
//		for (int i = 0; i < mData.get(0).size(); i++) {
//			MyfriendActivity_javabean recentFriend = data.get(0).get(i);
//			conversation = EMChatManager.getInstance().// δ����ϢС�����ʾ
//					getConversation(recentFriend.getHxusername());
//			unreadCount += conversation.getUnreadMsgCount();
//		}
//		LogUtil.d("δ������","Ϊ"+unreadCount);
//		intent.putExtra("unReadCount", unreadCount);
//		startActivityForResult(intent, 0);
		return true;
	}

	private Boolean deleteRepeatFriendIdInGroup(String hxuserid) {

		for (int i = 0; i < recentCont.size(); i++) {
			MyfriendActivity_javabean recentFriend = recentCont.get(i);
			if (recentFriend.getHxusername().equals(hxuserid)) {
				recentCont.remove(i);
				return true;
			}
		}
		return false;
	}

	private MyfriendActivity_javabean findFriendByHxId(String hxuserid) {
		List<MyfriendActivity_javabean> myFriends = data.get(1);
		for (int i = 0; i < myFriends.size(); i++) {
			MyfriendActivity_javabean friend = myFriends.get(i);
			LogUtil.d("", "");
			if (friend.getHxusername().equals(hxuserid)) {
				return friend;
			}
		}
		return null;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		LogUtil.d("���ؽ����", "" + resultCode);
		switch (resultCode) {
		case 1:
			deleteRepeatFriendIdInGroup(recentJavabean.getHxusername());
			recentCont.add(0, recentJavabean);
			List<MyfriendActivity_javabean> list = DataSupport
					.findAll(MyfriendActivity_javabean.class);
			initData();
			break;
		case 2:
			LogUtil.d("����", "����ˢ��");
			// if (deleteRepeatFriendIdInGroup(recentJavabean.getHxusername()))
			// {
			// LogUtil.d("�ҵĺ����б�ɾ�������ϵ�˺��ѳɹ�", "�ɹ�");
			// } else {
			// LogUtil.d("�ҵĺ����б�ɾ�������ϵ�˺��ѳɹ�", "�ɹ�");
			// }
			initData();
			break;
		default:
			break;
		}
	}

	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(msgReceiver);
	}

	@Override
	public void dialogSureClick() {
		// TODO Auto-generated method stub
		propellingMessageTurnTo.SaveRemoteMessage(true, message);
		propellingMessageTurnTo turnTo = new propellingMessageTurnTo();
		turnTo.turnToActivity(message, null);
	}

	@Override
	public void dialogCancleClick() {
		// TODO Auto-generated method stub
		propellingMessageTurnTo.SaveRemoteMessage(false, message);
		Main_Fragment main_Fragment = (Main_Fragment) getFragmentManager()
				.getFragments().get(0);
		main_Fragment.initLatestNewsTintNumber();
	};
}
