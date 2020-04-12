package org.sid.dao;

import java.util.List;
import java.util.Optional;

import org.sid.entities.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Localisation, Integer> {
	Optional<Localisation> findByVille(String ville);

	List<Localisation> findAllByVille(String ville);
}
