package org.sid.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ParticulierForm {
	@NotNull
	@Size(min=2, max=30)
	private String nom;
	
	@NotNull
	@Size(min=2, max=30)
	private String prenom;

	@NotEmpty @Email
    private String email;
	
	@NotNull 
    private Long mobile;
	
	@NotNull @Size(min=8)
    private String password;
	
	@NotNull @Size(min=8)
    private String repassword;
	
	@NotNull
	private String adresse;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	@Override
	public String toString() {
		return "ParticulierForm [nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", mobile=" + mobile
				+ ", password=" + password + ", repassword=" + repassword + ", adresse=" + adresse + "]";
	}
	
}
