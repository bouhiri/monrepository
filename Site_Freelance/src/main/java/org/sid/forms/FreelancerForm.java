package org.sid.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class FreelancerForm {


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
	@Size(min=30, max=150)
	private String diplome;
	@NotNull
	@Size(min=30, max=150)
	private String experience;
	@NotNull
	private String ville;
	@NotNull
	private String quartier;
	
	@NotNull
	private String domaine;
	@NotNull
	 private String presentation;
	
	public String getNom() {
		return nom;	
	
	}
		
	public String getRepassword() {
		return repassword;
		
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getQuartier() {
		return quartier;
	}
	public void setQuartier(String quartier) {
		this.quartier = quartier;
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
	public String getDiplome() {
		return diplome;
	}
	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}
	
	
	
	
	@Override
	public String toString() {
		return "FreelancerForm [nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", mobile=" + mobile
				+ ", password=" + password + ", repassword=" + repassword + ", diplome=" + diplome + ", experience="
				+ experience + ", ville=" + ville + ", quartier=" + quartier + "]";
	}
	
	}
	

	



