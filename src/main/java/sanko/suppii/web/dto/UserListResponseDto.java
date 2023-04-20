package sanko.suppii.web.dto;

import lombok.Getter;

import sanko.suppii.domain.user.User;
import sanko.suppii.domain.user.Role;

@Getter
public class UserListResponseDto {

	private final Long id;
	private final String name;
	private final String email;
	private final Role role;

	public UserListResponseDto(User entity){
		this.id = entity.getId();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.role = entity.getRole();
	}

}
