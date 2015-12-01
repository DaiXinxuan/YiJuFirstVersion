package different_adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import main.HouseinfoActivity;
import pictureconnectinit.InitPicture_setting;
import testandmanage.MyApplication;
import testandmanage.ViewHolder;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import differentjavabean.NormalHouseListViewModel;

public class Main_seehouse_Adapter extends BaseAdapter {

	private List<NormalHouseListViewModel> list = new ArrayList<NormalHouseListViewModel>();
	private NormalHouseListViewModel data;
	public Main_seehouse_Adapter(List<NormalHouseListViewModel> list) {
		this.list = list;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public NormalHouseListViewModel getItem(int position) {
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
		if (convertView == null) {
			convertView = LayoutInflater.from(MyApplication.getContext())
					.inflate(R.layout.watchhouseitem, null);

		}
		LinearLayout all = (LinearLayout) ViewHolder.get(convertView,R.id.houseinfoonclick);
		ImageView iv = (ImageView)  ViewHolder.get(convertView,R.id.house_view);
		TextView title = (TextView)  ViewHolder.get(convertView,R.id.title);
		TextView top = (TextView)  ViewHolder.get(convertView,R.id.top);
		TextView shi = (TextView) ViewHolder.get(convertView,R.id.shitingwei);
		TextView area = (TextView)  ViewHolder.get(convertView,R.id.area);
		TextView decoration = (TextView)  ViewHolder.get(convertView,R.id.decorrate);
		TextView sendarea = (TextView)  ViewHolder.get(convertView,R.id.sendarea);
		TextView floor = (TextView)  ViewHolder.get(convertView,R.id.floorth);
		TextView direction = (TextView)  ViewHolder.get(convertView,R.id.direction);
		TextView comment = (TextView) ViewHolder.get(convertView, R.id.comment);

		data = list.get(position);
		all.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent houseinfo = new Intent(MyApplication.getContext(),
						HouseinfoActivity.class);
				houseinfo.putExtra("houseid", data.getRoomId());
				houseinfo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				MyApplication.getContext().startActivity(houseinfo);
			}
		});
		
		InitPicture_setting.getImageLoader(data.getTypePhotos().get(0), iv);

		title.setText(data.getRoomNum());
		top.setText("Top" + (position + 1));
		shi.setText(data.getConfig());
		area.setText("套内面积" + data.getAllArea() + "㎡");
		if (data.getDecoration().equals("0"))
			decoration.setText("未装修");
		else
			decoration.setText("已装修");
		sendarea.setText("赠送面积:" + data.getGiveArea() + "㎡");
		floor.setText("楼层：" + data.getFloor() + "层");
		direction.setText("朝向："+data.getForward());
		DecimalFormat df = new DecimalFormat( "0.00 "); 
		Double price = Double.parseDouble(data.getRoomPrice())/10000;
		comment.setText("房屋总价："+df.format(price)+"万");

		return convertView;
	}

}
