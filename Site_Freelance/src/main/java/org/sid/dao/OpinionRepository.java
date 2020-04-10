package org.sid.dao;

import org.sid.entities.Avis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpinionRepository extends JpaRepository<Avis, Integer> {

}
