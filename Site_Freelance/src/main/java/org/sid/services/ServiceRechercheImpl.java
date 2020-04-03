package org.sid.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.sid.dao.CompetancesRepository;
import org.sid.dao.FreelancerRepository;
import org.sid.dao.LocalisationRepository;
import org.sid.dao.OffreRepository;
import org.sid.dao.ParticulierRepository;
import org.sid.entities.Competence;
import org.sid.entities.Freelancer;
import org.sid.entities.Localisation;
import org.sid.entities.Offre;
import org.sid.entities.Particulier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("serviceRecherche")
@Transactional
public class ServiceRechercheImpl implements ServiceRecherche {
	@Autowired
	private CompetancesRepository CompetencesRepository;
	@Autowired
	private LocalisationRepository LocalisationRepository;
	@Autowired
	private OffreRepository offreRepository;
	@Autowired
	private FreelancerRepository freelancerRepository;
	@Autowired
	private ParticulierRepository particulierRepository;

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
		return offreRepository.listOffreParMot("%" + mot + "%");
	}

	@Override
	public Set<Freelancer> chercherParLocalisationCompetance(String ville, String domaine) {
		Set<Freelancer> freelancerParCompetance = chercherParCompetences(domaine);
		Set<Freelancer> freelancerParLocalisation = chercherParLocalisation(ville);

		Set<Freelancer> outPut = new HashSet<Freelancer>();
		if (freelancerParCompetance != null) {
			for (Freelancer freelancer : freelancerParCompetance) {
				if (freelancer.getLocalisation().getVille().equals(ville)) {
					outPut.add(freelancer);
				}
			}
		}
		if (freelancerParLocalisation != null) {
			for (Freelancer freelancer : freelancerParLocalisation) {
				Set<Competence> comp = freelancer.getCompetences();
				List<String> domaines = new ArrayList<String>();
				for (Competence compt : comp) {
					domaines.add(compt.getDomaine());
				}
				if (domaines.contains(domaine)) {
					outPut.add(freelancer);
				}
			}
		}
		return outPut;
	}

	@Override
	public Set<Freelancer> listAllFreelancers() {
		Set<Freelancer> freelancer = new HashSet<Freelancer>();
		List<Freelancer> fr = freelancerRepository.findAll();
		for (Freelancer freelancer2 : fr) {
			freelancer.add(freelancer2);
		}
		return freelancer;
	}

	@Override
	public Freelancer findFreelancerById(Integer id) {
		Optional<Freelancer> freelancerOptional = freelancerRepository.findById(id);
		return freelancerOptional.isPresent() ? freelancerOptional.get() : null;
	}

	@Override
	public Particulier findParticulierById(Integer id) {
		Optional<Particulier> particulierOptional = particulierRepository.findById(id);
		return particulierOptional.isPresent() ? particulierOptional.get() : null;
	}

	@Override
	public void addOffre(Offre offre, Particulier particulier) {
		Set<Offre> offres = particulier.getOffres();
		offres.add(offre);
		offre.setParticulier(particulier);
		particulier.setOffres(offres);
		offreRepository.save(offre);
		particulierRepository.save(particulier);
	}

	@Override
	public void deleteOffreById(Integer id) {
		offreRepository.deleteById(id);
	}

	@Override
	public Freelancer findFreelancerByEmail(String email) {
		Optional<Freelancer> freelancerOptional = freelancerRepository.findByEmail(email);
		return freelancerOptional.isPresent() ? freelancerOptional.get() : null;
	}

	@Override
	public Particulier findParticulierByEmail(String email) {
		Optional<Particulier> particulierOptional = particulierRepository.findByEmail(email);
		return particulierOptional.isPresent() ? particulierOptional.get() : null;
	}

	public void updateParticulier(Particulier particulier) {
		particulierRepository.save(particulier);
	}

	public void updateFreelancer(Freelancer freelancer) {
		freelancerRepository.save(freelancer);
	}

	@Override
	public void updateLocalisation(Localisation localisation) {
		LocalisationRepository.save(localisation);
	}

	@Override
	public Competence findCompetanceByDomaine(String domaine) {
		Optional<Competence> competanceOptional = CompetencesRepository.findByDomaine(domaine);
		return competanceOptional.isPresent() ? competanceOptional.get() : null;
	}

	@Override
	public void addCompetance(Competence competence, Freelancer freelancer) {
		Set<Freelancer> f = competence.getFreelancers();
		Set<Competence> c = freelancer.getCompetences();
		c.add(competence);
		// f.add(freelancer);
		freelancer.setCompetences(c);
		// competence.setFreelancers(f);

		CompetencesRepository.save(competence);
		freelancerRepository.save(freelancer);
	}

	@Override
	public void deleteCompetance(Competence competence, Freelancer freelancer) {
		Set<Competence> competences =  freelancer.getCompetences();
		competences.remove(competence);
		freelancer.setCompetences(competences);
		freelancerRepository.save(freelancer);
	}

	@Override
	public Competence findCompetanceById(Integer id) {
		Optional<Competence> competanceOptional = CompetencesRepository.findById(id);
		return competanceOptional.isPresent() ? competanceOptional.get() : null;
	}

}
