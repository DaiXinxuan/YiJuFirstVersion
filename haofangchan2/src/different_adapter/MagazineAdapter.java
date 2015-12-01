package different_adapter;

import java.util.ArrayList;
import java.util.List;

import load.LoginActivity;
import pictureconnectinit.InitPicture_setting;
import testandmanage.MyApplication;
import testandmanage.ViewHolder;
import android.graphics.Color;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import differentjavabean.PersonalMagazineModel;
import differentjavabean.Setting_Magazine_javabean;

public class MagazineAdapter extends BaseAdapter{
	private List<PersonalMagazineModel> list=new 
			ArrayList<PersonalMagazineModel>();
	public MagazineAdapter(List<PersonalMagazineModel> list) {
		// TODO Auto-generated constructor stub
		this.list=list;
		
	}
	public MagazineAdapter() {
	
	}
	
	public int getCount() {
		return list.size();
	}
	
	
	public PersonalMagazineModel getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}
/*	public void addItem(String s){
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间。
		int hour = t.hour;
		int minute = t.minute;
	}*/
	TextView title,text;
	ImageView image;
    private TextView typeTextView; 
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.magazinelistitem, null);
		}
		title=ViewHolder.get(convertView,R.id.magazine_itemtitle);
		title.setTextColor(Color.BLACK);
		text=ViewHolder.get(convertView,R.id.magazine_itemtext);
		text.setTextColor(Color.BLACK);
     	image=ViewHolder.get(convertView,R.id.magazine_itemimg);
        typeTextView=ViewHolder.get(convertView, R.id.magazine_itemtype);
		InitPicture_setting.getImageLoader(list.get(position).getHeadPhoto(), image);
		title.setText(list.get(position).getName());
		text.setText(list.get(position).getIntroduce());
		typeTextView.setText(list.get(position).getKind());
		return convertView;
	}

}
