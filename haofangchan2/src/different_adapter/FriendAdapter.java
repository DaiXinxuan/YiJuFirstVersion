package different_adapter;

import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.List;
import java.util.Map;

import main_fragment.IMessageFragmentRefresh;
import pictureconnectinit.InitPicture_setting;
import rewriteView.RewriteExpandListViewWithDelete.Positions;
import setting.Handle_defined_dialog;
import setting.Logic_defined_dialog.ClickListenerInterface;
import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.ViewHolder;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.example.haofangchan2.R;
import com.readystatesoftware.viewbadger.BadgeView;

import de.hdodenhof.circleimageview.CircleImageView;
import different_jsonparse.State_parse_Json;
import differentjavabean.MyfriendActivity_javabean;

public class FriendAdapter extends BaseExpandableListAdapter {

	/* BadgeView bv = null; */
	EMConversation conversation = null;
	private Context mContext;
	private LayoutInflater mInflater = null;
	private String[] mGroupStrings = null;
	private List<List<MyfriendActivity_javabean>> mData = null;
	private IMessageFragmentRefresh ifreshFriends;

	public FriendAdapter(Context ctx,
			List<List<MyfriendActivity_javabean>> list,
			IMessageFragmentRefresh ifreshFriends) {
		mContext = ctx;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGroupStrings = mContext.getResources().getStringArray(R.array.group2);
		mData = list;
		this.ifreshFriends = ifreshFriends;

	}

	public void setData(List<List<MyfriendActivity_javabean>> list) {
		mData = list;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return mData.get(groupPosition).size();
	}

