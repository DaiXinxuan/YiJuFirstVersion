package different_adapter;

import java.util.ArrayList;
import java.util.List;

import pictureconnectinit.InitPicture_setting;
import testandmanage.MyApplication;
import testandmanage.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haofangchan2.R;

import differentjavabean.UserRoomCommentModel;

public class CommentAdapter extends BaseAdapter{
	private List<UserRoomCommentModel> list=new ArrayList<UserRoomCommentModel>();
	public CommentAdapter(){
		
	}
	public CommentAdapter(List<UserRoomCommentModel> list) {
		this.list=list;
	}

	@Override
	public int getCount() {
		return list.size();
	}
	
	@Override
	public UserRoomCommentModel getItem(int arg0) {
		
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
	
		return arg0;
	}
/*	public void addItem(String s){
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间。
		int month = t.month+1;
		int date = t.monthDay;
		list.add(0, new Commnet_javabean());
	}*/
	TextView name,text,date;
	ImageView image,rank;
	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null){
			convertView = LayoutInflater.from(MyApplication.getContext()).
					inflate(R.layout.commentlistitem, null);
		}
		name = ViewHolder.get(convertView, R.id.comment_username);
		text = ViewHolder.get(convertView, R.id.comment_text);
		date = ViewHolder.get(convertView, R.id.comment_date);
		image = ViewHolder.get(convertView, R.id.comment_image);
//		name=(TextView)convertView.findViewById(R.id.comment_username);
//		text=(TextView)convertView.findViewById(R.id.comment_text);
//		date=(TextView)convertView.findViewById(R.id.comment_date);
//		image=(ImageView) convertView.findViewById(R.id.comment_image);
//		rank=(ImageView) convertView.findViewById(R.id.comment_rank);
		
		/*image.setImageResource(R.drawable.profile_image);
		rank.setImageResource(R.drawable.fourstar);*/
//		 ImageLoader.getInstance().displayImage(LoginActivity.base_url_picture+
//				  list.get(position).getHeadPhoto(), 
//				   image);
		InitPicture_setting.getImageLoader(list.get(position).getHeadPhoto(), image);
//		 ImageLoader.getInstance().displayImage(LoginActivity.base_url_picture+
//				   list.get(position).get, 
//				   rank);
		
		name.setText(list.get(position).getUserName());
		text.setText(list.get(position).getComment());
		date.setText(list.get(position).getDate());
		return convertView;
	}

}
