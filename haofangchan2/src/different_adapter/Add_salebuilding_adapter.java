package different_adapter;

import java.util.List;

import pictureconnectinit.InitPicture_setting;

import load.LoginActivity;

import testandmanage.LogUtil;
import testandmanage.MyApplication;
import testandmanage.ViewHolder;
import testandmanage.Window_metrics;


import com.example.haofangchan2.R;

import com.nostra13.universalimageloader.core.ImageLoader;


import differentjavabean.Add_salebuilding_picture_data_javabean;


import android.graphics.Bitmap;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class Add_salebuilding_adapter extends BaseAdapter{
    
	LayoutInflater mInflater=LayoutInflater.from(MyApplication.getContext());
	List<Add_salebuilding_picture_data_javabean> data_list;

	 public Add_salebuilding_adapter(List<Add_salebuilding_picture_data_javabean> datalist) {
		this.data_list=datalist;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		LogUtil.d("售房部界面数据长度222", ""+data_list.size());
		return data_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	private ImageView img_sale_building=null;
	private int width=Window_metrics.getScreenWidth();
	private int height=Window_metrics.getScreenHeight();
	private int width2=width/3;
	private int height2=height/4;
	private int left_margin=width/12;
	public View getView(int position, View convertView, ViewGroup parent) {
         
         if(null==convertView){  
             convertView = LayoutInflater.from(MyApplication.getContext())
            		 .inflate(R.layout.add_salebuilding_item, null, false);   
         }
         img_sale_building=ViewHolder.get(convertView,R.id.sale_building_img);  
         LinearLayout.LayoutParams layoutParams = 
        		 new LinearLayout.LayoutParams(img_sale_building.getLayoutParams());
          layoutParams.setMargins(left_margin,5, 0,  0);
          layoutParams.width = width2; 
          layoutParams.height = height2;
          img_sale_building.setLayoutParams(layoutParams);
          
        InitPicture_setting.getImageLoader(data_list.get(position).getUrl(), img_sale_building);
		
		return convertView;
	}

}
