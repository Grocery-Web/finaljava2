package entity;

import java.util.Date;

public class Incident{
	private int id;
	private Date datetime;
	private String place;
	private String detail;
	
	public Incident() {};

	public Incident(int id, Date datetime, String place, String detail) {
		this.id = id;
		this.datetime = datetime;
		this.place = place;
		this.detail = detail;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "Incident [id=" + id + ", datetime=" + datetime + ", place=" + place + ", detail=" + detail + "]";
	}
}
