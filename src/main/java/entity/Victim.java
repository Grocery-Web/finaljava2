package entity;

import java.util.Date;

public class Victim extends Person {
	private Date deathTime;
	private String deathPlace;
	private String deathReason;
	private int complaintID;
	private boolean status;
	private String incidentName;
	
	public Victim() {}
	
	public Victim(Date deathTime, String deathPlace, String deathReason, int complaintID, boolean status,
			String incidentName) {
		super();
		this.deathTime = deathTime;
		this.deathPlace = deathPlace;
		this.deathReason = deathReason;
		this.complaintID = complaintID;
		this.status = status;
		this.incidentName = incidentName;
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
	
	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Victim [deathTime=" + deathTime + ", deathPlace=" + deathPlace + ", deathReason=" + deathReason
				+ ", complaintID=" + complaintID + ", status=" + status + ", incidentName=" + incidentName + "]";
	}
}
