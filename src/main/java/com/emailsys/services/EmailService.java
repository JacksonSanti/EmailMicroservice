package com.emailsys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.emailsys.enums.StatusEmail;
import com.emailsys.models.EmailModel;
import com.emailsys.repositories.EmailRepository;

@Service
public class EmailService {

	@Autowired
	EmailRepository emailRepository;
	
	@Autowired
	private JavaMailSender emailSender;

	public Object save(EmailModel emailModel) {
		return emailRepository.save(emailModel);
	}


	public void sendEmail(EmailModel emailModel) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(emailModel.getEmailFrom());
			message.setTo(emailModel.getEmailTo());
			message.setSubject(emailModel.getSubject());
			message.setText(emailModel.getText());
			emailSender.send(message);
			emailModel.setStatusEmail(StatusEmail.SENT);
		} catch(MailException e) {
			emailModel.setStatusEmail(StatusEmail.ERROR);
		}
	}
}
