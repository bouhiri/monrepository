package org.sid.dao;

import java.util.Optional;

import org.sid.entities.Particulier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticularRepository extends JpaRepository<Particulier, Integer> {

	Optional<Particulier> findByEmail(String email);

	Optional<Particulier> findByPassword(String password);
}