package com.emailsys.controllers;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emailsys.dtos.EmailDTO;
import com.emailsys.models.EmailModel;
import com.emailsys.services.EmailService;

@RestController
public class EmailController {

	@Autowired
	EmailService emailService;
	
	@PostMapping("/sending-email/")
	public ResponseEntity<Object> sendingEmail(@RequestBody @Valid EmailDTO emailDTO){
		var emailModel =  new EmailModel();
		BeanUtils.copyProperties(emailDTO, emailModel);
		emailService.sendEmail(emailModel);
		return ResponseEntity.status(HttpStatus.OK).body(emailService.save(emailModel));
	}
}
