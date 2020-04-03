package org.sid.services;

import org.sid.entities.Avis;
import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;

public interface ServiceEvaluation {
	public void AjouterAvis(Avis avis, Freelancer freelancer, Particulier particulier);

	public void AjouterAvis(Avis avis, Particulier particulier);
	
	public void deleteAvisById(Integer id);

	public void DonnerNote(Freelancer freelancer, Byte note);

	public Double RecalculerMoyenne(Freelancer freelancer);
	
	public void deleteEvaluationById(Integer id);
}
