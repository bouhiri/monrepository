package org.sid.services;

import java.util.ArrayList;
import java.util.HashSet;
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
	public Set<Freelancer> chercherParCompetences(String domaine) {
		Optional<Competence> freelancers = CompetencesRepository.findByDomaine(domaine);
		if (freelancers.isPresent())
			return freelancers.get().getFreelancers();
		else
			return null;
	}

	@Override
	
	public Set<Freelancer> chercherParLocalisation(String ville) {
		Optional<Localisation> freelancers = LocalisationRepository.findByVille(ville);
		if (freelancers.isPresent())
			return freelancers.get().getFreelancers();
		else

			return null;
	}

	@Override
	public List<Offre> listAllOffre() {
		return offreRepository.findAll();
	}

	@Override
	public List<Offre> listOffreParMot(String mot) {
		return offreRepository.listOffreParMot(mot);
	}

	@Override
	public Set<Freelancer> chercherParLocalisationCompetance(String ville, String domaine) {
		Set<Freelancer> freelancerParCompetance  = chercherParCompetences(domaine);
		Set<Freelancer> freelancerParLocalisation = chercherParLocalisation(ville);
		
		Set<Freelancer>  outPut = new HashSet<Freelancer>();
		
		for (Freelancer freelancer : freelancerParCompetance) {
			if(freelancer.getLocalisation().getVille().equals(ville)) {
				outPut.add(freelancer);
			}
		}
		
		for (Freelancer freelancer : freelancerParLocalisation) {
			Set<Competence> comp = freelancer.getCompetences();
			List<String> domaines = new ArrayList<String>();
			for (Competence compt : comp) {
				domaines.add(compt.getDomaine());
			}
			if(domaines.contains(domaine)) {
				outPut.add(freelancer);
			}
		}
		
		return outPut;
	}

}
