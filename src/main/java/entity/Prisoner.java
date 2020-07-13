package entity;

import java.util.Date;

public class Prisoner extends Person {
	private int prisonerId;
	private int startDate;
	private int prisonId;
	private boolean releasedStatus;
	private int duration;
	private int criminalId;
	
	public Prisoner() {}

	public Prisoner(int personalId, String name, Gender gender, Date dob, String address, String image,
			String nationality, String job, Boolean alive) {
		super(personalId, name, gender, dob, address, image, nationality, job, alive);
		// TODO Auto-generated constructor stub
	}

	public Prisoner(int prisonerId, int startDate, int prisonId, boolean releasedStatus, int duration, int criminalId) {
		super();
		this.prisonerId = prisonerId;
		this.startDate = startDate;
		this.prisonId = prisonId;
		this.releasedStatus = releasedStatus;
		this.duration = duration;
		this.criminalId = criminalId;
	}

	public int getPrisonerId() {
		return prisonerId;
	}

	public void setPrisonerId(int prisonerId) {
		this.prisonerId = prisonerId;
	}

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getPrisonId() {
		return prisonId;
	}

	public void setPrisonId(int prisonId) {
		this.prisonId = prisonId;
	}

	public boolean isReleasedStatus() {
		return releasedStatus;
	}

	public void setReleasedStatus(boolean releasedStatus) {
		this.releasedStatus = releasedStatus;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCriminalId() {
		return criminalId;
	}

	public void setCriminalId(int criminalId) {
		this.criminalId = criminalId;
	}

	@Override
	public String toString() {
		return "Prisoner [prisonerId=" + prisonerId + ", startDate=" + startDate + ", prisonId=" + prisonId
				+ ", releasedStatus=" + releasedStatus + ", duration=" + duration + ", criminalId=" + criminalId + "]";
	}
	
	
}
