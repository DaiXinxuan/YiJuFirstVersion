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
 * 消息ListView的Adapter
 * 
 * @author way
 */
public class ChatAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;// 收到对方的消息
		int IMVT_TO_MSG = 1;// 自己发送出去的消息
	}

	private static final int ITEMCOUNT = 2;// 消息类型的总数
	private List<EMMessage> coll = null;// 消息对象数组                                                  
	//修改为使用环信的对象
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
	 * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
	 */
	public int getItemViewType(int position) {
		EMMessage entity = coll.get(position);

		if (entity.direct == Direct.RECEIVE) {//收到的消息
			return IMsgViewType.IMVT_COM_MSG;
		} else {//自己发送的消息
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	/**
	 * Item类型的总数
	 */
	public int getViewTypeCount() {
		return ITEMCOUNT;
	}
    private ImageView chatHeadPhotoImageView;
    private TextView sendTimeTextView;
    private TextView sendContentTextView;
	public View getView(int position, View convertView, ViewGroup parent) {
		//进行判断传入的是多条消息还是一条消息
		EMMessage entity = coll.get(position);
		if (convertView == null) {
			LogUtil.d("convertView为空，此时方向为:", ""+entity.direct);
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
