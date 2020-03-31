package org.sid.services;

import java.util.Optional;


import org.sid.dao.FreelancerRepository;
import org.sid.dao.ParticulierRepository;
import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ServiceAutentificationImpl  implements ServiceAutentification{
	@Autowired
	FreelancerRepository freelancerRepository;
	@Autowired
	ParticulierRepository particulierRepository;
	@Override
	public Particulier AuthentificationParticulier(String email, String password) {
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
	public Freelancer AuthentificationFreelancer(String email, String password) {
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
	public Freelancer inscriptiondufreelancer(Freelancer free) {
		return freelancerRepository.save(free);
		
	}
	@Override
	public Particulier inscriptionduparticulier(Particulier pr) {
		
		return particulierRepository.save(pr);
	}
	
	
}
