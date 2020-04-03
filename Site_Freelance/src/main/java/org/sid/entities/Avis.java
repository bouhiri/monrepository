package org.sid.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AVIS")
public class Avis implements java.io.Serializable {

	private static final long serialVersionUID = -4387006198904407133L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idAvis;
	private String description;
    @ManyToOne @JoinColumn(name="idFreelancer")
	private Freelancer freelancer;
    @ManyToOne @JoinColumn(name="idParticulier")
	private Particulier particulier;
	
	public Avis(String description, Freelancer freelancer, Particulier particulier) {
		super();
		this.description = description;
		this.freelancer = freelancer;
		this.particulier = particulier;
	}
	public Freelancer getFreelancer() {
		return freelancer;
	}
	public void setFreelancer(Freelancer freelancer) {
		this.freelancer = freelancer;
	}
	public Particulier getParticulier() {
		return particulier;
	}
	public void setParticulier(Particulier particulier) {
		this.particulier = particulier;
	}
	public Integer getIdAvis() {
		return idAvis;
	}
	public void setIdAvis(Integer idAvis) {
		this.idAvis = idAvis;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Avis(String description) {
		this.description = description;
	}
	public Avis() {
	}
}
