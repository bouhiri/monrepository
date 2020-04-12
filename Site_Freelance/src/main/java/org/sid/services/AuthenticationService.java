package org.sid.services;

import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;

public interface AuthenticationService {

	public Particulier authenticationOfParticular(String email, String password);

	public Freelancer authenticationOfFreelancer(String email, String password);

	public Freelancer freelancerRegistration(Freelancer free);

	public Particulier particularRegistration(Particulier pr);
}
