package ar.edu.itba.webapp.form;

import ar.edu.itba.webapp.form.constraint.VerifyEquals;

import javax.validation.constraints.Size;

@VerifyEquals(first = "password", second = "verifyPassword")
class EditPasswordForm {


    @Size(min = 6, max = 100)
    private String password;

    @Size(min = 6, max = 100)
    private String verifyPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(final String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}
