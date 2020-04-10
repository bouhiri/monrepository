package org.sid.services;

import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;

public interface AuthenticationService {

	
	
	public Particulier AuthenticationOfParticular(String email, String password);

	public Freelancer AuthenticationOfFreelancer(String email, String password);
	public Freelancer FreelancerRegistration(Freelancer free);
	public Particulier ParticularRegistration(Particulier pr);
}
