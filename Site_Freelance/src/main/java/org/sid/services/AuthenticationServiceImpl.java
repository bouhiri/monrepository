package org.sid.services;

import java.util.Optional;


import org.sid.dao.FreelancerRepository;
import org.sid.dao.ParticularRepository;

import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class AuthenticationServiceImpl  implements AuthenticationService{
	@Autowired
	FreelancerRepository freelancerRepository;
	@Autowired
	ParticularRepository particulierRepository;
	@Override
	public Particulier authenticationOfParticular(String email, String password) {
		Optional<Particulier> particulierOptional = particulierRepository.findByEmail(email);
		Particulier particulier = null;
		if(particulierOptional.isPresent()) {
			particulier = particulierOptional.get();
			if(!particulier.getPassword().equals(password)) {
				particulier = null;
			}
		}
		return particulier;
	}
	@Override
	public Freelancer authenticationOfFreelancer(String email, String password) {
		Optional<Freelancer> freelancerOptional = freelancerRepository.findByEmail(email);
		Freelancer freelancer = null ;
		if(freelancerOptional.isPresent()) {
			freelancer = freelancerOptional.get();	
			if(!freelancer.getPassword().equals(password)) {
				freelancer = null;			
			}
		}
		return freelancer;
	}
	@Override
	public Freelancer freelancerRegistration(Freelancer free) {
		return freelancerRepository.save(free);
		
	}
	@Override
	public Particulier particularRegistration(Particulier pr) {
		
		return particulierRepository.save(pr);
	}
	
	
}
