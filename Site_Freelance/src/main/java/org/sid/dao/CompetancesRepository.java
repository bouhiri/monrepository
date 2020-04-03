package org.sid.dao;

import java.util.Optional;

import org.sid.entities.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetancesRepository extends JpaRepository<Competence	, Integer>{
	Optional<Competence>findByDomaine(String domaine);

}
