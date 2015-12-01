package different_adapter;



import com.example.haofangchan2.R;

import testandmanage.MyApplication;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingAdapter extends BaseAdapter{
	private int arr[]={
			R.drawable.set02,
			R.drawable.set09,
			R.drawable.set11,
			//R.drawable.set13,
			R.drawable.set17
		};
//	public SettingAdapter(Drawable d1,Drawable d2){
//		this.d1 = d1;
//		this.d2 = d2;
//	}
	private Drawable d1 ,d2;
    private String str[]={
				"修改密码",
				"清除缓存",
				"用户反馈",
				//"检查更新",
				"关于我们"
		};
		//列表项数目的静态常量
	private static int LIST_ITEM_COUNT=4;
	LayoutInflater mInflater=LayoutInflater.from(MyApplication.getContext());
	public int getCount() {
		return LIST_ITEM_COUNT;
	}

	public Object getItem(int position) {
		return str[position];
	}

	public long getItemId(int position) {
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		//引入布局
		if(convertView==null){
		convertView=mInflater.inflate(R.layout.setting_item, null);	}
		ImageView tubiao=(ImageView) convertView.findViewById(R.id.img1);
		final ImageView Goto=(ImageView) convertView.findViewById(R.id.img2);
		TextView txt=(TextView) convertView.findViewById(R.id.txt);
		tubiao.setImageResource(arr[position]);
		Goto.setImageResource(R.drawable.garynextstep2x);
		txt.setText(str[position]);
		return convertView;
	}
 
}
