package main;

import com.example.haofangchan2.R;

import different_adapter.CalculateAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CalculateResultActivity extends Activity{
	//显示数据的Textview
	private TextView loan,return_interest,return_money,return_peroid,month_return;
	//用于布局的TextView
	private TextView tv1,tv2,tv3,tv4,tv5,tv6;
	private ListView lv;
	private int count,firstpay,return_method,loan_method;
	//总房价
	private double price=500000;
	//利率
	private double lilv=0.00565500;
	private int screenW;
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.calculateresult);
        //获取屏幕分辨率宽度
        DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenW = dm.widthPixels;
        Intent i = getIntent();
        price = i.getDoubleExtra("price", 500000);
        count = i.getExtras().getInt("peroid")+1;
        firstpay = i.getExtras().getInt("firstpay")+2;
        loan_method = i.getExtras().getInt("rg1");
        return_method = i.getExtras().getInt("rg2");
        lilv = Double.parseDouble(i.getStringExtra("interestrate"));
        findView();
        double uLoan = price*(1-firstpay*0.1);
        if(uLoan%10000!=0){
        	double yu = uLoan%10000;
        	uLoan = uLoan+(10000-yu);
        }
        loan.setText(uLoan+"");
        return_peroid.setText(count*12+"期");
        if(return_method==R.id.equal_benjin){
        	CalculateAdapter c = new CalculateAdapter(lilv,count*12,screenW,1,uLoan);
        	lv.setAdapter(c);
        	return_interest.setText(c.getSumReturnRate()+"");
        	return_money.setText(c.getSumReturnBenjin()+"");
        	month_return.setText(c.getSumReturnMoney()+"");
        }else {
        	CalculateAdapter c = new CalculateAdapter(lilv,count*12,screenW,2,uLoan);
        	lv.setAdapter(c);
        	return_interest.setText(c.getSumReturnRate()+"");
        	return_money.setText(c.getSumReturnBenjin()+"");
        	month_return.setText(c.getSumReturnMoney()+"");
        }
	}
	public void findView(){
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CalculateResultActivity.this.finish();
			}
		});
		loan = (TextView) findViewById(R.id.loan_amounttv);
		return_interest = (TextView) findViewById(R.id.return_interesttv);
		return_money = (TextView) findViewById(R.id.return_moneytv);
		return_peroid = (TextView) findViewById(R.id.return_peroidtv);
		month_return = (TextView) findViewById(R.id.money_permonthtv);
		lv = (ListView) findViewById(R.id.calculate_list);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		LinearLayout.LayoutParams lp = (LayoutParams) tv1.getLayoutParams();
		lp.width=(int) ((screenW/6)*(0.4));
		tv1.setLayoutParams(lp);
		LinearLayout.LayoutParams lp1 = (LayoutParams) tv2.getLayoutParams();
		lp1.width=screenW/6;
		tv2.setLayoutParams(lp1);
		tv3.setLayoutParams(lp1);
		LinearLayout.LayoutParams lp2 = (LayoutParams) tv4.getLayoutParams();
		lp2.width=(int) ((screenW/6)*(1.2));
		tv4.setLayoutParams(lp2);
		tv5.setLayoutParams(lp2);
		tv6.setLayoutParams(lp2);
	}
}
