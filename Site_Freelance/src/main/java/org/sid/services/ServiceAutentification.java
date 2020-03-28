package org.sid.services;

import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;

public interface ServiceAutentification {
	
	public Particulier AuthentificationParticulier(String email, String password);

	public Freelancer AuthentificationFreelancer(String email, String password);
}
