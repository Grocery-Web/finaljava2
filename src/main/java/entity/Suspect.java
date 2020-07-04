package entity;

import entity.Person;

public class Suspect {
	private Person person;
	private String crimeType;
	
	public Suspect() {};
	public Suspect(Person person, String crimeType) {
		this.person = person;
		this.crimeType = crimeType;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getCrimeType() {
		return crimeType;
	}
	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}
	@Override
	public String toString() {
		return "Suspect [person=" + person + ", crimeType=" + crimeType + "]";
	}
	
	
	
}
