package sanko.suppii.controller;

import org.springframework.web.bind.annotation.*;

import sanko.suppii.service.EmailService;

@RestController
public class EmailController {

	private final EmailService emailService;

	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}

	@GetMapping("/")
	public String countMessage() {
		return emailService.countMessages();
	}

	@GetMapping("/test")
	public String testTest() {
		return "test";
	}

}
