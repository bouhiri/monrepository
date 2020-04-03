package org.sid.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FREELANCER")
public class Freelancer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idFreelancer;
	private String nom;
	private String prenom;
	private String email;
	private Long mobile;
	private String password;
	private String diplome;
	private String experience;
	@OneToMany(targetEntity = Avis.class, mappedBy = "freelancer")
	private Set<Avis> avis = new HashSet<Avis>();
	@OneToMany(targetEntity = Evaluation.class, mappedBy = "freelancer")
	private Set<Evaluation> evaluations = new HashSet<Evaluation>();
	@ManyToMany
	@JoinTable(name = "FreelancerCompetances", joinColumns = @JoinColumn(name = "idFreelancer"),
		inverseJoinColumns = @JoinColumn(name = "idCompetence"))
	private Set<Competence> competences = new HashSet<Competence>();
	@ManyToOne
	@JoinColumn(name = "idLocalisation")
	private Localisation localisation;
	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public Freelancer() {
	}

	public Freelancer(String nom, String prenom, String email, Long mobile, String password, String diplome,
			String experience) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.diplome = diplome;
		this.experience = experience;
	}

	public Integer getIdFreelancer() {
		return idFreelancer;
	}

	public void setIdFreelancer(Integer idFreelancer) {
		this.idFreelancer = idFreelancer;
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

	public Set<Avis> getAvis() {
		return avis;
	}

	public void setAvis(Set<Avis> avis) {
		this.avis = avis;
	}

	public Set<Competence> getCompetences() {
		return competences;
	}

	public void setCompetences(Set<Competence> competences) {
		this.competences = competences;
	}

	public Localisation getLocalisation() {
		return localisation;
	}

	public void setLocalisation(Localisation localisations) {
		this.localisation = localisations;
	}

	public Freelancer(String nom, String prenom, String email, Long mobile, String password, String diplome,
			String experience, Set<Avis> avis, Set<Competence> competences, Localisation localisations) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.diplome = diplome;
		this.experience = experience;
		this.avis = avis;
		this.competences = competences;
		this.localisation = localisations;
	}

}
