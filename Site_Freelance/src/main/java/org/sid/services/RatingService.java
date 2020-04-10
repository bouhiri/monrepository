package org.sid.services;

import org.sid.entities.Avis;
import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;

public interface RatingService {
	public void AddOpinion(Avis avis, Freelancer freelancer, Particulier particulier);

	public void AddOpinion(Avis avis, Particulier particulier);
	
	public void deleteOpinionById(Integer id);

	public void GiveScore(Freelancer freelancer, Byte note);

	public Double RecalculateAverage(Freelancer freelancer);
	
	public void deleteRatingById(Integer id);


}
