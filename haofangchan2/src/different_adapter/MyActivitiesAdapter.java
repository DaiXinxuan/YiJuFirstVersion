package different_adapter;

import java.util.ArrayList;
import java.util.List;

import load.LoginActivity;

import pictureconnectinit.InitPicture_setting;

import testandmanage.MyApplication;
import testandmanage.ViewHolder;

import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import differentjavabean.MyActivities_javabean;
import differentjavabean.MyActivityModel;
import differentjavabean.ProjectItemBean;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyActivitiesAdapter extends BaseAdapter{
	private List<MyActivityModel> list;
	public MyActivitiesAdapter(List<MyActivityModel> list) {
		this.list = list;
	}
	public MyActivitiesAdapter() {
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
	@Override
	public MyActivityModel getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public void addItem(String title,String text,String img,String date){
		
	}
	private TextView title,text,date;
	private ImageView image;
	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null){
			convertView= LayoutInflater.from(MyApplication.getContext()).
					inflate(R.layout.myactivitieslistitem, null);
		}
		title=ViewHolder.get(convertView,R.id.myactivities_title);
		text=ViewHolder.get(convertView,R.id.myactivities_text);
		image=ViewHolder.get(convertView,R.id.myactivities_img);
		date=ViewHolder.get(convertView,R.id.myactivities_date);
		InitPicture_setting.getImageLoader(list.get(position).getPhotopath(), image);	
		text.setText(list.get(position).getContent());
		title.setText(list.get(position).getName());
		date.setText(list.get(position).getTime());
		return convertView;
	}

}
