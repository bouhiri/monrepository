package org.sid.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PARTICULIER")
public class Particulier implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idParticulier;
	private String nom;
	private String prenom;
	private String email;
	private Long mobile;
	private String password;
	private String adresse;
	@OneToMany(targetEntity = Avis.class, mappedBy = "particulier",fetch = FetchType.EAGER)
	private Set<Avis> avis = new HashSet<Avis>();
	@OneToMany(targetEntity = Evaluation.class, mappedBy = "particulier", fetch = FetchType.EAGER)
	private Set<Evaluation> evaluations = new HashSet<Evaluation>();
	@OneToMany(targetEntity = Offre.class, mappedBy = "particulier",fetch = FetchType.EAGER)
	private Set<Offre> offres = new HashSet<Offre>();
	
	public Particulier(String nom, String prenom, String email, Long mobile, String password, String adresse) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.adresse = adresse;
	}
	public Particulier(String nom, String prenom, String email, Long mobile, String password,
			String adresse, Set<Avis> avis, Set<Evaluation> evaluations, Set<Offre> offres) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.adresse = adresse;
		this.avis = avis;
		this.evaluations = evaluations;
		this.offres = offres;
	}
	public Particulier() {
		// TODO Auto-generated constructor stub
	}
	public Integer getIdParticulier() {
		return idParticulier;
	}
	public void setIdParticulier(Integer idParticulier) {
		this.idParticulier = idParticulier;
	}	
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
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public Set<Avis> getAvis() {
		return avis;
	}
	public void setAvis(Set<Avis> avis) {
		this.avis = avis;
	}
	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	public Set<Offre> getOffres() {
		return offres;
	}
	public void setOffres(Set<Offre> offres) {
		this.offres = offres;
	}
	

	
}
