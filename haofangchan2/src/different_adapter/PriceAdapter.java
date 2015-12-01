package different_adapter;

import java.util.ArrayList;

import com.example.haofangchan2.R;

import testandmanage.MyApplication;

import differentjavabean.PriceBean;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PriceAdapter extends BaseAdapter{
	ArrayList<PriceBean> list = new ArrayList<PriceBean>();
	public PriceAdapter(){
		this.list.add(new PriceBean("14:16","张三","¥73�"));
		this.list.add(new PriceBean("11:19","李四","¥56�"));
		this.list.add(new PriceBean("14:16","赵六","¥23�"));
		this.list.add(new PriceBean("14:16","王麻�","¥22�"));
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public PriceBean getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.pricelistitem, null);
		}
		TextView time = (TextView) convertView.findViewById(R.id.li_time);
		TextView name = (TextView) convertView.findViewById(R.id.li_name);
		TextView price = (TextView) convertView.findViewById(R.id.li_price);
		time.setText(list.get(position).getTime());
		name.setText(list.get(position).getName());
		price.setText(list.get(position).getPrice());
		return convertView;
	}

}