	@Override
	public List<MyfriendActivity_javabean> getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mData.get(groupPosition);
	}

	@Override
	public MyfriendActivity_javabean getChild(int groupPosition,
			int childPosition) {
		// TODO Auto-generated method stub
		return mData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	private TextView friendsGroupNametTextView;
	private TextView friendsGroupCounTextView;
	private TextView friendsGroupMessageUnreadTextView;
	private int unreadCount = 0;

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.myfriendgroupitem, null);
		}

		if (groupPosition == 0) {

			for (int i = 0; i < mData.get(0).size(); i++) {
				MyfriendActivity_javabean recentFriend = mData.get(0).get(i);
				conversation = EMChatManager.getInstance().// 未读消息小红点显示
						getConversation(recentFriend.getHxusername());
				unreadCount += conversation.getUnreadMsgCount();
			}
			if (unreadCount > 0) {
				friendsGroupMessageUnreadTextView = ViewHolder.get(convertView,
						R.id.myfriend_group_unreadMessage);
				BadgeView childPhotoBadgeView = new BadgeView(mContext,
						friendsGroupMessageUnreadTextView);
				childPhotoBadgeView.setText("" + unreadCount);
				childPhotoBadgeView.setTextColor(Color.WHITE);
				childPhotoBadgeView
						.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
				childPhotoBadgeView.setBadgeMargin(0, 20);
				childPhotoBadgeView.show();
			}
		}

		ImageView mgroupimage = (ImageView) convertView
				.findViewById(R.id.myfriend_group_img);
		mgroupimage
				.setBackgroundResource(R.drawable.expandablelistviewindicatordown);
		if (!isExpanded) {
			mgroupimage
					.setBackgroundResource(R.drawable.expandablelistviewindicator);
		}
		friendsGroupNametTextView = ViewHolder.get(convertView,
				R.id.myfriend_group_name);
		friendsGroupCounTextView = ViewHolder.get(convertView,
				R.id.myfriend_group_count);

		friendsGroupNametTextView.setText(mGroupStrings[groupPosition]);

		friendsGroupCounTextView.setText("[" + mData.get(groupPosition).size()
				+ "]");

		return convertView;
	}

	private CircleImageView childPhotoCircleImageView;
	private TextView childNameTextView;
	private TextView detailMessageTextView;
	private Button deleteButton;
	private AlertDialog.Builder alert;
	private MyfriendActivity_javabean friend;

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		friend = mData.get(groupPosition).get(childPosition);
		LogUtil.d("环信名字", "" + friend.getHxusername());

		convertView = mInflater.inflate(R.layout.myfriendchilditem, null);

		conversation = EMChatManager.getInstance().// 未读消息小红点显示
				getConversation(friend.getHxusername());
		childPhotoCircleImageView = ViewHolder.get(convertView,
				R.id.myfriend_item_img);
		childNameTextView = ViewHolder
				.get(convertView, R.id.myfriend_item_name);
		detailMessageTextView = ViewHolder.get(convertView,
				R.id.myfriend_item_detail);
		deleteButton = ViewHolder.get(convertView,
				R.id.myfriendDeleteItemButton);

		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Positions group;
				// 注意在点击事件中拿tag时必须要用arg0拿，不能直接拿deleteButton的tag
				group = (Positions) arg0.getTag();
				LogUtil.d("group", "" + group);
				friend = mData.get(group.groupPos).get(group.childPos);

				// alert = new AlertDialog.Builder(mContext)
				// .setTitle("标题")
				// .setMessage("是否删除")
				// .setNeutralButton("确定",
				// new DialogInterface.OnClickListener() {
				//
				// public void onClick(DialogInterface arg0,
				// int arg1) {
				//
				// Map<String, String> map;
				// map = JSONCommand.JSON10035("6", "1",
				// friend.getPayuserid(),
				// friend.getHxusername());
				//
				// ServerAsyncHttpTask.execute(map,
				// new UpdateUIInterface() {
				//
				// @Override
				// public void updateUI(
				// Object model) {
				//
				// Boolean stateBoolean = (Boolean) model;
				// if (stateBoolean) {
				//
				// Toast.makeText(
				// mContext,
				// "删除好友成功！",
				// Toast.LENGTH_SHORT)
				// .show();
				// // 删除好友成功后接口回调，重新刷新我的好友列表
				// ifreshFriends
				// .deleteRefresh(friend
				// .getHxusername());
				//
				// } else {
				// Toast.makeText(
				// mContext,
				// "服务器繁忙，删除好友失败！",
				// Toast.LENGTH_SHORT)
				// .show();
				// }
				//
				// }
				// }, new State_parse_Json());
				//
				// }
				// })
				// .setNegativeButton("取消",
				// new DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface arg0,
				// int arg1) {
				//
				// }
				// });

				if (group.groupPos == 0) {
					ifreshFriends.deleteRefresh(friend.getHxusername());
				} else {
					// alert.show();
					if (friend.getIsVip() != 1) {
						final Handle_defined_dialog hd = new Handle_defined_dialog(
								mContext, "提示", "是否删除?", "确认", "取消");
						hd.dialog
								.setClicklistener(new ClickListenerInterface() {
									public void doConfirm() {
										// TODO Auto-generated method stub
										Map<String, String> map;
										map = JSONCommand.JSON10035("6", "1",
												friend.getPayuserid(),
												friend.getHxusername());

										ServerAsyncHttpTask.execute(map,
												new UpdateUIInterface() {

													@Override
													public void updateUI(
															Object model) {

														Boolean stateBoolean = (Boolean) model;
														if (stateBoolean) {

															Toast.makeText(
																	mContext,
																	"删除好友成功！",
																	Toast.LENGTH_SHORT)
																	.show();
															// 删除好友成功后接口回调，重新刷新我的好友列表
															ifreshFriends
																	.deleteRefresh(friend
																			.getHxusername());

														} else {
															Toast.makeText(
																	mContext,
																	"服务器繁忙，删除好友失败！",
																	Toast.LENGTH_SHORT)
																	.show();
														}

													}
												}, new State_parse_Json());
										LogUtil.d("Setting", "true");
										hd.dialog.dismiss();

									}

									public void doCancel() {
										// TODO Auto-generated method stub
										LogUtil.d("Setting", "false");
										hd.dialog.dismiss();
									}
								});
						hd.cleardialog();
					} else {
						Toast.makeText(mContext, "职业顾问不能删除！",
								Toast.LENGTH_SHORT).show();
					}

				}
			}
		});

		LogUtil.d("图片地址", ""
				+ getChild(groupPosition, childPosition).getHeadphotopath());

		if (conversation.getUnreadMsgCount() > 0 && groupPosition == 0) {

			BadgeView childPhotoBadgeView = new BadgeView(mContext,
					childPhotoCircleImageView);
			childPhotoBadgeView.setText("" + conversation.getUnreadMsgCount());
			childPhotoBadgeView.setTextColor(Color.WHITE);
			childPhotoBadgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
			childPhotoBadgeView.setBadgeMargin(0, 0);
			childPhotoBadgeView.show();

		}

		if (getChild(groupPosition, childPosition).getIsVip() == 1) {

			childNameTextView.setTextColor(Color.RED);
			if (groupPosition == 0) {
				detailMessageTextView.setTextColor(Color.RED);
			}
		}

		EMMessage message = conversation.getLastMessage();
		if (message != null) {
			TextMessageBody body = (TextMessageBody) message.getBody();
			String textString = body.getMessage();
			detailMessageTextView.setText(textString);
		}
		childNameTextView.setText(""
				+ getChild(groupPosition, childPosition).getNickname());
		if (groupPosition != 0) {
			detailMessageTextView.setVisibility(View.GONE);
		} else {
			detailMessageTextView.setVisibility(View.VISIBLE);
		}
		// 注意重用问题
		InitPicture_setting.getImageLoader(
				getChild(groupPosition, childPosition).getHeadphotopath(),
				childPhotoCircleImageView);

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		/* 很重要：实现ChildView点击事件，必须返回true */
		return true;
	}

}
