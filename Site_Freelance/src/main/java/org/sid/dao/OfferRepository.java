package org.sid.dao;

import java.util.List;

import org.sid.entities.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OfferRepository extends JpaRepository<Offre, Integer> {

	@Query("select o from Offre o where o.description =:xyz")
	public List<Offre> OffersListByKeyWord(@Param("xyz") String word);
}

