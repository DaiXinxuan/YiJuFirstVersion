package different_adapter;

import java.util.List;

import com.example.haofangchan2.R;

import differentjavabean.reserve_watchHousejavabean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class reserveAdapter extends BaseAdapter{

	private List<reserve_watchHousejavabean> list = null;
	private LayoutInflater minflater = null;
	
	public reserveAdapter(Context context ,List<reserve_watchHousejavabean> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.minflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
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
		reserve_watchHousejavabean data = list.get(position);
		viewHolder mHolder = null;
		if(convertView == null){
			convertView = minflater.inflate(R.layout.reserve_item, null);
			mHolder.code = (TextView) convertView.findViewById(R.id.reserve_watchHouse_code);
			mHolder.date = (TextView) convertView.findViewById(R.id.reserve_watchHouse_date);
			convertView.setTag(mHolder);
		}
		else
			mHolder = (viewHolder) convertView.getTag();
		mHolder.code.setText(data.getHouseName()+data.getHouseNameData());
		mHolder.date.setText(data.getVisitDate());
		return convertView;
	}

	public class viewHolder{
		
		TextView code;
		TextView date;
	}
	
}
