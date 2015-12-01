package different_adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pictureconnectinit.InitPicture_setting;

import testandmanage.LogUtil;
import testandmanage.ViewHolder;

import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.Direct;
import com.easemob.chat.TextMessageBody;
import com.example.haofangchan2.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * ��ϢListView��Adapter
 * 
 * @author way
 */
public class ChatAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;// �յ��Է�����Ϣ
		int IMVT_TO_MSG = 1;// �Լ����ͳ�ȥ����Ϣ
	}

	private static final int ITEMCOUNT = 2;// ��Ϣ���͵�����
	private List<EMMessage> coll = null;// ��Ϣ��������                                                  
	//�޸�Ϊʹ�û��ŵĶ���
	private EMMessage message = null;
	private LayoutInflater mInflater;
	private String myHeadPhotoString;
	private String friendHeadPhotoString;
	public ChatAdapter(Context context, List<EMMessage> coll,String myHeadPhotoString,String friendHeadPhotosString) {
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
		this.myHeadPhotoString=myHeadPhotoString;
		this.friendHeadPhotoString=friendHeadPhotosString;
	}
	
	public ChatAdapter(Context context, EMMessage message) {
		this.message = message;
		mInflater = LayoutInflater.from(context);
	}
	
	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * �õ�Item�����ͣ��ǶԷ�����������Ϣ�������Լ����ͳ�ȥ��
	 */
	public int getItemViewType(int position) {
		EMMessage entity = coll.get(position);

		if (entity.direct == Direct.RECEIVE) {//�յ�����Ϣ
			return IMsgViewType.IMVT_COM_MSG;
		} else {//�Լ����͵���Ϣ
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	/**
	 * Item���͵�����
	 */
	public int getViewTypeCount() {
		return ITEMCOUNT;
	}
    private ImageView chatHeadPhotoImageView;
    private TextView sendTimeTextView;
    private TextView sendContentTextView;
	public View getView(int position, View convertView, ViewGroup parent) {
		//�����жϴ�����Ƕ�����Ϣ����һ����Ϣ
		EMMessage entity = coll.get(position);
		if (convertView == null) {
			LogUtil.d("convertViewΪ�գ���ʱ����Ϊ:", ""+entity.direct);
			if (entity.direct == Direct.RECEIVE) {
				convertView = mInflater.inflate(
						R.layout.chat_leftitem, null);
			} else {
				convertView = mInflater.inflate(
						R.layout.chat_rightitem, null);
				
			}
		}
		chatHeadPhotoImageView=ViewHolder.get(convertView, R.id.chatitem_userhead);	
		sendTimeTextView=ViewHolder.get(convertView, R.id.chatitem_time);
		sendContentTextView=ViewHolder.get(convertView,R.id.chatitem_text);
		if(entity.direct==Direct.RECEIVE){
			InitPicture_setting.getImageLoader(friendHeadPhotoString, chatHeadPhotoImageView);
		}else{
			InitPicture_setting.getImageLoader(myHeadPhotoString, chatHeadPhotoImageView);
		}
		
		Date date = new Date(entity.getMsgTime());
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String time = sdf.format(date);
		TextMessageBody body = (TextMessageBody)entity.getBody();
	   	sendTimeTextView.setText(time);
	   	sendContentTextView.setText(body.getMessage());
		
		return convertView;
	}


}
