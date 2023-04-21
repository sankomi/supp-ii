package sanko.suppii.web;

import java.util.List;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*; //PathVariable

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

	@PutMapping("/user/{id}")
	public boolean setUser(@PathVariable Long id) {
		return userService.setUser(id);
	}

	@PutMapping("/guest/{id}")
	public boolean setGuest(@PathVariable Long id) {
		return userService.setGuest(id);
	}

}
