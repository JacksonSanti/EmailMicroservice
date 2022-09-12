package com.emailsys.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emailsys.dtos.EmailDTO;
import com.emailsys.models.EmailModel;
import com.emailsys.services.EmailService;

@RestController
public class EmailController {

	@Autowired
	EmailService emailService;
	
	@PostMapping("/sending_email/")
	public ResponseEntity<Object> sendingEmail(@RequestBody @Valid EmailDTO emailDTO){
		var emailModel =  new EmailModel();
		BeanUtils.copyProperties(emailDTO, emailModel);
		emailService.sendEmail(emailModel);
		return ResponseEntity.status(HttpStatus.OK).body(emailService.save(emailModel));
	}
	
	@GetMapping("/email_list/")
	public ResponseEntity<Object> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(emailService.findAll());
	}
	
	@GetMapping("/email_list/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value="id") @Valid Integer id){
		Optional<EmailModel> emailOptional =  emailService.findOne(id);
		if(!emailOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
		}
			return ResponseEntity.status(HttpStatus.OK).body(emailOptional.get());
	}
	
	@PutMapping("/email_list/{id}")
	public ResponseEntity<Object> updateEmail(@PathVariable(value="id")Integer id, @RequestBody @Valid EmailDTO emailDTO){
		Optional<EmailModel> emailOptional =  emailService.findOne(id);
		if(!emailOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
		}
			var emailModel = emailOptional.get();
		emailModel.setOwnerRef(emailDTO.getOwnerRef());
		emailModel.setEmailFrom(emailDTO.getEmailFrom());
		emailModel.setEmailTo(emailDTO.getEmailTo());
		emailModel.setSubject(emailDTO.getSubject());
		emailModel.setText(emailDTO.getText());
			emailService.sendEmail(emailModel);
			return ResponseEntity.status(HttpStatus.OK).body(emailService.save(emailModel));
	}
	
	@DeleteMapping("/email_list/{id}")
	public ResponseEntity<Object> deleteEmailOne(@PathVariable(value="id") @Valid Integer id){
		Optional<EmailModel> emailOptional =  emailService.findOne(id);
		if(emailOptional.isPresent()) {
			emailService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body("Email deleted successfully");
		}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
	}
	
	@DeleteMapping("/email_list/") 
	public ResponseEntity<Object> deleteEmailAll(){
		emailService.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body("All emails deleted successfully");
	}
	
	
}
