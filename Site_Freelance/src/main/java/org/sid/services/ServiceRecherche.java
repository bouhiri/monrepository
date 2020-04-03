package org.sid.services;

import java.util.List;
import java.util.Set;

import org.sid.entities.Competence;
import org.sid.entities.Freelancer;
import org.sid.entities.Localisation;
import org.sid.entities.Offre;
import org.sid.entities.Particulier;

public interface ServiceRecherche {
	public Set<Freelancer> chercherParCompetences(String domaine);

	public Set<Freelancer> chercherParLocalisation(String ville);

	public Set<Freelancer> chercherParLocalisationCompetance(String ville, String domaine);

	public List<Offre> listAllOffre();

	public Set<Freelancer> listAllFreelancers();

	public List<Offre> listOffreParMot(String mot);

	public Freelancer findFreelancerById(Integer id);

	public Particulier findParticulierById(Integer id);

	public Freelancer findFreelancerByEmail(String email);

	public Particulier findParticulierByEmail(String email);

	public void updateParticulier(Particulier particulier);
	
	public void updateLocalisation(Localisation localisation);
	
	public void updateFreelancer(Freelancer freelancer);
	public void addOffre(Offre offre, Particulier particulier);

	public void deleteOffreById(Integer id);
	
	public Competence findCompetanceByDomaine(String domaine);
	
	public Competence findCompetanceById(Integer id);
	public void addCompetance(Competence competence, Freelancer freelancer);

	public void deleteCompetance(Competence competence, Freelancer freelancer);

}
