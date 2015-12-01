package different_adapter;

import java.util.ArrayList;
import java.util.List;

import testandmanage.MyApplication;
import testandmanage.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.haofangchan2.R;

import differentjavabean.AttentionBean;
import differentjavabean.AttentionRoomModel;

public class AttentionAdater extends BaseAdapter {
	List<AttentionRoomModel> data = new ArrayList<AttentionRoomModel>();

	public AttentionAdater( List<AttentionRoomModel> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public AttentionRoomModel getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(MyApplication.getContext())
					.inflate(R.layout.attention_listitem, null);
		}
		TextView tv1 = ViewHolder.get(convertView,R.id.first_tv);
		tv1.setText(data.get(position).getRoomName());
		return convertView;
	}

}
