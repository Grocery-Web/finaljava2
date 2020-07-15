package entity;

import java.util.Date;

public class Victim extends Person {
	private Date deathTime;
	private String deathPlace;
	private String deathReason;
	private int complaintID;
	
	public Victim() {}
	
	public Victim(boolean status, Date deathTime, String deathPlace, String deathReason, int complaintID) {
		super();
		this.deathTime = deathTime;
		this.deathPlace = deathPlace;
		this.deathReason = deathReason;
		this.complaintID = complaintID;
	}
	
	public Date getDeathTime() {
		return deathTime;
	}
	
	public void setDeathTime(Date deathTime) {
		this.deathTime = deathTime;
	}
	
	public String getDeathPlace() {
		return deathPlace;
	}
	
	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}
	
	public String getDeathReason() {
		return deathReason;
	}
	
	public void setDeathReason(String deathReason) {
		this.deathReason = deathReason;
	}
	
	public int getComplaintID() {
		return complaintID;
	}
	
	public void setComplaintID(int complaintID) {
		this.complaintID = complaintID;
	}

	@Override
	public String toString() {
		return "Victim [personalID = " + this.getPersonalId() + ", life status = " + this.getAlive() + ", deathTime = " + deathTime + ", deathPlace = " + deathPlace + ", deathReason = "
				+ deathReason + ", complaintID = " + complaintID + "]";
	}
}
