package ar.edu.itba.webapp.form;

import javax.validation.constraints.Size;

import ar.edu.itba.webapp.form.constraint.VerifyEquals;

@VerifyEquals(first="password", second="verifyPassword")
public class EditPasswordForm {


	@Size(min=6, max=100)
	private String password;
	
	@Size(min=6, max=100)
	private String verifyPassword;

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
}
