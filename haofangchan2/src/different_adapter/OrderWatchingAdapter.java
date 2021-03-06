package different_adapter;

import java.util.ArrayList;

import com.example.haofangchan2.R;

import testandmanage.MyApplication;
import testandmanage.ViewHolder;

import differentjavabean.OrderWatchingItemModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderWatchingAdapter extends BaseAdapter{
	ArrayList<OrderWatchingItemModel> list;
	public OrderWatchingAdapter(ArrayList<OrderWatchingItemModel> list){
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public OrderWatchingItemModel getItem(int position) {
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
			convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.order_watching2_listitem, null);
		}
		TextView houseName = ViewHolder.get(convertView, R.id.buildinginfo_tv);
		TextView houseInfo = ViewHolder.get(convertView, R.id.specificinfo_tv);
		TextView date = ViewHolder.get(convertView, R.id.orderwatching_date);
		OrderWatchingItemModel model = list.get(position);
		houseName.setText(model.getHouseName());
		houseInfo.setText(model.getHouseNameData());
		date.setText(model.getVisitDate());
		return convertView;
	}

}
