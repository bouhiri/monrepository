package org.sid.services;

import java.util.List;
import java.util.Set;

import org.sid.entities.Freelancer;
import org.sid.entities.Offre;

public interface ServiceRecherche {
public Set<Freelancer> ChercherParCompetences(String domaine);
public Set<Freelancer> ChercherParLocalisation(String ville);
public List<Offre> ListAllOffre();
public List<Offre> ListOffreParMot(String mot);
}
