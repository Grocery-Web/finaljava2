package entity;

import java.util.Date;

public class Person {
	private int personalId;
	private String name;
	private Gender gender;
	private Date dob;
	private String address;
	private String image;
	private String nationality;
	private String job;
	private Boolean alive;
	
	public Person() {
	}

	public Person(int personalId, String name, Gender gender, Date dob, String address, String image, String nationality,
			String job, Boolean alive) {
		this.personalId = personalId;
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.image = image;
		this.nationality = nationality;
		this.job = job;
		this.alive = alive;
	}

	public int getPersonalId() {
		return personalId;
	}

	public void setPersonalId(int personalId) {
		this.personalId = personalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public Boolean getAlive() {
		return alive;
	}

	public void setAlive(Boolean alive) {
		this.alive = alive;
	}

	@Override
	public String toString() {
		return "Person [personalId=" + personalId + ", name=" + name + ", gender=" + gender + ", dob=" + dob
				+ ", address=" + address + ", image=" + image + ", nationality=" + nationality + ", job=" + job
				+ ", alive=" + alive + "]";
	}

}
