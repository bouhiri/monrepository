package org.sid.services;

import java.util.List;
import java.util.Set;

import org.sid.entities.Competence;
import org.sid.entities.Freelancer;
import org.sid.entities.Localisation;
import org.sid.entities.Offre;
import org.sid.entities.Particulier;

public interface ResearchService {
	public Set<Freelancer> searchBySkills(String domaine);

	public Set<Freelancer> searchByLocation(String ville);

	public Set<Freelancer> searchBySkillsAndLocation(String ville, String domaine);

	public List<Offre> offersList();

	public Set<Freelancer> listAllFreelancers();

	public List<Offre> offerslistByKeyword(String mot);

	public Freelancer findFreelancerById(Integer id);

	public Particulier findParticulierById(Integer id);

	public Freelancer findFreelancerByEmail(String email);

	public Particulier findParticularByEmail(String email);

	public void updateParticular(Particulier particulier);

	public void updateLocation(Localisation localisation);

	public void updateFreelancer(Freelancer freelancer);

	public void addOffer(Offre offre, Particulier particulier);

	public void deleteOfferById(Integer id);

	public Competence findSkillsByDomain(String domaine);

	public Competence findSkillsById(Integer id);

	public void addSkill(Competence competence, Freelancer freelancer);

	public void deleteSkills(Competence competence, Freelancer freelancer);
	
	public Localisation findLocalisationByVille(String ville);

}
