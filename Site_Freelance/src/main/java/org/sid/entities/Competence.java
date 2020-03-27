package org.sid.entities;
// Generated 19 mars 2020 16:20:42 by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "COMPETENCE")
public class Competence implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Integer idCompetence;
	private String domaine;
	@ManyToMany
	@JoinTable(name = "FreelancerCompetances", joinColumns = @JoinColumn(name = "idCompetence"),
			inverseJoinColumns = @JoinColumn(name = "idFreelancer"))
	private Set<Freelancer> freelancers = new HashSet<Freelancer>();
	
	public Set<Freelancer> getFreelancers() {
		return freelancers;
	}
	public void setFreelancers(Set<Freelancer> freelancers) {
		this.freelancers = freelancers;
	}
	
	public Competence() {
	}

	public Competence(Integer idCompetence) {
		this.idCompetence = idCompetence;
	}

	public Competence(String domaine) {
		this.domaine = domaine;
	}

	public Integer getIdCompetence() {
		return idCompetence;
	}

	public void setIdCompetence(Integer idCompetence) {
		this.idCompetence = idCompetence;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

}
