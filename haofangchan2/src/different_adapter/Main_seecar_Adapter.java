package different_adapter;

import java.util.List;

import testandmanage.MyApplication;
import testandmanage.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haofangchan2.R;

import differentjavabean.NormalCarListViewModel;

public class Main_seecar_Adapter extends BaseAdapter {
	private List<NormalCarListViewModel> list;

	public Main_seecar_Adapter(List<NormalCarListViewModel> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public NormalCarListViewModel getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	private TextView tvid, tvarea, tvscale, tvinfo, tvprice, tvstatus;
	private ImageView iv;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(MyApplication.getContext())
					.inflate(R.layout.carlistitem, null);

		}
		iv=(ImageView)ViewHolder.get(convertView, R.id.house_image);
		tvid = (TextView) ViewHolder.get(convertView, R.id.house_id);
		tvarea = (TextView) ViewHolder.get(convertView, R.id.house_area);
		tvscale = (TextView) ViewHolder.get(convertView, R.id.house_scale);
		tvinfo = (TextView) ViewHolder.get(convertView, R.id.house_info);
		tvprice = (TextView)ViewHolder.get(convertView, R.id.house_price);
		tvstatus = (TextView) ViewHolder.get(convertView, R.id.house_status);


		tvid.setText("编号(ID)：" + list.get(position).getNum());
		tvarea.setText("面积:" + list.get(position).getArea());
		tvscale.setText("尺寸:" + list.get(position).getSize());
		tvinfo.setText("说明:" + list.get(position).getStatement());
		tvprice.setText("价格:" + list.get(position).getBasePrice());
		tvstatus.setText("状态:" + list.get(position).getSaleState());
		return convertView;
	}
}
