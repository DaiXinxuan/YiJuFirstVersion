package different_adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import load.LoginActivity;
import pictureconnectinit.InitPicture_setting;

import testandmanage.MyApplication;
import android.R.integer;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import differentjavabean.ConsultcommentDataBean;

public class ConsultcommentAdapter extends BaseAdapter{
	private List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	public ConsultcommentAdapter(List<Map<String,String>> list) {
		// TODO Auto-generated constructor stub
		this.list=list;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public void addItem(String s){
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间。
		int month = t.month+1;
		int date = t.monthDay;
		/*list.add(0, new ConsultcommentDataBean("小鹅鹅",s,"image",month+"月"+date+"日","rank"));*/
	}
	TextView name,text,date;
	ImageView image;
	TextView rank;
	String level;
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null){
			convertView = LayoutInflater.from(MyApplication.getContext()).
					inflate(R.layout.consultcommentlistitem, null);
		}
		name=(TextView)convertView.findViewById(R.id.consultcomment_username);
		text=(TextView)convertView.findViewById(R.id.consultcomment_text);
		date=(TextView)convertView.findViewById(R.id.consultcomment_date);
		image=(ImageView) convertView.findViewById(R.id.consultcomment_image);
		rank=(TextView) convertView.findViewById(R.id.consultcomment_rank);
		 
		Map<String ,String> map=new HashMap<String,String>();
		map=list.get(position);
		//初始化图片设置，并且设置控件图片
	    InitPicture_setting initPicture_setting=new InitPicture_setting();
           initPicture_setting.init();
          
		   ImageLoader.getInstance().displayImage(LoginActivity.base_url_picture+
				map.get("headphoto"), 
				   image);
	
		name.setText(map.get("nickname"));
		text.setText(map.get("content"));
		date.setText(map.get("date"));
		String level="";
		int levelJudge=Integer.parseInt(map.get("level"));
		switch (levelJudge) {
		case 0:
			level="差评";
			break;
		case 1:
			level="中评";
			break;
		case 2:
			level="好评";
			
			break;

		default:
			break;
		}
		rank.setText(level);
		return convertView;
	}

}
