package sanko.suppii.web;

import java.util.List;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import sanko.suppii.service.UserService;
import sanko.suppii.config.auth.dto.SessionUser;
import sanko.suppii.web.dto.UserListResponseDto;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserApiController {

	private final UserService userService;
	private final HttpSession httpSession;

	@GetMapping("/")
	public List<UserListResponseDto> listUser() {
		return userService.listUser();	
	}

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
