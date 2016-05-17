package ar.edu.itba.webapp.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import ar.edu.itba.webapp.form.constraint.UserMailFree;
import ar.edu.itba.webapp.form.constraint.UserUsernameFree;
import ar.edu.itba.webapp.form.constraint.VerifyEquals;

@VerifyEquals(first="password", second="verifyPassword")
public class UserForm {

	@Size(min=1, max=100)
	@UserUsernameFree
	private String user;

	@Size(min=6, max=100)
	private String password;
	
	@Size(min=6, max=100)
	private String verifyPassword;
	
	@Size(min=1, max=100)
	@Email
	@UserMailFree
	private String mail;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
