package differentjavabean;

import java.io.Serializable;

public class PersonalInfoModel implements Serializable{
	private String nickName;// �û��ǳ�
	private String sex;// �û��Ա�
	private String introduce;// �û�������
	private String name;// �û���¼����
	private String age;// �û�����
	private String tel;// �û��绰
	private String addr;// �û���ַ
	private String member;// �û���ͥ��Ա����
	private String job;// �û�����
	private String income;// �û�������
	private String houseType;// �û�ϲ���Ļ���
	private String willPrice;// �û�����������λ

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
