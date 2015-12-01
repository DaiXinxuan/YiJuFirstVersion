package testandmanage;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;
import hx_util.NewMessageBroadcastReceiver;
import hx_util.propellingMessageTurnTo;

import java.io.File;
import java.util.List;
import java.util.Map;

import main.HouseinfoActivity;
import main.MainFragmentActivity;

import org.litepal.LitePalApplication;

import web.WebActivity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.OnMessageNotifyListener;
import com.easemob.chat.OnNotificationClickListener;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import different_jsonparse.State_parse_Json;
import differentjavabean.MyfriendActivity_javabean;
import differentjavabean.RequestFriendModel;

public class MyApplication extends LitePalApplication implements
		ApplicationDialogSureClickInterface {

	private static Context context;
	private static List<MyfriendActivity_javabean> friendData;
	public static ApplicationDialogSureClickInterface dialogInterface;
//	 public static final String base_url =
//	 "http://192.168.0.11:8080/project/ourIOS_IOS";
//	 public static final String base_url_picture =
//	 "http://192.168.0.11:8080/project";
//	 public static final String base_url_upload =
//	 "http://192.168.0.11:8080/project/ourUploadHeadPhoto_getJOSNObjectData";
	public static final String base_url = "http://112.124.62.158:2869/project/ourIOS_IOS";
	public static final String base_url_picture = "http://112.124.62.158:2869/project";
	public static final String base_url_upload = "http://112.124.62.158:2869/project/ourUploadHeadPhoto_getJOSNObjectData";
	private static SharedPreferences sp;
	private static String userName;
	private static RequestFriendModel requestFriendModel;

	public static String getpayid() {
		if (sp == null)
			sp = context.getSharedPreferences("setting",
					MyApplication.getContext().MODE_PRIVATE);
		return sp.getString("payid", null);
	}

	public static String getproid() {
		if (sp == null)
			sp = context.getSharedPreferences("setting",
					MyApplication.getContext().MODE_PRIVATE);
		return sp.getString("proid", null);
	}

	public static void setProid(String proid) {
		if (sp == null)
			sp = context.getSharedPreferences("setting",
					MyApplication.getContext().MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("proid", proid);
		editor.commit();
	}

	public static String getHXUsername() {

		if (sp == null)
			sp = context.getSharedPreferences("setting",
					MyApplication.getContext().MODE_PRIVATE);

		return sp.getString("hxusername", null);
	}

	public static String getUserName() {
		if (userName == null) {
			sp = context.getSharedPreferences("setting",
					MyApplication.getContext().MODE_PRIVATE);
			userName = sp.getString("username", null);
		}
		return userName;
	}

	public static String getHXPassWord() {
		if (sp == null)
			sp = context.getSharedPreferences("setting",
					MyApplication.getContext().MODE_PRIVATE);
		return sp.getString("hxpassword", null);
	}

	public static SharedPreferences getSharedPreferences() {
		if (sp == null) {
			sp = context.getSharedPreferences("setting",
					MyApplication.getContext().MODE_PRIVATE);
		}
		return sp;
	}

	public void onCreate() {
		context = this;
		super.onCreate();
		setImageLoaderConfiguration();
		// 初始化环信
		EMChat.getInstance().init(this);
		// 注册接受新消息的广播
		NewMessageBroadcastReceiver msgReceiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager
				.getInstance().getNewMessageBroadcastAction());
		intentFilter.setPriority(3);
		registerReceiver(msgReceiver, intentFilter);
		localNotification();
		EMChat.getInstance().setAppInited();

		if (getpayid() != null) {
			EMChatManager.getInstance().login(getHXUsername(), getHXPassWord(),
					new EMCallBack() {// 回调

						public void onSuccess() {
							Log.d("main", "myapplication登陆聊天服务器成功！");
						}

						@Override
						public void onProgress(int progress, String status) {

						}

						@Override
						public void onError(int code, String message) {
							Log.d("main", "myapplication登陆聊天服务器失败！");
						}
					});
		}
	}

	public void localNotification() {

		// 获取到EMChatOptions对象
		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		options.setShowNotificationInBackgroud(true);
		// // 设置自定义的文字提示
		options.setNotifyText(new OnMessageNotifyListener() {

			@Override
			public String onNewMessageNotify(EMMessage message) {
				// 可以根据message的类型提示不同文字，这里为一个简单的示例
				LogUtil.d("test", message.getFrom());
				for(int i = 0;i < friendData.size();i++){
					if(message.getFrom().equals(friendData.get(i).getHxusername())){
						return friendData.get(i).getNickname()+"发来一条新消息";
					}
				}
				return "宜居掌售：您有一条新消息";
			}

			@Override
			public String onLatestMessageNotify(EMMessage message,
					int fromUsersNum, int messageNum) {
				return null;
			}

			@Override
			public String onSetNotificationTitle(EMMessage arg0) {
				// TODO Auto-generated method stub
				return "宜居掌售";
			}

			@Override
			public int onSetSmallIcon(EMMessage arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		// 设置notification点击listener
		options.setOnNotificationClickListener(new OnNotificationClickListener() {

			@Override
			public Intent onNotificationClick(EMMessage message) {
				int type;
				LogUtil.d("是否执行通知点击", "点击");
				Intent intent = null;
				try {
					type = message.getIntAttribute("type");
					String html = null;
					if ((type == propellingMessageTurnTo.FriendRequest)
							|| (type == propellingMessageTurnTo.FriendAgreeRequest)) {

					} else {
						html = message.getStringAttribute("html");
					}
					intent = new Intent(MyApplication.getContext(),
							WebActivity.class);
					intent.putExtra("url", html);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_SINGLE_TOP);
					switch (type) {

					case propellingMessageTurnTo.MY_MAGAZINE:
						intent.putExtra("title", "精品杂志");
						break;
					case propellingMessageTurnTo.HOUSE_INFO:
						intent = new Intent(MyApplication.getContext(),
								HouseinfoActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_SINGLE_TOP);
						String roomid = message.getStringAttribute("idNumber");
						intent.putExtra("houseid", roomid);
						break;
					case propellingMessageTurnTo.AUCTION_INFO:
						intent.putExtra("title", "火热拍卖");
						break;
					case propellingMessageTurnTo.GROUP_BUY_INFO:
						intent.putExtra("title", "特价团购");
						break;
					case propellingMessageTurnTo.ACTIVITY_INFO:
						intent.putExtra("title", "精彩夺宝");
						break;
					case propellingMessageTurnTo.FriendRequest:
						RequestFriendModel requestFriendModel = new RequestFriendModel();
						requestFriendModel.setFriendid(message
								.getIntAttribute("friendid") + "");
						requestFriendModel.setFriendPhone(message
								.getStringAttribute("friendphone"));
						requestFriendModel.setRemarkName(message
								.getStringAttribute("remarkname"));
						requestFriendModel.setNickName(message
								.getStringAttribute("nickname"));
						requestFriendModel.setProid(message
								.getStringAttribute("proid"));
						requestFriendModel.setRealPayid(message
								.getIntAttribute("realPayid") + "");
						MyApplication.requestFriendModel = requestFriendModel;
						String content = requestFriendModel.getNickName()
								+ "请求添加您为好友，是否同意请求";

						// final Handle_defined_dialog hd = new
						// Handle_defined_dialog(
						// MyApplication.this, "好友请求", content, "确认", "取消");
						// hd.dialog
						// .setClicklistener(new ClickListenerInterface() {
						// public void doConfirm() {
						// // TODO Auto-generated method stub
						// dialogSureClick();
						// hd.dialog.dismiss();
						// }
						//
						// public void doCancel() {
						// // TODO Auto-generated method stub
						// hd.dialog.dismiss();
						// }
						// });
						// hd.cleardialog();

						MyApplication.dialogInterface = MyApplication.this;
						intent = new Intent(MyApplication.getContext(),
								MyApplicationAlertDialogActivity.class);
						intent.putExtra("content", content);
						intent.putExtra("title", "好友添加请求");
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								);
						return intent;
					case propellingMessageTurnTo.FriendAgreeRequest:
						intent = new Intent(MyApplication.getContext(),
								MyApplicationAlertDialogActivity.class);
						String content2 = "您已添加"
								+ message.getStringAttribute("nickname")
								+ "为好友";
						// final Handle_defined_dialog hd1 = new
						// Handle_defined_dialog(
						// MyApplication.this, "好友请求", content2, "确认",
						// "取消");
						// hd1.dialog
						// .setClicklistener(new ClickListenerInterface() {
						// public void doConfirm() {
						// // TODO Auto-generated method stub
						// // dialogSureClick();
						// hd1.dialog.dismiss();
						// }
						//
						// public void doCancel() {
						// // TODO Auto-generated method stub
						// hd1.dialog.dismiss();
						// }
						// });
						// hd1.cleardialog();
						MyApplication.dialogInterface = null;
						intent.putExtra("content", content2);
						intent.putExtra("title", "好友添加请求");
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								);
						break;

					default:
						break;

					}
					propellingMessageTurnTo.SaveRemoteMessage(true, message);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					intent = new Intent(getContext(),
							MainFragmentActivity.class);
					return intent;
				}
				return intent;
			}
		});
	}

	public void dialogSureClick() {

		Map<String, String> map = JSONCommand.JSON10042(
				requestFriendModel.getProid(), MyApplication.getproid(),
				requestFriendModel.getRemarkName(),
				requestFriendModel.getRealPayid(),
				requestFriendModel.getFriendPhone(),
				requestFriendModel.getFriendid());
		ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {

			@Override
			public void updateUI(Object model) {
				// TODO Auto-generated method stub
				Boolean state = (Boolean) model;
				if (state) {
					LogUtil.d("刷新", "刷新事件");
					Intent intent = new Intent();
					intent.setAction("action.refreshFriend");
					MyApplication.getContext().sendBroadcast(intent);
					Toast.makeText(MyApplication.getContext(),
							"您已添加" + requestFriendModel.getNickName() + "为好友",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MyApplication.getContext(), "服务器繁忙，添加失败",
							Toast.LENGTH_SHORT).show();
				}
			}
		}, new State_parse_Json());

	}

	private void setImageLoaderConfiguration() {

		// 网络图片加载缓存下面必须加
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				/*
				 * .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				 * null) // Can slow ImageLoader, use it carefully (Better don't
				 * use it) ///设置缓存的详细信息，最好不要设置这个
				 */.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache implementation/
				// 你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// 缓存的文件数量
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
				// connectTimeout (5 s), readTimeout (30 s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();// 开始构建
		// Initialize ImageLoader with configuration.

		ImageLoader.getInstance().init(config);
		// 配置好ImageLoaderConfiguration后，调用以下方法来实现初始化：

	}

	public static Context getContext() {
		return context;
	}

	public static List<MyfriendActivity_javabean> getFriendData() {
		return friendData;
	}

	public static void setFriendData(List<MyfriendActivity_javabean> friendData) {
		MyApplication.friendData = friendData;
	}

}
