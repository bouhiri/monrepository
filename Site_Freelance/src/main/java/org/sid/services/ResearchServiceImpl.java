package org.sid.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.sid.dao.FreelancerRepository;

import org.sid.dao.LocationRepository;
import org.sid.dao.OfferRepository;

import org.sid.dao.ParticularRepository;
import org.sid.dao.SkillsRepository;
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
public class ResearchServiceImpl implements ResearchService {
	@Autowired
	private SkillsRepository skillsRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private FreelancerRepository freelancerRepository;
	@Autowired
	private ParticularRepository particularRepository;

	@Override
	public Set<Freelancer> searchBySkills(String domaine) {
		Optional<Competence> freelancers = skillsRepository.findByDomaine(domaine);
		if (freelancers.isPresent())
			return freelancers.get().getFreelancers();
		else
			return null;
	}

	@Override
	public Set<Freelancer> searchByLocation(String ville) {
		List<Localisation> all  = locationRepository.findAllByVille( ville);
		Set<Freelancer> freelancers = new HashSet<Freelancer>();
		if(all != null ) {
			for (Localisation localisation : all) {
				freelancers.addAll(localisation.getFreelancers());
			}
			return freelancers;
		} else {
			return null;
		}
	}

	@Override
	public List<Offre> offersList() {
		return offerRepository.findAll();
	}

	@Override
	public List<Offre> offerslistByKeyword(String word) {
		return offerRepository.OffersListByKeyWord("%" + word + "%");
	}

	@Override
	public Set<Freelancer> searchBySkillsAndLocation(String ville, String domaine) {
		Set<Freelancer> freelancerParCompetance = searchBySkills(domaine);
		Set<Freelancer> freelancerParLocalisation = searchByLocation(ville);

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
		Optional<Particulier> particulierOptional = particularRepository.findById(id);
		return particulierOptional.isPresent() ? particulierOptional.get() : null;
	}

	@Override
	public void addOffer(Offre offre, Particulier particulier) {
		Set<Offre> offres = particulier.getOffres();
		offres.add(offre);
		offre.setParticulier(particulier);
		particulier.setOffres(offres);
		offerRepository.save(offre);
		particularRepository.save(particulier);
	}

	@Override
	public void deleteOfferById(Integer id) {
		offerRepository.deleteById(id);
	}

	@Override
	public Freelancer findFreelancerByEmail(String email) {
		Optional<Freelancer> freelancerOptional = freelancerRepository.findByEmail(email);
		return freelancerOptional.isPresent() ? freelancerOptional.get() : null;
	}

	@Override
	public Particulier findParticularByEmail(String email) {
		Optional<Particulier> particulierOptional = particularRepository.findByEmail(email);
		return particulierOptional.isPresent() ? particulierOptional.get() : null;
	}

	public void updateParticular(Particulier particulier) {
		particularRepository.save(particulier);
	}

	public void updateFreelancer(Freelancer freelancer) {
		freelancerRepository.save(freelancer);
	}

	@Override
	public void updateLocation(Localisation localisation) {
		locationRepository.save(localisation);
	}

	@Override
	public Competence findSkillsByDomain(String domaine) {
		Optional<Competence> competanceOptional = skillsRepository.findByDomaine(domaine);
		return competanceOptional.isPresent() ? competanceOptional.get() : null;
	}

	@Override
	public void addSkill(Competence competence, Freelancer freelancer) {
		Set<Freelancer> f = competence.getFreelancers();
		Set<Competence> c = freelancer.getCompetences();
		c.add(competence);
		// f.add(freelancer);
		freelancer.setCompetences(c);
		// competence.setFreelancers(f);

		skillsRepository.save(competence);
		freelancerRepository.save(freelancer);
	}

	@Override
	public void deleteSkills(Competence competence, Freelancer freelancer) {
		Set<Competence> competences = freelancer.getCompetences();
		competences.remove(competence);
		freelancer.setCompetences(competences);
		freelancerRepository.save(freelancer);
	}

	@Override
	public Competence findSkillsById(Integer id) {
		Optional<Competence> competanceOptional = skillsRepository.findById(id);
		return competanceOptional.isPresent() ? competanceOptional.get() : null;
	}

	@Override
	public Localisation findLocalisationByVille(String ville) {
		Optional<Localisation> optional = locationRepository.findByVille(ville);
		return optional.isPresent() ? optional.get() : null;
	}

}
