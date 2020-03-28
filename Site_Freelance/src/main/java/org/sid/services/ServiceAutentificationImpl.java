package org.sid.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ServiceAutentificationImpl implements ServiceAutentification {

	private ServiceAutentification ServiceAutentification;
	@Override
	public boolean AuthentificationParticulier(String email, String password) {
	 if(ServiceAutentification.AuthentificationParticulier(email, password))	
		return true;
	 else 
		 return false;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean AuthentificationFreelancer(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
