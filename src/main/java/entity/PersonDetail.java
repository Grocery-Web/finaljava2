package entity;

import java.util.Date;

public class PersonDetail {
	private int id;
	private String name;
	private boolean gender;
	private Date dob;
	private String address;
	private String img;
	private String national;
	private String job;
	private String blood;
	private int height;
	private String note;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getNational() {
		return national;
	}
	public void setNational(String national) {
		this.national = national;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getBlood() {
		return blood;
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public PersonDetail() {}
	
	public PersonDetail(int id, String name, boolean gender, Date dob, String address, String img, String national,
			String job, String blood, int height, String note) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.img = img;
		this.national = national;
		this.job = job;
		this.blood = blood;
		this.height = height;
		this.note = note;
	}
	@Override
	public String toString() {
		return "PersonDetail [id=" + id + ", name=" + name + ", gender=" + gender + ", dob=" + dob + ", address="
				+ address + ", img=" + img + ", national=" + national + ", job=" + job + ", blood=" + blood
				+ ", height=" + height + ", note=" + note + "]";
	}

}
