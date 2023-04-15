package sanko.suppii.web;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*; //GetMapping, PathVariable
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

import sanko.suppii.service.EmailsService;
import sanko.suppii.config.auth.dto.SessionUser;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final EmailsService emailsService;
	private final HttpSession httpSession;

	@GetMapping("/")
	public String index(Model model) {
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		if (user != null) {
			model.addAttribute("userName", user.getName());
		}
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
