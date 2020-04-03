package org.sid.services;

import java.util.Set;

import org.sid.dao.AvisRepository;
import org.sid.dao.EvaluationRepository;
import org.sid.dao.FreelancerRepository;
import org.sid.dao.ParticulierRepository;
import org.sid.entities.Avis;
import org.sid.entities.Evaluation;
import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceEvaluationImpl implements ServiceEvaluation {
	@Autowired
	public EvaluationRepository evaluationRepository;
	@Autowired
	public FreelancerRepository freelancerRepository;
	@Autowired
	public AvisRepository avisRepository;
	@Autowired
	public ParticulierRepository particulierRepository;

	@Override
	public void AjouterAvis(Avis avis, Freelancer freelancer, Particulier particulier) {
		Set<Avis> freelancerAvis = freelancer.getAvis();
		freelancerAvis.add(avis);
		freelancer.setAvis(freelancerAvis);
		avis.setFreelancer(freelancer);
		avis.setParticulier(particulier);
		avisRepository.save(avis);
		freelancerRepository.save(freelancer);
	}

	@Override
	public void AjouterAvis(Avis avis, Particulier particulier) {
		avisRepository.save(avis);
		Set<Avis> particulierAvis = particulier.getAvis();
		particulierAvis.add(avis);
		particulier.setAvis(particulierAvis);
		particulierRepository.save(particulier);
	}

	@Override
	public void DonnerNote(Freelancer freelancer, Byte note) {
		Evaluation evaluation = new Evaluation();
		evaluation.setNoteEvaluation(note);
		evaluation.setFreelancer(freelancer);
		Set<Evaluation> evaluations = freelancer.getEvaluations();
		evaluations.add(evaluation);
		freelancer.setEvaluations(evaluations);
		
		evaluationRepository.save(evaluation);
		freelancerRepository.save(freelancer);
	}

	@Override
	public Double RecalculerMoyenne(Freelancer freelancer) {
		Set<Evaluation> evaluations = freelancer.getEvaluations();
		double avrg = 0.0;
		for (Evaluation evaluation : evaluations) {
			avrg += (double) evaluation.getNoteEvaluation();
		}
		return avrg / (double) evaluations.size();
	}

	@Override
	public void deleteAvisById(Integer id) {
		avisRepository.deleteById(id);

	}

	@Override
	public void deleteEvaluationById(Integer id) {
		evaluationRepository.deleteById(id);

	}

}
