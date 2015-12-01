package different_adapter;

import java.util.List;

import pictureconnectinit.InitPicture_setting;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import testandmanage.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haofangchan2.R;

import differentjavabean.LatestNewsTable;

public class LatestNewsAdapter extends BaseAdapter{
	private List<LatestNewsTable> list;
	public LatestNewsAdapter(List<LatestNewsTable> list) {
		this.list = list;
	}
	public LatestNewsAdapter() {
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
	@Override
	public LatestNewsTable getItem(int arg0) {
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
		LogUtil.d("本地数据：", ""+list.get(position).getContent());
		text.setText(list.get(position).getContent());
		title.setText(list.get(position).getName());
		date.setText(list.get(position).getTime());
		return convertView;
	}

}

