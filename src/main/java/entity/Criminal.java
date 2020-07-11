package entity;

import java.util.Date;

public class Criminal extends Person{
	private int criminalId;
	private int personalId;
	private int complaintId;
	private String punishment;
	private Date appliedDate;
	private int rating;
	
	public Criminal() {}

	public Criminal(int criminalId, int personalId, int complaintId, String punishment, Date appliedDate, int rating) {
		super();
		this.criminalId = criminalId;
		this.personalId = personalId;
		this.complaintId = complaintId;
		this.punishment = punishment;
		this.appliedDate = appliedDate;
		this.rating = rating;
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

	@Override
	public String toString() {
		return "Criminal [criminalId=" + criminalId + ", personalId=" + personalId + ", complaintId=" + complaintId
				+ ", punishment=" + punishment + ", appliedDate=" + appliedDate + ", rating=" + rating + "]";
	}

}
