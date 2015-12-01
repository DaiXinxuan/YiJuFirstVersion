package different_adapter;

import java.util.ArrayList;
import java.util.List;

import testandmanage.MyApplication;

import com.example.haofangchan2.R;

import differentjavabean.ConsultcommentDataBean;
import differentjavabean.ProjectItemBean;

import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProjectItemAdapter extends BaseAdapter {
	private List<ProjectItemBean> list=new ArrayList<ProjectItemBean>();
	public ProjectItemAdapter(List<ProjectItemBean> list) {
		this.list = list;
	}
	public ProjectItemAdapter() {
		list.add(new ProjectItemBean("i am the title", "i am the content,but my exist is meaningless,eventually i will be replace.I hope you will still remember me.i am a content,in perpetuity.", "img"));
		list.add(new ProjectItemBean("i am the title", "i am the content,but my exist is meaningless,eventually i will be replace.I hope you will still remember me.i am a content,in perpetuity.", "img"));
		list.add(new ProjectItemBean("i am the title", "i am the content,but my exist is meaningless,eventually i will be replace.I hope you will still remember me.i am a content,in perpetuity.", "img"));
		list.add(new ProjectItemBean("i am the title", "i am the content,but my exist is meaningless,eventually i will be replace.I hope you will still remember me.i am a content,in perpetuity.", "img"));
		list.add(new ProjectItemBean("i am the title", "i am the content,but my exist is meaningless,eventually i will be replace.I hope you will still remember me.i am a content,in perpetuity.", "img"));
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
	public void addItem(String title,String text,String img){
		list.add(new ProjectItemBean(title,text,img));
	}
	TextView title,text;
	ImageView image;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null){
			convertView = LayoutInflater.from(MyApplication.getContext()).
					inflate(R.layout.projectlistitem, null);
		}
		title=(TextView)convertView.findViewById(R.id.projectitem_title);
		text=(TextView)convertView.findViewById(R.id.projectitem_text);
		image=(ImageView) convertView.findViewById(R.id.projectitem_img);
		image.setImageResource(R.drawable.profile_image);
		text.setText(list.get(position).getText());
		title.setText(list.get(position).getTitle());
		return convertView;
	}

}
