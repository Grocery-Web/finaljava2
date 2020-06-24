package entity;

public class Account {
	private String userID;
	private String fullName;
	private String email;
	private String password;
	private int privilege;
	private boolean status;
	
	public Account() {}

	public Account(String userID, String fullName, String email, String password, int privilege, boolean status) {
		this.userID = userID;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.privilege = privilege;
		this.status = status;
	}

	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getPrivilege() {
		return privilege;
	}
	
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
