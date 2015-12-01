package different_adapter;

import java.util.ArrayList;
import java.util.List;

import load.LoginActivity;

import pictureconnectinit.InitPicture_setting;

import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import testandmanage.LogUtil;
import testandmanage.MyApplication;
import testandmanage.ViewHolder;

import differentjavabean.ActivityModel;
import differentjavabean.Activity_Indiana_javabean;
import differentjavabean.DuobaoBean;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class DuobaoAdapter extends BaseAdapter{
//	ArrayList<DuobaoBean> li = new ArrayList<DuobaoBean>();
    private ArrayList<ActivityModel> al1,al2,al3;
	private ArrayList<ArrayList<ActivityModel>> list;
	public DuobaoAdapter(ArrayList<ArrayList<ActivityModel>> list){
		LogUtil.d("再次传值", ""+list);
		 this.list=list;
		 al1 = list.get(0);
		 al2=list.get(1);
		 al3=list.get(2);
	}
	public int getCount() {	
		
		LogUtil.d("数量","al1:"+al1+"al2"+al2+"al3"+al3);
		int a=al1.size()+al2.size()+al3.size();
		LogUtil.d("数量","为"+a);
		return a;
	}

	@Override
	public ArrayList<ActivityModel> getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LogUtil.d("进入更新","更新开始点击项"+position);
		
		if(convertView==null){
			convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.duobaolistitem, null);

		}
		ImageView iv = (ImageView)ViewHolder.get(convertView,R.id.intr_iv); 
		TextView name = (TextView) ViewHolder.get(convertView,R.id.activity_name_tv);
		TextView content = (TextView) ViewHolder.get(convertView,R.id.activity_content_tv);
		TextView time = (TextView) ViewHolder.get(convertView,R.id.activity_time_tv);
		TextView timeinfo = (TextView) ViewHolder.get(convertView,R.id.timeinfo);
	

		ArrayList<ActivityModel> al1 = list.get(0),al2=list.get(1),al3=list.get(2);
		
		ArrayList<ActivityModel> al;
		if(position<al1.size()){
			al=al1;
			timeinfo.setTextColor(Color.rgb(255, 220, 0));
			timeinfo.setText("正在进行");
		}else if(position<al1.size()+al2.size()){
			al=al2;
			position=position-al1.size();
			timeinfo.setTextColor(Color.RED);
			timeinfo.setText("即将开始");
		}else{
			al=al3;
			position = position - al1.size() - al2.size(); 
			timeinfo.setText("已结束");
			name.setTextColor(Color.GRAY);
			timeinfo.setTextColor(Color.GRAY);
			content.setTextColor(Color.GRAY);
			time.setTextColor(Color.GRAY);
		}

		name.setText(al.get(position).getName()+al.get(position).getActivityId());
		content.setText(al.get(position).getIntroduction());
		
		//设置活动时间文本
		time.setText("活动时间:"+
				al.get(position).getTime());
		InitPicture_setting.getImageLoader(al.get(position).getActivityImage(), iv);
		
		return convertView;
	}

}
