package different_adapter;

import java.util.ArrayList;

import com.example.haofangchan2.R;

import testandmanage.MyApplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoListAdapter extends BaseAdapter{
	ArrayList<String> list=new ArrayList<String>();
	public InfoListAdapter(ArrayList<String> list){
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public String getItem(int position) {
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
			convertView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.infolistitem, null);
			TextView tv = (TextView) convertView.findViewById(R.id.tv);
			tv.setText(list.get(position));
		}
		return convertView;
	}

}
