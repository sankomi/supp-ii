package sanko.suppii.web;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import sanko.suppii.service.EmailsService;
import sanko.suppii.web.dto.EmailsResponseDto;
import sanko.suppii.web.dto.EmailsListResponseDto;

@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
@RestController
public class EmailsApiController {

	private final EmailsService emailsService;

	@GetMapping("/")
	public List<EmailsListResponseDto> listEmails() {
		return emailsService.listEmails();
	}

	@GetMapping("/fetch")
	public boolean fetchMessages() {
		return emailsService.fetchEmails();
	}

	@GetMapping("/{id}")
	public EmailsResponseDto getEmailsById(@PathVariable Long id) {
		return emailsService.getEmailsById(id);
	}

	@GetMapping("/test")
	public String testTest() {
		return "test";
	}

}
