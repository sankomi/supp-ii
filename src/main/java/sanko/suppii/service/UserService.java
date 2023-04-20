package sanko.suppii.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import sanko.suppii.domain.user.UserRepository;
import sanko.suppii.domain.user.User;
import sanko.suppii.domain.user.Role;
import sanko.suppii.config.auth.dto.SessionUser;
import sanko.suppii.web.dto.UserListResponseDto;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public List<UserListResponseDto> listUser() {
		return userRepository.findAll()
			.stream()
			.map(UserListResponseDto::new)
			.collect(Collectors.toList());
	}

	@Transactional
	public void beUser(SessionUser sessionUser) {
		changeRole(sessionUser, Role.USER);
	}

	@Transactional
	public void beGuest(SessionUser sessionUser) {
		changeRole(sessionUser, Role.GUEST);
	}

	public void changeRole(SessionUser sessionUser, Role role) {
		userRepository.findByEmail(sessionUser.getEmail())
			.ifPresent(user -> {
				user.changeRole(role);
			});
	}

}
