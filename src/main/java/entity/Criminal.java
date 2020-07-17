package entity;

import java.util.Date;

public class Criminal extends Person{
	private int criminalId;
	private int personalId;
	private int complaintId;
	private String punishment;
	private Date appliedDate;
	private String hisOfViolent;
	private int rating;
	private String complaintName;
	
	public Criminal() {}

	public Criminal(int criminalId, int personalId, int complaintId, String punishment, Date appliedDate,
			String hisOfViolent, int rating, String complaintName) {
		super();
		this.criminalId = criminalId;
		this.personalId = personalId;
		this.complaintId = complaintId;
		this.punishment = punishment;
		this.appliedDate = appliedDate;
		this.hisOfViolent = hisOfViolent;
		this.rating = rating;
		this.complaintName = complaintName;
	}

	public int getCriminalId() {
		return criminalId;
	}

	public void setCriminalId(int criminalId) {
		this.criminalId = criminalId;
	}

	public int getPersonalId() {
		return personalId;
	}

	public void setPersonalId(int personalId) {
		this.personalId = personalId;
	}

	public int getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}

	public String getPunishment() {
		return punishment;
	}

	public void setPunishment(String punishment) {
		this.punishment = punishment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
	
	public String getHisOfViolent() {
		return hisOfViolent;
	}

	public void setHisOfViolent(String hisOfViolent) {
		this.hisOfViolent = hisOfViolent;
	}
	
	public String getComplaintName() {
		return complaintName;
	}

	public void setComplaintName(String complaintName) {
		this.complaintName = complaintName;
	}

	@Override
	public String toString() {
		return "Criminal [criminalId=" + criminalId + ", personalId=" + personalId + ", complaintId=" + complaintId
				+ ", punishment=" + punishment + ", appliedDate=" + appliedDate + ", hisOfViolent=" + hisOfViolent
				+ ", rating=" + rating + ", complaintName=" + complaintName + "]";
	}
}
