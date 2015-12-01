package hx_util;

import java.util.List;

import com.easemob.chat.EMContactListener;

public class MyContactListener implements EMContactListener {

	@Override
	public void onContactAdded(List<String> usernameList) {
		// 保存增加的联系人
			
	}

	@Override
	public void onContactDeleted(final List<String> usernameList) {
		// 被删除

	}

	@Override
	public void onContactInvited(String username, String reason) {
		// 接到邀请的消息，如果不处理(同意或拒绝)，掉线后，服务器会自动再发过来，所以客户端不要重复提醒
			
	}

	@Override
	public void onContactAgreed(String username) {
		//同意好友请求
	}

	@Override
	public void onContactRefused(String username) {
		// 拒绝好友请求

	}

}
