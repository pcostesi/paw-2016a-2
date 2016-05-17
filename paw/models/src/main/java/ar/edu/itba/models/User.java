package ar.edu.itba.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class User {

	@Id
	@Column(length = 100, nullable = false, unique = true)
	private String username;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(length = 100, nullable = false, unique = true)
	private String mail;
	
	public User() {
		// Just for hibernate
	}
	
	public User(String username, String password, String mail) {
		this.username = username;
		this.password = password;
		this.mail = mail;
	}

	public String username() {
		return username;
	}

	public String password() {
		return password;
	}

	public String mail() {
		return mail;
	}

}
