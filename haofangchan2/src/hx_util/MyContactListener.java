package hx_util;

import java.util.List;

import com.easemob.chat.EMContactListener;

public class MyContactListener implements EMContactListener {

	@Override
	public void onContactAdded(List<String> usernameList) {
		// �������ӵ���ϵ��
			
	}

	@Override
	public void onContactDeleted(final List<String> usernameList) {
		// ��ɾ��

	}

	@Override
	public void onContactInvited(String username, String reason) {
		// �ӵ��������Ϣ�����������(ͬ���ܾ�)�����ߺ󣬷��������Զ��ٷ����������Կͻ��˲�Ҫ�ظ�����
			
	}

	@Override
	public void onContactAgreed(String username) {
		//ͬ���������
	}

	@Override
	public void onContactRefused(String username) {
		// �ܾ���������

	}

}
