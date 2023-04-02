package sanko.suppii.web;

import org.springframework.web.bind.annotation.*;

import sanko.suppii.service.EmailService;
import sanko.suppii.web.dto.EmailResponseDto;

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

	@GetMapping("/email/dto")
	public EmailResponseDto emailDto(@RequestParam("subject") String subject) {
		return new EmailResponseDto(subject);
	}

}
