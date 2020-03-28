package org.sid.services;
import org.sid.entities.Freelancer;
import org.sid.entities.Particulier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Async
	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);/***/
	}

	@Override
	public void resetPasswordMail(Freelancer freelancer, String validationCode) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(freelancer.getEmail());
		message.setSubject("Password Reset Request");
		message.setText("Your confermation code is " + validationCode);
		sendEmail(message);
	}

	@Override
	public void resetPasswordMail(Particulier particulier, String validationCode) {
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setTo(particulier.getEmail());
		passwordResetEmail.setSubject("Password Reset Request");
		passwordResetEmail.setText("Your confermation code is " + validationCode);
		sendEmail(passwordResetEmail);
	}
}