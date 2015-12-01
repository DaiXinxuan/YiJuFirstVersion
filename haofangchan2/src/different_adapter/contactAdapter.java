package different_adapter;

import java.util.List;

import pictureconnectinit.InitPicture_setting;
import testandmanage.ViewHolder;
import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.haofangchan2.R;

import de.hdodenhof.circleimageview.CircleImageView;
import differentjavabean.MyfriendActivity_javabean;

public class contactAdapter extends BaseAdapter {

	private List<MyfriendActivity_javabean> contacts = null;
	private LayoutInflater mInflater = null;

	public contactAdapter(Context context,
			List<MyfriendActivity_javabean> contacts) {
		// TODO Auto-generated constructor stub
		this.contacts = contacts;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contacts.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return contacts.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private CircleImageView childPhotoCircleImageView;
	private TextView childNameTextView, detailTextView;

	public View getView(int position, View convertView, ViewGroup parent) {

		MyfriendActivity_javabean contact = contacts.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.myfriendchilditem, null);
		}

		detailTextView = ViewHolder.get(convertView, R.id.myfriend_item_detail);
		detailTextView.setVisibility(View.GONE);
		childPhotoCircleImageView = ViewHolder.get(convertView,
				R.id.myfriend_item_img);
		childNameTextView = ViewHolder
				.get(convertView, R.id.myfriend_item_name);

		InitPicture_setting.getImageLoader(contact.getHeadphotopath(),
				childPhotoCircleImageView);
		if (contact.getIsVip() == 1) {
			childNameTextView.setTextColor(Color.RED);
		}
		childNameTextView.setText("" + contact.getNickname());

		return convertView;
	}

}
