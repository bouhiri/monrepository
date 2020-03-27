package org.sid.dao;

import org.sid.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

}
