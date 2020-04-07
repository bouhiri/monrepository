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
@Table(name = "LOCALISATION")
public class Localisation implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idLocalisation;
	private String ville;
	private String quartier;
//	@ManyToMany
//	@JoinTable(name = "FreelancerLocalisation",
//	joinColumns = @JoinColumn(name = "idLocalisation"),
//	inverseJoinColumns = @JoinColumn(name = "idFreelancer"))
	@OneToMany(targetEntity = Freelancer.class, mappedBy = "localisation")
	private Set<Freelancer> freelancers = new HashSet<Freelancer>();

	public Set<Freelancer> getFreelancers() {
		return freelancers;
	}

	public void setFreelancers(Set<Freelancer> freelancers) {
		this.freelancers = freelancers;
	}

	public Localisation() {
	}

	public Localisation(Integer idLocalisation) {
		this.idLocalisation = idLocalisation;
	}

	public Localisation(String ville, String quartier) {
		this.ville = ville;
		this.quartier = quartier;
	}

	public Localisation(Freelancer freelancer, String ville, String quartier) {
		this.ville = ville;
		this.quartier = quartier;
	}

	public Integer getIdLocalisation() {
		return idLocalisation;
	}

	public void setIdLocalisation(Integer idLocalisation) {
		this.idLocalisation = idLocalisation;
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

}
