package org.sid.services;

import java.util.Set;

import org.sid.dao.FreelancerRepository;
import org.sid.dao.OpinionRepository;
import org.sid.dao.ParticularRepository;
import org.sid.dao.RatingRepository;
import org.sid.entities.Avis;
import org.sid.entities.Evaluation;
import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
	@Autowired
	public RatingRepository ratingRepository;
	@Autowired
	public FreelancerRepository freelancerRepository;
	@Autowired
	public OpinionRepository opinionRepository;
	@Autowired
	public ParticularRepository particulierRepository;

	@Override
	public void addOpinion(Avis avis, Freelancer freelancer, Particulier particulier) {
		Set<Avis> freelancerAvis = freelancer.getAvis();
		freelancerAvis.add(avis);
		freelancer.setAvis(freelancerAvis);
		avis.setFreelancer(freelancer);
		avis.setParticulier(particulier);
		opinionRepository.save(avis);
		freelancerRepository.save(freelancer);
	}

	@Override
	public void addOpinion(Avis avis, Particulier particulier) {
		opinionRepository.save(avis);
		Set<Avis> particulierAvis = particulier.getAvis();
		particulierAvis.add(avis);
		particulier.setAvis(particulierAvis);
		particulierRepository.save(particulier);
	}

	@Override
	public void giveScore(Freelancer freelancer, Byte note, Particulier particulier) {
		Evaluation evaluation = new Evaluation();
		evaluation.setNoteEvaluation(note);
		evaluation.setFreelancer(freelancer);
		evaluation.setParticulier(particulier);
		Set<Evaluation> evaluations = freelancer.getEvaluations();
		evaluations.add(evaluation);
		freelancer.setEvaluations(evaluations);
		
		ratingRepository.save(evaluation);
		freelancerRepository.save(freelancer);
	}

	@Override
	public Double recalculateAverage(Freelancer freelancer) {
		Set<Evaluation> evaluations = freelancer.getEvaluations();
		double avrg = 0.0;
		for (Evaluation evaluation : evaluations) {
			avrg += (double) evaluation.getNoteEvaluation();
		}
		return avrg / (double) evaluations.size();
	}

	@Override
	public void deleteOpinionById(Integer id) {
	opinionRepository.deleteById(id);

	}

	@Override
	public void deleteRatingById(Integer id) {
		ratingRepository.deleteById(id);

	}
}
