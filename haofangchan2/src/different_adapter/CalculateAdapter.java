package different_adapter;

import java.text.DecimalFormat;

import testandmanage.MyApplication;
import differentjavabean.CalculateBean;
import android.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class CalculateAdapter extends BaseAdapter{
	private CalculateBean array[];
	private int width;
	private double lilv=0.005655;
	//使数据保留两位小数
	DecimalFormat df = new DecimalFormat("######0.00");
	public CalculateAdapter(double lilv,int num,int width,int method,double loan){
		this.lilv = lilv;
		this.width=width;
		array = new CalculateBean[num];
		if(method==1){
			double month_benjin=loan/num;
			for(int i=0;i<num;i++){
				if(i==0){
					array[i] = new CalculateBean(i+1, Double.parseDouble(df.format(month_benjin+loan*lilv)), 
							Double.parseDouble(df.format(month_benjin)), 
							Double.parseDouble(df.format(loan*lilv)),
							Double.parseDouble(df.format(loan-(i+1)*(month_benjin))), 
							Double.parseDouble(df.format(loan+loan*lilv)));
				}else{
					array[i] = new CalculateBean(i+1, Double.parseDouble(df.format(month_benjin+array[i-1].getRest_benjin()*lilv)),
							Double.parseDouble(df.format(month_benjin)), 
							Double.parseDouble(df.format(lilv*array[i-1].getRest_benjin())), 
							Double.parseDouble(df.format(array[i-1].getRest_benjin()-month_benjin)),
							Double.parseDouble(df.format(lilv*array[i-1].getRest_benjin()+array[i-1].getRest_benjin())));
				}
			}
		}else{
			for(int i=0;i<num;i++){
				double month_return=loan*lilv*Math.pow(1+lilv, num)/(Math.pow(1+lilv, num)-1);
				if(i==0){
					array[i] = new CalculateBean(i+1, Double.parseDouble(df.format(month_return)), 
							Double.parseDouble(df.format(month_return-loan*lilv)), 
							Double.parseDouble(df.format(loan*lilv)),
							Double.parseDouble(df.format((Math.pow(1+lilv, num)-Math.pow(1+lilv, i+1))/(Math.pow(1+lilv, num)-1)*loan)), 
							Double.parseDouble(df.format(loan+loan*lilv)));
				}else{
					array[i] = new CalculateBean(i+1, Double.parseDouble(df.format(month_return)),
							Double.parseDouble(df.format(month_return-lilv*array[i-1].getRest_benjin())), 
							Double.parseDouble(df.format(lilv*array[i-1].getRest_benjin())), 
							Double.parseDouble(df.format((Math.pow(1+lilv, num)-Math.pow(1+lilv, i+1))/(Math.pow(1+lilv, num)-1)*loan)),
							Double.parseDouble(df.format(lilv*array[i-1].getRest_benjin()+array[i-1].getRest_benjin())));
				}
			}
		}
	}
	public double getSumReturnRate(){
		double sum=0;
		for(int i=0;i<array.length;i++){
			sum=sum+array[i].getMonth_interest();
		}
		return Double.parseDouble(df.format(sum));
	}
	public double getSumReturnBenjin(){
		double sum=0;
		for(int i=0;i<array.length;i++){
			sum=sum+array[i].getMonth_benjin();
		}
		return Double.parseDouble(df.format(sum));
	}
	public double getSumReturnMoney(){
		double sum=0;
		for(int i=0;i<array.length;i++){
			sum=sum+array[i].getMonth_money();
		}
		return Double.parseDouble(df.format(sum));
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.length;
	}

	@Override
	public CalculateBean getItem(int position) {
		// TODO Auto-generated method stub
		return array[position];
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
			convertView = LayoutInflater.from(MyApplication.getContext()).inflate(com.example.haofangchan2.R.layout.calculate_listitem, null);
		}
		TextView count = (TextView) convertView.findViewById(com.example.haofangchan2.R.id.num);
		TextView month_pay = (TextView) convertView.findViewById(com.example.haofangchan2.R.id.month_pay);
		TextView month_benjin = (TextView) convertView.findViewById(com.example.haofangchan2.R.id.month_money);
		TextView month_interest = (TextView) convertView.findViewById(com.example.haofangchan2.R.id.month_interest);
		TextView rest_money = (TextView) convertView.findViewById(com.example.haofangchan2.R.id.rest_money);
		TextView advance_return = (TextView) convertView.findViewById(com.example.haofangchan2.R.id.advance_return);
		LinearLayout.LayoutParams lp = (LayoutParams) count.getLayoutParams();
		lp.width=(int) ((width/6)*(0.4));
		count.setLayoutParams(lp);
		LinearLayout.LayoutParams lp1 = (LayoutParams) month_pay.getLayoutParams();
		lp1.width=width/6;
		month_pay.setLayoutParams(lp1);
		month_benjin.setLayoutParams(lp1);
		LinearLayout.LayoutParams lp2 = (LayoutParams) advance_return.getLayoutParams();
		lp2.width=(int) ((width/6)*(1.2));
		month_interest.setLayoutParams(lp2);
		rest_money.setLayoutParams(lp2);
		advance_return.setLayoutParams(lp2);
		count.setText(array[position].getCount()+"");
		month_pay.setText(array[position].getMonth_money()+"");
		month_benjin.setText(array[position].getMonth_benjin()+"");
		month_interest.setText(array[position].getMonth_interest()+"");
		rest_money.setText(array[position].getRest_benjin()+"");
		advance_return.setText(array[position].getAdvance_return()+"");
		return convertView;
	}

}
