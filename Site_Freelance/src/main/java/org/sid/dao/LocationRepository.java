package org.sid.dao;

import java.util.Optional;

import org.sid.entities.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Localisation, Integer> {
	Optional<Localisation> findByCity(String ville);
}
