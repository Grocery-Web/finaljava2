package entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public class Prisoner extends Criminal {
	private int prisonerId;
	private int criminalId;
	private int prisonId;
	private String type;
	private Date startDate;
	private int duration;
	private Date endDate;
	private boolean releasedStatus;
	
	public Prisoner() {}


	
	public Prisoner(int criminalId, int personalId, int complaintId, String punishment, Date appliedDate,
			String hisOfViolent, int rating) {
		super(criminalId, personalId, complaintId, punishment, appliedDate, hisOfViolent, rating);
	}

	public Prisoner(int criminalId, int prisonId, String type, Date startDate, int duration,
			Date endDate, boolean releasedStatus) {
		super();
		this.criminalId = criminalId;
		this.prisonId = prisonId;
		this.type = type;
		this.startDate = startDate;
		this.duration = duration;
		this.endDate = endDate;
		this.releasedStatus = releasedStatus;
	}

	public Prisoner(int prisonerId, int criminalId, int prisonId, String type, Date startDate, int duration,
			Date endDate, boolean releasedStatus) {
		super();
		this.prisonerId = prisonerId;
		this.criminalId = criminalId;
		this.prisonId = prisonId;
		this.type = type;
		this.startDate = startDate;
		this.duration = duration;
		this.endDate = endDate;
		this.releasedStatus = releasedStatus;
	}

	public int getPrisonerId() {
		return prisonerId;
	}

	public void setPrisonerId(int prisonerId) {
		this.prisonerId = prisonerId;
	}

	public int getCriminalId() {
		return criminalId;
	}

	public void setCriminalId(int criminalId) {
		this.criminalId = criminalId;
	}

	public int getPrisonId() {
		return prisonId;
	}

	public void setPrisonId(int prisonId) {
		this.prisonId = prisonId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isReleasedStatus() {
		return releasedStatus;
	}

	public void setReleasedStatus(boolean releasedStatus) {
		this.releasedStatus = releasedStatus;
	}

	@Override
	public String toString() {
		return "Prisoner [prisonerId=" + prisonerId + ", criminalId=" + criminalId + ", prisonId=" + prisonId
				+ ", type=" + type + ", startDate=" + startDate + ", duration=" + duration + ", endDate=" + endDate
				+ ", releasedStatus=" + releasedStatus + "]";
	}
}
