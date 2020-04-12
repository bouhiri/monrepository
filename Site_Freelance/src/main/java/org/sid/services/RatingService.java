package org.sid.services;

import org.sid.entities.Avis;
import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;

public interface RatingService {
	public void addOpinion(Avis avis, Freelancer freelancer, Particulier particulier);

	public void addOpinion(Avis avis, Particulier particulier);
	
	public void deleteOpinionById(Integer id);

	public void giveScore(Freelancer freelancer, Byte note,Particulier particulier );

	public Double recalculateAverage(Freelancer freelancer);
	
	public void deleteRatingById(Integer id);


}
