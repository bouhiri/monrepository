package org.sid.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.sid.dao.CompetancesRepository;
import org.sid.dao.LocalisationRepository; 
import org.sid.dao.OffreRepository;
import org.sid.entities.Competence;
import org.sid.entities.Freelancer;
import org.sid.entities.Localisation;
import org.sid.entities.Offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ServiceRechercheImpl implements ServiceRecherche {
    @Autowired
	private CompetancesRepository CompetencesRepository;
    @Autowired
    private LocalisationRepository LocalisationRepository;
    @Autowired
    private OffreRepository offreRepository;

	@Override
	public Set<Freelancer> ChercherParCompetences(String domaine) {
		Optional<Competence> freelancers= CompetencesRepository.findByDomaine("");
		if(freelancers.isPresent())
		return freelancers.get().getFreelancers();
		else 
			return null;
	}

	@Override
	public Set<Freelancer> ChercherParLocalisation(String ville) {
		Optional<Localisation> freelancers= LocalisationRepository.findByVille("");
		if(freelancers.isPresent())
		return freelancers.get().getFreelancers();
		else 
			return null;
	}

	@Override
	public List<Offre> ListAllOffre() {
		return offreRepository.findAll();
	}

	@Override
	public List<Offre> ListOffreParMot(String mot) {
		return offreRepository.listOffreparmot(mot);
	}

	
	

}
