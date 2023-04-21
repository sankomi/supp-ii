package sanko.suppii.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import sanko.suppii.domain.user.UserRepository;
import sanko.suppii.domain.user.User;
import sanko.suppii.domain.user.Role;
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
	public boolean setUser(Long id) {
		return changeRole(id, Role.USER);
	}

	@Transactional
	public boolean setGuest(Long id) {
		return changeRole(id, Role.GUEST);
	}
	
	private boolean changeRole(Long id, Role role) {
		boolean[] changed = {false};
		userRepository.findById(id)
			.ifPresent(user -> {
				user.changeRole(role);
				changed[0] = true;
			});
		return changed[0];
	}

}
