package entity;

import java.util.Date;

public class PrisonerInList {
	private String name;
	private int personID;
	private int prisonID;
	private Date dob;
	private Gender gender;
	private Date startDate;
	private Date endDate;
	private int duration;
	private String nationality;
	
	public PrisonerInList() {};
	
	public PrisonerInList(String name, int personID, int prisonID, Date dob, Gender gender, Date startDate,
			Date endDate, int duration, String nationality) {
		super();
		this.name = name;
		this.personID = personID;
		this.prisonID = prisonID;
		this.dob = dob;
		this.gender = gender;
		this.startDate = startDate;
		this.endDate = endDate;
		this.duration = duration;
		this.nationality = nationality;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public int getPrisonID() {
		return prisonID;
	}
	public void setPrisonID(int prisonID) {
		this.prisonID = prisonID;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public String toString() {
		return "PrisonerInList [name=" + name + ", personID=" + personID + ", prisonID=" + prisonID + ", dob=" + dob
				+ ", gender=" + gender + ", startDate=" + startDate + ", endDate=" + endDate + ", duration=" + duration
				+ ", nationality=" + nationality + "]";
	}	
	

}
