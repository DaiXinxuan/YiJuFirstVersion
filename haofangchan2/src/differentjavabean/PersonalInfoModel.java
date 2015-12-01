package differentjavabean;

import java.io.Serializable;

public class PersonalInfoModel implements Serializable{
	private String nickName;// 用户昵称
	private String sex;// 用户性别
	private String introduce;// 用户年收入
	private String name;// 用户登录名称
	private String age;// 用户年龄
	private String tel;// 用户电话
	private String addr;// 用户地址
	private String member;// 用户家庭成员数量
	private String job;// 用户工作
	private String income;// 用户年收入
	private String houseType;// 用户喜欢的户型
	private String willPrice;// 用户购买的心理价位

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getWillPrice() {
		return willPrice;
	}

	public void setWillPrice(String willPrice) {
		this.willPrice = willPrice;
	}

}
