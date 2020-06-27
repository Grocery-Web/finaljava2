package entity;

import java.util.Date;

public class Complaint {
	private int id;
	private Date datetime;
	private String place;
	private String declarantName;
	private String detail;
	private boolean status;
	
	public Complaint() {}

	public Complaint(int id, Date datetime, String place, String declarantName, String detail, boolean status) {
		this.id = id;
		this.datetime = datetime;
		this.place = place;
		this.declarantName = declarantName;
		this.detail = detail;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDeclarantName() {
		return declarantName;
	}

	public void setDeclarantName(String declarantName) {
		this.declarantName = declarantName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Complaint [id=" + id + ", datetime=" + datetime + ", place=" + place + ", declarantName="
				+ declarantName + ", detail=" + detail + ", status=" + status + "]";
	}
}
