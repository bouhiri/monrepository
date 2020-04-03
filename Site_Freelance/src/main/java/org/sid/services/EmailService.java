package org.sid.services;

import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
	public void sendEmail(SimpleMailMessage email);

	public void resetPasswordMail(Freelancer freelancer, String validationCode);

	public void resetPasswordMail(Particulier particulier, String validationCode);

}