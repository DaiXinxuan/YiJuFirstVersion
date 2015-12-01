package different_adapter;

import java.util.ArrayList;
import java.util.List;

import testandmanage.MyApplication;
import com.example.haofangchan2.R;
import differentjavabean.MyMessageItemBean;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyMessageAdapter extends BaseAdapter{
	private List<MyMessageItemBean> list=new ArrayList<MyMessageItemBean>();
	public MyMessageAdapter(List<MyMessageItemBean> list) {
		// TODO Auto-generated constructor stub
		this.list=list;
		this.list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
		this.list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
		this.list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
		this.list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
		this.list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
	}
	public MyMessageAdapter() {
		// TODO Auto-generated constructor stub
		list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
		list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
		list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
		list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
		list.add(new MyMessageItemBean("小鹅鹅","今天天气很好，哈哈，大家好啊，这个房子真不错，可惜我没钱，啊哈哈","11:23","img"));
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
		int hour = t.hour;
		int minute = t.minute;
		list.add(0, new MyMessageItemBean("小鹅鹅",s,hour+":"+minute,"img"));
	}
	TextView name,text,date;
	ImageView image;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null){
			convertView = LayoutInflater.from(MyApplication.getContext()).
					inflate(R.layout.mymessagelistitem, null);
		}
		name=(TextView)convertView.findViewById(R.id.mymessage_item_name);
		text=(TextView)convertView.findViewById(R.id.mymessage_item_detail);
		date=(TextView)convertView.findViewById(R.id.mymessage_item_time);
		image=(ImageView) convertView.findViewById(R.id.mymessage_item_img);
		image.setImageResource(R.drawable.profile_image);
		name.setText(list.get(position).getName());
		text.setText(list.get(position).getDetail());
		date.setText(list.get(position).getTime());
		return convertView;
	}

}
