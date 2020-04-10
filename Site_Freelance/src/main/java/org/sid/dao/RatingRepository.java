package org.sid.dao;

import org.sid.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Evaluation, Integer> {

}
