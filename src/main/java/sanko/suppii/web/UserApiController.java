package sanko.suppii.web;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import sanko.suppii.service.UserService;
import sanko.suppii.config.auth.dto.SessionUser;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserApiController {

	private final UserService userService;
	private final HttpSession httpSession;

	@PutMapping("/user")
	public boolean beUser() {
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		if (user != null) {
			userService.beUser(user);
		}
		return true;
	}

	@PutMapping("/guest")
	public boolean beGuest() {
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		if (user != null) {
			userService.beGuest(user);
		}
		return true;
	}

}
