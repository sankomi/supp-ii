package sanko.suppii.config.auth.dto;

import java.io.Serializable;

import lombok.Getter;

import sanko.suppii.domain.user.User;

@Getter
public class SessionUser implements Serializable {

	private String name;
	private String email;

	public SessionUser(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
	}

}
