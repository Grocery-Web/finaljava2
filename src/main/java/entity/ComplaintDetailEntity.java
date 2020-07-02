package entity;

public class ComplaintDetailEntity {
	private int id;
	private int personId;
	private int compId;
	private String crimeType;
	
	public ComplaintDetailEntity() {}

	public ComplaintDetailEntity(int id, int personId, int compId, String crimeType) {
		super();
		this.id = id;
		this.personId = personId;
		this.compId = compId;
		this.crimeType = crimeType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getCompId() {
		return compId;
	}

	public void setCompId(int compId) {
		this.compId = compId;
	}

	public String getCrimeType() {
		return crimeType;
	}

	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}

	@Override
	public String toString() {
		return "ComplaintDetail [id=" + id + ", personId=" + personId + ", compId=" + compId + ", crimeType="
				+ crimeType + "]";
	}
}
