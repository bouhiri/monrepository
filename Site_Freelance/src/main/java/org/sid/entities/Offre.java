package org.sid.entities;
// Generated 19 mars 2020 16:20:42 by Hibernate Tools 4.0.1.Final

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OFFRE")
public class Offre implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idOffre;
	private String description;
	@ManyToOne
	@JoinColumn(name = "idParticulier", nullable = false)
	private Particulier particulier;

	public Particulier getParticulier() {
		return particulier;
	}

	public void setParticulier(Particulier particulier) {
		this.particulier = particulier;
	}

	public Offre() {
	}

	public Offre(String description) {
		super();
		this.description = description;
	}

	public Integer getIdOffre() {
		return idOffre;
	}

	public void setIdOffre(Integer idOffre) {
		this.idOffre = idOffre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
