package org.sid.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class Login {

	@NotNull
	@Email
	private String mail;

	@NotNull
	private String password;

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
