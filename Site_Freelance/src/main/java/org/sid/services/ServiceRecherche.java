package org.sid.services;

import java.util.List;
import java.util.Set;

import org.sid.entities.Freelancer;
import org.sid.entities.Offre;

public interface ServiceRecherche {
	public Set<Freelancer> chercherParCompetences(String domaine);

	public Set<Freelancer> chercherParLocalisation(String ville);

	public Set<Freelancer> chercherParLocalisationCompetance(String ville, String domaine);

	public List<Offre> listAllOffre();

	public List<Offre> listOffreParMot(String mot);
}
