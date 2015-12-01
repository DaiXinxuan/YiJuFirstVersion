package different_adapter;


import httpConnect.ServerAsyncHttpTask;
import httpConnect.UpdateUIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import load.LoginActivity;

import pictureconnectinit.InitPicture_setting;

import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import different_jsonparse.BooleanParser;
import differentjavabean.HomePageHouseListModel;
import differentjavabean.HomePageModel;
import differentjavabean.Main_Message_DataBean;

import testandmanage.JSONCommand;
import testandmanage.LogUtil;
import testandmanage.MyApplication;
import testandmanage.ViewHolder;
import web.WebActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class Main_MessageAdapter extends BaseAdapter{
	private HomePageModel hpm;
	private List<HomePageHouseListModel> listhouse;
	private Context context;
	class MyonClickListener implements OnClickListener{
		int position;
		ImageView attenBtn;
		public MyonClickListener(int position,ImageView iv){
			this.position = position;
			attenBtn=iv;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("test", "Yes");
			if(attenBtn.getDrawable().getCurrent().getConstantState().equals(context.getResources().getDrawable(R.drawable.att2).getConstantState())){
				Log.d("test", "No");
				HashMap<String, String> map = JSONCommand.JSON10011("6", "1", listhouse.get(position).getRoomId());
				ServerAsyncHttpTask.execute(map, new UpdateUIInterface() {
					@Override
					public void updateUI(Object model) {
						// TODO Auto-generated method stub
						if((boolean) model){
							Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
							attenBtn.setImageResource(R.drawable.att1);
							listhouse.get(position).setAttentioned("true");
						}else{
							Toast.makeText(context, "关注失败，服务器繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
						}
					}
				}, new BooleanParser());
			}
		}
		
	}
	
	
	public Main_MessageAdapter(ArrayList<HomePageHouseListModel> houses,Context context){
		this.listhouse=houses;
		this.context=context;
	}
	@Override
	public int getCount() {
		return listhouse.size();
	}

	@Override
	public HomePageHouseListModel getItem(int position) {
		return listhouse.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	ImageView houseicon ,houseattention;
	TextView nameview,content,area,price,ob,huxing,tv;
	private HomePageHouseListModel hhl;
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			convertView = LayoutInflater.from(context).
					inflate(R.layout.main_listview_layout, null);
		}
		
		houseicon = ViewHolder.get(convertView,R.id.house_icon);
		nameview = ViewHolder.get(convertView,R.id.sell_title);
		content = ViewHolder.get(convertView,R.id.info);
		area = ViewHolder.get(convertView, R.id.area);
		price = ViewHolder.get(convertView, R.id.price);
		ob = ViewHolder.get(convertView, R.id.ob_num);
		huxing = ViewHolder.get(convertView, R.id.huxing);
		ImageView attenBtn = ViewHolder.get(convertView, R.id.attention_button);
		tv = ViewHolder.get(convertView, R.id.text);
		
		
		LogUtil.d("listhouse in adapter",""+listhouse.get(position));
		hhl=listhouse.get(position);
		Double area1 = Double.parseDouble(hhl.getStrutsArea());
		Double price1 = Double.parseDouble(hhl.getAllPrice());
		int p1 = (int) (price1/area1);
		int a1 = (int) ((area1*1000)/1000);
		nameview.setText(hhl.getDong()+"号楼"+hhl.getUnit()+"单元"+hhl.getFloorString()+"层"+hhl.getCount()+"号房");
		content.setText(hhl.getConfig());
		area.setText("面积"+a1+"㎡");
		price.setText(p1+"元/平米");
		ob.setText("已有"+hhl.getPersonCount()+"人关注");
		huxing.setText(hhl.getHuxing()+"户型");
		tv.setText(hhl.getRoomId());
		if(hhl.getAttentioned().equals("false")){
			attenBtn.setImageResource(R.drawable.att2);
		}else attenBtn.setImageResource(R.drawable.att1);
		
		attenBtn.setOnClickListener(new MyonClickListener(position,attenBtn));
		
        InitPicture_setting.getImageLoader(hhl.getPhoto(), houseicon);
        houseicon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String htmlString=hhl.getHtml();
				Intent intent=new Intent(context,WebActivity.class);
				intent.putExtra("url", htmlString);
				intent.putExtra("title", "户型杂志");
				context.startActivity(intent);
				
			}
		});
		return convertView;
	}

}
