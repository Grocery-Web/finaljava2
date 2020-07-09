package entity;

import java.util.Date;

public class Criminal extends Person{
	private int criminalId;
	private int personalId;
	private int complainttId;
	private String punishment;
	private int rating;
	
	public Criminal() {}

	public Criminal(int criminalId, int personalId, int complainttId, String punishment, int rating) {
		super();
		this.criminalId = criminalId;
		this.personalId = personalId;
		this.complainttId = complainttId;
		this.punishment = punishment;
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

	public int getComplainttId() {
		return complainttId;
	}

	public void setComplainttId(int complainttId) {
		this.complainttId = complainttId;
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

	@Override
	public String toString() {
		return "Criminal [criminalId=" + criminalId + ", personalId=" + personalId + ", complainttId=" + complainttId
				+ ", punishment=" + punishment + ", rating=" + rating + "]";
	}

}
