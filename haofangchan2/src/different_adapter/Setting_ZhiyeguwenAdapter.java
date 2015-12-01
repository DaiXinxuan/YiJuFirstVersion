package different_adapter;

import java.util.List;

import load.LoginActivity;
import pictureconnectinit.InitPicture_setting;
import testandmanage.MyApplication;
import testandmanage.ViewHolder;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import differentjavabean.Zhiyeguwen_javabean;

public class Setting_ZhiyeguwenAdapter extends BaseAdapter{

	List<Zhiyeguwen_javabean> list;
	public Setting_ZhiyeguwenAdapter(List<Zhiyeguwen_javabean> list){
		this.list=list;
		
	}
	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	ImageView photo;
	TextView name;
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView =  LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.setting_zhiyeguwen_item, null, false);   
		}
        photo=ViewHolder.get(convertView, R.id.img_zhiyeguwen_item);
        name = ViewHolder.get(convertView, R.id.txt_zhiyeguwen_item);
       		  
        InitPicture_setting.getImageLoader(list.get(position).getPhotourl(), photo);
        if(list.get(position).getHasSaler()){
			  name.setTextColor(Color.RED);
		  }
		name.setText(list.get(position).getName());
/*		  if(null==photo){
			  // 如果获取的图片为空，则默认显示一个图片  
			  photo.setImageResource(R.drawable.ic_launcher);

         }  
		*/
		return convertView;
	}

}