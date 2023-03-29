package sanko.suppii.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import sanko.suppii.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@GetMapping("/")
	public String countMessage() {
		return emailService.countMessages();
	}

}
