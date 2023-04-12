package sanko.suppii.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*; //GetMapping, PathVariable
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

import sanko.suppii.service.EmailsService;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final EmailsService emailsService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/emails/")
	public String listEmails(Model model) {
		model.addAttribute("emailsList", emailsService.listEmails());
		return "emails-list";
	}

	@GetMapping("/emails/{id}")
	public String showEmais(Model model, @PathVariable Long id) {
		model.addAttribute("id", id);
		model.addAttribute("emails", emailsService.getEmailsById(id));
		model.addAttribute("replys", emailsService.getReplyEmailsById(id));
		return "emails-show";
	}

}
