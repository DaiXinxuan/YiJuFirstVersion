package different_adapter;

import java.util.ArrayList;

import pictureconnectinit.InitPicture_setting;

import com.example.haofangchan2.R;

import testandmanage.MyApplication;
import testandmanage.ViewHolder;

import differentjavabean.ConsultantCommentModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConsultantCommentAdapter extends BaseAdapter{
	ArrayList<ConsultantCommentModel> list = new ArrayList<ConsultantCommentModel>();
	public ConsultantCommentAdapter(ArrayList<ConsultantCommentModel> list){
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	private ImageView head,commentiv;
	private TextView name, content,date;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.consultant_comment_item, null);
		}
		head = ViewHolder.get(convertView, R.id.comment_image);
		commentiv = ViewHolder.get(convertView, R.id.comment_iv);
		name = ViewHolder.get(convertView, R.id.comment_username);
		content = ViewHolder.get(convertView, R.id.comment_text);
		date = ViewHolder.get(convertView, R.id.comment_date);
		
		name.setText(list.get(position).getName());
		content.setText(list.get(position).getContent());
		date.setText(list.get(position).getDate());
		InitPicture_setting.getImageLoader(list.get(position).getHeadphoto(), head);
		switch(list.get(position).getLevel()){
		case "1": commentiv.setImageResource(R.drawable.comment03);
				  break;
		case "2": commentiv.setImageResource(R.drawable.comment06);
				  break;
		case "3": commentiv.setImageResource(R.drawable.comment09);
				  break;
		}
		
		return convertView;
	}

}
