package differentjavabean;

public class CalculateBean {
	private int count;
	private double month_money,month_benjin,month_interest,rest_benjin,advance_return;
	public CalculateBean(int count,double b1,double b2,double b3,double b4,double b5){
		this.count=count;
		month_money=b1;
		month_benjin=b2;
		month_interest=b3;
		rest_benjin=b4;
		advance_return = b5;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getMonth_money() {
		return month_money;
	}
	public void setMonth_money(double month_money) {
		this.month_money = month_money;
	}
	public double getMonth_benjin() {
		return month_benjin;
	}
	public void setMonth_benjin(double month_benjin) {
		this.month_benjin = month_benjin;
	}
	public double getMonth_interest() {
		return month_interest;
	}
	public void setMonth_interest(double month_interest) {
		this.month_interest = month_interest;
	}
	public double getRest_benjin() {
		return rest_benjin;
	}
	public void setRest_benjin(double rest_benjin) {
		this.rest_benjin = rest_benjin;
	}
	public double getAdvance_return() {
		return advance_return;
	}
	public void setAdvance_return(double advance_return) {
		this.advance_return = advance_return;
	}
}
