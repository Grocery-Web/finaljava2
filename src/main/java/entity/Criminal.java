package entity;

public class Criminal {
	private int id;
	private boolean catchStatus;
	private int personId;
	private int incidentId;
	private int rating;
	
	public Criminal() {};
	
	public Criminal(int id, boolean catchStatus, int personId, int incidentId, int rating) {
		this.id = id;
		this.catchStatus = catchStatus;
		this.personId = personId;
		this.incidentId = incidentId;
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isCatchStatus() {
		return catchStatus;
	}

	public void setCatchStatus(boolean catchStatus) {
		this.catchStatus = catchStatus;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(int incidentId) {
		this.incidentId = incidentId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Criminal [id=" + id + ", catchStatus=" + catchStatus + ", personId=" + personId + ", incidentId="
				+ incidentId + ", rating=" + rating + "]";
	}

	
	
	
}
