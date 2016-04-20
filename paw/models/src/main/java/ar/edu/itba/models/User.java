package ar.edu.itba.models;

public class User {
	
	private String username;
	private String password;
	private String mail;
	
	public User(String username, String password, String mail){
		this.username = username;
		this.password = password;
		this.mail = mail;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", mail=" + mail + "]";
	}
	
}
