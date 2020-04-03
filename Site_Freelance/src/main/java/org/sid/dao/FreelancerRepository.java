package org.sid.dao;

import java.util.Optional;

import org.sid.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FreelancerRepository extends JpaRepository<Freelancer, Integer> {

	Optional<Freelancer> findByEmail(String email);
	
	Optional<Freelancer> findByPassword(String password);
	

}